import java.util.Vector;

public class Ennemy{
    private String name;
    private int HP, power, constHP;
    private Vector<Item> loot;
    private String presentation;
    private boolean finalBoss;

    public Ennemy(String name){
        this.name = name;
    }

    public Ennemy(String name,int HP, int power, Vector<Item> loot, String presentation, boolean finalBoss){
        this.name = name;
        this.HP = HP;
        this.power = power;
        this.loot = loot;
        this.constHP = HP;
        this.presentation = presentation;
        this.finalBoss = finalBoss;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void presentation(){
        System.out.println(this.presentation);
    }

    public int getHP(){
        return this.HP;
    }

    public void info(){
        int HP = this.HP;
        int constHP = this.constHP;
        if(HP > (int)0.75*constHP)
            System.out.println(ConsoleColors.RED + "Ennemy HP = " + Integer.toString(HP) + " Lots of HP left" + ConsoleColors.RESET);
        else if((int)0.50*constHP < HP && HP <= (int)0.75*constHP)
            System.out.println(ConsoleColors.YELLOW + "Ennemy HP = " + Integer.toString(HP) + " Ennemy is weaker" + ConsoleColors.RESET);
        else if((int)0.25*constHP < HP && HP <= (int)0.50*constHP)
            System.out.println(ConsoleColors.CYAN + "Player HP = " + Integer.toString(HP) + " Continue, he's going to die" + ConsoleColors.RESET);
        else if(HP < (int)0.25*constHP)
            System.out.println(ConsoleColors.GREEN + "Player HP = " + Integer.toString(HP) + " Finish him now !" + ConsoleColors.RESET);
    }

    public int getPower(){
        return this.power;
    }

    public void hit(int damage){
        this.HP -= damage;
        System.out.println("Great, you inflicted " + damage + " damage points !");
    }

    public Vector<Item> getLootVector(){
        return this.loot;
    }

    public boolean isBoss(){
        return this.finalBoss;
    }
}
