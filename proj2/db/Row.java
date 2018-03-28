package db;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Row {
    private int order;

    private int colNum = 0;

    private List<String> names;

    private Map<String, Integer> row;

    public Row(int order, String ...names) {
        this.order = order;
        this.row = new HashMap<>();
        this.names = new ArrayList<>();
        this.colNum = names.length;
        for(int i = 0; i < names.length; i++) {
            this.names.add(names[i]);
            this.row.put(names[i] ,0);
        }
    }
    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getColNum() {
        return this.colNum;
    }



    public void addCol(String name, Integer val) {
        this.names.add(name);
        this.row.put(name,val);
        this.colNum += 1;
    }


    public void addValues(Integer ...values) {
        if(values.length != this.names.size()) {
            System.out.println("names can't match values");
            return;
        }
        for(int i = 0; i < values.length; i++) {
            this.row.put(this.names.get(i), values[i]);
        }
    }
    public List<String> getNames() {
        return this.names;
    }

    public Integer getValue(int i) {
        return this.row.get(this.names.get(i));
    }

    public Integer getValue(String name) {return this.row.get(name);}

    public Integer[] getAllValues() {
        Integer[] temp = new Integer[this.colNum];
        for(int i = 0; i < this.colNum; i++) {
            temp[i] = this.getValue(i);
        }
        return temp;
    }

    public void printRow() {
        System.out.println(this.getNames());
        Integer[] temp = this.getAllValues();
        for(int i = 0 ; i < temp.length ; i++) {
            System.out.print(temp[i] + " , ");
        }
        System.out.println();
    }


    public static Row contactRows(Row r1, Row r2) {
        Row temp = new Row(0);
        temp.names.addAll(r1.names);
        temp.names.addAll(r2.names);
        temp.row.putAll(r1.row);
        temp.row.putAll(r2.row);
        temp.setColNum(r1.getColNum() + r2.getColNum());
        return temp;
    }

    public static Row unionRows(Row r1, Row r2) {
        Row temp = new Row(0);
        for(String r1_name:r1.getNames()) {
            for(String r2_name: r2.getNames()) {
                if(r1_name.equals(r2_name)) {
                    if(!r1.getValue(r1_name).equals(r2.getValue(r2_name))) return null;
                    temp.addCol(r1_name,r1.getValue(r1_name));
                }
            }
        }
        return temp;
    }

    public static Row mergeRows(Row r1, Row r2) {
        Row temp = Row.unionRows(r1,r2);
        if(temp == null )  return null;
        else if (temp.getColNum() == 0) return Row.contactRows(r1,r2);
        for(String name: r1.getNames()) {
            if (!isTagInRow(name,temp)) temp.addCol(name, r1.getValue(name));
        }
        for(String name: r2.getNames()) {
            if (!isTagInRow(name, temp)) temp.addCol(name, r2.getValue(name));
        }
        return temp;

    }

    public static boolean isTagInRow(String tag, Row row) {
        if(row.getNames().contains(tag)) return true;
        return false;
    }

}
