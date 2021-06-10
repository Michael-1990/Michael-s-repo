/**
 * ArrayBag class implements BagInterface and specifies methods
 * @param <T>
 */
public final class ArrayBag<T> implements BagInterface<T>
{
    private final T[] bag;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayBag()
    {
        this(DEFAULT_CAPACITY);
    }

    public ArrayBag(int capacity){
        @SuppressWarnings("unchecked")
        T[] tempBag = (T[])new Object[capacity];
        bag = tempBag;
        numberOfEntries = 0;
    }

    /**
     * add specified account to bag
     *
     * @param newEntry
     * @return
     */
    @Override
    public boolean add(T newEntry) {
        if (!(numberOfEntries >= bag.length))
        {
            bag[numberOfEntries] = newEntry;
            numberOfEntries++;
        }
        return false;
    }

    /**
     * removes account from bag
     * @return
     */
    @Override
    public boolean remove() {
        if (numberOfEntries > 0)
        {
            bag[numberOfEntries - 1] = null;
            numberOfEntries--;
        }
        return true;
    }

    /**
     * removes specified account from bag
     *
     * @param anEntry
     */
    @Override
    public void remove(T anEntry) {
        boolean stillLooking = true;
        int index = 0;

        while (stillLooking && index < numberOfEntries )
        {
            if (anEntry.equals(bag[index]))
            {
                bag[index] = bag[numberOfEntries - 1];
                stillLooking = false;
            }
            index++;

        }
        remove();
    }

    /**
     * searches bag for account specified and returns true
     * or false as appropriate
     *
     * @param anEntry
     * @return
     */
    @Override
    public boolean contains(T anEntry) {
        boolean contains = false;
        int index = 0;

        while(!contains && (index < numberOfEntries))
        {
            if(anEntry.equals(bag[index]))
            {
                contains = true;
            }
            index++;
        }

        return  contains;
    }

    /**
     * returns number of accounts in bag
     *
     * @return
     */
    @Override
    public int getSize() {
        return numberOfEntries;
    }

    /**
     * prints accounts in bag to console
     * @return
     */
    @Override
    public T[] toArray()
    {
        @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[numberOfEntries];
        for (int index = 0; index < numberOfEntries; index++)
        {
            result[index] = bag[index];
        }
        return result;
    }
}
