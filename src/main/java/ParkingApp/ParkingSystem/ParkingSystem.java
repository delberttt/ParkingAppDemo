package ParkingApp.ParkingSystem;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.ParkingLot.CarAndMotorParkingLotServiceImpl;
import ParkingApp.ParkingLot.ParkingLot;
import ParkingApp.ParkingLot.ParkingLotService;
import ParkingApp.Payment.CarAndMotorPaymentSystemServiceImpl;
import ParkingApp.Payment.PaymentSystem;
import ParkingApp.Payment.PaymentSystemService;
import ParkingApp.Vehicles.Vehicle;
import ParkingApp.Vehicles.VehicleFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Parking System class that parses input file, initializes Parking Lot Objects and translates into events for the parking lot.
 * Inputs of vehicle types car, motorcycle and their respective fees per hour are hardcoded here.
 */
public class ParkingSystem {

    public ParkingSystem(File parkingInputFile)
    {
        // open file and read
        try {
            Scanner scanner = new Scanner(parkingInputFile);

            // parse parking lot definition for number of lots for each vehicle
            String vehicleDefinition = scanner.nextLine();
            String[] noOfVehicleLots = vehicleDefinition.split(" ");

            // hard coded vehicle type input here (Parking Lot Definition)
            HashMap<String, Integer> carAndMotorInput = new HashMap<>();
            carAndMotorInput.put( "car", Integer.parseInt(noOfVehicleLots[0]) );
            carAndMotorInput.put( "motorcycle", Integer.parseInt(noOfVehicleLots[1]) );

            // initialize objects
            ParkingLotService parkingLot = new CarAndMotorParkingLotServiceImpl(carAndMotorInput);
            PaymentSystemService paymentSystem = new CarAndMotorPaymentSystemServiceImpl(parkingLot);
            VehicleFactory vehicleFactory = new VehicleFactory();

            // set payment Fees
            paymentSystem.registerVehicleFee("motorcycle", 1.0);
            paymentSystem.registerVehicleFee("car", 2.0);

            // set payment system for parking lot
            parkingLot.setPaymentSystem(paymentSystem);

            // parse file inputs into events
            while (scanner.hasNextLine()) {
                String parkingOperation = scanner.nextLine();

                String[] vehicleEvent = parkingOperation.split(" ");
//                System.out.println(Arrays.toString(vehicleEvent));
                String operation = vehicleEvent[0];
                if ( isValidOperation(operation) )
                {
                    if ( operation.equals("Enter") && vehicleEvent.length == 4)
                    {
                        String vehicleType = vehicleEvent[1];
                        String vehicleLicensePlate = vehicleEvent[2];
                        Long timeIn = Long.parseLong(vehicleEvent[3]);

                        // initialize vehicle object
                        Vehicle vehicle = vehicleFactory.createVehicle(vehicleType, vehicleLicensePlate, timeIn);
                        parkingLot.enterVehicle(vehicle);

                    } else if (operation.equals("Exit") && vehicleEvent.length == 3)
                    {
                        String vehicleLicensePlate = vehicleEvent[1];
                        Long timeOut = Long.parseLong(vehicleEvent[2]);

                        parkingLot.exitVehicle(vehicleLicensePlate, timeOut);
                    }
                }
                else
                {
                    throw new ParkingException("Parking Lot Exception: Invalid operation on parking lot.");
                }
            }
            System.out.println("Total revenue collected: " + Double.toString(paymentSystem.getTotalRevenue()));
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private boolean isValidOperation(String operation)
    {
        return operation.equals("Enter") || operation.equals("Exit") ;
    }
}
