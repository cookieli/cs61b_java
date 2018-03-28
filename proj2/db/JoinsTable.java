package db;


import java.util.*;

public class JoinsTable extends Table{
    public Map<Integer, Row> rows;
    public List<Column> cols;
    public JoinsTable() {
        super();
        this.rows = new HashMap<>();
        this.cols = new LinkedList<>();
    }

    public JoinsTable(String name, String ...tags) {
        super(name, tags);
        this.rows = new HashMap<>();
        this.cols = new LinkedList<>();
        for(int i = 0; i < tags.length; i++)
            this.cols.add(this.bind.get(tags[i]));
    }
    public JoinsTable(String name, List<Row> rows) {
        this(name);
        this.tags = rows.get(0).getNames();
        for(int i = 0; i < this.tags.size(); i++) {
            String tag = this.tags.get(i);
            this.bind.put(tag, new Column<>(tag));
            this.cols.add(this.bind.get(tag));
        }
        this.colNum = this.tags.size();

        for(int i = 0; i < rows.size();  i++) {
            this.addRowToTable(rows.get(i));
        }

    }

    public void addRowToTable(Row r) {
        for(int i = 0; i < this.colNum; i ++) {
            this.cols.get(i).add(r.getValue(i));
        }
        this.rowNum += 1;
    }

    public void divideIntoRows() {
        String[] tags = new String[this.getColNum()];
        for(int i = 0 ; i < this.getColNum(); i++) {
            tags[i] = this.tags.get(i);
        }
        for(int i = 0 ; i < this.rowNum; i++) {
            Row temp = new Row(i, tags);
            temp.addValues(this.getRow(i));
            this.rows.put(i, temp);
        }
    }


    public Row getRowObject(int order) {
        return this.rows.get(order);
    }

    public static JoinsTable selectWithoutCommons(JoinsTable t1, JoinsTable t2) {
        t1.divideIntoRows();
        t2.divideIntoRows();
        List<Row> newRows = new ArrayList<>();
        for(int i = 0; i < t1.getRowNum(); i++) {
            Row t1_row = t1.getRowObject(i);
            for(int j = 0; j < t2.getRowNum(); j++) {
                Row t2_row = t2.getRowObject(j);
                Row mergeRow = Row.mergeRows(t1_row, t2_row);
                if(mergeRow == null) continue;
                newRows.add(mergeRow);
            }
        }
        if (newRows.size() == 0)  return null;
        JoinsTable t = new JoinsTable("untitled", newRows);
        return t;
    }

   /* public static JoinsTable selectWithCommon(JoinsTable t1, JoinsTable t2) {

    }*/


}
