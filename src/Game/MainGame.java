package Game;

import Entity.Antibody;
import Entity.Host;
import Entity.Virus;
import Evaluator.EvalError;
import Evaluator.Parser;

import java.io.FileNotFoundException;
import java.util.*;

public class MainGame {
//    private int credits;
//    private int timeUnits;
//    private int timeSpeed;
    protected static Map<Host, Map<String, Integer>> bindingStorage = new HashMap<>();
    protected static CellsField cellsField;
    protected static Spawner virusSpawner;
    protected static Spawner antibodySpawner;

    protected static fCodeReader reader = new fCodeReader();
    protected static Random rand = new Random(0);

    public static void main(String[] args) throws EvalError, FileNotFoundException {
        int[] config = reader.readConfig("src/Config/Config.txt");
        cellsField = new CellsField(config[0], config[1]);

        virusSpawner = new Spawner(cellsField);
        virusSpawner.setUnitAttribute("Virus", config[5], config[7], config[8]);
        antibodySpawner = new Spawner(cellsField);
        antibodySpawner.setUnitAttribute("Antibody", config[6], config[9], config[10]);

        Parser virusParser = new Parser(reader.readGenCode("src/Config/virus.txt"));
        Parser antibodyParser = new Parser(reader.readGenCode("src/Config/antibody.txt"));

        virusParser.addCellsField(cellsField);
        antibodyParser.addCellsField(cellsField);

        //randomly spawn 3 virus at first
        for(int k = 0; k < 3; k++){
            Virus vr = virusSpawner.randomSpawnVirus(1, config[0], config[1]);
            bindingStorage.put(vr, new HashMap<>());
            cellsField.addUnit(vr);
            System.out.println("Spawn virus at " + vr.getPosition()[0] +"," + vr.getPosition()[1]);
        }

        //randomly spawn 2 antibody at first
        for(int k = 0; k < 2; k++){
            Antibody ab = antibodySpawner.randomSpawnAntibody(1, config[0], config[1]);
            bindingStorage.put(ab, new HashMap<>());
            cellsField.addUnit(ab);
            System.out.println("Spawn antibody at " + ab.getPosition()[0] +"," + ab.getPosition()[1]);
        }
        System.out.println();
        for(int i = 0; i < 10; i++){
            System.out.println("Round " + (i+1));

            Virus rv = virusSpawner.randomSpawnVirus(reader.config_rate, config[0], config[1]);
            if(rv != null) {
                bindingStorage.put(rv, new HashMap<>());
                cellsField.addUnit(rv);
                System.out.println("***    Randomly spawn virus at " + rv.getPosition()[0] + "," + rv.getPosition()[1] + "    ***");
                System.out.println();
            }

            List<Host> hl = new LinkedList<>(cellsField.hostList);
            for(Host host : hl){
                if(cellsField.isAlive(host)) {
                    if (host.getClass().getSimpleName().equals("Virus")) {
                        virusParser.addUnit(host, bindingStorage.get(host));
                        virusParser.eval();
                    } else {
                        antibodyParser.addUnit(host, bindingStorage.get(host));
                        antibodyParser.eval();
                    }
                }
            }
            for(int[] p : cellsField.mutantsPosList){
                Virus vr = virusSpawner.SpawnVirus(1, p[0], p[1]);
                bindingStorage.put(vr, new HashMap<>());
                cellsField.addUnit(vr);
                System.out.println("Spawn mutant virus at " + p[0] +"," + p[1] + "!");
            }
            cellsField.mutantsPosList.clear();

            System.out.println("============================================");
        }



    }

}
