package ParkingApp.Payment;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Vehicles.Vehicle;

/**
 * Interface of Parking Lot Payment System. Contains necessary methods to track and charge vehicle payments.
 */
public interface PaymentSystem {

    double chargeParkingFee(String vehicleType, Long timeIn, Long timeOut) throws ParkingException;

    boolean registerVehicleFee(String vehicleType, Double feePerHour) throws ParkingException;

    boolean deregisterVehicleFee(String vehicleType) throws ParkingException;

    double checkVehicleParkingFee(String vehicleType) throws ParkingException;
}
