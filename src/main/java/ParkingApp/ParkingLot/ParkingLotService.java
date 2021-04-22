package ParkingApp.ParkingLot;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Payment.PaymentSystem;
import ParkingApp.Vehicles.Vehicle;

import java.util.*;

public class ParkingLotService implements ParkingLot {

    private HashMap<String, Integer> vehicleLotIndex = new HashMap<>(); // { "car":0, "motor":1, "truck":2}
    private List<int[]> lotList = new ArrayList<>(); // [ [0,0,0], [0,0], [0] ]

    // track actual lot vehicle parks in
    private List<HashMap<Vehicle, Integer>> trackVehicleParkingLot = new ArrayList<HashMap<Vehicle, Integer>>();
    // [ {"SGX1234A": 0}, {"SGF9283P": 1}, {} ]

    // keeps track of all vehicles that is in lot based on license plate
    private HashMap<String, Vehicle> allVehiclesInLot = new HashMap<>();

    private PaymentSystem paymentSystem;

    public HashMap<String, Integer> getVehicleLotIndex() {
        return vehicleLotIndex;
    }

    public List<int[]> getLotList() {
        return lotList;
    }

    public List<HashMap<Vehicle, Integer>> getTrackVehicleParkingLot() {
        return trackVehicleParkingLot;
    }

    public HashMap<String, Vehicle> getAllVehiclesInLot() {
        return allVehiclesInLot;
    }

    public ParkingLotService(HashMap <String, Integer> parkingLotDefinition) throws ParkingException
    {
        // assume input: { car: 3, motor: 2, truck: 1}
        // TODO: check validity of parking lot definition
        // dynamic creation of parking lots based on type of vehicles
        int count = 0;
        for (String vehicleType : parkingLotDefinition.keySet())
        {
            vehicleLotIndex.put(vehicleType, count);

            int maxLotSize = parkingLotDefinition.get(vehicleType);
            int[] parkingSpaces = new int[maxLotSize];
            lotList.add(parkingSpaces);

            for (int i=0; i < maxLotSize; i++)
            {
                trackVehicleParkingLot.add(new HashMap<Vehicle, Integer>());
            }

            count++;
        }
    }

    // alternative constructor
    public ParkingLotService(HashMap<String, Integer> vehicleLotIndex, List<int[]> lotList, List<HashMap<Vehicle, Integer>> trackVehicleParkingLot, HashMap<String, Vehicle> allVehiclesInLot) throws ParkingException
    {
        if (vehicleLotIndex != null && lotList != null && trackVehicleParkingLot != null && allVehiclesInLot != null)
        {
            this.vehicleLotIndex = vehicleLotIndex;
            this.lotList = lotList;
            this.trackVehicleParkingLot = trackVehicleParkingLot;
            this.allVehiclesInLot = allVehiclesInLot;
        } else
        {
            throw new ParkingException("Parking Lot Exception: Not able to initialize parking lot, missing details.");
        }

    }



    public void setPaymentSystem(PaymentSystem paymentSystem)
    {
        this.paymentSystem = paymentSystem;
    }

    public boolean isValidVehicleType(String vehicleType)
    {
        if ( !vehicleType.isEmpty() && vehicleLotIndex.containsKey(vehicleType) )
        {
            return true;
        }
        return false;
    }

    public boolean isVehicleInParkingLot(String licensePlate)
    {
        if ( !licensePlate.isEmpty() && allVehiclesInLot.containsKey(licensePlate) )
        {
                return true;
        }
        return false;
    }

