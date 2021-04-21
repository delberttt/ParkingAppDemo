package ParkingApp.ParkingLot;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Payment.PaymentSystem;
import ParkingApp.Vehicles.Vehicle;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CarAndMotorParkingLotServiceImpl extends ParkingLotService {

    public CarAndMotorParkingLotServiceImpl(HashMap<String, Integer> parkingLotDefinition) throws ParkingException
    {
        super(parkingLotDefinition);
    }

}
