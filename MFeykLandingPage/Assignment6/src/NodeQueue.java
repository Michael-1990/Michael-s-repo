import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NodeQueue implements QueueInterface{
    private Node first;
    private Node last;

    private class Node{
        private Object data;
        private Node next;
    }
    private ConcurrentLinkedQueue<Object> queue;

    public NodeQueue(){
        first = null;
        last = null;
        this.queue = new ConcurrentLinkedQueue<>();
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

    public void join(NodeQueue NodeQueue2){
        this.last.next = NodeQueue2.first;
    }
}
