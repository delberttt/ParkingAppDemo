package ParkingApp.Vehicles;

/**
 * Abstract class respresenting Vehicles and their attributes.
 * All valid vehicles to be considered in parking lot will extend this class.
 */
public abstract class Vehicle {

    private String licensePlate;    // String identifier for unique vehicles e.g. SGC1234A
    private String vehicleType;     // name of vehicle based on input e.g. car, motorcyle
    private long timeIn;            // timestamp of vehicle entering parking lot e.g. 1613541902

    public Vehicle(String vehicleType, String licensePlate, Long timeIn)
    {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.timeIn = timeIn;
    }

    // GETTER methods for each private attribute, encapsulate private attributes through getters

    public String getLicensePlate()
    {
        return licensePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public long getTimeIn() {
        return timeIn;
    }


}
