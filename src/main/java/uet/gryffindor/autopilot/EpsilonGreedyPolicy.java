package uet.gryffindor.autopilot;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class EpsilonGreedyPolicy {
  MultiLayerConfiguration config;
  MultiLayerNetwork model;

  public EpsilonGreedyPolicy() {
    config =
        new NeuralNetConfiguration.Builder()
            .activation(Activation.RELU)
            .weightInit(WeightInit.XAVIER)
            .updater(new Sgd(0.1))
            .l2(1e-4)
            .list()
            .layer(new DenseLayer.Builder().nIn(GameState.N_FEATURES).nOut(6).build())
            .layer(
                new OutputLayer.Builder(LossFunctions.LossFunction.MEAN_SQUARED_LOGARITHMIC_ERROR)
                    .activation(Activation.SOFTMAX)
                    .nIn(6)
                    .nOut(GameAction.N_ACTIONS)
                    .build())
            .build();

    model = new MultiLayerNetwork(config);
    model.init();
  }
}
