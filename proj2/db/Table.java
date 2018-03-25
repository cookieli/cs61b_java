package db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Table {
   // public List<Column> cols;
    public List<String> tags;
    public String name;
    public Map<String,Column<Integer>> bind;
    public int rowNum;
    public int colNum;


    public Table (String name, String ...tags) {
        this.name = name;
        this.tags = new LinkedList<>();
        //this.cols = new LinkedList<>();
        this.bind = new HashMap<>();
        for(int i = 0; i < tags.length; i++) {
            this.tags.add(tags[i]);
            //this.cols.add(new Column(tags[i]));
            this.bind.put(tags[i],new Column<>(tags[i]));
        }
        this.colNum = tags.length;
        this.rowNum = 0;

    }

    public int getColNum() {
        return this.colNum;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public void insert (Integer ...values) {
        if(values.length != tags.size()) {
            System.out.println("values' length can't map tags");
            return;
        }
        for(int i = 0; i < values.length; i++) {
            bind.get(tags.get(i)).add(values[i]);
        }
        this.rowNum += 1;
    }


    public Integer get(String tag, int index) {
        Column<Integer> col = this.bind.get(tag);
        return col.get(index);
    }


    public Column<Integer> getCol(String tag) {
        return this.bind.get(tag);
    }


    public void printTable() {
        System.out.println(this.name);
        for (String tag: this.tags) {
            System.out.print(tag + " ");
        }
        System.out.println();
        for (int i = 0; i < this.rowNum; i++) {
            for (String tag: this.tags) {
                System.out.print(this.get(tag, i) + " ");
            }
            System.out.println();
        }

    }


    public static void main(String[] args) {
        Table t = new Table("a","first","second");
        t.insert(1,2);
        t.insert(3,4);
        t.printTable();
    }
}
