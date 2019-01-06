# MunheEx4

# packman   (task_4)
This project is to make a map and let the packman to go and eat all his fruits in the shorts time 
with consideration of blocks ghosts and other packmans


# Getting Started

There is a Test Junit class that can run test program, of course it can be adjusted if needed.
You can use your own map but the default is the arial map

# Running The Program

This packman program will start running after running the main class in the map package, there is an example on how to use it.
You will need to provide a map and 2 of its corner GPS locations, and then you can load your own map details. you can speed up or slow down the speed of the map updating.


# Built With

•eclipse - The Platform for Open Innovation and Collaboration

# Authors

•	Shilo Gilor - Initial work 
•	Amiel Liberman - Initial work 

# Acknowledgments

•	We used youtube to understand better what is a Jframe funcion.

• We used http://zetcode.com/tutorials/javaswingtutorial/menusandtoolbars/ to help us in JMenuBar .

• We used		https://stackoverflow.com/questions/13366793/how-do-you-make-menu-item-jmenuitem-shortcut to make   generic shurtcut on all platforms

• We used http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html to make the Dijkstra algorithm.

# Description 
This program gets a map with packmans and fruits and blocks and ghosts and a main packman, so running the program will calculate a route for the main packman to go to eat all the fruits.

We have 6 packages to make the packman game, we used Point3D to crate the GPS places of the game.

i will explain the main packages:

algorithms is the package where all calculations are done, hence many other classes use this package.

entities is a package where all the entities are defined(packman,fruit,map,path,game,ghost,box).

GUI is a package where all the graphics are done.

#funcions in the map toolbar

file:

• new = create new map

• run = start desplaing the movements of all entities

• Exit = exit the app 

game:

• mypackman = click on the map will put a packman from now in the click location

speed:

• slow down = packman will run slower 

• fast forwards = packman will run faster 

import:

• open = open CSV file

Auto:

• Automate = will decide where the main packman needs to go and will go that way.
