package Entity;

public interface Host {
    public void attack(Host host);

    public void beAttacked(int dmg);

    public  int getCurHp();

    public int getAtkRange();

    public int[] getPosition();

    public void setPosition(int i, int j);


}
