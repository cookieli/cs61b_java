import java.util.LinkedList;

/**
 * Isn't this solution kinda... cheating? Yes.
 * The aesthete will be especially alarmed by the fact that this
 * supposed ArrayDeque is actually using a LinkedList. SAD!
 */
public class ArrayDequeSolution<Item> extends LinkedList<Item> implements Deque<Item> {
    @Override
    public void printDeque() {
        for(int i = 0; i < this.size(); i++) {
            System.out.print(this.get(i).toString() + " ");
        }
        System.out.println();
    }

    public Item getRecursive(int i) {
        return get(i);
    }
    @Override
    public Item removeFirst() {
        try {
            return super.removeFirst();
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public Item removeLast() {
        try {
            return super.removeLast();
        } catch (Exception e) {
            return null;
        }
    }
}
