Problem – Ships & Crane
Given specifications of a ship and different types of containers, find whether the containers can be successfully stacked in the ship or not. If they can be, output a possible stacking.

•	7 different parameters are used as constraints. They are: -
o	Number of stacks, Size of each stack, Maximum weight, Maximum height of each stack, Risk Tolerance, Maximum Power output, and Maximum Sway Tolerance.
•	Each container has a certain id, type (described below), weight and height. Depending on the type of the container, it can also have another parameter like power requirement, sway, toxicity, fragile.
•	A successful stacking is possible only when all the containers’ cumulative parameters do not exceed that of the ship.
•	Description of the different types of Containers: -
o	Regular (Type 1) ¬¬– Does not have any additional parameter, other than the 4 common parameters.
o	Refrigerate (Type 2) – Needs some power to continue refrigeration.
o	Liquid (Type 3) – Carries liquid substance. Has some sway.
o	Radioactive (Type 4) – Contains radioactive substance, hence has some associated toxicity with it.
o	Weak (Type 5) – Contains fragile equipment, hence no other container can be put on top of it

Code Explanation
•	Class ContainerType is the parent class of all different types of containers.
•	Class Refrigerate, Toxic, Liquid, Regular and Weak inherit the ContainerType class and denote the respective type of containers.
o	They contain a variable which stores the value of the parameter. The constructor of the class is used to fulfil this.
•	Class Container is the class of all the containers.
o	It contains id, type, weight and height parameters which are used by all types of containers.
o	Object ct of ContainerType denotes the type of the container. Function assignType is used to assign the container type to ct.
•	Class Ship denotes the Ship. It contains the information about all the capacity of the ship. 
•	Class HeightComparator is used to sort a list containing all the containers as per their heights in descending order. See explanation of Crane. 
•	Class Crane denotes the crane. It performs all the work of stacking via the stack() function. It is explained in more detail below.
•	Class Driver contains the main() function. It initializes all the classes, takes the inputs from the user and store them accordingly. It also receives the verdict from the Crane class whether the containers can be stacked or not, and accordingly gives the output.	

Class Crane
•	2-D List Array ‘arrange’ stores the final stacking of containers. Each list denotes a stack. The 1st element of each list of the 2-D list (arrange[0]) denotes the bottom-most container of the stack, the 2nd element denotes the container above it, and so on. Hence the last element of each list in ‘arrange’ denotes the topmost container of the stack.
•	To ensure that the container stacking process is optimized, containers should be stacked such that those having largest heights are different stacks. To do so, the containerArray, which contains information about all the containers, is sorted in descending order of the containers’ heights. For that, a custom comparator named HeightComparator is used.
•	The containers are stacked from left to right for 1st layer, then right to left for 2nd layer, left to right for 3rd layer, and so on. This is done to ensure maximum stacking of containers as height uniformity will be achieved.
•	As weak container has to be on top, they are stacked at the end. Whenever a weak container comes, it is stored in ‘weakContainer’ List.
•	 Each parameter of a container is checked. As soon as a container fails in any of the parameter, a ‘false’ is returned, which in turn results in a “Not possible” output to the user. Only if all the containers fulfil the constraints of the Ship, a ‘true’ is returned.
•	For stacking a weak container (which is done in the end, all stacking all other containers), a HashSet mp is maintained. A stack is added to mp if a weak container is stacked in it. Hence, that stack is sealed now and no more container can be stacked in it. The weak container will be on the top.

Test Cases

Case 1:-

Ship Details: -
Enter number of stacks: 
3
Enter capacity of stack: 
4
Enter maximum weight load: 
100
Enter maximum risk tolerance: 
6
Enter maximum Output power: 
8.4
Enter maximum sway tolerance: 
4.3
Enter maximum height of a stack: 
120
Ship Details stored!

Enter mumber of containers: 
12
Please enter Container details in the format as mentioned below:- 
Each line contains details of 1 unique container.
The 1st entry will be the container id, followed by its type, weight, & height. After that enter the next parameter as per the type of the container..
1 1 10 30
2 3 6 50 2.6
3 5 8 20
4 2 10 40 4.5
5 4 13 10 4
6 2 9 25 1.2
7 1 10 27
8 3 14 30 0.9
9 1 5 24 
10 1 6 25
11 1 3 20
12 1 4 15

Yes!It's Possible!
[[2, 6, 10, 3], [4, 7, 9, 5], [1, 8, 11, 12]]

Case 2

Ship Details:-
Enter number of stacks: 
3
Enter capacity of stack: 
2
Enter maximum weight load: 
78
Enter maximum risk tolerance: 
3
Enter maximum Output power: 
2
Enter maximum sway tolerance: 
4
Enter maximum height of a stack: 
132
Ship Details stored!

Enter mumber of containers: 
5
Please enter Container details in the format as mentioned below:- 
Each line contains details of 1 unique container.
The 1st entry will be the container id, followed by its type, weight, & height. After that enter the next parameter as per the type of the container..
1 1 34 56
2 4 23 87 2
3 3 45 65 2.3
4 5 21 15
5 2 7 106 2.8

Arrangement Not possible :(

