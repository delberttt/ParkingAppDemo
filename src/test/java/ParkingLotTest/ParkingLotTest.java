package ParkingLotTest;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.ParkingLot.CarAndMotorParkingLotServiceImpl;
import ParkingApp.ParkingLot.ParkingLot;
import ParkingApp.ParkingLot.ParkingLotService;
import ParkingApp.Payment.CarAndMotorPaymentSystemServiceImpl;
import ParkingApp.Payment.PaymentSystem;
import ParkingApp.Payment.PaymentSystemService;
import ParkingApp.Vehicles.Vehicle;
import ParkingApp.Vehicles.VehicleFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    PaymentSystemService paymentSystem;
    ParkingLotService parkingLot;
    VehicleFactory vehicleFactory;
    Vehicle car;

    @BeforeEach
    public void init() throws ParkingException
    {
        // init parking lot
        HashMap<String, Integer> carAndMotorInput = new HashMap<>();
        carAndMotorInput.put( "car", 3 );
        carAndMotorInput.put( "motorcycle", 4 );

        parkingLot = new CarAndMotorParkingLotServiceImpl(carAndMotorInput);

        // init payment system
        paymentSystem = new CarAndMotorPaymentSystemServiceImpl(parkingLot);

        paymentSystem.registerVehicleFee("motorcycle", 1.0);
        paymentSystem.registerVehicleFee("car", 2.0);

        parkingLot.setPaymentSystem(paymentSystem);

        //init vehicle factory
        vehicleFactory = new VehicleFactory();
        // init vehicle
        car = vehicleFactory.createVehicle("car", "SGX1234A", 1613541902l);

        parkingLot.enterVehicle(car);
    }

    @Test
    public void checkValidVehicleType()
    {
        assertTrue(parkingLot.isValidVehicleType("car"));
        assertTrue(parkingLot.isValidVehicleType("motorcycle"));
    }

    @Test
    public void checkVehicleInParkingLot()
    {
        assertTrue(parkingLot.isVehicleInParkingLot(car.getLicensePlate()));
    }

    @Test
    public void enteringVehicle() throws ParkingException
    {
        Vehicle motorcycle = vehicleFactory.createVehicle("motorcycle", "SGX9876A", 1613541902l);
        parkingLot.enterVehicle(motorcycle);
        String licensePlate = motorcycle.getLicensePlate();

        HashMap<String, Vehicle> allVehiclesInLot = parkingLot.getAllVehiclesInLot();
        assertTrue(allVehiclesInLot.containsKey(licensePlate));

    }

    @Test
    public void exitingVehicle() throws ParkingException
    {
        assertTrue(parkingLot.isVehicleInParkingLot(car.getLicensePlate()));
        parkingLot.exitVehicle(car.getLicensePlate(),1613541904l);
        HashMap<String, Vehicle> allVehiclesInLot = parkingLot.getAllVehiclesInLot();
        assertFalse(allVehiclesInLot.containsKey(car.getLicensePlate()));
    }


}
