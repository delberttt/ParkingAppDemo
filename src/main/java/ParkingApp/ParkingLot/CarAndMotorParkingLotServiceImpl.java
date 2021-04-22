package ParkingApp.ParkingLot;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Payment.PaymentSystem;
import ParkingApp.Vehicles.Vehicle;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Concrete implementation of Parking Lot Service that takes in car and motor vehicle types.
 */
public class CarAndMotorParkingLotServiceImpl extends ParkingLotService {

    public CarAndMotorParkingLotServiceImpl(HashMap<String, Integer> parkingLotDefinition) throws ParkingException
    {
        super(parkingLotDefinition);
    }

    // TODO: possible custom configuration based on car and motors e.g. size of lots, etc.
}
