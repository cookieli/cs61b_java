public class LinkedListDeque<Item> {
    private  class LinkNode {
        public Item item;
        public LinkNode next;
        public LinkNode prev;

        public LinkNode(Item item, LinkNode prev, LinkNode next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
    private LinkNode sentinel;
    private int size;
    public LinkedListDeque() {
        this.sentinel = new LinkNode(null,null,null);
        this.sentinel.prev = this.sentinel;
        this.sentinel.next = this.sentinel;
        this.size = 0;
    }
    public void addFirst(Item item) {
        LinkNode newNode = new LinkNode(item, null, null);
        newNode.next = this.sentinel.next;
        this.sentinel.next = newNode;
        newNode.prev = this.sentinel;
        newNode.next.prev = newNode;
        this.size += 1;

    }
    public void addLast(Item item) {
        LinkNode newNode = new LinkNode(item, null, null);
        newNode.prev = this.sentinel.prev;
        this.sentinel.prev = newNode;
        newNode.next = this.sentinel;
        newNode.prev.next = newNode;
        this.size += 1;
    }
    public boolean isEmpty() {
        return this.size == 0;
    }
    public int size() {
        return this.size;
    }
    public Item removeFirst() {
        LinkNode first = this.sentinel.next;
        this.sentinel.next = first.next;
        first.next.prev = this.sentinel;
        first.next = null;
        first.prev = null;
        this.size -= 1;
        return first.item;
    }
    public Item removeLast() {
        LinkNode last = this.sentinel.prev;
        this.sentinel.prev = last.prev;
        last.prev.next = this.sentinel;
        last.next = null;
        last.prev = null;
        this.size -= 1;
        return last.item;
    }
    public Item get(int index) {
        if(index > this.size())  return null;
        LinkNode cursor = this.sentinel.next;
        for(int i = 0; i < index; i++) {
            if(cursor.next == null)  return null;
            cursor = cursor.next;
        }
        return cursor.item;
    }
    public Item getRecursive(int index) {
        if(index > this.size())  return null;
        if(getRecursive(this.sentinel.next, index) == null)  return null;
        return getRecursive(this.sentinel.next, index).item;
    }
    public LinkNode getRecursive(LinkNode node, int index) {
        if(index == 0)  return node;
        if(node == null) return null;
        return getRecursive(node.next, index - 1);
    }
    public void printDeque() {
        LinkNode cursor = this.sentinel.next;
        while(cursor!= this.sentinel) {
            System.out.print(cursor.item.toString());
            cursor = cursor.next;
        }
    }

}