package ParkingApp.ParkingLot;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Vehicles.Vehicle;

public interface ParkingLot {

    Boolean isValidVehicleType(String vehicleType);

    Boolean isVehicleInParkingLot(String licensePlate);

    void enterVehicle(Vehicle vehicle) throws ParkingException;

    void exitVehicle(String vehicleLicensePlate, Long timeOut) throws ParkingException;

}
