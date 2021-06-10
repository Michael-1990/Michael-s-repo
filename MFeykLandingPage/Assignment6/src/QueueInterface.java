public interface QueueInterface<T> {

    /**
     * adds new entry to queue
     * @param newEntry entry to be added
     */
    public void enqueue(T newEntry);

    /**
     * removes entry from front of queue
     * @return entry
     */
    public T dequeue();

    /**
     * returns entry at front of queue
     * @return front entry
     */
    public T getFront();

    /**
     * returns whether or not queue is empty
     * @return true if empty
     */
    public boolean isEmpty();

    /**
     * removes all entries from queue
     */
    public void clear();

    /**
     * returns size of queue
     * @return queue size
     */
    int size();
}
