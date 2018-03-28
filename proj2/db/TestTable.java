package db;
import org.junit.Test;
import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;
import java.util.List;

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
        Integer[] row = t.getRow(0);
        for(int i = 0; i < row.length; i++) {
            System.out.print( row[i] + " ");
        }

    }

    @Test
    public void TestDivideRows() {
        JoinsTable t = new JoinsTable("test", "first","second", "third");
        t.insert(1,2,3);
        t.insert(4,5,6);
        t.insert(7,8,9);
        t.divideIntoRows();
        System.out.println(t.rows.size());
        t.rows.get(0).printRow();
        t.rows.get(1).printRow();
        t.rows.get(2).printRow();
        Row r = new Row(0, "first", "second", "third");
        r.addValues(10,11,12);
        assertEquals(3, t.getColNum());
        t.addRowToTable(r);
        t.printTable();
        assertEquals(4, t.getRowNum());
    }
    @Test
    public void TestConstructorsWithRows() {
        Row r = new Row(0, "first", "second", "third");
        r.addValues(10,11,12);
        Row j = new Row(0, "first", "second", "third");
        j.addValues(1,2,3);
        Row u = new Row(0, "first", "second", "third");
        u.addValues(3,4,5);
        List<Row> l = new ArrayList<>();
        l.add(r);
        l.add(j);
        l.add(u);
        JoinsTable J = new JoinsTable("uk", l);
        assertEquals(r.getColNum(), J.colNum);
        assertEquals(3, J.getRowNum());
        J.printTable();
    }
    @Test
    public void TestSelect() {
        JoinsTable t = new JoinsTable("test", "first","second", "third");
        t.insert(1,2,3);
        t.insert(4,5,6);
        t.insert(7,8,9);
        JoinsTable r = new JoinsTable("test", "fourth","fifth", "sixth");
        r.insert(10,11,12);
        r.insert(13,14,15);
        r.insert(16,17,18);
        JoinsTable f = JoinsTable.selectWithoutCommons(t, r);
        f.printTable();
        assertEquals(9,f.getRowNum());
        Column<Integer> first = f.getCol("first");
        Column<Integer> second = f.getCol("second");
        Column<Integer> third = f.getCol("third");
        //Column<Integer> fourth = f.getCol("fourth");
        Column<Integer> fifth = f.getCol("fifth");
        Column<Integer> sixth = f.getCol("sixth");
        assertEquals((Integer) 1,first.get(0));
        assertEquals((Integer) 2, second.get(0));
        assertEquals((Integer)3,third.get(0));
//        assertEquals((Integer)10, fourth.get(0));
//        assertEquals((Integer)10, fourth.get(3));
//        assertEquals((Integer)10, fourth.get(6));
        assertEquals((Integer)11, fifth.get(0));
        assertEquals((Integer)11, fifth.get(3));
        assertEquals((Integer)11, fifth.get(6));
        assertEquals((Integer)12, sixth.get(0));
        assertEquals((Integer)12, sixth.get(3));
        assertEquals((Integer)12, sixth.get(6));
        assertEquals(f.getRowNum(), first.size());
        assertEquals(f.getRowNum(), second.size());
        assertEquals(f.getRowNum(),third.size());
        assertEquals(null,first.get(9));
    }

    @Test
    public void TestMerge() {
        JoinsTable t = new JoinsTable("test", "first","second", "third");
        t.insert(1,2,3);
        t.insert(4,5,6);
        t.insert(7,8,9);
        JoinsTable r = new JoinsTable("test", "fourth","first", "sixth");
        r.insert(1,1,12);
        r.insert(4,4,15);
        r.insert(7,7,18);
        JoinsTable test = JoinsTable.selectWithoutCommons(t, r);
        assertEquals(3,test.getRowNum());
        assertEquals(5,test.getColNum());
        Column<Integer> first = test.getCol("first");
        Column<Integer> second = test.getCol("second");
        Column<Integer> third = test.getCol("third");
        assertEquals((Integer) 1, first.get(0));
        assertEquals((Integer)4, first.get(1));
        assertEquals(test.getRowNum(),first.size());
        assertEquals(test.getRowNum(),second.size());
        assertEquals(test.getRowNum(),third.size());
        test.printTable();


        JoinsTable t3 = new JoinsTable("test","fourth","fifth","first");
        t3.insert(100,2,1);
        t3.insert(101,5,4);
        test = JoinsTable.selectWithoutCommons(t, t3);
        test.printTable();
        //assertEquals(null, test);
    }
}
