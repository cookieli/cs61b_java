package db;


import db.comparision.Comparision;
import db.operation.Operation;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Table {
    private String name;
    private ArrayList<String> tags;
    private ArrayList<Column> cols;
    private ArrayList<Type> types;
    private int colNum;
    private int rowNum;
    private HashMap<String, Integer> tagsToIndex;

    public Table(String name) {
        this.name = name;
        tags = new ArrayList<>();
        cols = new ArrayList<>();
        types = new ArrayList<>();
        tagsToIndex = new HashMap<>();
        colNum = 0;
        rowNum = 0;
    }

    public Table(String name, List<Column> columnList) {
        this(name);
        for(Column c: columnList) {
            AddColumn(c);
        }
    }

    public int getRowNum() {
        return rowNum;
    }
    public int getColNum() {
        return colNum;
    }

    public void AddColumn(Column c) {
        String tag = c.getName();
        Type type = c.getType();
        tags.add(tag);
        types.add(type);
        cols.add(c);
        tagsToIndex.put(tag, colNum);
        colNum++;
        if(rowNum == 0 && colNum == 1)
            rowNum = c.size();
        else if(rowNum != c.size())
            throw new IllegalArgumentException("Column's size can't match rowNum");

    }
    public void setName(String name){
        this.name = name;
    }

    public boolean containsColumn(String name){
        return tags.contains(name);
    }

    public void AddColumnFromTable(Table joined, String colName){
        Column c = joined.cols(joined.tagsToIndex.get(colName));
        AddColumn(c);
    }

    public Column getColumn(String colName){
        return cols(this.tagsToIndex.get(colName));
    }

    public Column removeColumn(String Colname) {
        if(!tags.contains(Colname))
            return null;
        int index = tagsToIndex.get(Colname);
        Column c = cols.get(index);
        tags.remove(index);
        cols.remove(index);
        types.remove(index);
        tagsToIndex.remove(Colname);
        colNum--;
        return c;
    }

    public void removeRow(int row) {
        if(row >= rowNum)
            throw new IllegalArgumentException("row is greater than rownum");
        for(int i = 0; i < colNum; i++){
            cols.get(i).remove(row);
        }
        rowNum--;
    }
    public void AddColumn(String colName, Type colType) {
        if (colType == Type.STRING){
            Column<String> c = new Column<>(colName, colType);
            AddColumn(c);
        } else if(colType == Type.INT){
            Column<Integer> c = new Column<>(colName, colType);
            AddColumn(c);
        } else {
            Column<Float> c = new Column<>(colName, colType);
            AddColumn(c);
        }
    }
    public void evaluateArithmeticExpression(Table joined, Operation operation, String firstOperand, String secondOperand, String resulName){
        Column c1 = joined.cols(joined.tagsToIndex.get(firstOperand));
        Column result;
        if(joined.tagsToIndex.containsKey(secondOperand)){
            Column c2 = joined.cols(joined.tagsToIndex.get(secondOperand));
            result = operation.operation(resulName, c1, c2);
        } else{
            result = operation.operation(resulName, c1, secondOperand);
        }
        this.AddColumn(result);
    }

    public void makeComparision(Comparision comparision, String[] condition){
        Column c1 = cols(tagsToIndex.get(condition[0]));

        if(tagsToIndex.containsKey(condition[2])) {
            Column c2 = cols(tagsToIndex.get(condition[2]));
            for(int i = 0; i < c1.size(); i++) {
                if (!comparision.compare(c1, c2, i)) {
                    removeRow(i);
                    i--;
                }
            }
        } else {
            for(int i = 0; i < c1.size(); i++){
                if(!comparision.compare(c1, condition[2], i)){
                    removeRow(i);
                    i--;
                }
            }
        }
    }

    public Column cols(int i) {
        return cols.get(i);
    }

    public String tags(int i) {
        return tags.get(i);
    }

    public Type types(int i) {
        return types.get(i);
    }

    public Object getValue(int rowNum, int colNum){
        return cols(colNum).get(rowNum);
    }

    public Object getValue(String tag, int rowNum){
        int colNum = tagsToIndex.get(tag);
        return getValue(rowNum, colNum);
    }

    public static boolean containsSameTag(Table t1, Table t2){
        for(String tag: t1.tags){
            if(t2.tags.contains(tag))
                return true;
        }
        return false;
    }

    public static List<String> findSameTags(Table t1, Table t2) {
        List<String> tags = new ArrayList<>();
        for(String tag: t1.tags){
            if(t2.tags.contains(tag))
                tags.add(tag);
        }
        return tags;
    }

    public static List<String> mergeRowShared(Table t1, Table t2, List<String> sameTags, int row1, int row2){
        boolean canMerged = true;
        for(String tag : sameTags){
            if(!t1.getValue(tag, row1).equals(t2.getValue(tag, row2))){
                canMerged = false;
                break;
            }
        }
        if(canMerged) {
            List<String>  l= new ArrayList<>();
            for(String tag: sameTags){
                l.add(String.valueOf(t1.getValue(tag, row1)));
            }
            for(String tag: t1.tags) {
                if(!sameTags.contains(tag)){
                    l.add(String.valueOf(t1.getValue(tag, row1)));
                }
            }
            for(String tag: t2.tags) {
                if(!sameTags.contains(tag)){
                    l.add(String.valueOf(t2.getValue(tag, row2)));
                }
            }
            return l;
        }
        return null;
    }



    public static Table CombineUnSharedColumns(Table t1, Table t2){
        Table t = new Table("");
        for(int i = 0; i < t1.colNum; i++){
            t.tags.add(t1.tags(i));
            t.types.add(t1.types(i));
        }
        for(int i = 0; i < t2.colNum; i++){
            t.tags.add(t2.tags(i));
            t.types.add(t2.types(i));
        }
        t.colNum = t1.colNum + t2.colNum;
        for(int i = 0; i < t.colNum; i++){
            t.tagsToIndex.put(t.tags(i), i);
            switch (t.types(i)){
                case FLOAT:t.cols.add(new Column<Float>(t.tags(i), t.types(i)));break;
                case INT:  t.cols.add(new Column<Integer>(t.tags(i), t.types(i))); break;
                case STRING:t.cols.add(new Column<String>(t.tags(i), t.types(i)));break;
            }
        }
        return t;
    }
    public static Table CombineSharedColumns(Table t1, Table t2) {
        Table t = new Table("");
        List<String> sameTags = Table.findSameTags(t1, t2);
        for(String tag: sameTags) {
            t.tags.add(tag);
            t.types.add(t1.types(t1.tagsToIndex.get(tag)));
        }
        for(int i = 0; i < t1.colNum; i++) {
            if(!sameTags.contains(t1.tags(i))){
                t.tags.add(t1.tags(i));
                t.types.add(t1.types(i));
            }
        }
        for(int i = 0; i < t2.colNum; i++) {
            if(!sameTags.contains(t2.tags(i))){
                t.tags.add(t2.tags(i));
                t.types.add(t2.types(i));
            }
        }
        t.colNum = t.tags.size();
        for(int i =0; i < t.colNum; i++){
            t.tagsToIndex.put(t.tags(i), i);
            switch (t.types(i)){
                case FLOAT:t.cols.add(new Column<Float>(t.tags(i), t.types(i)));break;
                case INT:  t.cols.add(new Column<Integer>(t.tags(i), t.types(i))); break;
                case STRING:t.cols.add(new Column<String>(t.tags(i), t.types(i)));break;
            }
        }
        return t;
    }
    public static Table CartesianJoin(Table t1, Table t2){
        Table t = CombineUnSharedColumns(t1, t2);
        for (int i = 0; i < t1.rowNum; i++){
            for(int j = 0; j < t2.rowNum; j++){
                t.AddRowValues(Table.mergeRowUnShared(t1, t2, i, j));
            }
        }
        return t;
    }
    public static Table SharedJoin(Table t1, Table t2){
        Table t = CombineSharedColumns(t1, t2);
        List<String> sameTags = findSameTags(t1, t2);
        for(int i = 0; i < t1.rowNum; i++){
            for(int j =0 ; j < t2.rowNum; j++){
                List<String> shared = Table.mergeRowShared(t1, t2, sameTags, i, j);
                if(shared != null)
                    t.AddRowValues(shared);
            }
        }
        return t;
    }
    public static Table Join(Table t1, Table t2){
        Table t;
        if(containsSameTag(t1, t2))
            t = SharedJoin(t1, t2);
        else
            t = CartesianJoin(t1, t2);
        return t;
    }

    public static Table Join(List<Table> tableList) {
        if(tableList.size() == 1)
            return tableList.get(0);
        Stack<Table> tableStack = multipleTableJoinHelper(tableList);
        while(tableStack.size()> 1){
            Table t1 = tableStack.pop();
            Table t2 = tableStack.pop();
            tableStack.push(Join(t1, t2));
        }
        return tableStack.pop();
    }

    public static Table Join(String name, List<Table> tableList) {
        Table t = Join(tableList);
        t.name = name;
        return t;
    }

    public static Table constructSelectedTable(String name, List<Table> tableList, List<String> colNames) {
        Table t = Join(tableList);
        List<Column> l = new ArrayList<>();
        for(String col: colNames) {
            l.add(t.removeColumn(col));
        }
        Table n = new Table(name, l);
        return n;
    }

    public static Stack<Table> multipleTableJoinHelper(List<Table> tables) {
        Stack<Table> tableStack = new Stack<>();
        for(int i = tables.size() -1; i>=0; i--){
            tableStack.push(tables.get(i));
        }
        return tableStack;
    }

    public void AddRowValues(List<String> values) {
        if (values.size() != colNum)
            throw new IllegalArgumentException();
        for (int i = 0; i < colNum; i++) {
            if (values.get(i) == "NOVALUE")
                cols(i).add(Type.NOVALUE);
            else if (values.get(i) == "NaN")
                cols(i).add(Type.NaN);
            else {
                switch (types(i)) {
                    case FLOAT:
                        cols(i).add(Float.parseFloat(values.get(i)));
                        break;
                    case INT:
                        cols(i).add(Integer.parseInt(values.get(i)));
                        break;
                    case STRING:
                        cols(i).add(values.get(i));
                        break;
                }
            }
        }
        rowNum++;
    }

    public List<String> getRow(int i) {
        List<String> row = new ArrayList<>();
        for (Column col : cols) {
            if (col.get(i) == null) {
                if (col.containsNan(i))
                    row.add("NaN");
                else if (col.containsNoValue(i))
                    row.add("NOVALUE");
                else
                    throw new IllegalArgumentException("something wrong about none value");
            } else {
                switch (col.getType()) {
                    case INT: row.add( col.get(i));break;
                    case FLOAT: row.add(col.get(i));break;
                    case STRING: row.add(col.get(i));break;
                }
            }
        }
        return row;
    }
    @Override
    public String toString(){
        String space = ",";
        String tab = ",";
        String header = "";
        String values = "";
        for(int i = 0; i < colNum; i++) {
            header += tags(i)+" "+ types(i)+ space;
        }
        for(int i = 0; i < rowNum; i++) {
            for(int j = 0; j < colNum; j++) {
                if(types(j) == Type.STRING)
                    values += getValue(i, j) + tab;
                else
                    values += getValue(i, j) + tab;
            }
            values += "\n";
        }
        String out = header + "\n" + values;
        return out;
    }
    public static List<String> mergeRowUnShared(Table t1, Table t2, int i1, int i2) {
        List<String> l1 = t1.getRow(i1);
        List<String> l2 = t2.getRow(i2);
        List<String> newList = new ArrayList<>(l1);
        newList.addAll(l2);
        return newList;
    }

    public static void main(String[] args) {
        Table t = new Table("");
        Column<Integer> x = new Column<>("x", Type.INT);
        Column<Float> y = new Column<>("y", Type.FLOAT);
        Column<String> z = new Column<>("z", Type.STRING);
        t.AddColumn(x);
        t.AddColumn(y);
        t.AddColumn(z);
        System.out.println(t.colNum);
        System.out.println(t.rowNum);
        List<String> l = new ArrayList<>();
        l.add("1");
        l.add("2.2");
        l.add("a");
        t.AddRowValues(l);
        List<String> l2 = new ArrayList<>();
        l2.add("2");
        l2.add("3.3");
        l2.add("b");
        t.AddRowValues(l2);
        List<String> l5 = new ArrayList<>();
        l5.add("NaN");
        l5.add("100.3");
        l5.add("NOVALUE");
        t.AddRowValues(l5);
        System.out.println(t);
        Table t2 = new Table("");
        Column<Integer> a = new Column<>("x", Type.INT);
        Column<Float> b = new Column<>("y", Type.FLOAT);
        Column<String> c = new Column<>("a", Type.STRING);
        t2.AddColumn(a);
        t2.AddColumn(b);
        t2.AddColumn(c);
        List<String> l3 = new ArrayList<>();
        l3.add("1");
        l3.add("2.2");
        l3.add("c");
        t2.AddRowValues(l3);
        List<String> l4 = new ArrayList<>();
        l4.add("4");
        l4.add("4.3");
        l4.add("d");
        t2.AddRowValues(l4);
        System.out.println(t2);
        if(Table.containsSameTag(t, t2)) {
            List<String> sameTags = Table.findSameTags(t, t2);
            System.out.println(sameTags);
            List<String> temp = Table.mergeRowShared(t, t2, sameTags, 0, 0);
            System.out.println(temp);
            System.out.println(t.getValue("x", 0));
            System.out.println(t2.getValue("x",0));
            for(String tag: sameTags) {
                System.out.println(t.getValue(tag,0).equals(t2.getValue(tag,0)));
            }
        }
        Table s = Table.CombineSharedColumns(t, t2);
        System.out.println(s);
        Table SS = Table.SharedJoin(t, t2);
        System.out.println(SS);
    }
}
