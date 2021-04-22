package PaymentTest;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.ParkingLot.ParkingLotService;
import ParkingApp.Payment.CarAndMotorPaymentSystemServiceImpl;
import ParkingApp.Payment.PaymentSystem;
import ParkingApp.Payment.PaymentSystemService;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentSystemTest {

    PaymentSystemService paymentSystem;
    ParkingLotService parkingLotService;
    @BeforeEach
    public void init() throws ParkingException
    {
        //init parking lot
        HashMap<String, Integer> carAndMotorInput = new HashMap<>();
        carAndMotorInput.put( "car", 3 );
        carAndMotorInput.put( "motorcycle", 4 );

        parkingLotService = new ParkingLotService(carAndMotorInput);

        // init payment system
        paymentSystem = new CarAndMotorPaymentSystemServiceImpl(parkingLotService);

        parkingLotService.setPaymentSystem(paymentSystem);

        paymentSystem.registerVehicleFee("motorcycle", 1.0);
    }

    @Test
    public void inputVehicleFee() throws ParkingException
    {
        String vehicleType = "car";
        Double feePerHour = 2.0;
        paymentSystem.registerVehicleFee(vehicleType,  feePerHour);
        assertEquals(paymentSystem.getVehiclePaymentMap().get(vehicleType), feePerHour);

    }

    @Test
    public void removeVehicleFee() throws ParkingException
    {
        String vehicleType = "motorcycle";
        paymentSystem.deregisterVehicleFee(vehicleType);
        assertFalse(paymentSystem.getVehiclePaymentMap().containsKey(vehicleType));
    }

    @Test
    public void checkVehicleFee() throws ParkingException
    {
        String vehicleType = "motorcycle";
        assertEquals(paymentSystem.checkVehicleParkingFee(vehicleType), 1.0);
    }

    @Test
    public void retrieveRevenue()
    {
        Long totalRevenue = paymentSystem.getTotalRevenue();

        assertTrue(totalRevenue instanceof Long);
    }

    @Test
    public void calculateParkingFee() throws ParkingException
    {
        long timeIn = 1613541902;
        long timeOut = 1613545602;

        assertEquals(paymentSystem.chargeParkingFee("motorcycle", timeIn, timeOut), 2.0);
    }

}
