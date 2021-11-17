package uet.gryffindor.autopilot;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import com.google.gson.Gson;

import org.apache.commons.lang3.ArrayUtils;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.shade.guava.reflect.TypeToken;

import uet.gryffindor.game.Game;
import uet.gryffindor.game.engine.BaseService;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.util.OtherUtils;
import uet.gryffindor.util.Pair;

public class EpsilonGreedyPolicy extends BaseService {
  private MultiLayerConfiguration config;
  private MultiLayerNetwork model;
  private BatchHistory batchHistory = new BatchHistory(1000);

  private HashMap<String, double[]> qTable = new HashMap<>();
  private double epsilon = 1;
  private double lr = 0.1;
  private double discountFactor = 0.9;
  private Random random;

  private GameEnvironment env;
  private Bomber agent;

  public EpsilonGreedyPolicy(boolean trainContinue) {
    if (trainContinue) {
      try {
        model = MultiLayerNetwork.load(new File("src/main/resources/uet/gryffindor/model.pth"), true);

        FileReader reader = new FileReader(new File("src/main/resources/uet/gryffindor/table.json"));
        qTable = new Gson().fromJson(reader, new TypeToken<HashMap<String, double[]>>(){}.getType());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      final int HIDDEN_UNITS = 256;

      config = new NeuralNetConfiguration.Builder()
              .activation(Activation.RELU)
              .weightInit(WeightInit.XAVIER)
              .updater(new Adam())
              .l2(1e-4)
              .list()
              .layer(new DenseLayer.Builder().nIn(GameState.N_FEATURES).nOut(HIDDEN_UNITS).build())
              .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                                            .activation(Activation.SOFTMAX)
                                            .nIn(HIDDEN_UNITS).nOut(GameAction.N_ACTIONS).build())
              .build();


      model = new MultiLayerNetwork(config);
      model.init();
    }

    random = new Random();
    random.setSeed(System.currentTimeMillis());
  }

  public void initialize(Game game) {
    env = new GameEnvironment(game);
    agent = env.getAgent();
    agent.setAuto(true);
  }  

  public void save() {
    try {
      model.save(new File("src/main/resources/uet/gryffindor/model.pth"));

      FileWriter wr = new FileWriter(new File("src/main/resources/uet/gryffindor/table.json"));
      new Gson().toJson(qTable, wr);
      wr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private GameAction getAction(GameState state) {
    if (random.nextDouble() < epsilon) {
      // int[] predict = model.predict(state.data);
      Pair<Integer, Double> maxQVal = OtherUtils.max(ArrayUtils.toObject(qTable.get(state.toString())));

      return GameAction.valueOf(maxQVal.first);
    } else {
      return GameAction.valueOf(random.nextInt(GameAction.N_ACTIONS));
    }
  }

  private void reTrain() {
    env.restart();
    agent = env.getAgent();

    if (batchHistory.size() > 0) {
      model.fit(batchHistory.toDataSet());
      batchHistory = new BatchHistory(1000);
    }
  }

  private void trainModel(GameState oldState, GameState newState, GameAction action, int reward) {
    double qOld = 0;
    if (qTable.containsKey(oldState.toString())) {
      qOld = qTable.get(oldState.toString())[action.ordinal()];
    } else {
      qTable.put(oldState.toString(), new double[GameAction.N_ACTIONS]);
    }

    Pair<Integer, Double> futureQVal = null; 
    if (qTable.containsKey(newState.toString())) {
      double[] qAction = qTable.get(newState.toString());

      futureQVal = OtherUtils.max(ArrayUtils.toObject(qAction));
    } else {
      qTable.put(newState.toString(), new double[GameAction.N_ACTIONS]);
      futureQVal = Pair.of(0, 0.0);
    }
    
    double temporalDiff = reward + discountFactor * futureQVal.second - qOld;

    double qNew = qOld + lr * temporalDiff;

    qTable.get(oldState.toString())[action.ordinal()] = qNew;

    Pair<Integer, Double> expectedVal = OtherUtils.max(ArrayUtils.toObject(qTable.get(oldState.toString())));
    GameAction expectedAction = GameAction.valueOf(expectedVal.first);
    // model.fit(oldState.data, expectedAction.toNdArray());
    System.out.println("Predicted: " + action + " Expected: " + expectedAction);   

    batchHistory.add(oldState.data, expectedAction.toNdArray());
  }

  @Override
  public void update() {
    GameState state = GameState.getStateAsArray(agent.position, env);
    GameAction action = getAction(state);

    agent.autopilot(action);

    int reward = GameReward.getReward(action, env);
    GameState newState = GameState.getStateAsArray(agent.position, env);

    trainModel(state, newState, action, reward);
  }
}
