package db.Test;

import db.Column;
import db.Type;
import db.comparision.Comparision;
import db.comparision.Equals;
import db.comparision.LessThan;
import db.comparision.NotEquals;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComparisionTest {

    @Test
    public void TestComparision(){
        Comparision c = new Equals();
        Column<Integer> col1 = new Column<>("col", Type.INT);
        Column<Double>  col2 = new Column<>("col2", Type.FLOAT);
        col1.add(Type.NOVALUE);
        col1.add(3);
        col1.add(4);
        col1.add(Type.NaN);
        col1.add(Type.NaN);
        col2.add(Type.NOVALUE);
        col2.add(3.0);
        col2.add(4.0);
        col2.add(Type.NaN);
        col2.add(6.0);
        assertEquals(c.compare(col1, col2, 0), false);
        assertEquals(c.compare(col1, col2, 1), true);
        assertEquals(c.compare(col1, col2, 2), true);
        assertEquals(c.compare(col1, col2, 3), true);
        assertEquals(c.compare(col1, col2, 4),false);
        Column<String> col3 = new Column<>("col3", Type.STRING);
        Column<String> col4 = new Column<>("col4", Type.STRING);
        col3.add(Type.NOVALUE);
        col3.add("hello");
        col3.add("world");
        col4.add("eee");
        col4.add("hello");
        col4.add("world");
        assertEquals(c.compare(col1, col2, 0), false);
        assertEquals(c.compare(col1, col2, 1), true);
        assertEquals(c.compare(col1, col2, 2), true);
        c = new NotEquals();
        col1 = new Column<>("col", Type.INT);
        col2 = new Column<>("col2", Type.FLOAT);
        col1.add(Type.NOVALUE);
        col1.add(3);
        col1.add(4);
        col1.add(Type.NaN);
        col1.add(6);
        col2.add(Type.NOVALUE);
        col2.add(3.0);
        col2.add(4.0);
        col2.add(Type.NaN);
        col2.add(6.0001);
        assertEquals(c.compare(col1, col2, 0), false);
        assertEquals(c.compare(col1, col2, 1), false);
        assertEquals(c.compare(col1, col2, 2), false);
        assertEquals(c.compare(col1, col2, 3), false);
        assertEquals(c.compare(col1, col2, 4),true);
    }
    @Test
    public void TestNotEquals() {
        Comparision notEquals = new NotEquals();
        Column<String> col3 = new Column<>("col3", Type.STRING);
        Column<String> col4 = new Column<>("col4", Type.STRING);
        col3.add("string");
        col3.add("list");
        col3.add("nothing");
        col3.add(Type.NOVALUE);
        col3.add(Type.NaN);
        col4.add("strin");
        col4.add("lis");
        col4.add("nothin");
        col4.add(Type.NaN);
        col4.add(Type.NOVALUE);
        assertTrue(notEquals.compare(col3, col4, 0));
        assertTrue(notEquals.compare(col3, col4, 1));
        assertTrue(notEquals.compare(col3, col4, 2));
        assertFalse(notEquals.compare(col3, col4, 3));
        assertFalse(notEquals.compare(col3, col4, 4));


        assertTrue(notEquals.compare(col3, "sln", 0));
        assertTrue(notEquals.compare(col3, "sln", 1));
        assertTrue(notEquals.compare(col3, "sln", 2));
        assertFalse(notEquals.compare(col3, "sln", 3));
        assertTrue(notEquals.compare(col3, "sln", 4));

    }

    @Test
    public void TestLessThan(){
        Comparision lessThan = new LessThan();
        Column<Integer> c1 = new Column<>("c1", Type.INT);
        c1.add(4);
        c1.add(5);
        c1.add(6);
        c1.add(Type.NOVALUE);
        c1.add(Type.NaN);
        assertTrue(lessThan.compare(c1, "6.001", 0));
        assertTrue(lessThan.compare(c1, "6.001", 1));
        assertTrue(lessThan.compare(c1, "6.001", 2));

        assertFalse(lessThan.compare(c1, "3.999", 0));
        assertFalse(lessThan.compare(c1, "3.999", 1));
        assertFalse(lessThan.compare(c1, "3.999", 2));

        assertTrue(lessThan.compare(c1, "4.999", 0));
        assertFalse(lessThan.compare(c1,"4.999",1));
        assertFalse(lessThan.compare(c1,"4.999",2));
        assertFalse(lessThan.compare(c1,"4.999",3));
        assertFalse(lessThan.compare(c1,"4.999",4));

        Column<Double> c2 = new Column<>("c2", Type.FLOAT);
        c2.add(3.9999);
        c2.add(4.9999);
        c2.add(5.9999);
        c2.add(3.3);
        c2.add(99.0);
        assertTrue(lessThan.compare(c2, c1, 0));
        assertTrue(lessThan.compare(c2, c1, 1));
        assertTrue(lessThan.compare(c2, c1, 2));
        assertFalse(lessThan.compare(c2, c1, 3));
        assertTrue(lessThan.compare(c2, c1, 4));

        Column<String> c3 = new Column<>("c3", Type.STRING);
        c3.add("a");
        c3.add("b");
        c3.add("c");


    }

}
