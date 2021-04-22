package ParkingApp.ParkingLot;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Vehicles.Vehicle;

public interface ParkingLot {

    boolean isValidVehicleType(String vehicleType);

    boolean isVehicleInParkingLot(String licensePlate);

    void enterVehicle(Vehicle vehicle) throws ParkingException;

    void exitVehicle(String vehicleLicensePlate, Long timeOut) throws ParkingException;

}
