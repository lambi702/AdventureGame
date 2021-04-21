import java.util.Scanner;
import java.util.Vector;

public class AdventureGame{

    public static void main(String[] args) {
        System.out.print(ConsoleColors.RESET);
        int [][] maze = 
            {
            {1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,1,1,1,0,0,0,1},
            {1,0,1,1,0,1,1,0,0,0,1,0,1},
            {1,0,1,1,0,0,0,0,1,0,1,0,1},
            {1,0,0,0,1,1,1,0,1,0,1,0,1},
            {1,0,1,0,0,1,1,0,1,0,1,0,1},
            {1,0,1,1,0,0,0,0,1,0,1,0,1},
            {1,0,0,1,0,1,1,0,1,0,1,0,1},
            {1,1,0,1,0,1,1,0,1,0,1,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1}
            };

        Room[][] gameRooms = roomsInit(maze);
        Room init = gameRooms[0][0];
        
        System.out.println("\n===== Welcome in Donjon & Goblins ===== \n \n");
        Scanner newScanner = new Scanner(System.in);
        
        String str = new String();
        str = nameDefine(newScanner);

        Player gamePlayer = new Player(str);

        System.out.println("\nGame start");
        System.out.println("You appear in the donjon, you must defeat the boss at the end of it\n" + "\nUse help for commands");
        gamePlayer.setRoom(init);
        int gameStatus = 1; // 0 = stopped, 1 = in play, 2 = dead, 3 = win
        while(gameStatus != 0){
            gameStatus = play(gamePlayer,gameRooms,newScanner);
        }

        newScanner.close();
    }

    private static int play(Player gamePlayer, Room[][] gameRooms, Scanner gameScanner){
        gamePlayer.getCurrentRoom().printRoom();
        String str = new String();
        boolean roomChanged = false;
        while(roomChanged == false){
            System.out.println("\nYou are currently oriented " +  gamePlayer.getOrientation());
            System.out.print(">> ");
            str = gameScanner.nextLine();

            if(str.equalsIgnoreCase("Turn right"))
                gamePlayer.setOrientation("right");
            else if(str.equalsIgnoreCase("Turn left"))
                gamePlayer.setOrientation("left");
            else if(str.equalsIgnoreCase("Move"))
                roomChanged = gamePlayer.forward(gameRooms);
            else if(str.contains("Take"))
                gamePlayer.take(str);
            else if(str.equalsIgnoreCase("Help"))
                help();
            else if(str.contains("Equip"))
                gamePlayer.equip(str);
            else if(str.contains("Unlock with"))
                gamePlayer.unlock(str);
            else if(str.equalsIgnoreCase("Exit"))
                System.exit(0);
            else if(str.equalsIgnoreCase("Room"))
                gamePlayer.getCurrentRoom().printRoom();
            else if(str.equalsIgnoreCase("Inventory"))
                gamePlayer.printInventory();
            else if(str.equalsIgnoreCase("Stuff"))
                gamePlayer.printStuff();
            else if(str.contains("Info"))
                gamePlayer.info(str);
            else if(str.contains("Eat"))
                gamePlayer.eat(str);
            else if(str.equalsIgnoreCase("HP"))
                gamePlayer.info();
            else if(str.equalsIgnoreCase("Fight")){
                if(gamePlayer.getCurrentRoom().getMonster().getName() == "not"){
                    System.out.println("There's no ennemy in this room");
                }
                else{
                    System.out.println(ConsoleColors.GREEN + "Fight started" + ConsoleColors.RESET);
                    gamePlayer.fight(gamePlayer.getCurrentRoom().getMonster(),gameScanner);
                }
            }
            else
                System.out.println("Invalid command, use help to see all valid commands");
        }
        return 1;
    }

