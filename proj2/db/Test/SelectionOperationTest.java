package db.Test;

import db.Column;
import db.Table;
import db.Type;
import db.operation.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SelectionOperationTest{


    @Test
    public void AddOperationTest(){
        Table t = new Table("t1");
        List<String> temp;
        Column<Integer> c1 = new Column<>("c1", Type.INT);
        Column<String>  c2 = new Column<>("c2", Type.STRING);
        Column<Double>  c3 = new Column<>("c3", Type.FLOAT);
        t.AddColumn(c1);
        t.AddColumn(c2);
        t.AddColumn(c3);
        temp = new ArrayList<>();
        temp.add("3");
        temp.add("sad");
        temp.add("2.2");
        t.AddRowValues(temp);
        temp = new ArrayList<>();
        temp.add("2");
        temp.add("mood");
        temp.add("-2.2");
        t.AddRowValues(temp);
        System.out.println(t);
        Table result = new Table("result");
        Operation op = new AddOperation();
        result.evaluateArithmeticExpression(t, op, "c1","c3", "ll");
        System.out.println(result);
    }

    @Test
    public void SubtractionTest(){
        Table t = new Table("t");
        List<String> list;
        t.AddColumn("X", Type.INT);
        t.AddColumn("Y", Type.FLOAT);
        t.AddColumn("Z",Type.FLOAT);
        list = new ArrayList<>();
        list.add("3");
        list.add("4.4");
        list.add("5.5");
        t.AddRowValues(list);
        list = new ArrayList<>();
        list.add("6");
        list.add("3.3");
        list.add("7.7");
        t.AddRowValues(list);
        System.out.println(t);
        Table result = new Table("result");
        Operation op = new Subtraction();
        result.evaluateArithmeticExpression(t, op, "X","Y", "rr");
        System.out.println(result);
        result.evaluateArithmeticExpression(t, op, "Z", "2.2", "ll");
        System.out.println(result);
    }
    @Test
    public void MultiplicationTest(){
        Table t = new Table("t");
        List<String> list;
        t.AddColumn("a", Type.INT);
        t.AddColumn("b", Type.FLOAT);
        t.AddColumn("c", Type.INT);
        list = new ArrayList<>();
        list.add("2");
        list.add("4.4");
        list.add("3");
        t.AddRowValues(list);
        list = new ArrayList<>();
        list.add("3");
        list.add("1.4");
        list.add("2");
        t.AddRowValues(list);
        list = new ArrayList<>();
        list.add("6");
        list.add("3.3");
        list.add("5");
        t.AddRowValues(list);
        System.out.println(t);
        Operation op = new Multiplication();
        Table result = new Table("result");
        result.evaluateArithmeticExpression(t, op, "a", "b", "ss");
        result.evaluateArithmeticExpression(t, new AddOperation(), "c","3.3","ee");
        System.out.println(result);

    }
    @Test
    public void DivisionTest(){
        Table t = new Table("t");
        List<String> list;
        t.AddColumn("a", Type.FLOAT);
        t.AddColumn("b", Type.INT);
        t.AddColumn("c", Type.STRING);
        list = new ArrayList<>();
        list.add("4.4");
        list.add("6");
        list.add("hello");
        t.AddRowValues(list);
        list = new ArrayList<>();
        list.add("3.3");
        list.add("4");
        list.add("doc");
        t.AddRowValues(list);
        System.out.println(t);
        Table result = new Table("result");
        result.evaluateArithmeticExpression(t, new Division(), "a", "b", "rr");
        result.evaluateArithmeticExpression(t, new AddOperation(), "c"," yang", "ll");
        System.out.println(result);
    }


}
