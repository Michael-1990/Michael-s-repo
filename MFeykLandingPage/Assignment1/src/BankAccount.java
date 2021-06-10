import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Superclass BankAccount
 */
public class BankAccount {
    public String accountNum;
    public String createDate;
    public double balance;
    private String type;

    static String bankName;
    private static int currentNumber = 0;

    /**
     * two-argument constructor
     * @param actNo
     * @param crtDt
     */

    public BankAccount(String actNo, String crtDt){
        accountNum = actNo;
        createDate = crtDt;
    }

    /**
     * four-argument constructor
     * @param actNo
     * @param crtDt
     * @param bl
     * @param tp
     */

    public BankAccount(String actNo, String crtDt, double bl, String tp){
        accountNum = actNo;
        createDate = crtDt;
        balance = bl;
        type = tp;
    }

    public String getAccountNum() {
        return accountNum;
    }

    /**
     * data field getters
     */

    public String getCreateDate() {
        return createDate;
    }

    public double getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }

    public static String getBankName() {
        return bankName;
    }

    public static int getCurrentNumber() {
        return currentNumber;
    }

    /**
     * data field setters
     *
     */

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static void setBankName(String bankName) {
        BankAccount.bankName = bankName;
    }

    public static void setCurrentNumber() {
        BankAccount.currentNumber++;
    }

    @Override
    public String toString()
    {
        String newString = "";
        newString += getType() + " Number: " + getAccountNum() + "\n"
                + "Create Date: " + getCreateDate() + "\n"
                + "AccountBalance: $" + getBalance() + "\n" + "\n";

        return newString;
    }

    /**
    * Main method
    */

    public static void main(String[] args){
        System.out.println("Welcome Message");
        System.out.println("Please Specify account type and balance: ");

        BankAccount[] accounts = new BankAccount[2];

        Scanner scanner = new Scanner(System.in);
        while(currentNumber < 2) {
            System.out.println("Account Type:");
            String type = scanner.next();

            System.out.println("Account Balance: ");
            double balance = scanner.nextDouble();

            currentNumber++;
            if (type.equals("checking")) {
                CheckingAccount cAccount1 = new CheckingAccount("" + currentNumber, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), balance, type);
                accounts[currentNumber - 1] = cAccount1;
            } else if (type.equals("savings")) {
                SavingsAccount sAccount1 = new SavingsAccount("" + currentNumber, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), balance, type);
                accounts[currentNumber - 1] = sAccount1;
            }
        }

            System.out.println("Account 1:");
            System.out.println(accounts[0].getType());
            System.out.println(accounts[0].getCreateDate());
            System.out.println(accounts[0].getBalance());

            System.out.println("Account 2:");
            System.out.println(accounts[1].getType());
            System.out.println(accounts[1].getCreateDate());
            System.out.println(accounts[1].getBalance());



    }
}
