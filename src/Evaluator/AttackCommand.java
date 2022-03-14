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
    public void eval(Map<String, Integer> binding, Host unit, CellsField cf) throws EvalError {
        System.out.println("Attack : " + direction.eval(binding));
        testOut.append("Attack : ").append(direction.eval(binding)).append("\n");

        if(unit == null) throw new EvalError("Missing Host unit");

        int dst_i = unit.getPosition()[0];
        int dst_j = unit.getPosition()[1];

        if(direction.eval(binding).equals("up")){
            for(int k = 1; k <= unit.getAtkRange(); k++){
                dst_i -= 1;
                if(cf.checkUnit(dst_i, dst_j).equals("out"))    //out of map
                    break;
                if((unit.getClass().getSimpleName().equals("Virus") && cf.checkUnit(dst_i, dst_j).equals("Antibody"))
                        || (unit.getClass().getSimpleName().equals("Antibody") && cf.checkUnit(dst_i, dst_j).equals("Virus")) ){
                    Host target = cf.getUnit(dst_i, dst_j);
                    unit.attack(target);
                    if(target.getCurHp() == 0)
                        cf.removeUnit(target);
                    break;      //attack the closest target in the given direction only
                }
            }

        }else if(direction.eval(binding).equals("upright")){
            for(int k = 1; k <= unit.getAtkRange(); k++){
                dst_i -= 1;
                dst_j += 1;
                if(cf.checkUnit(dst_i, dst_j).equals("out"))    //out of map
                    break;
                if(unit.getClass().getSimpleName().equals("Virus") && cf.checkUnit(dst_i, dst_j).equals("Antibody")
                        || unit.getClass().getSimpleName().equals("Antibody") && cf.checkUnit(dst_i, dst_j).equals("Virus") ){
                    Host target = cf.getUnit(dst_i, dst_j);
                    unit.attack(target);
                    if(target.getCurHp() == 0)
                        cf.removeUnit(target);
                    break;      //attack the closest target in the given direction only
                }
            }

        }else if(direction.eval(binding).equals("right")){
            for(int k = 1; k <= unit.getAtkRange(); k++){
                dst_j += 1;
                if(cf.checkUnit(dst_i, dst_j).equals("out"))    //out of map
                    break;
                if(unit.getClass().getSimpleName().equals("Virus") && cf.checkUnit(dst_i, dst_j).equals("Antibody")
                        || unit.getClass().getSimpleName().equals("Antibody") && cf.checkUnit(dst_i, dst_j).equals("Virus") ){
                    Host target = cf.getUnit(dst_i, dst_j);
                    unit.attack(target);
                    if(target.getCurHp() == 0)
                        cf.removeUnit(target);
                    break;      //attack the closest target in the given direction only
                }
            }

        }else if(direction.eval(binding).equals("downright")){
            for(int k = 1; k <= unit.getAtkRange(); k++){
                dst_i += 1;
                dst_j += 1;
                if(cf.checkUnit(dst_i, dst_j).equals("out"))    //out of map
                    break;
                if(unit.getClass().getSimpleName().equals("Virus") && cf.checkUnit(dst_i, dst_j).equals("Antibody")
                        || unit.getClass().getSimpleName().equals("Antibody") && cf.checkUnit(dst_i, dst_j).equals("Virus") ){
                    Host target = cf.getUnit(dst_i, dst_j);
                    unit.attack(target);
                    if(target.getCurHp() == 0)
                        cf.removeUnit(target);
                    break;      //attack the closest target in the given direction only
                }
            }

        }else if(direction.eval(binding).equals("down")){
            for(int k = 1; k <= unit.getAtkRange(); k++){
                dst_i += 1;
                if(cf.checkUnit(dst_i, dst_j).equals("out"))    //out of map
                    break;
                if(unit.getClass().getSimpleName().equals("Virus") && cf.checkUnit(dst_i, dst_j).equals("Antibody")
                        || unit.getClass().getSimpleName().equals("Antibody") && cf.checkUnit(dst_i, dst_j).equals("Virus") ){
                    Host target = cf.getUnit(dst_i, dst_j);
                    unit.attack(target);
                    if(target.getCurHp() == 0)
                        cf.removeUnit(target);
                    break;      //attack the closest target in the given direction only
                }
            }

        }else if(direction.eval(binding).equals("downleft")){
            for(int k = 1; k <= unit.getAtkRange(); k++){
                dst_i += 1;
                dst_j -= 1;
                if(cf.checkUnit(dst_i, dst_j).equals("out"))    //out of map
                    break;
                if(unit.getClass().getSimpleName().equals("Virus") && cf.checkUnit(dst_i, dst_j).equals("Antibody")
                        || unit.getClass().getSimpleName().equals("Antibody") && cf.checkUnit(dst_i, dst_j).equals("Virus") ){
                    Host target = cf.getUnit(dst_i, dst_j);
                    unit.attack(target);
                    if(target.getCurHp() == 0)
                        cf.removeUnit(target);
                    break;      //attack the closest target in the given direction only
                }
            }

        }else if(direction.eval(binding).equals("left")){
            for(int k = 1; k <= unit.getAtkRange(); k++){
                dst_j -= 1;
                if(cf.checkUnit(dst_i, dst_j).equals("out"))    //out of map
                    break;
                if(unit.getClass().getSimpleName().equals("Virus") && cf.checkUnit(dst_i, dst_j).equals("Antibody")
                        || unit.getClass().getSimpleName().equals("Antibody") && cf.checkUnit(dst_i, dst_j).equals("Virus") ){
                    Host target = cf.getUnit(dst_i, dst_j);
                    unit.attack(target);
                    if(target.getCurHp() == 0)
                        cf.removeUnit(target);
                    break;      //attack the closest target in the given direction only
                }
            }

        }else if(direction.eval(binding).equals("upleft")){
            for(int k = 1; k <= unit.getAtkRange(); k++){
                dst_i -= 1;
                dst_j -= 1;
                if(cf.checkUnit(dst_i, dst_j).equals("out"))    //out of map
                    break;
                if(unit.getClass().getSimpleName().equals("Virus") && cf.checkUnit(dst_i, dst_j).equals("Antibody")
                        || unit.getClass().getSimpleName().equals("Antibody") && cf.checkUnit(dst_i, dst_j).equals("Virus") ){
                    Host target = cf.getUnit(dst_i, dst_j);
                    unit.attack(target);
                    if(target.getCurHp() == 0)
                        cf.removeUnit(target);
                    break;      //attack the closest target in the given direction only
                }
            }

        }
    }
}
