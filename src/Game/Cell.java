package Game;

import Entity.Host;

public class Cell {

    private Host thisUnits;

    public Cell(){
        thisUnits = null;
    }

    public Host getThisUnits() {
        return thisUnits;
    }

    public void addUnit(Host unit){
        thisUnits = unit;
    }

    public void removeUnit(){
        thisUnits = null;
    }

    public boolean isEmpty(){
        return thisUnits == null;
    }

    public boolean isVirus(){
        return thisUnits.getClass().getSimpleName().equals("Virus");
    }

    public boolean isAntibody(){
        return thisUnits.getClass().getSimpleName().equals("Antibody");
    }


}