    private static Room[][] roomsInit(int[][] maze){
        Room[][] Rooms = new Room[8][11];

        // general rooms init

        for(int i = 0;i < 8; i++){
            for(int j = 0;j < 11;j++){
                //System.out.println("i : " + i + " j : " + j);
                int[] neighbors = checkNeighbors(maze,i+1,j+1);
                Vector<Item> items = new Vector<Item>();
                
                Vector<Door> doors = new Vector<Door>();
                for(int k = 0; k < neighbors.length; k++){
                    Door newDoor = new Door(false,"0");
                    doors.add(newDoor);
                }
                
                Rooms[i][j] = new Room(i,j,neighbors,items);
            }
        }

        // specific hardcode
        // --- Stuff ----
        Weapon dSword = new Weapon("DiamondSword",25,"The best sword in Donjon & Goblins");
        Weapon gSword = new Weapon("GoldSword",15,"A good sword, much better than the iron one");
        Weapon iSword = new Weapon("IronSword",5,"A good beginner's sword");
        
        Shield iShield = new Shield("IronShield",5,"Protects you for your firsts fights",1.0);
        Shield gShield = new Shield("GoldShield",10,"Protects you for your more difficult fights",1.5);
        Shield dShield = new Shield("DiamondShield",15,"Protects you for your hardest fights",2.0);

        Armor iArmor = new Armor("IronArmor",0.10,"Protects you against your first goblins");
        Armor gArmor = new Armor("GoldArmor",0.5,"Necessary against stronger creatures");
        Armor dArmor = new Armor("DiamondArmor",0.75,"Useful against the big boss");
        
        // --- Keys ---
        Key key1 = new Key("Key1","Gives you acces to your first secret reward");
        Key key2 = new Key("Key2","A great reward is hidden behind this door");
        Key key3 = new Key("Key3","This secret item will be useful against strong ennemies");
        Key key4 = new Key("Key4","The best thing you can hope for");
        Key key5 = new Key("Key5", "An essential key");
        Key key6 = new Key("Key6", "An essential key");


        // --- Food & Potion ---
        Potion potion1 = new Potion("BasePotion",15,"Great for a little boost of HP during a fight");
        Potion potion2 = new Potion("MediumPotion",30,"Big boost whenever you need it");
        Potion potion3 = new Potion("AdvancedPotion",45,"If you use this you must be in danger");
        
        Food bread = new Food("Bread",10,"Base food");
        Food chicken = new Food("Chicken",15,"To help you after a fight");
        Food beef = new Food("Beef",40,"You lust be getting ready for the big one");

        // --- Ennemies ---
        Vector<Item> loot1 = new Vector<Item>();
        Vector<Item> loot2 = new Vector<Item>();
        Vector<Item> loot3 = new Vector<Item>();

        loot1.add(potion1);
        loot1.add(key1);

        loot2.add(potion3);
        loot2.add(key6);


        Ennemy ennemy1 = new Ennemy("Goblin",30,5,loot1,"The weakest creature, should be a piece of cake",false);
        Ennemy ennemy2 = new Ennemy("Orc",50,10,loot2,"Modified goblin, faster ans stronger",false);
        Ennemy ennemy3 = new Ennemy("Boss",200,30,loot3,"Your final test",true);

        // --- Created instances on grid ---
        Rooms[0][0].addItem(iSword);
        Rooms[0][0].addItem(iShield);
        Rooms[0][0].addItem(iArmor);

        Rooms[1][8].addItem(gSword);
        Rooms[5][5].addItem(gShield);
        Rooms[4][3].addItem(gArmor);
        
        Rooms[7][1].addItem(dSword);
        Rooms[5][10].addItem(dShield);
        Rooms[2][10].addItem(dArmor);
        
        Rooms[0][0].addItem(chicken);
        Rooms[2][4].addItem(bread);
        Rooms[4][10].addItem(beef);

        Rooms[6][1].addItem(key2);
        Rooms[3][1].addItem(key3);
        Rooms[7][6].addItem(key4);
        Rooms[7][6].addItem(key5);

        Rooms[3][6].addItem(potion2);


        Rooms[0][3].setLockedDoor("Key1","South");
        Rooms[3][0].setLockedDoor("Key2","East");
        Rooms[6][1].setLockedDoor("Key3","South");
        Rooms[5][6].setLockedDoor("Key4","West");
        Rooms[1][7].setLockedDoor("Key5", "East");
        Rooms[3][10].setLockedDoor("Key6", "South");

        Rooms[0][4].setEnemyRoom(ennemy1);
        Rooms[0][10].setEnemyRoom(ennemy2);
        Rooms[7][10].setEnemyRoom(ennemy3);

        System.out.println("Rooms loaded");
        return Rooms;
    }

    private static int[] checkNeighbors(int[][] maze, int i, int j) {
        int[] neighbors = new int[4];

        if(maze[i-1][j] == 0)
            neighbors[0] = 1;
        else
            neighbors[0] = 0;
        if(maze[i][j+1] == 0)
            neighbors[1] = 1;
        else
            neighbors[1] = 0;
        if(maze[i+1][j] == 0)
            neighbors[2] = 1;
        else
            neighbors[2] = 0;
        if(maze[i][j-1] == 0)
            neighbors[3] = 1;
        else
            neighbors[3] = 0;
        
        return neighbors;
    }

    private static String nameDefine(Scanner newScanner) {
        String str = new String();
        boolean name = true;
        while(name){
            System.out.println("Enter your name :");
            System.out.print(">> ");
            str = newScanner.nextLine();

            boolean check = true;
            while(check){
                System.out.println("\nYou're name will be " + str + "\nContinue ? (yes/no/exit)");
                System.out.print(">> ");
                str = newScanner.nextLine();
                if(str.equals("yes")){
                    name = false;
                    check = false;
                }
                else if(str.equals("no")){
                    name = true;
                    check = false;
                }
                else if(str.equals("exit"))
                    System.exit(0);
                else
                    check = true;
            }
        }
        return str;
    }
    public static void help(){
        System.out.println("\nAvailable commands :\n");
        System.out.println("- Turn (left | right) : Changes player orientation");
        System.out.println("- Move : makes the player go forward (if possible)");
        System.out.println("- Help : shows available commands");
        System.out.println("- Take <item> : take an item on the ground into your inventory");
        System.out.println("- Equip <item> : equip item from inventory to active stuff (only weapons,shields and armors)");
        System.out.println("- Inventory : display inventory\n");
        System.out.println("- Stuff : display stuff\n");
        System.out.println("- Fight : enter fight with ennemy in room");
        System.out.println("- Unlock with <keyname> : unlock locked door with key named <keyname>\n");
        System.out.println("- Info <Item> : display information on item in inventory (not applicable to stuff)\n");
        System.out.println("- Eat <Food> : eat food and raise HP");
        System.out.println("- HP : display player's HP");
        System.out.println("- Room : display info on current room (updated since entrance to room)\n");
        System.out.println("- Exit : Quit the game\n");
    }
}