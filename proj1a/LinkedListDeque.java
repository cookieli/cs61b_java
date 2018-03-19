public class LinkedListDeque<Item> {
    private  LinkNode sentinelF;//point to the first node
    private  LinkNode sentinelB;//point to the last node
    private int size;

    public class LinkNode {
        public Item item;
        public LinkNode next;
        public LinkNode prev;

        public LinkNode(Item item, LinkNode prev, LinkNode next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
    public LinkedListDeque() {
        this.sentinelF = new LinkNode(null, null, null);
        this.sentinelB = new LinkNode(null, this.sentinelF, null);
        this.sentinelF.next = this.sentinelF;
        this.size = 0;
    }
    public LinkedListDeque(Item i) {
        this.sentinelF = new LinkNode(null, null, null);
        this.sentinelB = new LinkNode(null, null, null);
        this.sentinelF.next = new LinkNode(i, sentinelF,sentinelB);
        this.sentinelB.prev = sentinelF.next;
        this.size = 1;
    }
    public void addFirst(Item item) {
        LinkNode newNode = new LinkNode(item , null, null);
        newNode.next = this.sentinelF.next;
        newNode.next.prev = newNode;
        this.sentinelF.next = newNode;
        newNode.prev = this.sentinelF;
        size += 1;
    }
    public void addLast(Item item) {
        LinkNode newNode = new LinkNode(item, null, null);
        this.sentinelB.prev.next = newNode;
        newNode.prev = this.sentinelB.prev;
        newNode.next = this.sentinelB;
        this.sentinelB.prev = newNode;
        this.size += 1;
    }
    public boolean isEmpty() {
        return this.size == 0;
    }
    public int size() {
        return this.size;
    }
    public Item removeFirst() {
        LinkNode first = this.sentinelF.next;
        this.sentinelF.next = first.next;
        this.sentinelF.next.prev = this.sentinelF;
        first.next = null;
        first.prev = null;
        this.size -= 1;
        return first.item;
    }
    public Item removeLast() {
        LinkNode last = this.sentinelB.prev;
        this.sentinelB.prev = last.prev;
        this.sentinelB.prev.next = this.sentinelB;
        last.next = null;
        last.prev = null;
        this.size -= 1;
        return last.item;
    }
    public Item get(int index) {
        LinkNode newNode = this.sentinelF.next;
        for (int i = 0; i < index ; i++ ) {
            if(newNode.next == this.sentinelB) {
                return null;
            }
            newNode = newNode.next;
        }
        return newNode.item;
    }
    public void printDeque() {
        LinkNode thisNode = this.sentinelF.next;
        while (thisNode != null && thisNode.item != null) {
            System.out.print(thisNode.item.toString() + " ");
            thisNode = thisNode.next;
        }
        System.out.println();
    }
    public LinkNode getRecursive(int index, LinkNode node) {
        if (index == 0) {
            return node;
        } else {
            return getRecursive(index - 1, node.next);
        }
    }
    public Item getRecursive(int index) {
        if (this.size() == 0) return null;
        return this.getRecursive(index, this.sentinelB.next).item;
    }

}