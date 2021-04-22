public class Potion extends Item{
    private int regen;
    private String info;

    public Potion(String name, int regen, String info){
        super(name);
        this.regen = regen;
        this.info = info;
    }

    @Override
    public void information(){
        System.out.println(this.info);
    }
    
    @Override
    public int getFoodValue(){
        return this.regen;
    }
}
