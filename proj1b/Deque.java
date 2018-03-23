public interface Deque<Item> {
    public void addFirst(Item t);

    public void addLast(Item t);

    public boolean isEmpty();

    public int size();

    public void printDeque();

    public Item removeFirst();

    public Item removeLast();

    public Item get(int index);

}