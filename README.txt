********************************
* OOP Project : Adventure Game *
********************************

Authors :
- LAMBERMONT Romain
- LOUIS Arthur

22th April 2021

Classes :
AdventureGame : main class, runs the game by interacting with class Player
Player : contains the stuff, inventory, HP, position and orientation of the player currently
Item : abstract class extended by all the classes used to complete inventory and stuff (Armor, Food, Key, Potion, Shield, Weapon)
Room : contains position of the room (i,j) in the game grid, a vector of items on the ground, an ennemy (if not the ennmy is named not) and
    arrays of doors
Door : contains a boolean (closed, open) and if closed, the ID of th key needed to open items
Ennemy : contains all characteristics of a monster in the game (name, HP , power, loot, a small presentation and a boolean to verify if it's the boss)

Commands :

- Base game commands : - Help : shows available commands
                       - Turn (left | right) : Changes player orientation
                       - Move : makes the player go forward (if possible)
                       - Take <item> : take an item on the ground into your inventory
                       - Equip <item> : equip item from inventory to active stuff (only weapons,shields and armors)
                       - Inventory : display inventory
                       - Stuff : display stuff
                       - Fight : enter fight with ennemy in room
                       - Unlock with <keyname> : unlock locked door with key named <keyname>
                       - Info <Item> : display information on item in inventory (not applicable to stuff)
                       - Eat <Food> : eat food and raise HP
                       - HP : display player's HP
                       - Room : display info on current room (updated since entrance to room)
                       - Exit : Quit the game

- Fight commands : - Attack : Attacks monster with your weapon, if no weapon equipped, damage = 2
                   - Shield : Use shield to protect yourself. Be careful, only works half of the time and limited use
                   - Potion <potionName> : Uses potion named <potionName> from inventory
                   - Help : Displays all available commands
                   - Exit : Exit the fight (not the game)
    
/!\ Be careful, in fight-mode only your stuff is available. (Weapon, Shield, Armor) /!\
/!\ In fight-mode only Potion is available /!\

This an example of the beginning of a game:

===== Welcome in Donjon & Goblins ===== 
 

Enter your name :
>> player

You're name will be player
Continue ? (yes/no/exit)  
>> yes

Game start
You appear in the donjon, you must defeat the boss at the end of it

Use help for commands

----------------------------------------------------------

There is  no ennemy  in the room

There are doors to :
 - East (opened)
 - South (opened)

You can find on the ground :
- IronSword
- IronShield
- IronArmor
- Chicken

You are currently oriented North
>> Take IronSword
Item added to your inventory

You are currently oriented North
>> Equip IronSword
Item added to your stuff

You are currently oriented North
>> turn right

You are currently oriented East
>> Move

----------------------------------------------------------

There is  no ennemy  in the room

There are doors to :
 - East (opened)
 - West (opened)

There is nothing on the ground.

You are currently oriented East
>> ...