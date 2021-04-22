package ParkingApp.ParkingLot;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Vehicles.Vehicle;

/**
 * Interface of Parking Lot with methods to track valid vehicles and register events of vehicles entering & exiting
 */
public interface ParkingLot {

    boolean isValidVehicleType(String vehicleType);

    boolean isVehicleInParkingLot(String licensePlate);

    void enterVehicle(Vehicle vehicle) throws ParkingException;

    void exitVehicle(String vehicleLicensePlate, Long timeOut) throws ParkingException;

}
