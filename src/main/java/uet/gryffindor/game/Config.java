package uet.gryffindor.game;

public class Config {
    private int bomberId = 1;

    public int getBomberId() {
        return this.bomberId;
    }

    public Config setBomberId(int id) {
        this.bomberId = id;
        return this;
    }
}
