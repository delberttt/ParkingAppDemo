package ParkingApp.Payment;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Vehicles.Vehicle;

public interface PaymentSystem {
    
    double chargeParkingFee(Vehicle vehicle, long timeIn, long timeOut) throws ParkingException;

    boolean registerVehicleFee(Vehicle vehicle, double feePerHour) throws ParkingException;

    boolean updateVehicleFee(Vehicle vehicle, double feePerHour) throws ParkingException;

    boolean deregisterVehicleFee(Vehicle vehicle) throws ParkingException;

    double checkVehicleParkingFee(Vehicle vehicle) throws ParkingException;
}
