package ParkingApp.Vehicles;

public abstract class Vehicle {

    private String licensePlate;
    private String vehicleType;
    private long timeIn;

    public Vehicle(String vehicleType, String licensePlate, Long timeIn)
    {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.timeIn = timeIn;
    }

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
