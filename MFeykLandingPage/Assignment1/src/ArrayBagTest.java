import java.text.SimpleDateFormat;
import java.util.Date;

public class ArrayBagTest {
    public static void main(String[] args)
    {
        ArrayBag bag = new ArrayBag();

        CheckingAccount account1 = new CheckingAccount("" + 1, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), 300.00, "checking");
        SavingsAccount account2 = new SavingsAccount("" + 2, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), 150.00, "savings");
        SavingsAccount account3 = new SavingsAccount("" + 3, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), 150.00, "savings");
        CheckingAccount account4 = new CheckingAccount("" + 4, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), 100.00, "checking");

        bag.add(account1);
        bag.add(account2);
        bag.add(account3);
        bag.add(account4);

        Object[] bArray = bag.toArray();

        for(int i = 0; i < bArray.length; i++)
        {
            System.out.print(bArray[i]);
        }

        System.out.println("Bag size: " + bag.getSize());

        bag.remove();

        System.out.println("Bag size: " + bag.getSize());

        bag.remove(account2);

        System.out.println("Bag size: " + bag.getSize());

        System.out.println("Accounts with balance greater than $200: \n");
        bag.remove();

        bArray = bag.toArray();

        for(int i = 0; i < bArray.length; i++)
        {
            if ((((BankAccount) bArray[i]).getBalance() >= 200.00))
            System.out.println(bArray[i]);
        }
    }
}
