public class ArrayDeque<Item> {
    private Item[] array;
    private int size;
    private int front;
    private int back;
    public ArrayDeque() {
        this.array = (Item[]) new Object[8];
        this.front = 0;
        this.back  = 0;
        this.size  = 0;
    }
    public void resize(int length) {
        Item[] temp = (Item[]) new Object[length];
        for(int i = this.front, j = 0; j < this.size; j++) {
            i = (i + 1) % this.array.length;
            temp[j] = this.array[i];
        }
        this.array = temp;
        this.front = this.array.length - 1;
        this.back = this.size;
    }
    public void addFirst(Item item) {
        if(this.size == this.array.length) resize(2*this.array.length);
        this.array[this.front] = item;
        this.front = (this.front-1 + this.array.length) % this.array.length;
        this.size += 1;
    }
    public void addLast(Item item) {
        if(this.size == this.array.length) resize(2*this.array.length);
        this.array[this.back] = item;
        this.back = (this.back + 1 + this.array.length) % this.array.length;
        this.size += 1;
    }
    public int size() {
        return this.size;
    }
    public boolean isEmpty() {
        return this.size == 0;
    }
    public void printDeque() {
        int cursor = this.front ;
        for (int i = 0; i < this.size; i++) {
            cursor = (cursor + 1) % this.array.length;
            System.out.print(this.array[cursor].toString());
        }
    }

    public Item removeFirst() {
        if(this.size == 0)  return null;
        this.front = (this.front + 1 ) % this.array.length;
        Item temp = this.array[this.front];
        this.array[this.front] = null;
        this.size -= 1;
        if(this.size > 0 && this.size == this.array.length / 4) resize(this.array.length/2);
        return temp;
    }

    public Item removeLast() {
        if(this.size == 0)  return null;
        this.back = (this.back - 1 + this.array.length) % this.array.length;
        Item temp = this.array[this.back];
        this.array[this.back] = null;
        this.size -= 1;
        if(this.size > 0 && this.size == this.array.length / 4) resize(this.array.length/2);
        return temp;
    }
    public Item get(int index) {
        int i = (this.front + 1 + index) % this.array.length;
        return this.array[i];
    }
}