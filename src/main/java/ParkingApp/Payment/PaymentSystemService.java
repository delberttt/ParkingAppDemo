package ParkingApp.Payment;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.ParkingLot.ParkingLot;
import ParkingApp.Vehicles.Vehicle;

import java.util.HashMap;

public abstract class PaymentSystemService implements PaymentSystem {


    private long totalRevenue;

    private HashMap<String, Double> vehiclePaymentMap;

    private ParkingLot parkingLot;

    public PaymentSystemService(ParkingLot parkingLot) throws ParkingException
    {
        this.totalRevenue = totalRevenue;
        this.vehiclePaymentMap = new HashMap<String, Double>();
        if (parkingLot != null)
        {
            this.parkingLot = parkingLot;
        } else
        {
            throw new ParkingException("Payment System Exception: Cannot initialize payment system, parking lot cannot be empty");
        }


    }

    public ParkingLot getParkingLot()
    {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot)
    {
        this.parkingLot = parkingLot;
    }

    public long getTotalRevenue()
    {
        return totalRevenue;
    }

    public HashMap<String, Double> getVehiclePaymentMap()
    {
        return vehiclePaymentMap;
    }

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

    public double chargeParkingFee(String vehicleType, Long timeIn, Long timeOut) throws ParkingException
    {
        if ( parkingLot.isValidVehicleType(vehicleType) && vehiclePaymentMap.containsKey(vehicleType) )
        {
            double feePerHour = vehiclePaymentMap.get(vehicleType);
            double feeCharged = this.calculateParkingFee(feePerHour, timeIn, timeOut);


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

    private double calculateParkingFee(double feePerHour, long timeIn, long timeOut)
    {
        double parkingDurationSeconds = timeOut - timeIn;
        double parkingDurationHours = Math.ceil( parkingDurationSeconds / 3600 );

        return feePerHour * parkingDurationHours;
    }


}
