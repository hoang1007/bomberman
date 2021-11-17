package uet.gryffindor.autopilot;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;

public class BatchHistory {
  private int batchSize;
  private INDArray samples;
  private INDArray labels;
  private int currentSize = 0;

  public BatchHistory(int batchSize) {
    this.batchSize = batchSize;

  }

  public void add(INDArray sample, INDArray label) {
    if (currentSize == 0) {
      samples = sample;
      labels = label;
      currentSize = 1;
    } else if (currentSize < batchSize) {
      samples.addRowVector(sample);
      labels.addRowVector(label);
      currentSize++;
    }
  }

  public DataSet toDataSet() {
    return new DataSet(samples, labels);
  }

  public int size() {
    return currentSize;
  }
}
