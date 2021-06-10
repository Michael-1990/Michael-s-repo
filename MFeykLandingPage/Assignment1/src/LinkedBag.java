public final class LinkedBag<T> implements BagInterface<T>{
    /**
     * add specified account to bag
     *
     * @param newEntry
     */

    private int numNodes = 0;
    private Node firstNode;

    public LinkedBag(){};

    /**
     * add specified account to bag
     *
     * @param newEntry
     * @return
     */
    @Override
    public boolean add(Object newEntry) {
        return false;
    }
    public boolean add(BankAccount newEntry) {
        Node newNode = new Node(newEntry);
        newNode.setNext(firstNode);
        firstNode = newNode;
        numNodes ++;

        return true;
    }

    /**
     * removes account from bag
     * @return
     */
    @Override
    public boolean remove() {
        boolean removed = false;
        if (firstNode != null)
        {
            firstNode = firstNode.next;
            numNodes--;
            removed = true;
        }
        return removed;
    }

    /**
     * removes specified account from bag
     *
     * @param anEntry
     */
    @Override
    public void remove(Object anEntry) {

    }
    public boolean remove(BankAccount anEntry){
        boolean removed = false;
        boolean found = false;
        Node currentNode = firstNode;
        while (!found && (currentNode != null))
        {
            if (anEntry.equals(currentNode.getData()))
            {
                found = true;
                currentNode.setData(firstNode.getData());
                removed = this.remove();
            }
            else
            {
                currentNode = currentNode.getNext();
            }
        }
        return removed;
    }

    /**
     * searches bag for account specified and returns true
     * or false as appropriate
     *
     * @param anEntry
     * @return
     */
    @Override
    public boolean contains(Object anEntry) {
        return false;
    }
    public boolean contains(BankAccount anEntry){
        boolean found = false;
        Node currentNode = firstNode;
        while (!found && (currentNode != null))
        {
            if (anEntry.equals((currentNode.getData())))
                found = true;
            else
                currentNode = currentNode.getNext();
        }
        return found;
    }

    /**
     * returns number of accounts in bag
     *
     * @return
     */
    @Override
    public int getSize() {
        return numNodes;
    }

    /**
     * prints accounts in bag to console
     * @return
     */
    @Override
    public T[] toArray(){
        @SuppressWarnings("unchecked")
        T[] accounts = (T[])new Object[numNodes];

        int index = 0;
        Node currentNode = firstNode;
        while ((index < numNodes) && (currentNode != null))
        {
            accounts[index] = (T) currentNode.getData();
            index++;
            currentNode = currentNode.getNext();
        }
        return accounts;
    }

    /**
     * Inner class Node
     */
    private class Node {
        private BankAccount data;
        private Node next;

        public Node(){};
        public Node(BankAccount acct){this(acct, null);}
        public Node (BankAccount acct, Node next)
        {
            data = acct;
            this.next = next;
        }

        public void setData(BankAccount acct)
        {
            data = acct;
        }
        public void setNext(Node next) {
            this.next = next;
        }

        public BankAccount getData() {
            return data;
        }

        public Node getNext() {
            return next;
        }
    }
}
