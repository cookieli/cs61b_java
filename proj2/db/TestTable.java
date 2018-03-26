package db;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestTable {
    @Test
    public void testCreate() {
        Table t = new Table("test", "first","second", "third");
        assertEquals(3,t.getColNum());
        assertEquals(0,t.getRowNum());
        Column<Integer> first = t.getCol("first");
        assertEquals("first",first.getName());
        assertEquals(0,first.size());
        Column<Integer> second = t.getCol("second");
        Column<Integer> third = t.getCol("third");
        assertEquals(first.size(),second.size());
        assertEquals(first.size(),third.size());
    }
    @Test
    public void TestInsert() {
        Table t = new Table("test", "first","second", "third");
        t.insert(1,2,3);
        assertEquals(1,t.getRowNum());
        Column<Integer> first = t.getCol("first");
        Column<Integer> second = t.getCol("second");
        Column<Integer> third = t.getCol("third");
        assertEquals((Integer) 1,first.get(0));
        assertEquals((Integer) 2, second.get(0));
        assertEquals((Integer)3,third.get(0));
        assertEquals(t.getRowNum(), first.size());
        assertEquals(t.getRowNum(), second.size());
        assertEquals(t.getRowNum(),third.size());
        assertEquals(null,first.get(1));

    }
}
