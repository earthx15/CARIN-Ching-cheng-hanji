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
    public void eval(Map<String, Integer> binding, Host unit, CellsField cf) throws EvalError {
        System.out.println("Move : " + direction.eval(binding));
        testOut.append("Move : ").append(direction.eval(binding)).append("\n");

        if(unit == null) throw new EvalError("Missing Host unit");

        int dst_i = unit.getPosition()[0];
        int dst_j = unit.getPosition()[1];

        if(direction.eval(binding).equals("up")){
            dst_i -= 1;
        }else if(direction.eval(binding).equals("upright")){
            dst_i -= 1;
            dst_j += 1;
        }else if(direction.eval(binding).equals("right")){
            dst_j += 1;
        }else if(direction.eval(binding).equals("downright")){
            dst_i += 1;
            dst_j += 1;
        }else if(direction.eval(binding).equals("down")){
            dst_i += 1;
        }else if(direction.eval(binding).equals("downleft")){
            dst_i += 1;
            dst_j -= 1;
        }else if(direction.eval(binding).equals("left")){
            dst_j -= 1;
        }else if(direction.eval(binding).equals("upleft")){
            dst_i -= 1;
            dst_j -= 1;
        }
        if(cf.checkUnit(dst_i, dst_j).equals("empty") && !cf.checkUnit(dst_i, dst_j).equals("out"))
            cf.moveUnit(dst_i, dst_j, unit);
    }
}
