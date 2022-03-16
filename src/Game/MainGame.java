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

    protected static Random rand = new Random();

    protected static Spawner virusSpawner;
    protected static Spawner virus2Spawner;
    protected static Spawner virus3Spawner;


    protected static Spawner antibodySpawner;
    protected static Spawner antibody2Spawner;
    protected static Spawner antibody3Spawner;

    protected static fCodeReader reader = new fCodeReader();

    public static void main(String[] args) throws EvalError, FileNotFoundException {
        int[] config = reader.readConfig("src/Config/Config.txt");
        cellsField = new CellsField(config[0], config[1]);

        virusSpawner = new Spawner(cellsField);
        virusSpawner.setUnitAttribute("Virus", config[5], config[7], 1, config[8]);

        virus2Spawner = new Spawner(cellsField);
        virus2Spawner.setUnitAttribute("Virus", config[5], config[7], 1, config[8]);

        virus3Spawner = new Spawner(cellsField);
        virus3Spawner.setUnitAttribute("Virus", config[5], config[7], 1, config[8]);

        Parser virusParser = new Parser(reader.readGenCode("src/Config/virus.txt"));
        virusParser.addCellsField(cellsField);

        Parser virus2Parser = new Parser(reader.readGenCode("src/Config/virus2.txt"));
        virus2Parser.addCellsField(cellsField);

        Parser virus3Parser = new Parser(reader.readGenCode("src/Config/virus3.txt"));
        virus3Parser.addCellsField(cellsField);

        antibodySpawner = new Spawner(cellsField);
        antibodySpawner.setUnitAttribute("Antibody", config[6], config[9], 3, config[10]);

        antibody2Spawner = new Spawner(cellsField);
        antibody2Spawner.setUnitAttribute("Antibody", config[6], config[9], 5, config[10]);

        antibody3Spawner = new Spawner(cellsField);
        antibody3Spawner.setUnitAttribute("Antibody", config[6], config[9], 9, config[10]);

        Parser antibodyParser = new Parser(reader.readGenCode("src/Config/antibody.txt"));
        antibodyParser.addCellsField(cellsField);

        Parser antibody2Parser = new Parser(reader.readGenCode("src/Config/antibody2.txt"));
        antibody2Parser.addCellsField(cellsField);

        Parser antibody3Parser = new Parser(reader.readGenCode("src/Config/antibody3.txt"));
        antibody3Parser.addCellsField(cellsField);

        //randomly spawn 3 normal virus at first
        for(int k = 0; k < 3; k++){
            Virus vr = virusSpawner.randomSpawnVirus(1, config[0], config[1]);
            bindingStorage.put(vr, new HashMap<>());
            cellsField.addUnit(vr);
            System.out.println("Spawn normal virus at " + vr.getPosition()[0] +"," + vr.getPosition()[1]);
        }

        //randomly spawn 3 normal antibody at first
        for(int k = 0; k < 3; k++){
            Antibody ab = antibodySpawner.randomSpawnAntibody(1, config[0], config[1]);
            bindingStorage.put(ab, new HashMap<>());
            cellsField.addUnit(ab);
            System.out.println("Spawn normal antibody at " + ab.getPosition()[0] +"," + ab.getPosition()[1]);
        }
        System.out.println();
        for(int i = 0; i < 999; i++){
            System.out.println("--------------------------------------------");
            if(cellsField.virusCount > 0 && cellsField.antibodyCount == 0){
                System.out.println();
                System.out.println("             *** GAME OVER ***             ");
                System.out.println("    *** VIRUS HAS CONQUERED THE BODY ***      ");
                System.out.println();
                System.out.println("--------------------------------------------");
                System.out.println("============================================");
                break;
            }else if(cellsField.virusCount == 0 && cellsField.antibodyCount > 0){
                System.out.println();
                System.out.println("             *** GAME OVER ***             ");
                System.out.println("   *** ANTIBODY HAS CONQUERED THE BODY ***      ");
                System.out.println();
                System.out.println("--------------------------------------------");
                System.out.println("============================================");
                break;
            }
            System.out.println("               * Round " + (i+1) + " *");

            int randomSpawn = rand.nextInt(100) + 1;     //1 - 100
            Virus rv;
            String type;
            if(randomSpawn < 50) {
                rv = virusSpawner.randomSpawnVirus(reader.config_rate, config[0], config[1]);
                type = "normal";
            }
            else if(randomSpawn < 90) {
                rv = virus2Spawner.randomSpawnVirus(reader.config_rate, config[0], config[1]);
                type = "great";
            }
            else {
                rv = virus3Spawner.randomSpawnVirus(reader.config_rate, config[0], config[1]);
                type = "furious";
            }
            if(rv != null) {
                bindingStorage.put(rv, new HashMap<>());
                cellsField.addUnit(rv);
                System.out.println("***    Randomly spawn " + type + " virus at " + rv.getPosition()[0] + "," + rv.getPosition()[1] + "    ***");
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
                    System.out.println("--------------------------------------------");
                }
            }

            //45% -> normal virus
            //40% -> great virus
            //15% -> furious virus
            for(int[] p : cellsField.mutantsPosList){
                int randomMutant = rand.nextInt(100) + 1;     //1 - 100
                Virus vr;
                if(randomMutant < 45) {
                    vr = virusSpawner.SpawnVirus(1, p[0], p[1]);
                    System.out.println("Spawn normal mutant virus at " + p[0] +"," + p[1] + "!");
                }
                else if(randomMutant < 85) {
                    vr = virus2Spawner.SpawnVirus(1, p[0], p[1]);
                    System.out.println("Spawn great mutant virus at " + p[0] +"," + p[1] + "!");
                }
                else {
                    vr = virus3Spawner.SpawnVirus(1, p[0], p[1]);
                    System.out.println("Spawn furious mutant virus at " + p[0] +"," + p[1] + "!");
                }
                bindingStorage.put(vr, new HashMap<>());
                cellsField.addUnit(vr);

            }
            cellsField.mutantsPosList.clear();

            System.out.println("============================================");
            if(i == 998){
                System.out.println("There are more than 999 turns! ");
                System.out.println("Adjust the Config file if you do not want the war last too long");
            }
        }




    }

}
