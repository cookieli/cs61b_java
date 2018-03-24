package db;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Row {
    private int order;
    private Map<String,Column> values;


    public Row(int order) {
        this.order = order;
        this.values = new HashMap<>();
    }
    public void addTag(String name) {
        this.values.put(name, null);
    }

    public<T> void add(String name, T value) {
        this.values.put(name, new Column(name, value));
    }


    public int getOrder() {
        return this.order;
    }

    public Object getValue(String name) {
        return this.values.get(name).get(0);
    }


    public int size() {
        return this.values.size();
    }

    public void printRow() {
        System.out.println("order" + this.order + " ");
        Set<String> s = this.values.keySet();
        for (String name:s) {
            System.out.print(name + " ");
        }
        System.out.println();
        for (String name:s) {
            System.out.print(this.getValue(name) + " ");
        }
    }

 /*   @Test
    public void testRow() {
        Row r = new Row(1);
        r.add("a", 1);
        r.add("b","sting");
        r.add("c", 3.2);
        assertEquals(3, r.size());
    }*/
    public static void main (String[] args) {
        Row r = new Row(1);
        r.addTag("la");
        r.add("la","ass");
        System.out.println(r.getValue("la"));

    }

}
