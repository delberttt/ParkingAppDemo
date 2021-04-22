//import ParkingApp.ParkingSystem.ParkingSystem;

import ParkingApp.ParkingSystem.ParkingSystem;

import java.io.File;

public class main {

    public static void main(String[] args) {

        if (args != null)
        {
            String fileName = args[0];
            if (fileName !=  null)
            {
                File parkingInputFile = new File(fileName);

                ParkingSystem parkingSystem = new ParkingSystem(parkingInputFile);
            }
        }
        else
        {
            System.out.println("Input file not provided.");
        }

    }


}
