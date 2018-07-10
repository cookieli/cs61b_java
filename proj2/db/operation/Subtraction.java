package db.operation;

import db.Column;
import db.Type;

public class Subtraction extends AbstractOperation {

    @Override
    protected Column<Double> operationFloat(String colName, Column c, double literal) {
        Column<Double> result = new Column<>(colName, Type.FLOAT);
        for (int i = 0; i < c.size(); i++) {
            if (c.containsNan(i))
                result.add(Type.NaN);
            else if (c.containsNoValue(i))
                result.add(-literal);
            else
                result.add(Double.parseDouble(c.get(i)) - literal);
        }
        return result;
    }

    @Override
    protected Column<Double> operationFloat(String colName, Column c1, Column c2) {
        Column<Double> result = new Column<>(colName, Type.FLOAT);
        for (int i = 0; i < c1.size(); i++) {
            if (c1.containsNan(i) || c2.containsNan(i))
                result.add(Type.NaN);
            else if (c1.containsNoValue(i) && c2.containsNoValue(i))
                result.add(Type.NOVALUE);
            else {
                if (c1.containsNoValue(i))
                    result.add(-Double.parseDouble(c2.get(i)));
                else if (c2.containsNoValue(i))
                    result.add(Double.parseDouble(c1.get(i)));
                else
                    result.add((Double.parseDouble(c1.get(i)) - Double.parseDouble(c2.get(i))));
            }
        }
        return result;
    }

    @Override
    protected Column<Integer> operationInt(String colName, Column<Integer> c1, Column<Integer> c2) {
        Column<Integer> result = new Column<>(colName, Type.INT);
        for (int i = 0; i < c1.size(); i++) {
            if (c1.containsNan(i) || c2.containsNan(i))
                result.add(Type.NaN);
            else if (c1.containsNoValue(i) && c2.containsNoValue(i))
                result.add(Type.NOVALUE);
            else {
                if (c1.containsNoValue(i))
                    result.add(-Integer.parseInt(c2.get(i)));
                else if (c2.containsNoValue(i))
                    result.add(Integer.parseInt(c1.get(i)));
                else
                    result.add(Integer.parseInt(c1.get(i)) - Integer.parseInt(c2.get(i)));
            }
        }
        return result;
    }

    @Override
    protected Column<Integer> operationInt(String colName, Column<Integer> c, int literal) {
        Column<Integer> result = new Column<>(colName, Type.INT);
        for (int i = 0; i < c.size(); i++) {
            if (c.containsNan(i))
                result.add(Type.NaN);
            else if (c.containsNoValue(i))
                result.add(-literal);
            else
                result.add(Integer.parseInt(c.get(i)) - literal);
        }
        return result;
    }

}
