public abstract class Item{
    protected String name;

    public Item(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void information(){
        return;
    }

    public int getFoodValue() {
        return 0;
    }

    public double returnDamage() {
        return 0;
    }

    public int getPower(){
        return 0;
    }
    
    public double getProtection(){
        return 0;
    }

    public int use(){
        return 0;
    }
}
