import com.sun.jmx.remote.internal.ArrayQueue;

import java.lang.reflect.Array;
import java.util.*;

public class MyArrayQueue implements QueueInterface{
    private Queue<Object> queue;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayQueue(){
        this.queue = (Queue<Object>) new ArrayQueue<Object>(DEFAULT_CAPACITY);
    }

    /**
     * adds new entry to queue
     *
     * @param newEntry entry to be added
     */
    @Override
    public void enqueue(Object newEntry) {
        this.queue.add(newEntry);
    }

    /**
     * removes entry from front of queue
     *
     * @return entry
     */
    @Override
    public Object dequeue() {
        return this.queue.remove();
    }

    /**
     * returns entry at front of queue
     *
     * @return front entry
     */
    @Override
    public Object getFront() {
        return this.queue.peek();
    }

    /**
     * returns whether or not queue is empty
     *
     * @return true if empty
     */
    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    /**
     * removes all entries from queue
     */
    @Override
    public void clear() {
        this.queue.clear();
    }

    /**
     * returns size of queue
     *
     * @return queue size
     */
    @Override
    public int size() {
        return this.queue.size();
    }

    /**
     * joins second queue to first queue
     * @param secondQueue
     */
    public void join(QueueInterface<Object> secondQueue){
        Queue<Object> joinedQueue = (Queue<Object>) new ArrayQueue<Object>(this.queue.size() + secondQueue.size());

        while(!this.queue.isEmpty()){
            joinedQueue.add(this.queue.remove());
        }

        while(!secondQueue.isEmpty()){
            joinedQueue.add(secondQueue.dequeue());
        }

        this.queue = joinedQueue;
    }
}
