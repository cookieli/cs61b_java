package db;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestRow {
    @Test
    public void testRow() {
        Row r = new Row(1);
        r.add("a", 1);
        r.add("b", "sting");
        r.add("c", 3.2);
        r.add("a",6);
        assertEquals(3, r.size());
        assertEquals(1,r.getOrder());
        assertEquals(6,r.getValue("a"));
        assertEquals("sting",r.getValue("b"));
        assertEquals(3.2,r.getValue("c"));
        r.printRow();
    }
}
