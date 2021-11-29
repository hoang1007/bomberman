package uet.gryffindor.decision;

import uet.gryffindor.util.BooleanFunction;

public class ConditionNode extends Node {
    public final Node negNode; // node tiếp theo nếu điều kiện sai
    public final Node posNode; // node tiếp theo nếu điều kiện đúng
    public final BooleanFunction condition;
    private String conditionName;

    private ConditionNode(Node neg, Node pos, String name, BooleanFunction condition) {
        this.negNode = neg;
        this.posNode = pos;
        this.conditionName = name;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return this.conditionName;
    }

    public static class Builder {
        private Node negNode;
        private Node posNode;
        private String conditionName = "Unknown condition";
        private BooleanFunction condition;

        public Builder setPositive(Node node) {
            this.posNode = node;
            return this;
        }

        public Builder setNegative(Node node) {
            this.negNode = node;
            return this;
        }

        public Builder setConditionName(String condition) {
            this.conditionName = condition;
            return this;
        }

        public Builder setCondition(BooleanFunction condition) {
            this.condition =  condition;
            return this;
        }

        public ConditionNode build() {
            if (negNode == null || posNode == null || condition == null) {
                throw new IllegalArgumentException("Arguments must not be null");
            }

            return new ConditionNode(negNode, posNode, conditionName, condition);
        }
    }
}