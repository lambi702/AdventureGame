import java.util.Vector;
import java.util.Scanner;
import java.util.Random;

public class Player {
    String name, orientation;
    int HP;
    Vector<Item> inventory = new Vector<Item>();
    Item[] stuff = new Item[3];
    Room current;

    public Player(String str){
        this.name = str;
        this.HP = 100;
        this.orientation = "North";
    }

    public String getOrientation(){
        return this.orientation;
    }

    public void setOrientation(String chosenTurn){
        if(chosenTurn.equals("right")){
            if(this.orientation.equals("North"))
                this.orientation = "East";
            else if(this.orientation.equals("East"))
                this.orientation = "South";
            else if(this.orientation.equals("South"))
                this.orientation = "West";
            else if(this.orientation.equals("West"))
                this.orientation = "North";
        }
        else if(chosenTurn.equals("left")){
            if(this.orientation.equals("North"))
                this.orientation = "West";
            else if(this.orientation.equals("East"))
                this.orientation = "North";
            else if(this.orientation.equals("South"))
                this.orientation = "East";
            else if(this.orientation.equals("West"))
                this.orientation = "South";
        }
    }
    
    public void addItem(Item add){
        this.inventory.add(add);
    }

    public void setRoom(Room room){
        this.current = room;
    }

    public Room getCurrentRoom(){
        return this.current;
    }

    public boolean forward(Room[][] gameRooms){
        String currentOrientation = this.orientation;
        Room currentRoom = this.current;

        int i = currentRoom.getI();
        int j = currentRoom.getJ();

        if(currentOrientation.equals("North"))
            if(currentRoom.getNeighbors()[0] == 1){
                this.current = gameRooms[i-1][j];
                return true;
            }
            else if(currentRoom.getNeighbors()[0] == 2)
                System.out.println("Door currently locked");
            else
                System.out.println(ConsoleColors.RED + "Hey stupid boy don't go into the wall" + ConsoleColors.RESET);
        
        else if(currentOrientation.equals("East"))
            if(currentRoom.getNeighbors()[1] == 1){
                this.current = gameRooms[i][j+1];
                return true;
            }
            else if(currentRoom.getNeighbors()[1] == 2)
                System.out.println("Door currently locked");
            else
                System.out.println(ConsoleColors.RED + "Hey stupid boy don't go into the wall" + ConsoleColors.RESET);
        
        else if(currentOrientation.equals("South"))
            if(currentRoom.getNeighbors()[2] == 1){
                this.current = gameRooms[i+1][j];
                return true;
            }
            else if(currentRoom.getNeighbors()[2] == 2)
                System.out.println("Door currently locked");
            else
                System.out.println(ConsoleColors.RED + "Hey stupid boy don't go into the wall" + ConsoleColors.RESET);
        
        else if(currentOrientation.equals("West"))
            if(currentRoom.getNeighbors()[3] == 1){
                this.current = gameRooms[i][j-1];
                return true;
            }
            else if(currentRoom.getNeighbors()[3] == 2)
                System.out.println("Door currently locked");
            else
                System.out.println(ConsoleColors.RED + "Hey stupid boy don't go into the wall" + ConsoleColors.RESET);

        return false;
    }

    public void take(String str){
        String[] substr = str.split(" ");
        if(substr.length != 2)
            System.out.println("Invalid command : take <object>");
        else{
            Item itemToTake = this.current.getRoomItems(substr[1]);
            if(itemToTake == null)
                System.out.println("Invalid item : item not in the room");
            else{
                this.current.pickupItem(itemToTake);
                this.inventory.add(itemToTake);
                System.out.println(ConsoleColors.GREEN + "Item added to your inventory" + ConsoleColors.RESET);
            }
        }
    }

    public void equip(String str){
        String[] substr = str.split(" ");
        if (substr.length != 2)
            System.out.println("Invalid command : Equip <object>");
        else{
            Item itemToEquip = getInventoryItems(substr[1]);
            Item returnToInventory;
            if (itemToEquip == null)
                System.out.println(ConsoleColors.RED + "Invalid item : item not in the inventory" + ConsoleColors.RESET);
            else{
                this.inventory.remove(itemToEquip);
                if(itemToEquip instanceof Weapon){
                    returnToInventory = this.stuff[0];
                    this.stuff[0] = itemToEquip;
                }
                else if(itemToEquip instanceof Armor){
                    returnToInventory = this.stuff[1];
                    this.stuff[1] = itemToEquip;
                }
                else if(itemToEquip instanceof Shield){
                    returnToInventory = this.stuff[2];
                    this.stuff[2] = itemToEquip;
                }
                else{
                    System.out.println(ConsoleColors.RED + "Equip failed : instance of Weapon/Armor/Shield excpected" + ConsoleColors.RESET);
                    return;
                }
                this.inventory.add(returnToInventory);    
                System.out.println(ConsoleColors.GREEN + "Item added to your stuff" + ConsoleColors.RESET);
            }
        }

    }

