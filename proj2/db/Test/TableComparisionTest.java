package db.Test;

import db.Table;
import db.Type;
import db.comparision.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TableComparisionTest {

    @Test
    public void TestEquals(){
        Table t = new Table("t");
        List<String> list;
        t.AddColumn("x", Type.STRING);
        t.AddColumn("y", Type.FLOAT);
        t.AddColumn("z", Type.STRING);
        list = new ArrayList<>();
        list.add("NaN");
        list.add("4.0");
        list.add("NaN");
        t.AddRowValues(list);
        System.out.println(t);
        list = new ArrayList<>();
        list.add("error");
        list.add("2.6");
        list.add("NOVALUE");
        t.AddRowValues(list);
        System.out.println(t);
        String[] condition = new String[]{"x", ">", "z"};
        t.makeComparision(new GreaterOrEquals(), condition);
        System.out.println(t);


    }
}
