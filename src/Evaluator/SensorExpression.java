package Evaluator;

import Entity.Host;
import Game.CellsField;

import java.util.Map;

public class SensorExpression implements Expression {
    private String command;
    private Direction dir;
    private Host unit;
    private CellsField cf;

    public SensorExpression(int dt) {
        this.distance = dt;
    }

    @Override
    public int eval(Map<String, Integer> strg) {
        return distance;
    }
}
