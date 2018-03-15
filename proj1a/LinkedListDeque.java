public class LinkedListDeque<Item> {
    private  LinkNode sentinelF;
    private  LinkNode sentinelB;
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

    }
    public LinkedListDeque(Item i) {
        sentinelF = new LinkNode(null, null, null);
        sentinelB = new LinkNode(null, null, null);
        sentinelF.next = new LinkNode(i, null,null);
        sentinelB.prev = sentinelF.next;
        size = 1;
    }
}