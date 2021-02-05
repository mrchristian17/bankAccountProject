import java.text.DecimalFormat;

public class Checking extends AccountAbstract{

    //default constructor
    public Checking() {
        super();
    }

    //Constructors
    public Checking(double balance, double interest) {
       super(balance, interest);
    }

}
