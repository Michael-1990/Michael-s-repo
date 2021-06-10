/**
 * Interface specifying methods to be used by Bag
 */
public interface BagInterface<T>
{
    /**
     * add specified account to bag
     * @return
     */
    public boolean add(T newEntry);

    /**
     * removes account from bag
     * @return
     */
    public boolean remove();

    /**
     * removes specified account from bag
     * @param anEntry
     */
    public void remove(T anEntry);

    /**
     * searches bag for account specified and returns true
     * or false as appropriate
     * @param anEntry
     * @return
     */
    public boolean contains(T anEntry);

    /**
     * returns number of accounts in bag
     * @return
     */
    public int getSize();

    /**
     * prints accounts in bag to console
     * @return
     */
    public T[] toArray();
}
