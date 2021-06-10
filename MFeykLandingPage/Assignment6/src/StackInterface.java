public interface StackInterface<T> {
    /**
     * add entry to stack
     * @param newEntry entry to add to stack
     */
    public void push(T newEntry);

    /**
     * remove and return entry from stack
     * @return removed entry
     */
    public T pop();

    /**
     * return entry from top of stack without removing
     * @return entry from top of stack
     */
    public T peek();

    /**
     * returns true if stack is empty
     * @return True if stack is empty
     */
    public boolean isEmpty();

    /**
     * removes all entries from stack
     */
    public void clear();

}
