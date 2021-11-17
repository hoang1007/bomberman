package uet.gryffindor.autopilot;

import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public enum GameAction {
  UP, DOWN, LEFT, RIGHT, BOMB, STAND;

  public static final int N_ACTIONS = 6;

  public static GameAction valueOf(int value) {
    switch (value) {
      case 0: return UP;
      case 1: return DOWN;
      case 2: return LEFT;
      case 3: return RIGHT;
      case 4: return BOMB;
      case 5: return STAND;
    }

    return null;
  }

  public INDArray toNdArray() {
    int[] arr = new int[N_ACTIONS];

    arr[this.ordinal()] = 1;

    return Nd4j.create(arr, new long[]{1, arr.length}, DataType.INT16);
  }
}