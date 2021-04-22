package VehicleTest;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Vehicles.Car;
import ParkingApp.Vehicles.MotorCycle;
import ParkingApp.Vehicles.Vehicle;
import ParkingApp.Vehicles.VehicleFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VehicleCreationTest {

    private VehicleFactory vehicleFactory = new VehicleFactory();;

    @Test
    public void createCar() throws ParkingException
    {
        Vehicle car = vehicleFactory.createVehicle("car", "SGX1234A", 100020l);
        assertTrue(car instanceof Vehicle);
        assertTrue(car instanceof Car);
    }

    @Test
    public void createMotorcycle() throws ParkingException
    {
        Vehicle motorcycle = vehicleFactory.createVehicle("motorcycle", "SGX9876A", 100032l);
        assertTrue(motorcycle instanceof Vehicle);
        assertTrue(motorcycle instanceof MotorCycle);
    }

    @Test
    public void createDirectCar()
    {
//        Car car = new Car("car", )
    }

    @Test
    public void createVehicleMissingDetails()
    {
        Exception e = assertThrows(ParkingException.class, () -> vehicleFactory.createVehicle("", "", 10000l));
        String expectedMessage = "Vehicle Exception: Failed to create vehicle, vehicle detail missing.";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createVehicleInvalidDetails()
    {
        Exception e = assertThrows(ParkingException.class, () -> vehicleFactory.createVehicle("boat", "AD3214G", 10000l));
        String expectedMessage = "Vehicle Exception: Failed to create vehicle, invalid vehicle.";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
