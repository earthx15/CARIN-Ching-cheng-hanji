package Evaluator;

import Entity.Host;
import Game.CellsField;

import java.util.Map;

public class AttackCommand implements Statement {
    public static StringBuilder testOut = new StringBuilder();

    private Direction direction;

    public AttackCommand(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void eval(Map<String, Integer> strg) {
        System.out.println("Attack : " + direction.eval(strg));
        testOut.append("Attack : ").append(direction.eval(strg)).append("\n");
    }
}
