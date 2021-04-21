package ParkingApp.Payment;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Vehicles.Vehicle;

public interface PaymentSystem {


    double chargeParkingFee(String vehicleType, long timeIn, long timeOut) throws ParkingException;

    boolean registerVehicleFee(String vehicleType, double feePerHour) throws ParkingException;

    boolean updateVehicleFee(String vehicleType, double feePerHour) throws ParkingException;

    boolean deregisterVehicleFee(String vehicleType) throws ParkingException;

    double checkVehicleParkingFee(String vehicleType) throws ParkingException;
}