    public Item getInventoryItems(String name){
        for(Item i : this.inventory){
            if(i == null)
                continue;
            else{
                String itemID = i.getName();
                if(itemID.equalsIgnoreCase(name))
                    return i;
            }
        }
        return null;
    }
    
    public void unlock(String str){
        String[] substr = str.split(" ");
        if(substr.length != 3)
            System.out.println(ConsoleColors.RED + "Invalid command : Unlock with <keyname>" + ConsoleColors.RESET);
        else{
            Item key = getInventoryItems(substr[2]);
            if(key==null){
                System.out.println("You don't have this item : "+ ConsoleColors.RED_UNDERLINED + " CHEATER" + ConsoleColors.RESET);
                return;
            }
            int neighbors = -1;
            if (this.orientation.equals("North"))
                neighbors = 0;
            else if (this.orientation.equals("East"))
                neighbors = 1;
            else if (this.orientation.equals("South"))
                neighbors = 2;
            else if (this.orientation.equals("West"))
                neighbors = 3;
            if (this.current.getDoorState(neighbors)==1){
                System.out.println("The door is already opened :-)");
                return;
            }
            else if (this.current.getDoorState(neighbors)==0){
                System.out.println(ConsoleColors.RED + "You are trying to open a wall..." + ConsoleColors.RESET);
                return;
            }
            Door door = this.current.getDoor(neighbors);
            if(door != null){
                if (key.getName().equals(door.getKey())){
                    System.out.println(ConsoleColors.GREEN+"Congratulations! The door is unlocked!"+ConsoleColors.RESET);
                    this.current.unlockDoor(neighbors);
                }
            }
                else 
                    System.out.println(ConsoleColors.RED + "Sorry.. It's not the right key" + ConsoleColors.RESET);
        }
    }

    public void printInventory(){
        if(this.inventory.size() == 0)
            System.out.println(ConsoleColors.RED + "Your inventory is currently empty" + ConsoleColors.RESET);
        else
            System.out.println("\nInventory : ");
            for(Item i : this.inventory)
                if(i == null)
                    continue;
                else
                    System.out.println(" - " + i.getName());
    }

    public void printStuff(){
        System.out.println("\nStuff : ");
        if(this.stuff[0] == null)
            System.out.println(" - Weapon : no weapon equipped");
        else
            System.out.println(" - Weapon : " + this.stuff[0].getName());
        if(this.stuff[1] == null)
            System.out.println(" - Armor : no armor equipped");
        else
            System.out.println(" - Armor : " + this.stuff[1].getName());
        if(this.stuff[2] == null)
            System.out.println(" - Shield : no shield equipped");
        else
            System.out.println(" - Shield : " + this.stuff[2].getName());
    }

    public void info(String str){
        String[] substr = str.split(" ");
        if(substr.length != 2){
            System.out.println(ConsoleColors.RED + "Invalid command : Info <itemName>" + ConsoleColors.RESET);
            return;
        }
        else{
            Item item = getInventoryItems(substr[1]);
            if(item == null){
                System.out.println(ConsoleColors.RED + "Item not found" + ConsoleColors.RESET);
                return;
            }
            else{
                item.information();
            }
        }
    }

    public void eat(String str){
        String[] substr = str.split(" ");
        if(substr.length != 2){
            System.out.println(ConsoleColors.RED + "Invalid command : eat <Food>" + ConsoleColors.RESET);
            return;
        }
        else{
            Item item = getInventoryItems(substr[1]);
            if(item == null){
                System.out.println(ConsoleColors.RED + "Food not found" + ConsoleColors.RESET);
                return;
            }
            else if(!(item instanceof Food)){
                System.out.println(ConsoleColors.RED + "Instance of food excpected" + ConsoleColors.RESET);
                return;
            }
            else{
                System.out.println(ConsoleColors.GREEN + "Food eaten" + ConsoleColors.RESET);
                this.inventory.remove(item);

                int boostHP = item.getFoodValue();
                this.HP += boostHP;
            }
        }
    }

    public void info(){
        int HP = this.HP;
        if(HP > 75)
            System.out.println(ConsoleColors.GREEN + "Player HP = " + Integer.toString(HP) + " All good" + ConsoleColors.RESET);
        else if(50 < HP && HP <= 75)
            System.out.println(ConsoleColors.CYAN + "Player HP = " + Integer.toString(HP) + " Think about fueling" + ConsoleColors.RESET);
        else if(25 < HP && HP <= 50)
            System.out.println(ConsoleColors.YELLOW + "Player HP = " + Integer.toString(HP) + " Getting dangerous" + ConsoleColors.RESET);
        else if(HP < 25)
            System.out.println(ConsoleColors.RED + "Player HP = " + Integer.toString(HP) + " Eat now !" + ConsoleColors.RESET);
    }

