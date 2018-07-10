package db.operation;

import db.Column;
import db.Type;

import java.util.regex.Pattern;

public class AddOperation extends AbstractOperation {

    @Override
    public Column operation(String name, Column c1, Column c2) {
        if (bothColumnsAreNumbers(c1, c2)) {
            if (bothColumnsAreIntegers(c1, c2)) {
                return operationInt(name, c1, c2);
            } else
                return operationFloat(name, c1, c2);
        } else if (bothColumnsAreStrings(c1, c2))
            return concat(name, c1, c2);
        else
            throw new IllegalArgumentException("the column one is string, other isn't string");
    }

    @Override
    public Column operation(String name, Column c, String literal) {
        if (ColumnAndLiteralContainsNumbers(c, literal))
            return super.operation(name, c, literal);
        else if (c.getType() != Type.STRING)
            throw new IllegalArgumentException("can't concat literal and column which can't be string");
        return concat(name, c, literal);
    }

    @Override
    protected Column<Double> operationFloat(String colName, Column c1, Column c2) {
        Column<Double> c = new Column<>(colName, Type.FLOAT);
        for (int i = 0; i < c1.size(); i++) {
            if (c1.containsNan(i) || c2.containsNan(i))
                c.add(Type.NaN);
            else if (c1.containsNoValue(i) && c2.containsNoValue(i))
                c.add(Type.NOVALUE);
            else {
                if (c1.containsNoValue(i))
                    c.add(Double.parseDouble(c2.get(i)));
                else if (c2.containsNoValue(i))
                    c.add(Double.parseDouble(c1.get(i)));
                else
                    c.add((Double.parseDouble(c1.get(i)) + Double.parseDouble(c2.get(i))));
            }
        }
        return c;
    }

    @Override
    protected Column<Double> operationFloat(String colName, Column c, double literal) {
        Column<Double> result = new Column<>(colName, Type.FLOAT);
        for (int i = 0; i < c.size(); i++) {
            if (c.containsNan(i))
                result.add(Type.NaN);
            else if (c.containsNoValue(i))
                result.add(literal);
            else
                result.add(Double.parseDouble(c.get(i)) + literal);

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
                    result.add(Integer.parseInt(c2.get(i)));
                else if (c2.containsNoValue(i))
                    result.add(Integer.parseInt(c1.get(i)));
                else
                    result.add(Integer.parseInt(c1.get(i)) + Integer.parseInt(c2.get(i)));
            }
        }
        return result;
    }


    @Override
    protected Column<Integer> operationInt(String colName, Column<Integer> c, int literal) {
        Column<Integer> result = new Column<>(colName, Type.INT);
        for (int i = 0; i < c.size(); i++) {
            if (c.containsNoValue(i))
                result.add(literal);
            else if (c.containsNan(i))
                result.add(Type.NaN);
            else
                result.add(literal + Integer.parseInt(c.get(i)));
        }
        return result;
    }

    protected Column<String> concat(String colName, Column<String> c1, Column<String> c2) {
        Column<String> result = new Column<>(colName, Type.STRING);
        for (int i = 0; i < c1.size(); i++) {
                result.add(c1.get(i).substring(0,c1.get(i).length() - 1) + c2.get(i).substring(1, c2.get(i).length()));

        }
        return result;
    }

    protected Column<String> concat(String colName, Column<String> c1, String c2) {
        Column<String> result = new Column<>(colName, Type.STRING);
        for (int i = 0; i < c1.size(); i++) {
            result.add(c1.get(i).substring(0, c1.get(i).length() -1) + c2.substring(1,c2.length()) );
        }
        return result;
    }

    protected boolean bothColumnsAreNumbers(Column c1, Column c2) {
        return (c1.getType() == Type.INT || c1.getType() == Type.FLOAT) && (c2.getType() == Type.FLOAT || c2.getType() == Type.INT);
    }

    protected boolean bothColumnsAreIntegers(Column c1, Column c2) {
        return (c1.getType() == Type.INT) && (c2.getType() == Type.INT);
    }

    protected boolean bothColumnsAreStrings(Column c1, Column c2) {
        return (c1.getType() == Type.STRING) && (c2.getType() == Type.STRING);
    }

    protected boolean ColumnAndLiteralContainsNumbers(Column c, String literal) {
        String int_Pattern = "-?(\\d+)";
        String float_Pattern = "-?(\\d*\\.\\d+|\\d+\\.\\d*)";
        boolean isNumLiteral = Pattern.matches(int_Pattern, literal) || Pattern.matches(float_Pattern, literal);
        return (!literal.contains("'")) && isNumLiteral && (c.getType() == Type.INT || c.getType() == Type.FLOAT);
    }


    public static void main(String[] args) {
        AddOperation add = new AddOperation();
        Column<Double> a = new Column("a", Type.FLOAT);
        Column<Integer> b = new Column("b", Type.FLOAT);
        a.add(3.3);
        a.add(Type.NaN);
        a.add(Type.NOVALUE);
        b.add(1);
        b.add(Type.NOVALUE);
        Column<Double> f = add.operationFloat("", a, b);
        System.out.println(f);
        f = add.operation(" ", a, b);
        System.out.println(f);
        f = add.operationFloat("", a, 2);
        System.out.println(f);
        Column<Integer> c = new Column("c", Type.INT);
        Column<Integer> d = new Column<>("d", Type.INT);
        c.add(3);
        c.add(4);
        d.add(1);
        d.add(6);
        Column<Integer> it = add.operationInt("h", c, d);
        System.out.println(it);
        Column<String> e = new Column<>("e", Type.STRING);
        Column<String> f1 = new Column<>("f", Type.STRING);
        e.add("hel");
        e.add(Type.NaN);
        f1.add("lo");
        f1.add("iannan");
        Column<String> ss = add.concat("ss", e, f1);
        System.out.println(ss);
        ss = add.operation("ss", e, f1);
        System.out.println(ss);
        ss = add.operation("ss", f1, "rr");
        System.out.println(ss);
    }
}
