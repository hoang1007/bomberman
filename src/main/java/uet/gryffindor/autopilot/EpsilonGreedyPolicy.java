package uet.gryffindor.autopilot;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import uet.gryffindor.game.Game;
import uet.gryffindor.game.engine.BaseService;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.statics.items.Item;
import uet.gryffindor.util.OtherUtils;
import uet.gryffindor.util.Pair;

public class EpsilonGreedyPolicy extends BaseService {
  private HashMap<String, double[]> qTable = new HashMap<>();
  private double epsilon = 0.6;
  private double lr = 0.1;
  private double discountFactor = 0.9;
  private Random random;

  private GameEnvironment env;
  private Bomber agent;

  public EpsilonGreedyPolicy(boolean trainContinue) {
    if (trainContinue) {
      try (FileReader reader = new FileReader(new File("src/main/resources/uet/gryffindor/table.json"))) {
        qTable = new Gson().fromJson(reader, new TypeToken<HashMap<String, double[]>>(){}.getType());
      } catch (IOException e) {
        e.printStackTrace();
      }
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
    try (FileWriter wr = new FileWriter(new File("src/main/resources/uet/gryffindor/table.json"))) {
      new Gson().toJson(qTable, wr);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private GameAction getAction(GameState state) {
    if (random.nextDouble() < epsilon) {
      Pair<Integer, Double> maxQVal = OtherUtils.max(OtherUtils.toObject(qTable.get(state.toString())));

      return GameAction.valueOf(maxQVal.first);
    } else {
      return GameAction.valueOf(random.nextInt(GameAction.N_ACTIONS));
    }
  }

  private void reTrain() {
    env.restart();
    agent = env.getAgent();
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

      futureQVal = OtherUtils.max(OtherUtils.toObject(qAction));
    } else {
      qTable.put(newState.toString(), new double[GameAction.N_ACTIONS]);
      futureQVal = Pair.of(0, 0.0);
    }
    
    double temporalDiff = reward + discountFactor * futureQVal.second - qOld;

    double qNew = qOld + lr * temporalDiff;

    qTable.get(oldState.toString())[action.ordinal()] = qNew;
  }

  @Override
  public void update() {
    if (env.getObject(Item.class).isEmpty()) {
      reTrain();
    } else {
      GameState state = GameState.getStateAsArray(agent.position, env);
      GameAction action = getAction(state);

      agent.autopilot(action);

      int reward = GameReward.getReward(action, env);
      GameState newState = GameState.getStateAsArray(agent.position, env);

      trainModel(state, newState, action, reward);
    }
  } 
}
