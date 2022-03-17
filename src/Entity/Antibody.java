package Entity;

public class Antibody implements Host {
    private String species;
    private int maxHP;
    private int curHP;
    private int atk;
    private int atkRange;
    private int killGain;
    private int[] position;
    //    ability?


    public Antibody(String species, int HP, int atk, int atkRange, int killGain, int[] position) {
        this.species = species;
        this.maxHP = curHP = HP;
        this.atk = atk;
        this.atkRange = atkRange;
        this.killGain = killGain;
        this.position = position;
    }

    public void attack(Host host){
        int dmg = atk;
        if(atk > host.getCurHp())
            dmg = host.getCurHp();
        host.beAttacked(dmg);
        System.out.println(species + " Antibody " + position[0] + "," + position[1] +  " attack target at " + host.getPosition()[0] + "," + host.getPosition()[1]);
        System.out.println(host.getSpecies() + " Virus HP decreases from "+ (host.getCurHp() + dmg) + " to " + host.getCurHp());
        if(host.getCurHp() == 0) {
            curHP = Math.min(curHP + killGain, maxHP);
            System.out.println(species + " Antibody gain some HP from " + (curHP - killGain) + " to " + curHP);
        }
    }

    public void beAttacked(int dmg) {
        curHP = Math.max(curHP - dmg, 0);
    }

    public String getSpecies() {
        return this.species;
    }

    public int getCurHp() {
        return this.curHP;
    }

    public int getAtkRange() {
        return this.atkRange;
    }

    public int[] getPosition() {
        return this.position;
    }

    public void setPosition(int i, int j) {
        this.position[0] = i;
        this.position[1] = j;
    }


}
