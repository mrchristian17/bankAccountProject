/*
Name: Daniel C Moreno
Date: 02/24/21
Course: CS 3331 – Advanced Object-Oriented Programming - Spring 2021
Instructor: Dr. Daniel Mejia
Assignment: Programming Assignment 2

Honesty Statement:I confirm that the work of this assignment is completely my own.
By turning in this assignment, I declare that I did not receive unauthorized assistance.
Moreover, all deliverables including,
but not limited to the source code, lab report and output files were written and produced by me alone.
 */

/**
 *
 * @author Daniel C Moreno
 * @version 2.0
 * @since February 24, 2021
 *
 * This class contains the main method where the bank is controlled from
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RunBank {
    private static Scanner in = new Scanner(System.in);
    private static InputManager inputManger = new InputManager();

    /**
     * Transaction File Types
     */
    enum FileTransaction {
        PAYS, DEPOSITS, WITHDRAWS, TRANSFERS, INQUIRES
    }

    /**
     *  User Types
     */
    enum UserType {
        /**
         * Bank Manager User
         */
        BANKMANAGER,
        /**
         * Individual User
         */
        INDIVIDUAL
    }

    /**
     *  Transaction types
     */
    enum TransactionType {
        /**
         * Balance
         */
        BALANCE,
        /**
         * Pay
         */
        PAY,
        /**
         * Deposit
         */
        DEPOSIT,
        /**
         * Withdraw
         */
        WITHDRAW,
        /**
         * Transfer
         */
        TRANSFER
    }

    /**
     * Account types
     */
    enum AccountType {
        /**
         * Checking account
         */
        CHECKING,
        /**
         * Saving account
         */
        SAVINGS,
        /**
         * Credit account
         */
        CREDIT
    }

    /**
     * Options: A or B
     */
    enum AB {
        /**
         * A
         */
        A,
        /**
         * B
         */
        B,
        /**
         * C
         */
        C
    }

    /**
     * Yes or No Question responses
     */
    enum YesNo {
        /**
         * Yes option
         */
        YES,
        /**
         * No option
         */
        NO
    }

    /**
     * @param
     * @return customerList
     *
     * Reads file and creates list of Customer objects
     * Currently allocates: First Name,Last Name,Date of Birth,IdentificationNumber,Address,Phone Number,Checking Account Number,Savings Account Number,Credit Account Number,Checking Starting Balance,Savings Starting Balance,Credit Starting Balance
     */
    public static List<Customer> fileReader() {
        List<Customer> accounts = new ArrayList<Customer>();
        String[] header = null;
        try {
            File bankAccountsFile = new File("src/CS 3331 - Bank Users 3.csv");
            Scanner file_reader= new Scanner(bankAccountsFile);
            int count = 0;

            while (file_reader.hasNextLine()) {
                String current_line = file_reader.nextLine();
                //regex used to skip separate data from csv file by comma, EXCEPT when the comma is surrounded by quotes

                if(count == 0) {
                    header = current_line.trim().split(",");
                    count++;
                    continue;
                }
                String[] currentUserData = current_line.trim().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String[] currUserDataSorted = new String[header.length];
                for(int i = 0; i < header.length; i++) {
                    switch(header[i]) {
                        case "First Name":
                            currUserDataSorted[0] = currentUserData[i];
                            break;
                        case "Last Name":
                            currUserDataSorted[1] = currentUserData[i];
                            break;
                        case "Date of Birth":
                            currUserDataSorted[2] = currentUserData[i];
                            break;
                        case "Identification Number":
                            currUserDataSorted[3] = currentUserData[i];
                            break;
                        case "Address":
                            currUserDataSorted[4] = currentUserData[i];
                            break;
                        case "Phone Number":
                            currUserDataSorted[5] = currentUserData[i];
                            break;
                        case "Checking Account Number":
                            currUserDataSorted[6] = currentUserData[i];
                            break;
                        case "Savings Account Number":
                            currUserDataSorted[7] = currentUserData[i];
                            break;
                        case "Credit Account Number":
                            currUserDataSorted[8] = currentUserData[i];
                            break;
                        case "Checking Starting Balance":
                            currUserDataSorted[9] = currentUserData[i];
                            break;
                        case "Savings Starting Balance":
                            currUserDataSorted[10] = currentUserData[i];
                            break;
                        case "Credit Starting Balance":
                            currUserDataSorted[11] = currentUserData[i];
                            break;
                        case "Credit Max":
                            currUserDataSorted[12] = currentUserData[i];
                            break;
                        default:
                            break;
                    }
                }
//                String firstName, String lastName, String dateOfBirth, String identificationNumber,
//                String address, String phoneNumber, long checkingAccountNum, long savingAccountNum,
//                long creditAccountNum,double checkingBalance, double savingBalance, double creditBalance
                accounts.add(new Customer(
                        currUserDataSorted[0],
                        currUserDataSorted[1],
                        //DOB
                        currUserDataSorted[2],
                        //SSN
                        Long.parseLong(currUserDataSorted[3]),
                        //Address
                        currUserDataSorted[4],
                        //Phone number
                        currUserDataSorted[5],
                        //account Numbers (takes out dashes)
                        Long.parseLong(currUserDataSorted[6]),
                        Long.parseLong(currUserDataSorted[7]),
                        Long.parseLong(currUserDataSorted[8]),
                        //balances
                        Double.parseDouble(currUserDataSorted[9]),
                        Double.parseDouble(currUserDataSorted[10]),
                        Double.parseDouble(currUserDataSorted[11]),
                        Double.parseDouble(currUserDataSorted[12])));
            }
            file_reader.close();
            System.out.println("User account information has been downloaded.");
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
        return accounts;
    }

    /**
     * @param
     * @return none
     *
     * Creates a file of bank account transactions
     * Overwrites transactionsReport.txt file when new instance of program is run
     * Has catches in the case that there are errors
     */
    public static void createFile() {
        //Creates transactions file
        try {
            File transactionReport = new File("transactionReport.txt");
            if (transactionReport.createNewFile()) {
                System.out.println("File created: " + transactionReport.getName());
            } else {
                System.out.println("Overwriting previous file: transactionReport.txt\n");
                FileWriter myWriter = new FileWriter("transactionReport.txt", false);
                myWriter.write("");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * @param accounts
     *
     * Executes Transactions Specified in the File
     * if there is an error in the transaction instructions, it will be logged into the
     * transactionreport.txt and will continue through the whole file
     */
    public static void executeFileTransactionActions(List<Customer> accounts) {
        try {
            File bankAccountsFile = new File("src/Transaction Actions.csv");
            Scanner file_reader = new Scanner(bankAccountsFile);

            int first = 0;
            while (file_reader.hasNextLine()) {

                String current_line = file_reader.nextLine();
                String[] currentTransaction = current_line.trim().split(",", -1);
                System.out.println(Arrays.toString(currentTransaction));
                //skips header
                if(first == 0) {
                    first++;
                    continue;
                }
                //all data read from string
                String firstNameUser1 = currentTransaction[0];
                String lastNameUser1 = currentTransaction[1];
                String accountStringUser1 = currentTransaction[2];
                String transactionType = currentTransaction[3];
                String firstNameUser2 = currentTransaction[4];
                String lastNameUser2 = currentTransaction[5];
                String accountStringUser2 = currentTransaction[6];
                String stringAmount = currentTransaction[7];

                double amount = -1;

                IAccount accountUser1 = null;
                IAccount accountUser2 = null;

                //checks to see if file contains a valid transactionType
                if(!inputManger.checkFileTransactionTypeInput(transactionType)) {
                    fileWriter(transactionType + " is not a valid Transaction Type");
                    continue;
                }
                //checks for both users even though one may be used
                List<Customer> usersForTransaction = new ArrayList<Customer>();
                Customer selectedUser1 = checkUserExists(accounts, firstNameUser1, lastNameUser1);
                Customer selectedUser2 = checkUserExists(accounts, firstNameUser2, lastNameUser2);
                usersForTransaction.add(selectedUser1);
                usersForTransaction.add(selectedUser2);

                //logic for loops depending on transaction is intialized
                int count = 0;
                int i = 0;
                if (transactionType.equalsIgnoreCase("PAYS")) {
                    count = 2;
                }
                else if(transactionType.equalsIgnoreCase("TRANSFERS")) {
                    count = 2;
                    selectedUser2 = checkUserExists(accounts, firstNameUser1, lastNameUser2);
                }
                else if (transactionType.equalsIgnoreCase("DEPOSITS")) {
                    count = 2;
                    i = 1;
                } else {
                    count = 1;
                }
                int start = i;
                boolean skip = false;
                //
                for (; i < count && !skip; i++) {
                    String currUserName = "";
                    String currAccount = "";

                    //checks to see if user exists
                    if (i == 0) {
                        if(selectedUser1 == null) {
                            fileWriter("User: " + currUserName + " does not exist.");
                            skip = true;
                            continue;
                        }
                        currUserName = firstNameUser1 + " " + lastNameUser1;
                        currAccount = accountStringUser1;
                    } else {
                        currUserName = firstNameUser2 + " " + lastNameUser2;
                        currAccount = accountStringUser2;
                        if(selectedUser2 == null) {
                            fileWriter("User: " + currUserName + " does not exist.");
                            skip = true;
                            continue;
                        }
                    }

                    //checks if account entered is a valid account type
                    AccountType accountTypeEnum = null;
                    try {
                        accountTypeEnum = AccountType.valueOf(currAccount.toUpperCase());
                        currAccount = accountTypeEnum.name();
                    } catch (IllegalArgumentException e) {
                        fileWriter(currAccount + " is not a valid Account Type");
                        skip = true;
                        continue;
                    }
                    Customer currUserAccount = null;
                    if (i == 0) {
                        currUserAccount = selectedUser1;
                    } else {
                        currUserAccount = selectedUser2;
                    }
                    //checks to see if user has specified account
                    switch (currAccount.toUpperCase()) {
                        case "CHECKING":
                            if (currUserAccount.getChecking() == null) {
                                fileWriter("No " + currAccount + " for user " + selectedUser1.getFullName());
                                skip = true;
                                continue;
                            }
                            if (i == 0) {
                                accountUser1 = currUserAccount.getChecking();
                            } else {
                                accountUser2 = currUserAccount.getChecking();
                            }
                            break;
                        case "SAVINGS":
                            if (currUserAccount.getSaving() == null) {
                                fileWriter("No " + currAccount + " for user " + selectedUser1.getFullName());
                                skip = true;
                                continue;
                            }
                            if (i == 0) {
                                accountUser1 = currUserAccount.getSaving();
                            } else {
                                accountUser2 = currUserAccount.getSaving();
                            }
                            break;
                        case "CREDIT":
                            if (currUserAccount.getCredit() == null) {
                                fileWriter("No " + currAccount + " for user " + selectedUser1.getFullName());
                                skip = true;
                                continue;
                            }
                            if (i == 0) {
                                accountUser1 = currUserAccount.getCredit();
                            } else {
                                accountUser2 = currUserAccount.getCredit();
                            }
                            break;
                        default:
                            fileWriter(currAccount + " is not a valid Account Type for " +
                                    selectedUser1.getFullName());
                            skip = true;
                            continue;
                    }
                }
                if(skip)
                    continue;

                //converts amount to double(except in the case of INQUIRES/Balance
                if(!transactionType.equalsIgnoreCase("INQUIRES")) {
                    try{
                        amount = Double.parseDouble(stringAmount);
                        if(amount < 0) {
                            throw new NumberFormatException();
                        }
                    }
                    catch (NumberFormatException e) {
                        fileWriter("Invalid input: not a valid amount.");
                        continue;
                    }
                }

                //executes proper transaction
                TransactionManager transactionManager = null;
                if(transactionType.equalsIgnoreCase("WITHDRAWS")) {
                    transactionManager = new TransactionManager(selectedUser1, transactionType, accountUser1, amount);
                }
                else if(transactionType.equalsIgnoreCase("DEPOSITS")){
                    transactionManager = new TransactionManager(selectedUser2, transactionType, accountUser2, amount);
                }
                else if(transactionType.equalsIgnoreCase("INQUIRES")) {
                    transactionManager = new TransactionManager(selectedUser1, transactionType, accountUser1);
                }
                else{
                    transactionManager = new TransactionManager(selectedUser1, selectedUser2, transactionType,
                            accountUser1,accountUser2, amount);
                }
                //result is written to file and console
                String transactionResult = transactionManager.executeAndLogTransaction();
                System.out.println("Transaction Results: "+ transactionResult);
                fileWriter(transactionResult);
            }
        }
        catch (FileNotFoundException e) {
                System.out.println("File Not Found");
                e.printStackTrace();
        }

    }

    /**
     * @param currTransaction
     * @return currentTransaction
     *
     * Appends transactions to file
     */
    public static void fileWriter(String currTransaction) {
        try {
            FileWriter myWriter = new FileWriter("transactionReport.txt", true);
            myWriter.write(currTransaction+ "\n");
            myWriter.close();
            System.out.println("Successfully wrote to transaction log.\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * @param customers
     * @return none
     *
     * Writes modified user bank account information to a csv file in the same format as the one that was read
     */
    public static void updatedCSVFileWriter(List<Customer> customers) {
        System.out.println("Writing an updated csv file");
        //Header information
        try (FileWriter csvWriter = new FileWriter("Bank User Modified.csv")) {
            csvWriter.append("First Name");
            csvWriter.append(",");
            csvWriter.append("Last Name");
            csvWriter.append(",");
            csvWriter.append("Date of Birth");
            csvWriter.append(",");
            csvWriter.append("IdentificationNumber");
            csvWriter.append(",");
            csvWriter.append("Address");
            csvWriter.append(",");
            csvWriter.append("Phone Number");
            csvWriter.append(",");
            csvWriter.append("Checking Account Number");
            csvWriter.append(",");
            csvWriter.append("Savings Account Number");
            csvWriter.append(",");
            csvWriter.append("Credit Account Number");
            csvWriter.append(",");
            csvWriter.append("Checking Starting Balance");
            csvWriter.append(",");
            csvWriter.append("Savings Starting Balance");
            csvWriter.append(",");
            csvWriter.append("Credit Starting Balance");
            csvWriter.append("\n");

            //writes all information from customer objects to the csv file
            for (int i = 0; i < customers.size(); i++) {
                Customer currCustomer = customers.get(i);
                csvWriter.append(currCustomer.getFirstName());
                csvWriter.append(",");
                csvWriter.append(currCustomer.getLastName());
                csvWriter.append(",");
                csvWriter.append(currCustomer.getDateOfBirth());
                csvWriter.append(",");
                csvWriter.append(Long.toString(currCustomer.getIdentificationNumber()));
                csvWriter.append(",");
                csvWriter.append(currCustomer.getAddress());
                csvWriter.append(",");
                csvWriter.append(currCustomer.getPhoneNumber());
                csvWriter.append(",");
                if(currCustomer.getChecking() == null)
                    csvWriter.append("-");
                else
                    csvWriter.append(Long.toString(currCustomer.getChecking().getAccountNum()));
                csvWriter.append(",");
                csvWriter.append(Long.toString(currCustomer.getSaving().getAccountNum()));
                csvWriter.append(",");
                if(currCustomer.getCredit() == null)
                    csvWriter.append("-");
                else
                    csvWriter.append(Long.toString(currCustomer.getCredit().getAccountNum()));
                csvWriter.append(",");
                if(currCustomer.getChecking() == null)
                    csvWriter.append("-");
                else
                    csvWriter.append(Double.toString(currCustomer.getChecking().getBalance()));
                csvWriter.append(",");
                csvWriter.append(Double.toString(currCustomer.getSaving().getBalance()));
                csvWriter.append(",");
                if(currCustomer.getCredit() == null)
                    csvWriter.append("-");
                else
                    csvWriter.append(Double.toString(currCustomer.getCredit().getBalance()));
                csvWriter.append("\n");
            }
            //closes csv file
            csvWriter.flush();
            csvWriter.close();
            System.out.println("File writing complete.  File name: \"Bank Account Updated.csv\"");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param accounts
     * @return none
     *
     * Gets the first and last name of a user and finds their account if it exists
     */
    public static Customer findUser(List<Customer> accounts) {
        Customer currUser = null;
        while (currUser == null) {
            //Gets user first and last name
            System.out.println("Please input the following information:");
            System.out.println("First name?");
            String currUserFirstName = checkUserNameInput();
            System.out.println("Last name?");
            String currUserLastName = checkUserNameInput();

            //Based on input, checks if the user exists then returns the appropriate account
            currUser = checkUserExists(accounts, currUserFirstName, currUserLastName);
            if (currUser == null){
                System.out.println("User does not exist");
            }
        }
        return currUser;
    }


    /**
     * @param accounts,userFirstname,userLastName
     * @return customerAccount that is being seaarched for
     *
     * checks if the user exists using the first and last name
     */
    public static Customer checkUserExists(List<Customer> accounts, String userFirstName, String userLastName) {
        Customer currAccount = null;
        for(int i = 0; i < accounts.size(); i++) {
            currAccount = accounts.get(i);
            String currFirstName = currAccount.getFirstName();
            String currLastName = currAccount.getLastName();

            if (currFirstName.equalsIgnoreCase(userFirstName) &&
                    currLastName.equalsIgnoreCase(userLastName)) {
                return currAccount;
            }
        }
        return null;
    }

    /**
     * @param accounts,accountType
     * @return customerAccount or null if account doesn't exist
     *
     * uses account number and account type to find user
     */
    public static Customer checkAccountExists(List<Customer> accounts, String accountType) {
        Customer currAccount = null;
        while (currAccount == null) {
            long inputAccountNum = -1;
            while (true) {
                try {
                    System.out.println("What's the account number?");
                    inputAccountNum = Long.parseLong(in.nextLine());
                    if(inputAccountNum < 0) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: not a valid amount.");
                }
            }
            long currAccountNum = -1;
            Customer tempAccount = null;
            for (int i = 0; i < accounts.size(); i++) {
                tempAccount = accounts.get(i);
                if (accountType.equalsIgnoreCase("CHECKING"))
                    currAccountNum = tempAccount.getChecking().getAccountNum();
                else if (accountType.equalsIgnoreCase("SAVINGS"))
                    currAccountNum = tempAccount.getSaving().getAccountNum();
                else
                    currAccountNum = tempAccount.getCredit().getAccountNum();
                if (currAccountNum == inputAccountNum) {
                    currAccount = tempAccount;
                    break;
                }
            }
            if(currAccount == null) {
                System.out.println("Invalid Account Number for " + accountType);
                System.out.println("Please try again.\n");
            }
        }
        return null;
    }

    /**
     * @param
     * @return inputName wihtout spaces
     *
     * cuts out spaces from user input for name
     */
    public static String checkUserNameInput() {
        String inputName = in.nextLine();
        inputName.replaceAll("\\s","");
        return inputName;
    }

    /**
     * @param accountType,currCustomer
     * @return customerAccount
     *
     * returns specified accountType:
     * Credit vs Saving vs Checking
     */
    public static IAccount findUserAccount(String accountType, Customer currCustomer) {
        switch (accountType) {
            case "CHECKING":
                return currCustomer.getChecking();
            case "SAVINGS":
                return currCustomer.getSaving();
            case "CREDIT":
                return currCustomer.getCredit();
            default:
                return null;
        }
    }

    /**
     * @param accounts
     * @return all info for all accounts
     */
    public static String printAllUserAccountInfo(List<Customer> accounts) {
        String userAccountInfo = "\nInquire all accounts:\n";
        for(int i = 0; i < accounts.size(); i++) {
            userAccountInfo+= accounts.get(i).printAllInfo();
            userAccountInfo += "\n";
        }
        return userAccountInfo;
    }

    /**
     * @param args
     * @return
     *
     * handles the functionality of a bankAccount
     */
    public static void main(String[] args) {
        List<Customer> accounts= fileReader();
//        Comparator<Customer> compareByFullName = (Customer a1, Customer a2) -> a1.getFullName().compareTo( a2.getFullName() );
//        Collections.sort(accounts, compareByFullName);
        createFile();
        executeFileTransactionActions(accounts);


        TransactionManager transactionManager = null;

        //Runs loop that allows user to access different users
        boolean resumeSession = true;
        while(resumeSession){

            System.out.println("What would you like to do?");
            System.out.println("A. Create New User");
            System.out.println("B. Use existing Account");
            System.out.println("C. End Session");
            String startOption = inputManger.checkABInput();

            //creates a new user with minimum requirements
            if(startOption.equals("A")) {
                Customer newAccount = null;
                boolean newUserSession = true;
                while(newUserSession) {
                    Customer lastAccount = null;
                    long idNumber = 1;
                    if(accounts != null) {
                        lastAccount = accounts.get(accounts.size()-1);
                        idNumber = lastAccount.getIdentificationNumber() + 1;
                    }
                    //All information collected
                    System.out.println("Please enter the minimum information required for a new user:");
                    System.out.println("First Name?");
                    String firstName = in.nextLine();
                    System.out.println("Last Name");
                    String lastName = in.nextLine();
                    //Checks to see if user with same first and last name exists
                    //if so then name needs to be reentered
                    if(checkUserExists(accounts, firstName, lastName) != null) {
                        System.out.println("User with this name already exists");
                        continue;
                    }

                    //User info
                    System.out.println("Date of Birth?");
                    System.out.println("dd-MM-yy");
                    String dob = in.nextLine();
                    System.out.println("Current Address?");
                    System.out.println("Address, City, State Zip");
                    System.out.println("Street Number: ");
                    String streetNumber = in.nextLine();
                    System.out.println("Street name: ");
                    String streetName = in.nextLine();
                    System.out.println("State Abbreviation (Texas = TX)");
                    String state = in.nextLine();
                    System.out.println("City");
                    String city = in.nextLine();
                    System.out.println("Zip: ");
                    String zip = in.nextLine();
                    String address = "\""+streetNumber + " " + streetName + ", " + city + ", " + state.toUpperCase()
                            + ", " + zip + "\"";
                    boolean vaildNumber = false;
                    String phoneNumber = "";
                    while(!vaildNumber) {
                        System.out.println("Phone Number?");
                        System.out.println("3-digit area code: ");
                        String area = in.nextLine();
                        System.out.println("7 digit phone number: ");
                        String number = in.nextLine();
                        if (area == null || number == null) {
                            System.out.println("Not a valid phone number...");
                            continue;
                        }
                        try {
                            int intArea = Integer.parseInt(area);
                            int intNumber = Integer.parseInt(number);
                            String combinedNumber = area+number;
                            if(combinedNumber.length() != 10)
                                throw new NumberFormatException();
                        } catch (NumberFormatException e) {
                            System.out.println("Not a valid phone number...");
                            continue;
                        }
                        phoneNumber = "(" + area + ") " + number.substring(0,3) + "-" + number.substring(3,7);
                        break;
                    }
                    //Savings account is mandatory
                    double savingsDeposit = inputManger.checkMoneyInput("deposit into your Savings Account");
                    long savingsAccountNum = lastAccount.getSaving().getAccountNum() + 1;

                    //user can optionally create a checking and credit account
                    //instead of creating a new object, the already created object is modified
                    String createCheckingAccount =
                            inputManger.check_yes_no("Would you like to create a checking account?");
                    double checkingDeposit = -1;
                    long checkingAccountNum = 0;
                    if(createCheckingAccount.equals("YES")) {
                        checkingDeposit = inputManger.checkMoneyInput("deposit into your Checking Account");
                        checkingAccountNum = idNumber + 1000-1;
                    }

                    String createCreditAccount =
                            inputManger.check_yes_no("Would you like to create a credit account?");
                    double creditDeposit = -1;
                    long creditAccountNum = 0;
                    if(createCreditAccount.equals("YES")) {
                        System.out.println("Credit Account are initialized with $0 balance and max of $1000");
                        creditDeposit = 0;
                        creditAccountNum = idNumber + 3000-1;
                    }
                    newAccount = new Customer(firstName, lastName, dob, idNumber, address,
                            phoneNumber, savingsAccountNum, savingsDeposit);
                    if (createCheckingAccount.equals("YES")) {
                        newAccount.setChecking(new Checking(checkingAccountNum,checkingDeposit));
                    }
                    //Credit accounts created with a max of 1000 by default
                    if (createCreditAccount.equals("YES")) {
                        newAccount.setCredit(new Credit(creditAccountNum,creditDeposit, 1000));
                    }
                    if(newAccount != null)
                        break;
                }
                System.out.println("New user created:");
                accounts.add(newAccount);
                continue;
            }
            //Existing account EX: customer or bank manager
            else if (startOption.equals("B")) {
                //Prompts user to identify whether they are a bankmanager or an individual
                System.out.println("What type of user are you?");
                String userType = inputManger.checkUserTypeInput();
                boolean resumeUserSession = true;
                boolean resumeBankManagerSession = true;
                if (userType.equalsIgnoreCase("BANKMANAGER")) {
                    resumeUserSession = false;
                    resumeBankManagerSession = true;
                } else {
                    resumeUserSession = true;
                    resumeBankManagerSession = false;
                }
                //Functionality for bankmanager
                while (resumeBankManagerSession) {
                    System.out.println("“A. Inquire account by name”\n" +
                            "“B. Inquire account by type/number”\n" +
                            "C. Inquire all accounts");
                    String abc = inputManger.checkABInput();
                    String userInfoResults = "";
                    if (abc.equalsIgnoreCase("A")) {
                        System.out.println("Please identify the user:");
                        Customer currBMUser = findUser(accounts);
                        System.out.println("Would you like to:\n" +
                                "A. Inquire accounts\n" +
                                "B. Create a Bank Statement");
                        String bankManagerOptions = inputManger.checkABInput();
                        if(bankManagerOptions.equalsIgnoreCase("A")) {
                            userInfoResults = findUser(accounts).printAllInfo();
                        }
                        else {
                            currBMUser.getBankStatement().writeBankStatementFile();
                            userInfoResults = "Created bankStatement for: " + currBMUser.getFullName();
                        }

                    } else if (abc.equalsIgnoreCase("B")) {
                        System.out.println("What account type?");
                        String accountType = inputManger.checkAccountTypeInput();
                        Customer currCustomer = checkAccountExists(accounts, accountType);
                        if (currCustomer != null)
                            userInfoResults = currCustomer.printAllInfo();
                        else {
                            userInfoResults = "Account does not exist";
                        }
                    } else {
                        System.out.println("Here is an inquiry for all accounts: ");
                        userInfoResults = printAllUserAccountInfo(accounts);
                        userInfoResults += "End of List...";
                    }
                    System.out.println(userInfoResults);
                    fileWriter(userInfoResults);

                    String resumeBankManagerInput =
                            inputManger.check_yes_no("Would you like to get info for another user?");

                    //ends session for current account
                    if (resumeBankManagerInput.equals("NO"))
                        resumeBankManagerSession = false;
                }

                //initializes user to be used for transactions
                Customer currUser = null;
                if (resumeUserSession) {
                    System.out.println("Please identify yourself:");
                    currUser = findUser(accounts);
                }
                //Functionality for individual user
                while (resumeUserSession) {
                    String currTransaction = inputManger.checkTransactionTypeInput();
                    IAccount currAccountType = findUserAccount(inputManger.checkAccountTypeInput(), currUser);
                    if(currAccountType == null) {
                        System.out.println("User does not have this type of account..");
                        continue;
                    }
                    //If PAY is selected, then the user is asked to enter the name of a second user "to pay"
                    if (currTransaction.equalsIgnoreCase("PAY")) {
                        System.out.println("Who would you like to pay?");
                        Customer userToPay = findUser(accounts);
                        if (userToPay == currUser) {
                            System.out.println("Cannot PAY yourself, please select TRANSFER.");
                            continue;
                        }
                        IAccount userToPayAccountType = findUserAccount(inputManger.checkAccountTypeInput(), userToPay);
                        if(userToPayAccountType == null) {
                            System.out.println("User does not have this type of account..");
                            continue;
                        }
                        transactionManager = new TransactionManager(
                                currUser, userToPay, currTransaction, currAccountType,
                                userToPayAccountType, inputManger.checkMoneyInput(currTransaction));
                    }
                    //Transfer uses the same methodology as PAY, but a second account needs to be entered for the same user
                    else if (currTransaction.equalsIgnoreCase("TRANSFER")) {
                        System.out.println("Transfer selected: ");
                        IAccount userToPayAccountType = findUserAccount(inputManger.checkAccountTypeInput(), currUser);
                        if(userToPayAccountType == null) {
                            System.out.println("User does not have this type of account..");
                            continue;
                        }
                        transactionManager = new TransactionManager(
                                currUser, currUser, currTransaction, currAccountType,
                                userToPayAccountType, inputManger.checkMoneyInput(currTransaction));
                    }
                    //returns balance without need for entering an amount variable
                    else if (currTransaction.equalsIgnoreCase("BALANCE")) {
                        transactionManager = new TransactionManager(currUser, currTransaction, currAccountType);
                    }
                    //Withdraw and transfer
                    else {
                        transactionManager = new TransactionManager(
                                currUser, currTransaction, currAccountType, inputManger.checkMoneyInput(currTransaction));
                    }

                    //Writes the transaction executed by the user to the .txt file
                    String transactionResult = transactionManager.executeAndLogTransaction();
                    System.out.println(transactionResult);
                    fileWriter(transactionResult);

                    //Asks the user if they want to continue making transactions
                    String resumeUserInput = inputManger.check_yes_no("Would you like to make another transaction?");

                    //ends session for current account
                    if (resumeUserInput.equals("NO"))
                        resumeUserSession = false;
                }
                //prompts user to log into a different bank account to conduct transactions
                String resumeSessionInput = inputManger.check_yes_no("Login as a new user?");
                if (resumeSessionInput.equals("NO"))
                    resumeSession = false;
            }
            else {
                resumeSession = false;
            }
        }
        //Writes updated CSV File
        updatedCSVFileWriter(accounts);

        //Ends the current session
        System.out.println("Active session closed....  Thank you, come again.");

    }
}
