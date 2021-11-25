package uet.gryffindor.autopilot;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import uet.gryffindor.game.Game;
import uet.gryffindor.game.engine.BaseService;

public class EpsilonGreedyPolicy extends BaseService {
    // Double Deep Q-Learning
    private MultiLayerNetwork model1;
    private MultiLayerNetwork model2;
    private MemoryBatch memoryBatch;
    private GameEnvironment env;
    private Random random;

    private final int N_HIDDENS = 128;
    private final int N_LAYERS = 3;

    /**
     * Initialize deep network.
     * 
     * @param update restore previous version
     */
    public EpsilonGreedyPolicy(boolean update) {
        if (update) {
            try {
                model1 = MultiLayerNetwork.load(new File("src/main/resources/uet/gryffindor/model1.pth"), true);

                model2 = MultiLayerNetwork.load(new File("src/main/resources/uet/gryffindor/model2.pth"), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MultiLayerConfiguration config = new NeuralNetConfiguration.Builder().activation(Activation.RELU)
                    .weightInit(WeightInit.XAVIER).updater(new Adam()).list()
                    .layer(new DenseLayer.Builder().nIn(GameState.N_FEATURES).nOut(N_HIDDENS).build())
                    .layer(new DenseLayer.Builder().nIn(N_HIDDENS).nOut(N_HIDDENS).build())
                    .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE).nOut(GameAction.N_ACTIONS).build())
                    .build();

            model1 = new MultiLayerNetwork(config);
            model2 = new MultiLayerNetwork(config);
            model1.init();
            model2.init();
        }

        memoryBatch = new MemoryBatch();
        random = new Random();
    }

    public void initialize(Game game) {
        env = new GameEnvironment(game);
    }

    public void save() {
        try {
            model1.save(new File("src/main/resources/uet/gryffindor/model1.pth"), true);
            model2.save(new File("src/main/resources/uet/gryffindor/model2.pth"), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double bellmanEqualtion(double reward, GameState nextState) {
        final double gamma = 0.7;
        INDArray qvaluesOfModel1 = model1.feedForward(nextState.getNdArray()).get(N_LAYERS);
        INDArray qvaluesOfModel2 = model2.feedForward(nextState.getNdArray()).get(N_LAYERS);

        double maxQOfModel1 = qvaluesOfModel1.max().getDouble(0);
        double maxQOfModel2 = qvaluesOfModel2.max().getDouble(0);

        return reward + gamma * (maxQOfModel1 < maxQOfModel2 ? maxQOfModel1 : maxQOfModel2);
    }

    private void updatePolicy() {
        model1.fit(memoryBatch.features, memoryBatch.labels1);
        model2.fit(memoryBatch.features, memoryBatch.labels2);
        memoryBatch.clear();
    }

    private GameAction getAction(GameState state) {
        final double epsilon = 0.6;
        if (random.nextDouble() < epsilon) {
            INDArray qValues = model1.feedForward(state.getNdArray()).get(N_LAYERS);
            return GameAction.valueOf(qValues.argMax().getInt());
        } else {
            return GameAction.valueOf(random.nextInt(GameAction.N_ACTIONS));
        }
    }

    //////// Update ////////
    // Order of game services is: autopilot -> update game object -> collider update -> autopilot
    // But rewards only can be updated when collider update is called
    // Thus the update method is divided into two parts
    // 1. Update previous parameters
    // 2. Predict next action and call autopilot

    private GameAction preAction = null;
    private GameState preState = null;

    @Override
    public void update() {
        GameState state = GameState.getState(env.getAgent().position, env);

        if (preAction != null) {
            double reward = GameReward.getReward(preAction, env);

            double qExpected = bellmanEqualtion(reward, state);

            INDArray predict1 = model1.feedForward(preState.getNdArray()).get(N_LAYERS);
            INDArray predict2 = model2.feedForward(preState.getNdArray()).get(N_LAYERS);

            memoryBatch.add(state, preAction, predict1, predict2, qExpected);

            if (memoryBatch.isFull()) {
                updatePolicy();
            }
        }

        if (env.getAgent().isDeath() || env.getAgent().isWon()) {
            preAction = null;
            preState = null;
            env.restart();
        } else {
            GameAction action = getAction(state);
            env.getAgent().autopilot(action);
            preState = state;
            preAction = action;
        }
    }
}