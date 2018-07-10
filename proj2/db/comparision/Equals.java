package db.comparision;

import db.Column;

public class Equals extends AbstractComparision {

    @Override
    protected boolean compareNumericColumns(Column c1, Column c2, int row) {
        if(c1.containsNoValue(row) || c2.containsNoValue(row))
            return false;
        else if(c1.containsNan(row) && c2.containsNan(row))
            return true;
        else if(c1.containsNan(row) || c2.containsNan(row))
            return false;
        double c1_val = Double.parseDouble(c1.get(row));
        double c2_val = Double.parseDouble(c2.get(row));
        return c1_val == c2_val;
    }

    @Override
    protected boolean compareStringColumns(Column<String> c1, Column<String> c2, int row) {
        if(c1.containsNoValue(row) || c2.containsNoValue(row))
            return false;
        else if(c1.containsNan(row) && c2.containsNan(row))
            return true;
        else if(c1.containsNan(row) || c2.containsNan(row))
            return false;
        String c1_val = c1.get(row);
        String c2_val = c2.get(row);
        return c1_val.equals(c2_val);
    }

    @Override
    protected boolean compareToNumber(Column c1, double literal, int row) {
        if(c1.containsNan(row) || c1.containsNoValue(row))
            return false;
        double c1_val = Double.parseDouble(c1.get(row));
        return c1_val == literal;
    }

    @Override
    protected boolean compareToString(Column<String> c1, String literal, int row) {
        if(c1.containsNoValue(row) || c1.containsNan(row))
            return false;
        String c1_val = c1.get(row);
        return c1_val.equals(literal);
    }

    public static void main(String[] args) {
        String a = "2";
        System.out.println(Double.parseDouble(a));
    }
}
