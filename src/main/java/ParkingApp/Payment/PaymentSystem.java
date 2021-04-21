package ParkingApp.Payment;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Vehicles.Vehicle;

public interface PaymentSystem {


    Double chargeParkingFee(String vehicleType, Long timeIn, Long timeOut) throws ParkingException;

    Boolean registerVehicleFee(String vehicleType, Double feePerHour) throws ParkingException;

    Boolean updateVehicleFee(String vehicleType, Double feePerHour) throws ParkingException;

    Boolean deregisterVehicleFee(String vehicleType) throws ParkingException;

    Double checkVehicleParkingFee(String vehicleType) throws ParkingException;
}
