package db;

import java.util.ArrayList;

import java.util.HashSet;

public class Column<Item> {
    private String name;
    private ArrayList<Item> items;
    private Type type;
    private HashSet<Integer> NanSet;
    private HashSet<Integer> noValueSet;


    public Column(String name, Type type) {
        this.name = name;
        this.type = type;
        items = new ArrayList<>();
        NanSet = new HashSet<>();
        noValueSet = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int size() {
        return items.size();
    }

    public void add(Item t) {
        items.add(t);
    }

    public void add(Type t) {
        items.add(null);
        if (t == Type.NaN)
            NanSet.add(size() - 1);
        else if (t == Type.NOVALUE)
            noValueSet.add(size() - 1);
        else
            throw new IllegalArgumentException("not Nan or NOVALUE");
    }

    public String get(int row) {
        if(containsNoValue(row))
            return Type.NOVALUE.getDecription();
        if(containsNan(row))
            return Type.NaN.getDecription();
        return String.valueOf(items.get(row));
    }

    public boolean containsNan(int row) {
        return NanSet.contains(row);
    }

    public boolean containsNoValue(int row) {
        return noValueSet.contains(row);
    }

    public Item remove(int row) {
        if(row >= size())
            throw new IllegalArgumentException("row number is greater than size");
        if(containsNoValue(row)){
//            items.remove(row);
            noValueSet.remove(row);
        } else if(containsNan(row)){
//            items.remove(row);
            NanSet.remove(row);
        }
        return items.remove(row);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        String s = name + "\\" + type.getDecription() + "\n";
        for (int i = 0; i < size(); i++) {
            if (containsNoValue(i)) {
                s += Type.NOVALUE + "\n";
                continue;
            } else if (containsNan(i)) {
                s += Type.NaN + "\n";
                continue;
            } else {
                s += get(i) + "\n";
            }
        }
        return s;
    }

    public static void main(String[] args) {
        Column<Integer> c = new Column<>("s", Type.INT);
        c.add(3);
        c.add(5);
        c.add(Type.NaN);
        System.out.println(c);
    }
}
