package uet.gryffindor.autopilot;

import javax.annotation.Nonnull;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class MemoryBatch {
  private static int trainTime = 0;
  public static int batchSize = 64;

  public INDArray features = null;
  public INDArray labels1 = null;
  public INDArray labels2 = null;
  private int size = 0;

  /**
   * Add memory.
   * 
   * @param state  state
   * @param action action
   * @param qvalue expected Q value
   */
  public void add(@Nonnull GameState state, @Nonnull GameAction action,
                @Nonnull INDArray predict1, @Nonnull INDArray predict2, @Nonnull double qvalue) {
      INDArray feature = state.getNdArray();

      double[] raw = new double[GameAction.N_ACTIONS];
      raw[action.ordinal()] = qvalue;
      INDArray label = Nd4j.create(raw);
      assert label.shape().equals(predict1.shape()) : "Label should be same shape as predict";

      this.cat(feature, predict1.add(label), predict2.add(label));
  }

  public boolean isFull() {
    return size >= batchSize;
  }

  public void clear() {
    features = null;
    labels1 = null;
    labels2 = null;
    size = 0;
    System.out.println("Time train: " + trainTime++);
  }

  private void cat(INDArray feature, INDArray label1, INDArray label2) {
    if (size == 0) {
      features = feature;
      labels1 = label1;
      labels2 = label2;
    } else {
      assert features.shape()[1] == feature.shape()[0] : "Feature has wrong shape";
      assert labels1.shape()[1] == label1.shape()[0] : "Label has wrong shape";

      features.addRowVector(feature);
      labels1.addRowVector(label1);
      labels2.addRowVector(label2);
    }

    size++;
  }
}