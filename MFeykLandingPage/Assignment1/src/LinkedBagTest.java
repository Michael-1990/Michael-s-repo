import java.text.SimpleDateFormat;
import java.util.Date;

public class LinkedBagTest {
    public static void main(String[] args)
    {
        LinkedBag bag = new LinkedBag();

        CheckingAccount account1 = new CheckingAccount("" + 1, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), 300.00, "checking");
        SavingsAccount account2 = new SavingsAccount("" + 2, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), 150.00, "savings");
        SavingsAccount account3 = new SavingsAccount("" + 3, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), 150.00, "savings");
        CheckingAccount account4 = new CheckingAccount("" + 4, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), 100.00, "checking");

        bag.add(account1);
        bag.add(account2);
        bag.add(account3);
        bag.add(account4);

        bag.remove();

        Object[] bArray = bag.toArray();

        for(int i = 0; i < bArray.length; i++)
        {
            System.out.print(bArray[i]);
        }

    }
}
