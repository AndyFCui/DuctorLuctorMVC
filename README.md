# Doctor Lucky java Jframe game

This repo represents the coursework for CS 5010, the Spring 2022 Edition!

**Name:** Andy(Xiang-yu) Cui

**Email:** cui.xiangyu@northeastern.edu

**Preferred Name:** Andy(Xiang-yu) Cui



### About/Overview

* Student of NEU 
* Simple game base on `Kill Doctor Lucky`.

#### Game demo:

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

![doctorLucky](https://github.com/AndyFCui/DuctorLuctorMVC/assets/10085168/ce1724bd-733b-487e-bd11-e18257abff33)

---------------------------------------------------------------------------------------------------------------------------------------------------------------


### Project performance 

#### Code structure

* All concrete class has their own interface and easy to access which make program more flexible.

* All code use NU check style to generate 

* The project use MVC mode to deploy: 

  * M is model of  game

  * V is the GUI and terminal view for the game

  * C is controller of game

  * `Driver` the main class for the whole game
  
    


#### Game feature

* Read game world parameters by txt file named `mainsion.txt`.

* Game target: Kill the Doctor Lucky.

* Allow DIY in game parameters: 

  * Allow set the max game turn by users.
  * Allow set the number of the player by users.
  * Allow set the each Player name by users.
  * Allow set the each Player’s born position by users.
  * Allow set the each Player’s max number of hold item by users.
  * Allow set the Robot Player by users.  

* Player have maximum five different actions in their turn.

  * Move (keyboard 1): Player can move to available room from current position to target position.

  * Look Around (keyboard 2): Player can look around their neighbors base on their  current position. 

    Note: If pet occupied the neighbor room, then player can not seen that room’s detail information. 

  * Move pet to other room (keyboard 3): If human player and pet in same room, human player can move pet to other specified space.  

  * Pick up item (keyboard 4): If current room exist item, then players can pick up item in room.

    Note: any item if pick up by anyone the item will remove from the room.

  * attack assumption (keyboard 5): If player and target character in the same room, then player can try attack assumption.

    Note: robot always use the max attack method to attack target, and human can choose item to attack. Any player if do the attack assumption seen by pet or other players, then the attack assumption was failed. If player use item, then the item will disappear, and any item will be use by one time, whatever player attack successful or not.    

* Target is `Doctor Lucky`, he has not his own game turn, every turn he will move to available target position base on his current position. The born position of Doctor is room index 0. 

* Pet is born with Doctor Lucky, and pet is Wandering Pet, which means it can not move with Doctor Lucky, its will move random base on its current position every turn like Doctor Lucky and it has not its own turn as well.

* Environment information: 

  Every turn the screen will display the basic information like neighbors and hold item base on player position. 

* Ending Game

  * Turn arrived the max rand but the Doctor Lucky hp still greater than zero, then the game over. Doctor still alive bad ending, and nobody win the game.

  * Someone kill the Doctor Lucky whatever human player or robot, the game will show the winner who kill the Doctor Lucky.

* Additional function: All functions are still work from milestone 3, and create GUI and mouse action for milestone 4. 

  * Mouse clike avaliable room to move to the expect room, when the turn for that player.
  * Key board can use for `look around`, `attack assumpt`, and `pick up`.
  * Look anount can showed by small windows.

  

### How to Run

The project main function is `Driver.java` which located `~/src/Driver.java`.
Open the repo directory and then open project use IDE IntelliJ. Choose the `Driver.java` and click the green button to run it. 




### How to Use the Program

The project just have one package named `world`.
`~/rec` There are my code.

`~/test` There are my test case.  

`~/res` There are my resource file include:

* pdf file about the maze `world.png` 
* txt file about world specification `mansion.txt`. 
* UML file `Milestone4_gamePlay-merged.pdf`
* jar file `milestone4_theView.jar`

P.S. All public method possess the details comment, its easy to use. 



### Jar File Instruction

1. File > Project Structure... or press Ctrl + Alt + Shift + S.

2. Project Settings > Modules > Dependencies > "+" sign >
    JARs or directories...

3. Select the jar file and click on OK, then click on another OK
    button to confirm.

4. You can view the jar file in the "External Libraries" folder.

   

### Example Runs

* Set game: 4 player  
* Max turn: 10
* two robot, two human





### Design/Model Changes

#### Class naming method 

* Milestone 3: I changed my design again base on Milestone 2. Some class name was not clear, so I generate all `interface` use feature name and use name + Impl as `concrete` class. E.g. my interface is `Room` and my implement class named `RoomImpl` this code style like our lab.  
* Interms of milestone 4, since I need create many interface, so I can not use `Class   name` and `Name+Impl` to name some interface and concrete class. So, according to the interface can be writen by `I/F` so, I use If+class name to named the interface and concret class. 

#### Code structure

* I changed my code structure, since I asked professor, the project should by deploy by MVC design model, so I create three package  for do three things which are`cotroller`, `model` and `view`. Three packages store the right things for different users. 
* I added GUI controller for milestone 4.

#### OO design 

* After milestone 1 and 2, I change my class has `static` flag only in Driver class, so we can use the whole program by this file.

#### UML design 

* I did the most of function I design on UML before. I add some detail implement content. For the controller, I just use one class to do the implement, but the example give us few controller implement class to implement controller interface in different times, I think is not necessary in our project.



### Assumptions

* There are some block during my implement my implement my project, since I use read txt file each time before I want use some data from the txt file, finally I use driver to parse file, then I can just read each field in Model class. 

* When start do my controller, I guess we need do different class, but I try use one controller to control all player’s action like our lab Tic-tac-toe. 




### Limitations

I think the limitations is that:

* When player bag was full, I need give some feedback.
* When select miss some choice, I need reorder the select actions.
* It should enter again when player enter wrong actions.
* invalid command-line arguments should be ask for input again.



### Citations

[1] (n.d.). GeeksforGeeks | A computer science portal for geeks. Retrieved February 13, 2022, from http://www.geeksforgeeks.org
