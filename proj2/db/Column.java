package db;

import java.util.ArrayList;
import java.util.List;

public class Column<Item> {
    private String name;
    private List<Item> col;

    public Column(String name) {
        this.name = name;
        this.col = new ArrayList<>();
    }
    public Column(String name, Item data) {
        this(name);
        this.col.add(data);
    }


    public void add(Item data) {
        this.col.add(data);
    }



    public Item get(int i) {
        return this.col.get(i);
    }


    public void printCol() {
        System.out.println("contents of " + this.name + " " +  this.col);
    }


    public int indexOf(Item item) {
        return this.col.indexOf(item);
    }


    public int size() {
        return this.col.size();
    }

    public static void main(String[] args) {
        Column<String> c = new Column<>("A");
        c.add("sdada");
        c.add("hello");
        c.add("aaaaa");
        for(int i = 0; i < c.size(); i++) {
            System.out.print(c.get(i) + " " + c.indexOf(c.get(i)) + " ");
        }
        c.printCol();

    }
}
