package ParkingApp.Payment;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.ParkingLot.ParkingLot;
import ParkingApp.Vehicles.Vehicle;

import java.util.HashMap;

public abstract class PaymentSystemService implements PaymentSystem {


    private long totalRevenue;

    private HashMap<Vehicle, Double> vehiclePaymentMap;

    private ParkingLot parkingLot;

    public PaymentSystemService(ParkingLot parkingLot)
    {
        this.totalRevenue = totalRevenue;
        this.vehiclePaymentMap = new HashMap<Vehicle, Double>();
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


    public HashMap<Vehicle, Double> getVehiclePaymentMap()
    {
        return vehiclePaymentMap;
    }

    public void setVehiclePaymentMap(HashMap<Vehicle, Double> vehiclePaymentMap)
    {
        this.vehiclePaymentMap = vehiclePaymentMap;
    }

    public boolean registerVehicleFee(Vehicle v, double feePerHour) throws ParkingException
    {
        if ( parkingLot.validVehicle(v) && feePerHour >= 0 )
        {
            vehiclePaymentMap.put(v, feePerHour);
        } else
        {
            throw new ParkingException("Parking Payment Exception: Failed to register vehicle fee.");
        }
        return true;
    }

    public boolean updateVehicleFee(Vehicle v, double feePerHour) throws ParkingException
    {
        if ( parkingLot.validVehicle(v) && vehiclePaymentMap.containsKey(v) && feePerHour >= 0 )
        {
            vehiclePaymentMap.put(v, feePerHour);
        } else
        {
            throw new ParkingException("Parking Payment Exception: Failed to update vehicle fee.");
        }
        return true;
    }

    public boolean deregisterVehicleFee(Vehicle v) throws ParkingException
    {
        if ( parkingLot.validVehicle(v) && vehiclePaymentMap.containsKey(v) )
        {
            vehiclePaymentMap.remove(v);
        } else
        {
            throw new ParkingException("Parking Payment Exception: Failed to deregister vehicle fee.");
        }
        return true;
    }

    public double checkVehicleParkingFee(Vehicle v) throws ParkingException
    {
        if ( vehiclePaymentMap.containsKey(v) )
        {
            return vehiclePaymentMap.get(v);
        }
        else
        {
            throw new ParkingException("Parking Payment Exception: Failed to check vehicle fee.");
        }
    }

    public double chargeParkingFee(Vehicle v, long timeIn, long timeOut) throws ParkingException
    {
        if ( parkingLot.validVehicle(v) && vehiclePaymentMap.containsKey(v) )
        {
            double feePerHour = vehiclePaymentMap.get(v);
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
