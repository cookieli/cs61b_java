package db.Test;

import db.Column;
import db.Table;
import db.Type;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TableTest {
    @Test
    public void TestTable(){
        Table t1 = new Table("T1");
        List<String> temp;
        Column<Integer> c1 = new Column<>("c1", Type.INT);
        Column<String>  c2 = new Column<>("c2", Type.STRING);
        Column<Float>  c3 = new Column<>("c3", Type.FLOAT);
        t1.AddColumn(c1);
        t1.AddColumn(c2);
        t1.AddColumn(c3);
        temp = new ArrayList<>();
        temp.add("3");
        temp.add("as");
        temp.add("3.3");
        t1.AddRowValues(temp);
        temp = new ArrayList<>();
        temp.add(0, "5");
        temp.add(1, "df");
        temp.add(2, "4.4");
        t1.AddRowValues(temp);
        System.out.println(t1);
        assertEquals(t1.getRowNum(), 2);
        assertEquals(t1.getColNum(), 3);
        temp = new ArrayList<>();
        temp.add(0, "7");
        temp.add(1, "eg");
        temp.add(2, "6.6");
        t1.AddRowValues(temp);
        System.out.println(t1);
        assertEquals(t1.getRowNum(), 3);
        assertEquals(t1.getColNum(), 3);
    }
    @Test
    public void TestJoins() {
        Table t1 = new Table("T1");
        List<String> temp;
        Column<Integer> c1 = new Column<>("x", Type.INT);
        Column<String>  c2 = new Column<>("y", Type.INT);
        //Column<Float>  c3 = new Column<>("c3", Type.FLOAT);
        t1.AddColumn(c1);
        t1.AddColumn(c2);
        //t1.AddColumn(c3);
        temp = new ArrayList<>();
        temp.add("1");
        temp.add("7");
        t1.AddRowValues(temp);
        temp = new ArrayList<>();
        temp.add(0, "7");
        temp.add(1, "7");
        t1.AddRowValues(temp);
        temp = new ArrayList<>();
        temp.add(0, "1");
        temp.add(1, "9");
        t1.AddRowValues(temp);
        System.out.println(t1);
        Table t2 = new Table("T2");
        Column<Integer> c4 = new Column<>("x", Type.INT);
        Column<String>  c5 = new Column<>("z", Type.INT);
        t2.AddColumn(c4);
        t2.AddColumn(c5);
        temp = new ArrayList<>();
        temp.add("3");
        temp.add("8");
        t2.AddRowValues(temp);
        temp = new ArrayList<>();
        temp.add(0, "4");
        temp.add(1, "9");
        t2.AddRowValues(temp);
        temp = new ArrayList<>();
        temp.add(0, "5");
        temp.add(1, "10");
        t2.AddRowValues(temp);
        System.out.println(t2);
        Table comb = Table.Join(t1, t2);
        System.out.println(comb);
        Table t3 = new Table("T3");
        Column<Integer> c7 = new Column<>("W", Type.INT);
        Column<String>  c8 = new Column<>("B", Type.INT);
        Column<Float>  c9 = new Column<>("Z", Type.INT);
        t3.AddColumn(c7);
        t3.AddColumn(c8);
        t3.AddColumn(c9);
        temp = new ArrayList<>();
        temp.add("1");
        temp.add("7");
        temp.add("4");
        t3.AddRowValues(temp);
        temp = new ArrayList<>();
        temp.add(0, "7");
        temp.add(1, "7");
        temp.add(2, "3");
        t3.AddRowValues(temp);
        temp = new ArrayList<>();
        temp.add(0, "1");
        temp.add(1, "9");
        temp.add(2, "6");
        t3.AddRowValues(temp);
        temp = new ArrayList<>();
        temp.add(0, "1");
        temp.add(1, "11");
        temp.add(2, "9");
        t3.AddRowValues(temp);
        System.out.println(t3);
        Table t4 = new Table("T4");
        Column<Integer> c10 = new Column<>("X", Type.INT);
        Column<String>  c11 = new Column<>("Y", Type.INT);
        Column<Float>  c12 = new Column<>("Z", Type.INT);
        Column<Float>  c13 = new Column<>("W", Type.INT);
        t4.AddColumn(c10);
        t4.AddColumn(c11);
        t4.AddColumn(c12);
        t4.AddColumn(c13);
        temp = new ArrayList<>();
        temp.add("1");
        temp.add("7");
        temp.add("2");
        temp.add("10");
        t4.AddRowValues(temp);
        temp = new ArrayList<>();
        temp.add("7");
        temp.add("7");
        temp.add("4");
        temp.add("1");
        t4.AddRowValues(temp);
        temp = new ArrayList<>();
        temp.add( "1");
        temp.add( "9");
        temp.add( "9");
        temp.add( "1");
        t4.AddRowValues(temp);
        System.out.println(t4);
        comb = Table.Join(t3, t4);
        System.out.println(comb);

    }
}
