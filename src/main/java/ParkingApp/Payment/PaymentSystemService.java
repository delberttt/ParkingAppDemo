package ParkingApp.Payment;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.ParkingLot.ParkingLot;
import ParkingApp.Vehicles.Vehicle;

import java.util.HashMap;

public abstract class PaymentSystemService implements PaymentSystem {


    private long totalRevenue;

    private HashMap<String, Double> vehiclePaymentMap;

    private ParkingLot parkingLot;

    public PaymentSystemService(ParkingLot parkingLot)
    {
        this.totalRevenue = totalRevenue;
        this.vehiclePaymentMap = new HashMap<String, Double>();
        this.parkingLot = parkingLot;
    }

    public long getTotalRevenue()
    {
        return totalRevenue;
    }

    public void setTotalRevenue(long totalRevenue)
    {
        this.totalRevenue = totalRevenue;
    }


    public HashMap<String, Double> getVehiclePaymentMap()
    {
        return vehiclePaymentMap;
    }

    public void setVehiclePaymentMap(HashMap<String, Double> vehiclePaymentMap)
    {
        this.vehiclePaymentMap = vehiclePaymentMap;
    }

    public boolean registerVehicleFee(String vehicleType, double feePerHour) throws ParkingException
    {
        if ( parkingLot.validVehicle(vehicleType) && feePerHour >= 0 )
        {
            vehiclePaymentMap.put(vehicleType, feePerHour);
        } else
        {
            throw new ParkingException("Parking Payment Exception: Failed to register vehicle fee.");
        }
        return true;
    }

    public boolean updateVehicleFee(String vehicleType, double feePerHour) throws ParkingException
    {
        if ( parkingLot.validVehicle(vehicleType) && vehiclePaymentMap.containsKey(vehicleType) && feePerHour >= 0 )
        {
            vehiclePaymentMap.put(vehicleType, feePerHour);
        } else
        {
            throw new ParkingException("Parking Payment Exception: Failed to update vehicle fee.");
        }
        return true;
    }

    public boolean deregisterVehicleFee(String vehicleType) throws ParkingException
    {
        if ( parkingLot.validVehicle(vehicleType) && vehiclePaymentMap.containsKey(vehicleType) )
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

    public double chargeParkingFee(String vehicleType, long timeIn, long timeOut) throws ParkingException
    {
        if ( parkingLot.validVehicle(vehicleType) && vehiclePaymentMap.containsKey(vehicleType) )
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
