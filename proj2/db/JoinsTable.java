package db;

import java.util.HashMap;
import java.util.LinkedList;

public class JoinsTable extends Table{

    public JoinsTable() {
        super();
    }

    public JoinsTable(String name, Column<Integer> ...cols) {
        this.name = name;
        this.tags = new LinkedList<>();
        this.bind = new HashMap<>();
        for(int i = 0; i < cols.length; i++) {
            this.tags.add(cols[i].getName());
            this.bind.put(cols[i].getName(), cols[i]);
        }
        this.colNum = cols.length;
        this.rowNum = cols[0].size();
    }


}