    public void enterVehicle(Vehicle vehicle) throws ParkingException
    {
        if ( paymentSystem == null )
        {
            throw new ParkingException("Parking Lot Exception: Failed to complete parking lot operation, please set payment system.");
        }

        if ( vehicle != null)
        {
            String vehicleType = vehicle.getVehicleType();
            if ( isValidVehicleType( vehicleType ) )
            {
                try {
                    String vehicleLicensePlate = vehicle.getLicensePlate();

                    // map vehicle to lot
                    int vehicleIndex = vehicleLotIndex.get(vehicleType);
                    int[] vehicleLots = lotList.get(vehicleIndex);
                    HashMap<Vehicle, Integer> vehiclePark = trackVehicleParkingLot.get(vehicleIndex);
                    int foundLotIndex = findAvailableLot(vehicleLots);

                    if (foundLotIndex < 0) {
                        System.out.println("Reject");
                        //                    throw new ParkingException("Parking Lot Exception: Failed to enter vehicle into parking lot, all lots full.");
                    }
                    else {
                        // insertion process
                        insertVehicleIntoLot(vehicle, vehicleLots, vehiclePark, foundLotIndex);

                        // print vehicle insertion result
                        String printIndex = Integer.toString(foundLotIndex + 1);
                        System.out.println("Accept " + vehicleType + "Lot" + printIndex);
                    }



                } catch (Exception e)
                {
                    e.printStackTrace();
                    throw new ParkingException("Parking Lot Exception: Vehicle unable to enter parking lot.");
                }
            }
        } else
        {
            throw new ParkingException("Parking Lot Exception: Failed to enter vehicle into parking lot, vehicle empty.");
        }
    }

    public void exitVehicle(String vehicleLicensePlate, Long timeOut) throws ParkingException
    {
        if ( paymentSystem == null )
        {
            throw new ParkingException("Parking Lot Exception: Failed to complete parking lot operation, please set payment system.");
        }

        if ( !vehicleLicensePlate.isEmpty() && isVehicleInParkingLot(vehicleLicensePlate) && timeOut != null)
        {

            try {
                // map vehicle to lot
                Vehicle vehicle = allVehiclesInLot.get(vehicleLicensePlate);

                if (vehicle.getTimeIn() > timeOut)
                {
                    throw new ParkingException("Parking Lot Exception: Failed to complete parking lot operation, invalid time out set.");
                }

                String vehicleType = vehicle.getVehicleType();

                int vehicleIndex = vehicleLotIndex.get(vehicleType);
                int[] vehicleLots = lotList.get(vehicleIndex);
                HashMap<Vehicle, Integer> vehiclePark = trackVehicleParkingLot.get(vehicleIndex);

                // removal process
                Integer removedLotIndex = removeVehicleFromLot(vehicle, vehicleLots, vehiclePark);

                // charge vehicle parking fee
                Double parkingFee = paymentSystem.chargeParkingFee(vehicleType, vehicle.getTimeIn(), timeOut);
                System.out.println(vehicleType + "Lot" + Integer.toString(removedLotIndex+1) + " " + Double.toString(parkingFee));


            } catch(Exception e)
            {
                e.printStackTrace();
                throw new ParkingException("Parking Lot Exception: Unable to remove vehicle from parking lot, missing or invalid exit details.");
            }
        }

    }

    private int insertVehicleIntoLot(Vehicle vehicle, int[] vehicleLots, HashMap<Vehicle, Integer> vehiclePark, int foundLotIndex)
    {
        try {
            vehicleLots[foundLotIndex] = 1;
            vehiclePark.put(vehicle, foundLotIndex);
            allVehiclesInLot.put(vehicle.getLicensePlate(), vehicle);
        } catch (Exception e)
        {
            throw e;
        }
        return foundLotIndex;
    }

    private int removeVehicleFromLot(Vehicle vehicle, int[] vehicleLots, HashMap<Vehicle, Integer> vehiclePark)
    {
        try {
            // unmark from vehicle lots
            int parkedLotIndex = vehiclePark.get(vehicle);
            vehicleLots[parkedLotIndex] = 0;

            // remove from vehicle park
            vehiclePark.remove(vehicle);

            // remove from vehicle license tracking
            allVehiclesInLot.remove(vehicle.getLicensePlate());

            return parkedLotIndex;
        } catch (Exception e)
        {
            throw e;
        }

    }

    //algorithm to find lowest index with 0
    private int findAvailableLot(int[] vehicleLot)
    {
        for (int i=0; i < vehicleLot.length; i++)
        {
            if ( vehicleLot[i] == 0)
            {
                return i;
            }
        }
        return -1;
    }

}
