public class Door {
    private boolean closed;
    private String key;

    public Door(boolean closed, String key){
        this.closed = closed;
        this.key = key;
    }

   public String getKey(){ 
        return this.key;
   }

   public void openDoor(){
        this.closed = false;
   }
}


