package Entity;

import Entity.Host;

public class Antibody implements Host {
    //    private String species;
    private int maxHP;
    private int curHP;
    private int atk;
    private int atkRange;
    private int killGain;
    private int[] position;
    //    ability?


    public Antibody(int HP, int atk, int atkRange, int killGain, int[] position) {
        this.maxHP = curHP = HP;
        this.atk = atk;
        this.atkRange = atkRange;
        this.killGain = killGain;
        this.position = position;
    }

    public void attack(Host host){
        if(atk > host.getCurHp())
            curHP = Math.min(curHP + killGain, maxHP);
        host.beAttacked(atk);
        System.out.println("Antibody attack target at " + host.getPosition()[0] + "," + host.getPosition()[1]);
        System.out.println("Virus now has HP " + host.getCurHp());
        System.out.println();
    }

    public void beAttacked(int dmg) {
        curHP = Math.max(curHP - dmg, 0);
    }

    @Override
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
