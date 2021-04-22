package ParkingApp.Payment;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.ParkingLot.ParkingLot;

/**
 * Concrete implementation of Payment System for Car and Motor Parking Lot.
 */
public class CarAndMotorPaymentSystemServiceImpl extends PaymentSystemService {

    public CarAndMotorPaymentSystemServiceImpl(ParkingLot parkingLot) throws ParkingException
    {
        super(parkingLot);
    }

    // TODO: possible custom calculations for Payment System e.g. discounts, etc.

}
