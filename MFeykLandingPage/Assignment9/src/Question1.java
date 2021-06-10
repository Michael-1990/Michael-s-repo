import java.util.*;

public class Question1 {
    public void DisplayAll(){
        Iterator<String> nameIterator = phoneBook.getKeyIterator();
        Iterator<String> numberIterator = phoneBook.getValueIterator();

        while (nameIterator.hasNext())
            System.out.println(nameIterator.next() + ", " + numberIterator.next());
    }
}
