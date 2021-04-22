package ParkingApp.ParkingLot;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Payment.PaymentSystem;
import ParkingApp.Vehicles.Vehicle;
import java.util.*;

/**
 * Abstract class of Parking Lot. Contains general attributes and methods that tracks valid vehicles and events.
 */
public abstract class ParkingLotService implements ParkingLot {

    // Acts as lookup index for valid vehicle types in the following list attributes.
    private HashMap<String, Integer> vehicleLotIndex = new HashMap<>(); // e.g. { "car": 0, "motorcycle": 1 }

    // Actual representation of free/occupied lots based on each type of indexed vehicle.
    private List<int[]> lotList = new ArrayList<>(); // e.g.  [ [0,0,0], [0,0,0,0] ]

    // Tracks actual lot in lotList that vehicle parks in
    private List<HashMap<Vehicle, Integer>> trackVehicleParkingLot = new ArrayList<HashMap<Vehicle, Integer>>(); // [ {"SGX1234A": 0}, {"SGF9283P": 1}, {} ]

    // Keeps track of all vehicles that parked in parking lot based on license plate mapped to actual Vehicle Object
    private HashMap<String, Vehicle> allVehiclesInLot = new HashMap<>();    // {"SGX1234A" : MotorCycle Object, "SGF9283P" : Car Object }

    // Reference to Payment System used to charge vehicles
    private PaymentSystem paymentSystem;



    // Main constructor. ParkingLotDefinition based on input file indicates number of spaces for each vehicle
    public ParkingLotService(HashMap <String, Integer> parkingLotDefinition) throws ParkingException    // e.g. { car: 3, motorcycle: 4 }
    {
        int lotIndexCount = 0;  // index for each unique vehicle type

        // Dynamic creation of parking lots based on vehicle type
        for (String vehicleType : parkingLotDefinition.keySet())
        {
            // populate map of lookup index based on vehicle type
            vehicleLotIndex.put(vehicleType, lotIndexCount);

            // init lot representation to put in lotList
            int maxLotSize = parkingLotDefinition.get(vehicleType);
            int[] parkingSpaces = new int[maxLotSize];
            lotList.add(parkingSpaces);

            //  init maps in trackVehicleParkingLot
            trackVehicleParkingLot.add(new HashMap<Vehicle, Integer>());    // list of empty hashmaps


            lotIndexCount++;
        }
    }

    // Alternative constructor to manually init private tracking/storage attributes
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

    // GETTER methods for private attributes in Parking Lot
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

    // SET payment system. Need to initialize and set payment system before vehicles can enter.
    public void setPaymentSystem(PaymentSystem paymentSystem)
    {
        this.paymentSystem = paymentSystem;
    }

    // Check if vehicle type is valid and supported by parking lot
    public boolean isValidVehicleType(String vehicleType)
    {
        if ( !vehicleType.isEmpty() && vehicleLotIndex.containsKey(vehicleType) )
        {
            return true;
        }
        return false;
    }

    // Check if vehicle with given license plate is parked in parking lot
    public boolean isVehicleInParkingLot(String licensePlate)
    {
        if ( !licensePlate.isEmpty() && allVehiclesInLot.containsKey(licensePlate) )
        {
                return true;
        }
        return false;
    }

    // Event of vehicle entering parking lot. Takes in Vehicle Object that is instantiated based on enter/exit event
    public void enterVehicle(Vehicle vehicle) throws ParkingException
    {
        if ( paymentSystem == null )
        {
            throw new ParkingException("Parking Lot Exception: Failed to complete parking lot operation, please set payment system.");
        }

        if ( vehicle != null)
        {
            String vehicleType = vehicle.getVehicleType();
            String vehicleLicensePlate = vehicle.getLicensePlate();

            if ( isValidVehicleType( vehicleType ) && !isVehicleInParkingLot(vehicleLicensePlate) )
            {
                try {
                    // map vehicle to index
                    int vehicleIndex = vehicleLotIndex.get(vehicleType);

                    // list of lots for vehicle type
                    int[] vehicleLots = lotList.get(vehicleIndex);

                    // map containing currently parked vehicles for vehicle type
                    HashMap<Vehicle, Integer> vehiclePark = trackVehicleParkingLot.get(vehicleIndex);

                    // find available lot returns index in vehicleLots
                    int foundLotIndex = findAvailableLot(vehicleLots);

                    if (foundLotIndex < 0) {
                        System.out.println("Reject");
                    }
                    else {
                        // insertion process into parking lot attributes
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
            else
            {
                throw new ParkingException("Parking Lot Exception: Failed to enter vehicle into parking lot, invalid vehicle type or licensePlate.");
            }
        } else
        {
            throw new ParkingException("Parking Lot Exception: Failed to enter vehicle into parking lot, vehicle empty.");
        }
    }

    // Event of vehicle exiting parking lot. Takes in vehicle license plate and time of exit based on exit event
    public void exitVehicle(String vehicleLicensePlate, Long timeOut) throws ParkingException
    {
        if ( paymentSystem == null )
        {
            throw new ParkingException("Parking Lot Exception: Failed to complete parking lot operation, please set payment system.");
        }

        if ( !vehicleLicensePlate.isEmpty() && isVehicleInParkingLot(vehicleLicensePlate) && timeOut != null)
        {
            try {
                // find vehicle object in parking lot
                Vehicle vehicle = allVehiclesInLot.get(vehicleLicensePlate);

                if (vehicle.getTimeIn() > timeOut)
                {
                    throw new ParkingException("Parking Lot Exception: Failed to complete parking lot operation, invalid exit time set.");
                }

                String vehicleType = vehicle.getVehicleType();

                // map vehicle to index
                int vehicleIndex = vehicleLotIndex.get(vehicleType);

                // list of lots for vehicle type
                int[] vehicleLots = lotList.get(vehicleIndex);

                // map containing currently parked vehicles for vehicle type
                HashMap<Vehicle, Integer> vehiclePark = trackVehicleParkingLot.get(vehicleIndex);

                // removal process out of parking lot attributes
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
        else {
            throw new ParkingException("Parking Lot Exception: Unable to remove vehicle from parking lot, invalid vehicle details or vehicle not in lot.");
        }
    }

    // Helper method to insert vehicle object into parking lot attributes
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

    // Helper method to remove vehicle object into parking lot attributes
    private int removeVehicleFromLot(Vehicle vehicle, int[] vehicleLots, HashMap<Vehicle, Integer> vehiclePark)
    {
        try {

            int parkedLotIndex = vehiclePark.get(vehicle);

            vehicleLots[parkedLotIndex] = 0;
            vehiclePark.remove(vehicle);

            // remove from vehicle license tracking
            allVehiclesInLot.remove(vehicle.getLicensePlate());

            return parkedLotIndex;
        } catch (Exception e)
        {
            throw e;
        }

    }

    // Helper method to find lowest index with 0
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
