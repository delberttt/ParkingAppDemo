package ParkingApp.ParkingException;

/**
 * Implementation of Exception for Parking Application.
 */
public class ParkingException extends Exception {

    public ParkingException()
    {
        super();
    }

    public ParkingException (String errorMessage)
    {
        super(errorMessage);
    }

    public ParkingException (String errorMessage, Throwable err)
    {
        super(errorMessage, err);
    }

}
