public abstract class PersonAbstract {
    //Generic attributes to a person
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private long identificationNumber;
    private String address;
    private String phoneNumber;

    /**
     * default constructor
     */
    public PersonAbstract() {

    }

    /**
     *
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param identificationNumber
     * @param address
     * @param phoneNumber
     *
     * constructor for data read from csv file
     */
    public PersonAbstract(String firstName, String lastName, String dateOfBirth, long identificationNumber,
                          String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.identificationNumber = identificationNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    /**
     *
     * @return person's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * {@link PersonAbstract#getFirstName()}
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return person's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * {@link PersonAbstract#getLastName()}
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return person's first and last name space seperated
     */
    public String getFullName() { return this.firstName + " " + this.lastName;}

    /**
     *
     * @return person's date of birth as string
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * {@link PersonAbstract#getDateOfBirth()}
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     *
     * @return person's identification number
     */
    public long getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     * {@link PersonAbstract#getIdentificationNumber()}
     */
    public void setIdentificationNumber(long identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    /**
     *
     * @return person's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * {@link PersonAbstract#getAddress()}
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return person's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * {@link PersonAbstract#getPhoneNumber()}
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
