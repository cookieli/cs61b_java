package db;
import edu.princeton.cs.introcs.In;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestRow {

    @Test
    public void TestNamesOrder() {
        Row r = new Row(1, "first", "second", "third");
        List<String>  names = r.getNames();
        assertEquals("first", names.get(0));
        assertEquals("second", names.get(1));
        assertEquals("third", names.get(2));
        r.addValues(1, 2, 3);
        assertEquals((Integer) 1, r.getValue(0));
        assertEquals((Integer) 2, r.getValue(1));
        assertEquals((Integer) 3, r.getValue(2));
        assertEquals((Integer)1, r.getValue("first"));
        assertEquals((Integer)2, r.getValue("second"));
        assertEquals((Integer)3, r.getValue("third"));
      //  r.printRow();
        Row b = new Row(2, "fourth", "fifth", "sixth");
        b.addValues(4, 5, 6);
        Row c = Row.contactRows(r, b);
        assertEquals((Integer)1, c.getValue(0));
        Integer[] d = c.getAllValues();
        assertEquals(6, c.getColNum());
        c.printRow();
    }
}
