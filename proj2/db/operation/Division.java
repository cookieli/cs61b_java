package db.operation;

import db.Column;
import db.Type;

import java.net.Proxy;

public class Division extends AbstractOperation {

    @Override
    protected Column<Integer> operationInt(String colName, Column<Integer> c, int literal) {
        Column<Integer> result = new Column<>(colName, Type.INT);
        for (int i = 0; i < c.size(); i++) {
            if (c.containsNan(i))
                result.add(Type.NaN);
            else if (c.containsNoValue(i))
                result.add(Type.NOVALUE);
            else
                result.add(Math.floorDiv(Integer.parseInt(c.get(i)), literal));
        }
        return result;
    }

    @Override
    protected Column<Integer> operationInt(String colName, Column<Integer> c1, Column<Integer> c2) {
        Column<Integer> result = new Column<>(colName, Type.INT);
        for (int i = 0; i < c1.size(); i++) {
            if (c1.containsNan(i) || c2.containsNan(i))
                result.add(Type.NaN);
            else if (c2.containsNoValue(i) && !c1.containsNoValue(i))
                result.add(Type.NaN);
            else if (c1.containsNoValue(i))
                result.add(Type.NOVALUE);
            else
                result.add(Math.floorDiv(Integer.parseInt(c1.get(i)), Integer.parseInt(c2.get(i))));
        }
        return result;
    }

    @Override
    protected Column<Double> operationFloat(String colName, Column c1, Column c2) {
        Column<Double> result = new Column<>(colName, Type.FLOAT);
        for (int i = 0; i < c1.size(); i++) {
            if (c1.containsNan(i) || c2.containsNan(i))
                result.add(Type.NaN);
            else if (c2.containsNoValue(i) && !c1.containsNoValue(i))
                result.add(Type.NaN);
            else if (c1.containsNoValue(i))
                result.add(Type.NOVALUE);
            else
                result.add(Double.parseDouble(c1.get(i)) / Double.parseDouble(c2.get(i)));

        }
        return result;
    }

    @Override
    protected Column<Double> operationFloat(String colName, Column c, double literal) {
        Column<Double> result = new Column<>(colName, Type.FLOAT);
        for (int i = 0; i < c.size(); i++) {
            if (c.containsNan(i))
                result.add(Type.NaN);
            else if (c.containsNoValue(i))
                result.add(Type.NOVALUE);
            else
                result.add(Double.parseDouble(c.get(i)) / literal);
        }
        return result;
    }

    public static void main(String[] args) {
        Division div = new Division();
        Column<Double> c1 = new Column<>("c1", Type.FLOAT);
        c1.add(3.3);
        c1.add(6.6);
        c1.add(9.9);
        Column<Double> result = div.operation("r", c1, "3.0");
        Column<Double> c2 = new Column<>("c2", Type.FLOAT);
        c2.add(1.0);
        c2.add(2.0);
        c2.add(3.0);
        result = div.operation("r2", c1, c2);
        System.out.println(result);
    }
}
