package ParkingApp.Payment;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.ParkingLot.ParkingLot;

public class CarAndMotorPaymentSystemServiceImpl extends PaymentSystemService {

    public CarAndMotorPaymentSystemServiceImpl(ParkingLot parkingLot) throws ParkingException
    {
        super(parkingLot);
    }


}
