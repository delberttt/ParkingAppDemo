//import ParkingApp.ParkingSystem.ParkingSystem;

import ParkingApp.ParkingSystem.ParkingSystem;

import java.io.File;

public class main {

    public static void main(String[] args) {

        File parkingInputFile = new File("parkingApp_test.txt");

        ParkingSystem parkingSystem = new ParkingSystem(parkingInputFile);
    }


}
