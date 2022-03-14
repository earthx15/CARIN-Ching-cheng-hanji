package Game;

import Entity.Host;

import java.util.HashSet;
import java.util.Set;

public class CellsField {
    private Cell[][] cellsField;
    private Set<Host> hostSet = new HashSet<>();

    public CellsField(int row, int col){
        cellsField = new Cell[col][row];
        for(int i = 0; i < col; i ++){
            for(int j = 0; j < row; j++){
                cellsField[i][j] = new Cell();
            }
        }
    }

    public Host getUnit(int i, int j){
        if(checkUnit(i,j).equals("Antibody") || checkUnit(i,j).equals("Virus"))
            return cellsField[i][j].getThisUnits();
        else
            return null;
    }

    public void addUnit(Host unit){
        if(cellsField[unit.getPosition()[0]][unit.getPosition()[1]].isEmpty()) {
            cellsField[unit.getPosition()[0]][unit.getPosition()[1]].addUnit(unit);
            hostSet.add(unit);
        }
    }

    public void moveUnit(int dst_i, int dst_j, Host unit){
        cellsField[unit.getPosition()[0]][unit.getPosition()[1]].removeUnit();
        //destination must empty!
        cellsField[dst_i][dst_j].addUnit(unit);
        unit.setPosition(dst_i, dst_j);
    }

    public void removeUnit(Host unit){
        int i = unit.getPosition()[0];
        int j = unit.getPosition()[1];
        if(checkUnit(i,j).equals("Antibody") || checkUnit(i,j).equals("Virus")) {
            cellsField[i][j].removeUnit();
            hostSet.remove(unit);
        }
    }

    public String checkUnit(int i, int j){
        if(i < 0 || i >= cellsField.length || j < 0 || j >= cellsField[0].length)
            return "out";
        else if(cellsField[i][j].isEmpty())
            return "empty";
        else{
            return cellsField[i][j].isAntibody() ? "Antibody" : "Virus";
        }
    }

    public boolean isAlive(Host unit){
        return hostSet.contains(unit);
    }




}
