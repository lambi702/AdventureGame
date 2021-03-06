public class Shield extends Item{
    private int protectionTurns;
    private String info;
    private double returnDamage;
    
    public Shield(String name, int protectionTurns, String info, double returnDamage){
        super(name);
        this.protectionTurns = protectionTurns;
        this.info = info;
        this.returnDamage = returnDamage;
    }

    @Override
    public void information(){
        System.out.println(this.info);
    }

    @Override
    public double returnDamage(){
        return this.returnDamage;
    }

    @Override
    public int use(){
        this.protectionTurns--;
        return protectionTurns;
    }
}
