package ParkingApp.Payment;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.ParkingLot.ParkingLot;
import ParkingApp.Vehicles.Vehicle;
import java.util.HashMap;

/**
 * Abstract class of Payment System. Contains general attributes and methods to track and charge vehicle payments.
 */
public abstract class PaymentSystemService implements PaymentSystem {

    private long totalRevenue;  // Stores all vehicle charges in totalRevenue

    private HashMap<String, Double> vehiclePaymentMap;  // Tracks valid vehicles mapped to each of their fee per hour

    private ParkingLot parkingLot;  // Reference to ParkingLot interface

    // Constructor for Payment System Service. Must first initialize parking lot to be attached to.
    public PaymentSystemService(ParkingLot parkingLot) throws ParkingException
    {
        this.totalRevenue = 0;
        this.vehiclePaymentMap = new HashMap<String, Double>();
        if (parkingLot != null)
        {
            this.parkingLot = parkingLot;
        } else
        {
            throw new ParkingException("Payment System Exception: Cannot initialize payment system, parking lot cannot be empty");
        }
    }

    // GETTER methods for private Payment System Service attributes
    public ParkingLot getParkingLot()
    {
        return parkingLot;
    }

    public long getTotalRevenue()
    {
        return totalRevenue;
    }

    public HashMap<String, Double> getVehiclePaymentMap()
    {
        return vehiclePaymentMap;
    }

    //  Track valid vehicle type and corresponding hourly fee to be tracked by Payment System
    public boolean registerVehicleFee(String vehicleType, Double feePerHour) throws ParkingException
    {
        if ( parkingLot.isValidVehicleType(vehicleType) && feePerHour >= 0 )
        {
            vehiclePaymentMap.put(vehicleType, feePerHour);
        } else
        {
            throw new ParkingException("Parking Payment Exception: Failed to register vehicle fee.");
        }
        return true;
    }

    //  Untrack vehicle type and corresponding hourly fee from vehiclePaymentMap
    public boolean deregisterVehicleFee(String vehicleType) throws ParkingException
    {
        if ( parkingLot.isValidVehicleType(vehicleType) && vehiclePaymentMap.containsKey(vehicleType) )
        {
            vehiclePaymentMap.remove(vehicleType);
        } else
        {
            throw new ParkingException("Parking Payment Exception: Failed to deregister vehicle fee.");
        }
        return true;
    }

    // Check hourly fee of valid vehicle type that is currently tracked in vehiclePaymentMap
    public double checkVehicleParkingFee(String vehicleType) throws ParkingException
    {
        if ( vehiclePaymentMap.containsKey(vehicleType) )
        {
            return vehiclePaymentMap.get(vehicleType);
        }
        else
        {
            throw new ParkingException("Parking Payment Exception: Failed to check vehicle fee.");
        }
    }

    // Charge exiting vehicle based on hourly rate and parking duration. Called by Parking Lot when vehicle is exiting.
    public double chargeParkingFee(String vehicleType, Long timeIn, Long timeOut) throws ParkingException
    {
        if ( parkingLot.isValidVehicleType(vehicleType) && vehiclePaymentMap.containsKey(vehicleType) )
        {
            double feePerHour = vehiclePaymentMap.get(vehicleType);
            double feeCharged = this.calculateParkingFee(feePerHour, timeIn, timeOut);

            // Check for valid fee charged
            if ( feeCharged >= 0 )
            {
                totalRevenue += feeCharged;
                return feeCharged;
            } else {
                throw new ParkingException("Parking Payment Exception: Failed to register vehicle fee, invalid fee.");
            }

        } else
        {
            throw new ParkingException("Parking Payment Exception: Failed to charge vehicle fee, invalid vehicle.");
        }
    }

    // Helper method to calculate fee charge based on duration parked
    private Double calculateParkingFee(double feePerHour, long timeIn, long timeOut)
    {
        double parkingDurationSeconds = timeOut - timeIn;
        double parkingDurationHours = Math.ceil( parkingDurationSeconds / 3600 );   // ROUND UP to nearest hour
        System.out.println("parking duration: " + Double.toString(parkingDurationSeconds));
        return feePerHour * parkingDurationHours;
    }


}
