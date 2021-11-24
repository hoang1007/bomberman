package uet.gryffindor.autopilot;

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
}