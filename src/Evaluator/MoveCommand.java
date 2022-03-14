package Evaluator;

import Entity.Host;
import Game.CellsField;

import java.util.Map;

public class MoveCommand implements Statement {
    public static StringBuilder testOut = new StringBuilder();

    private Direction direction;

    public MoveCommand(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void eval(Map<String, Integer> strg) {
        System.out.println("Move : " + direction.eval(strg));
        testOut.append("Move : ").append(direction.eval(strg)).append("\n");
    }
}
