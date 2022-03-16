package Game;

import Entity.Antibody;
import Entity.Host;
import Entity.Virus;
import Evaluator.EvalError;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Spawner {
    private CellsField cf;

    private String unitType;
    private int unitHP;
    private int unitATK;
    private int unitAtkRange;
    private int unitGain;       // atkGain -> virus, killGain -> antibody

    private Random rand = new Random(0);

    public Spawner(CellsField cf) {
        this.cf = cf;
    }

    public void setUnitAttribute(String unit, int unitHP, int unitATK, int unitAtkRange,  int unitGain ){
        this.unitType = unit;
        this.unitHP = unitHP;
        this.unitATK = unitATK;
        this.unitAtkRange = unitAtkRange;
        this.unitGain = unitGain;
    }

    public Virus randomSpawnVirus(float rate, int m, int n) throws EvalError {
        float f = rand.nextFloat();
        if(f <= rate){
            int i = -1;
            int j = -1;
            while (!cf.checkUnit(i,j).equals("empty")){
                i = rand.nextInt(m);
                j = rand.nextInt(n);
            }
            if(unitType.equals("Virus"))
                return new Virus(unitHP, unitATK, unitAtkRange, unitGain, new int[]{i,j});
            else
                throw new EvalError("Unknown unit type");
        }
        return null;
    }

    public Virus SpawnVirus(float rate, int i, int j) throws EvalError {
        if(unitType.equals("Virus"))
            return new Virus(unitHP, unitATK, unitAtkRange, unitGain, new int[]{i,j});
        else
            throw new EvalError("Unknown unit type");

    }

    public Antibody randomSpawnAntibody(float rate, int m, int n) throws EvalError {
        float f = rand.nextFloat();
        if(f <= rate){
            int i = -1;
            int j = -1;
            while (!cf.checkUnit(i,j).equals("empty")){
                i = rand.nextInt(m);
                j = rand.nextInt(n);
            }
            if(unitType.equals("Antibody"))
                return new Antibody(unitHP, unitATK, unitAtkRange, unitGain, new int[]{i,j});
            else
                throw new EvalError("Unknown unit type");
        }
        return null;
    }

    public Antibody SpawnAntibody(float rate, int i, int j) throws EvalError {
        if(unitType.equals("Antibody"))
            return new Antibody(unitHP, unitATK, unitAtkRange, unitGain, new int[]{i,j});
        else
            throw new EvalError("Unknown unit type");

    }





}
