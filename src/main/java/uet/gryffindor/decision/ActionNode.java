package uet.gryffindor.decision;

public class ActionNode<T extends Enum<T>> extends Node {
    private T action;

    public ActionNode<T> setAction(T action) {
        this.action = action;
        return this;
    }

    public T getAction() {
        if (action == null) {
            throw new IllegalArgumentException("Action must not be null");
        }
        
        return this.action;
    }
}
