import java.util.*;

public class QueueStack implements StackInterface{

    private Deque<Object> deque;
    private static final int DEFAULT_CAPACITY = 10;

    public QueueStack(){
        deque = new ArrayDeque<Object>();
    }

    /**
     * add entry to stack
     *
     * @param newEntry entry to add to stack
     */
    @Override
    public void push(Object newEntry) {
        this.deque.push(newEntry);

        if(this.deque.size() == DEFAULT_CAPACITY){
            this.deque.removeLast();
        }
    }

    /**
     * remove and return entry from stack
     *
     * @return removed entry
     */
    @Override
    public Object pop() {
        return this.deque.pop();
    }

    /**
     * return entry from top of stack without removing
     *
     * @return entry from top of stack
     */
    @Override
    public Object peek() {
        return this.deque.peek();
    }

    /**
     * returns true if stack is empty
     *
     * @return True if stack is empty
     */
    @Override
    public boolean isEmpty() {
        return this.deque.isEmpty();
    }

    /**
     * removes all entries from stack
     */
    @Override
    public void clear() {
        this.deque.clear();
    }

    /**
     * Question 2
     * a)
     *
     * public void clearEach() {
     *     while(!isEmpty()){
     *         dequeue();
     *     }
     * }
     *
     * b)
     *
     * public void clearEachData(){
     *     while(queueNode.getNextNode() != null){
     *         queueNode.setData(null);
     *         queueNode = queueNode = getNextNode();
     *     }
     * }
     */
}
