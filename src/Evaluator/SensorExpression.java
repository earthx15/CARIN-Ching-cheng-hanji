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
    public int eval(Map<String, Integer> binding, Host unit) throws EvalError {
        if(command.equals("virus")){
            int this_i = unit.getPosition()[0];
            int this_j = unit.getPosition()[1];

            int k = 1;
            int outOfBounds = 0;
            boolean[] isOut = new boolean[8];
            while (true){
                if(outOfBounds == 8) return 0;

                //up
                if (!isOut[0]) {
                    if (cf.checkUnit(this_i - k, this_j).equals("out")) {
                        outOfBounds += 1;
                        isOut[0] = true;
                    } else if (cf.checkUnit(this_i - k, this_j).equals("Virus"))
                        return 10 * k + 1;
                }

                //upright
                if (!isOut[1]) {
                    if (cf.checkUnit(this_i - k, this_j + k).equals("out")) {
                        outOfBounds += 1;
                        isOut[1] = true;
                    } else if (cf.checkUnit(this_i - k, this_j + k).equals("Virus"))
                        return 10 * k + 2;
                }

                //right
                if (!isOut[2]) {
                    if (cf.checkUnit(this_i, this_j + k).equals("out")) {
                        outOfBounds += 1;
                        isOut[2] = true;
                    } else if (cf.checkUnit(this_i, this_j + k).equals("Virus"))
                        return 10 * k + 3;
                }

                //downleft
                if (!isOut[3]) {
                    if (cf.checkUnit(this_i + k, this_j + k).equals("out")) {
                        outOfBounds += 1;
                        isOut[3] = true;
                    } else if (cf.checkUnit(this_i + k, this_j + k).equals("Virus"))
                        return 10 * k + 4;
                }

                //down
                if (!isOut[4]) {
                    if (cf.checkUnit(this_i + k, this_j).equals("out")) {
                        outOfBounds += 1;
                        isOut[4] = true;
                    } else if (cf.checkUnit(this_i + k, this_j).equals("Virus"))
                        return 10 * k + 5;
                }

                //downleft
                if (!isOut[5]) {
                    if (cf.checkUnit(this_i + k, this_j - k).equals("out")) {
                        outOfBounds += 1;
                        isOut[5] = true;
                    } else if (cf.checkUnit(this_i + k, this_j - k).equals("Virus"))
                        return 10 * k + 6;
                }

                //left
                if (!isOut[6]) {
                    if (cf.checkUnit(this_i, this_j - k).equals("out")) {
                        outOfBounds += 1;
                        isOut[6] = true;
                    } else if (cf.checkUnit(this_i, this_j - k).equals("Virus"))
                        return 10 * k + 7;
                }

                //upleft
                if (!isOut[7]) {
                    if (cf.checkUnit(this_i - k, this_j - k).equals("out")) {
                        outOfBounds += 1;
                        isOut[7] = true;
                    } else if (cf.checkUnit(this_i - k, this_j - k).equals("Virus"))
                        return 10 * k + 8;
                }
                k++;
            }
        }else if(command.equals("antibody")){
            int this_i = unit.getPosition()[0];
            int this_j = unit.getPosition()[1];

            int k = 1;
            int outOfBounds = 0;
            boolean[] isOut = new boolean[8];
            while (true){
                if(outOfBounds == 8) return 0;

                //up
                if (!isOut[0]) {
                    if (cf.checkUnit(this_i - k, this_j).equals("out")) {
                        outOfBounds += 1;
                        isOut[0] = true;
                    } else if (cf.checkUnit(this_i - k, this_j).equals("Antibody"))
                        return 10 * k + 1;
                }

                //upright
                if (!isOut[1]) {
                    if (cf.checkUnit(this_i - k, this_j + k).equals("out")) {
                        outOfBounds += 1;
                        isOut[1] = true;
                    } else if (cf.checkUnit(this_i - k, this_j + k).equals("Antibody"))
                        return 10 * k + 2;
                }

                //right
                if (!isOut[2]) {
                    if (cf.checkUnit(this_i, this_j + k).equals("out")) {
                        outOfBounds += 1;
                        isOut[2] = true;
                    } else if (cf.checkUnit(this_i, this_j + k).equals("Antibody"))
                        return 10 * k + 3;
                }

                //downleft
                if (!isOut[3]) {
                    if (cf.checkUnit(this_i + k, this_j + k).equals("out")) {
                        outOfBounds += 1;
                        isOut[3] = true;
                    } else if (cf.checkUnit(this_i + k, this_j + k).equals("Antibody"))
                        return 10 * k + 4;
                }

                //down
                if (!isOut[4]) {
                    if (cf.checkUnit(this_i + k, this_j).equals("out")) {
                        outOfBounds += 1;
                        isOut[4] = true;
                    } else if (cf.checkUnit(this_i + k, this_j).equals("Antibody"))
                        return 10 * k + 5;
                }

                //downright
                if (!isOut[5]) {
                    if (cf.checkUnit(this_i + k, this_j - k).equals("out")) {
                        outOfBounds += 1;
                        isOut[5] = true;
                    } else if (cf.checkUnit(this_i + k, this_j - k).equals("Antibody"))
                        return 10 * k + 6;
                }

                //left
                if (!isOut[6]) {
                    if (cf.checkUnit(this_i, this_j - k).equals("out")) {
                        outOfBounds += 1;
                        isOut[6] = true;
                    } else if (cf.checkUnit(this_i, this_j - k).equals("Antibody"))
                        return 10 * k + 7;
                }

                //upleft
                if (!isOut[7]) {
                    if (cf.checkUnit(this_i - k, this_j - k).equals("out")) {
                        outOfBounds += 1;
                        isOut[7] = true;
                    } else if (cf.checkUnit(this_i - k, this_j - k).equals("Antibody"))
                        return 10 * k + 8;
                }
                k++;
            }
        }else if(command.equals("nearby")){
            int this_i = unit.getPosition()[0];
            int this_j = unit.getPosition()[1];

            int k = 1;

            String direction = this.dir.eval(binding);
            if(direction.equals("up")){
                while (true){
                    if (cf.checkUnit(this_i - k, this_j).equals("out")) {
                        return 0;
                    } else if (cf.checkUnit(this_i - k, this_j).equals("Virus") || cf.checkUnit(this_i - k, this_j).equals("Antibody"))
                        return 10 * k + 1;
                    k++;
                }
            }else if(direction.equals("upright")){
                while (true){
                    if (cf.checkUnit(this_i - k, this_j + k).equals("out")) {
                        return 0;
                    } else if (cf.checkUnit(this_i - k, this_j + k).equals("Virus") || cf.checkUnit(this_i - k, this_j + k).equals("Antibody"))
                        return 10 * k + 1;
                    k++;
                }
            }else if(direction.equals("right")){
                while (true){
                    if (cf.checkUnit(this_i, this_j + k).equals("out")) {
                        return 0;
                    } else if (cf.checkUnit(this_i, this_j + k).equals("Virus") || cf.checkUnit(this_i, this_j + k).equals("Antibody"))
                        return 10 * k + 3;
                    k++;
                }
            }else if(direction.equals("downright")){
                while (true){
                    if (cf.checkUnit(this_i + k, this_j + k).equals("out")) {
                        return 0;
                    } else if (cf.checkUnit(this_i + k, this_j + k).equals("Virus") || cf.checkUnit(this_i + k, this_j + k).equals("Antibody"))
                        return 10 * k + 4;
                    k++;
                }
            }else if(direction.equals("down")){
                while (true){
                    if (cf.checkUnit(this_i + k, this_j).equals("out")) {
                        return 0;
                    } else if (cf.checkUnit(this_i + k, this_j).equals("Virus") || cf.checkUnit(this_i + k, this_j).equals("Antibody"))
                        return 10 * k + 5;
                    k++;
                }
            }else if(direction.equals("downleft")){
                while (true){
                    if (cf.checkUnit(this_i + k, this_j - k).equals("out")) {
                        return 0;
                    } else if (cf.checkUnit(this_i + k, this_j - k).equals("Virus") || cf.checkUnit(this_i + k, this_j - k).equals("Antibody"))
                        return 10 * k + 6;
                    k++;
                }
            }else if(direction.equals("left")){
                while (true){
                    if (cf.checkUnit(this_i, this_j - k).equals("out")) {
                        return 0;
                    } else if (cf.checkUnit(this_i, this_j - k).equals("Virus") || cf.checkUnit(this_i, this_j - k).equals("Antibody"))
                        return 10 * k + 6;
                    k++;
                }
            }else if(direction.equals("upleft")){
                while (true){
                    if (cf.checkUnit(this_i- k, this_j - k).equals("out")) {
                        return 0;
                    } else if (cf.checkUnit(this_i- k, this_j - k).equals("Virus") || cf.checkUnit(this_i- k, this_j - k).equals("Antibody"))
                        return 10 * k + 6;
                    k++;
                }
            }

        }else {
            throw new EvalError("Unknown sensor command");
        }
        return 0;
    }
}
