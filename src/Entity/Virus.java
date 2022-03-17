package Entity;

public class Virus implements Host {
    private String species;
    private int maxHP;
    private int curHP;
    private int atk;
    private int atkGain;
    private int atkRange;
    private int[] position;
//    ability?

    public Virus(String species, int HP, int atk, int atkRange, int atkGain, int[] position) {
        this.species = species;
        this.maxHP = this.curHP = HP;
        this.atk = atk;
        this.atkGain = atkGain;
        this.atkRange = atkRange;
        this.position = position;
    }

    public void attack(Host host){
        int dmg = atk;
        if(atk > host.getCurHp())
            dmg = host.getCurHp();
        host.beAttacked(dmg);
        curHP = Math.min(curHP + atkGain, maxHP);
        System.out.println(species +" Virus " + position[0] + "," + position[1] +  " attack target at " + host.getPosition()[0] + "," + host.getPosition()[1]);
        System.out.println(species + " Virus gain some HP from " + (curHP - atkGain) + " to " + curHP);
        System.out.println(host.getSpecies() + " Antibody HP decreases from "+ (host.getCurHp() + dmg) + " to " + host.getCurHp());
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

    public int[] getPosition(){
        return this.position;
    }

    public void setPosition(int i, int j) {
        this.position[0] = i;
        this.position[1] = j;
    }

}