    public int getHP(){
        return this.HP;
    }

    public void helpFight(){
        System.out.println("\nAvailable commands :");
        System.out.println("\n- Attack : Attacks monster with your weapon, if no weapon equipped, damage = 2");
        System.out.println("- Shield : Use shield to protect yourself. Be careful, only works half of the time and limited use");
        System.out.println("- Potion <potionName> : Uses potion named <potionName> from inventory");
        System.out.println("- Help : Displays all available commands");
        System.out.println("- Exit : Exit the fight (not the game)");
    }

    public void fight(Ennemy monster,Scanner fightScan){
        int playerHP, monsterHP, damage,coinFlip,regen;
        double returnDamage;
        double protection;
        boolean fight = true;
        Item potion;
        String str;
        String[] split;
        Random rand = new Random();
        if(this.stuff[0]!=null)
            damage = this.stuff[0].getPower();
        else 
            damage = 2;
        
        if(this.stuff[1] != null)
            protection = this.stuff[1].getProtection();
        else 
            protection = 0;

        if(this.stuff[2] != null)
            returnDamage=this.stuff[2].returnDamage();
        else
            returnDamage=0.0;

        while(fight){
            
            this.info();
            monster.info();
            System.out.print("\nWhat do you do ?\n>> ");
            str = fightScan.nextLine();
            
            
            if (str.equalsIgnoreCase("Attack")){
                monster.hit(damage);
                System.out.println("The monster inflected you "+ (int) (monster.getPower() * (1.0 - protection))+" damages");
                this.HP -= (int)(monster.getPower()*(1.0-protection));
            }
            
            else if(str.equalsIgnoreCase("Shield")){
                if(this.stuff[2] != null){
                    coinFlip = rand.nextInt(2);   
                    if (coinFlip == 0){ // shield works
                        System.out.println(ConsoleColors.GREEN + "You succeded, the shield protected you and inflicted damage to ennemy");
                        monster.hit((int)(returnDamage*monster.getPower()));
                    }
                    else{
                        System.out.println(ConsoleColors.RED+ "Too bad ... the shield didn't worked, the monster inflicted you two times more damage" + ConsoleColors.RESET);
                        
                        this.HP -= 2*(int)(monster.getPower()*(1.0-protection));
                    }
                    
                    if(this.stuff[2]!=null && this.stuff[2].use()==0){
                        System.out.println(ConsoleColors.RED+"Your shield was destroyed"+ConsoleColors.RESET);
                        this.stuff[2] = null;
                        protection = 0.0;
                    }
                }
                else
                    System.out.println(ConsoleColors.RED + "Sorry you don't have a shield" + ConsoleColors.RESET);
            }

            else if(str.equalsIgnoreCase("Help")){
                this.helpFight();
            }
            else if(str.contains("Potion") || str.contains("potion")){
                split = str.split(" ");
                potion = getInventoryItems(split[1]);
                if(!(potion instanceof Potion)){
                    System.out.println(ConsoleColors.RED + "This item isn't a potion" + ConsoleColors.RESET);
                }
                else{
                    System.out.println(ConsoleColors.GREEN + "Potion used" + ConsoleColors.RESET);
                    regen = potion.getFoodValue();
                    this.HP += regen;
                    this.inventory.remove(potion);
                }
            }
            else if(str.equalsIgnoreCase("Exit")){
                System.out.println("Fight exitted");
                fight = false;
            }
            else{
                System.out.println(ConsoleColors.RED + "Invalid command : use Help to see all valid commands" + ConsoleColors.RESET);
                continue;
            }

            monsterHP = monster.getHP();
            playerHP = this.getHP();
            if(monsterHP <= 0){
                if (monster.isBoss()){
                    System.out.println(ConsoleColors.YELLOW + "You are an incredible fighter. You just kill te final "+ ConsoleColors.RED+"MONSTER "+ConsoleColors.YELLOW+"\nThe game is finished and you can be Happy." +ConsoleColors.RESET);
                    System.exit(0);
                }
                System.out.println(ConsoleColors.GREEN+ "You defeated the monster, well done, loot added to your inventory !" + ConsoleColors.RESET); 
                this.inventory.addAll(monster.getLootVector());
                fight = false;
                monster.setName("not");
            }

            else if(playerHP <= 0){
                System.out.println(ConsoleColors.RED+ "GAME OVER\nThe Monster defeated you" + ConsoleColors.RESET);
                System.exit(0);
            } 
        }
    }
}


