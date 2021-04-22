# ParkingAppDemo
This repository contains the submission for the Zendesk Backend Exercise. The task is to design automated valet car parking system where you manage a parking space
for vehicles as they enter/exit it and manage its revenue.

Assumptions:
---
* This submission assumes that there are only 2 types of vehicles, cars and motorcycles. However, the architecure is made dynamic to a certain extent that further extensions 
such as adding new vehicle types will be easier in the future. For example, the design of the ParkingLotService class opts for a dynamic way of maintaining vehicles and available 
lots.
* Hourly fees, Motorcycles: $1/hour, Car: $2/hour. These hourly fees are hard coded into the application. As such, changes in the code is needed to adjust the hourly fees.
* There are no guidelines on the vehicle number/license plate of the vehicles. It is assumed that it's a unique String identifier between vehicles.
* Text file inputs are in the given format as given in the Data Description segment of the problem sheet. Examples can be seen below. 

Prerequisites
---
This project runs on Java SDK 1.8.0_282. If you do not have this Java SDK version installed on your machine, follow these steps to set up your machine.

1. Update your main repository:
```
sudo apt-get update
```

2. Install java SE 8:
```
sudo apt-get install openjdk-8-jdk
```
3. Verify that Java and the Java compiler have been properly installed:
```
java -version
javac -version
```
The expected output.
```
java version "1.8.0_212"
OpenJDK Runtime Environment (build 1.8.0_212-8u212-b03-0ubuntu1.16.04.1-b03)
OpenJDK 64-Bit Server VM (build 25.212-b03, mixed mode)

javac 1.8.0_212
```
## Input & Output
---
This program takes in a text file input of the following format.

```
3 4
Enter motorcycle SGX1234A 1613541902
Enter car SGF9283P 1613541902
Exit SGX1234A 1613545602
Enter car SGP2937F 1613546029
Enter car SDW2111W 1613549730
Enter car SSD9281L 1613549740
Exit SDW2111W 1613559745
```

The following will be printed out as an output as expected of such an input.
```
Accept MotorcycleLot1
Accept CarLot1
MotorcycleLot1 2
Accept CarLot2
Accept CarLot3
Reject
CarLot3 6
Total Revenue: 8.0
```
If there are invalid inputs, the program will terminate with error message indicating the error encountered.

Run Test Case
----
1. Clone the repository.

2. From the root of your repository, change directory into ./src/main/java/ where the main.java file resides.

3. Compile main java file.
```
javac main.java
```
4. Upon successful compilation, run java program with name of text file as an argument.
```
java main parkingApp_test.txt
```
5. To test with other test cases, create a text file of the same format in this directory and pass it as the first argument. 
This program only takes in one input text file at a time.


Project Structure
--
This project is managed by Maven. This repository contains a pom.xml file that is used by Maven to manage dependencies such as for unit testing. 

Unit tests can be found in the ./src/test/java folder. The test cases uses Junit5 as a testing framework. This readme doesn't cover the setup to run the unit tests as this
requires further setting up for Maven and dependency handling. This project could be exported into an IDE such as IntelliJ Idea.


## Future Work
---
The architecture of this program is designed in a way that is extendable for future use cases. The use of OOP concepts such as inheritance and design patterns are used to
allow extensibnility for future use cases. For example, the Factory pattern is used in creating Vehicles that can be extended to be more than 2 types. The implementation of 
the parking lot and payment service also allows for further customisation of each type of car park or payment service.


