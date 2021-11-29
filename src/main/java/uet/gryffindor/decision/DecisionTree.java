package uet.gryffindor.decision;

public class DecisionTree {
    private Node root;

    public DecisionTree(Node root) {
        this.root = root;
    }

    private ActionNode<?> forward(Node node) {
        if (node instanceof ConditionNode) {
            ConditionNode cn = (ConditionNode) node;
            if (cn.condition.invoke()) {
                return forward(cn.posNode);
            } else {
                return forward(cn.negNode);
            }
        } else if (node instanceof ActionNode) {
            return (ActionNode<?>) node;
        }

        throw new IllegalArgumentException("Unexpected node");
    }

    public ActionNode<?> forward() {
        return forward(root);
    }
}
