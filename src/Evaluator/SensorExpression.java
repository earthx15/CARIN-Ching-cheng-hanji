package Evaluator;

import Entity.Host;
import Game.CellsField;

import java.util.Map;

public class SensorExpression implements Expression {
    private String command;
    private Direction dir;
    private Host unit;
    private CellsField cf;

    public SensorExpression(String command, Host unit, CellsField cf) {
        this.command = command;
        this.unit = unit;
        this.cf = cf;
    }

    public SensorExpression(String command, Direction dir, Host unit, CellsField cf) {
        this.command = command;
        this.dir = dir;
        this.unit = unit;
        this.cf = cf;
    }

    @Override
    public int eval(Map<String, Integer> strg) {
        return distance;
    }
}
