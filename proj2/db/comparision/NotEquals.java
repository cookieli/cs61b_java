package db.comparision;

import db.Column;

public class NotEquals extends AbstractComparision {

    @Override
    protected boolean compareToString(Column<String> c1, String literal, int row) {
        if(c1.containsNan(row))
            return true;
        else if(c1.containsNoValue(row))
            return false;
        else
            return !literal.equals(c1.get(row));
    }

    @Override
    protected boolean compareToNumber(Column c1, double literal, int row) {
        if(c1.containsNan(row))
            return true;
        else if(c1.containsNoValue(row))
            return false;
        else
            return literal != Double.parseDouble(c1.get(row));
    }

    @Override
    protected boolean compareStringColumns(Column<String> c1, Column<String> c2, int row) {
        if(c1.containsNoValue(row) || c2.containsNoValue(row))
            return false;
        else if(c1.containsNan(row) && c2.containsNan(row))
            return false;
        else if(c1.containsNan(row) || c2.containsNan(row))
            return true;
        return !c1.get(row).equals(c2.get(row));
    }

    @Override
    protected boolean compareNumericColumns(Column c1, Column c2, int row) {
        if(c1.containsNoValue(row) || c2.containsNoValue(row))
            return false;
        else if(c1.containsNan(row) && c2.containsNan(row))
            return false;
        else if(c1.containsNan(row) || c2.containsNan(row))
            return true;
        return Double.parseDouble(c1.get(row)) != Double.parseDouble(c2.get(row));
    }

    public static void main(String[] args) {
        double x = 1.0;
        int y = 1;
        System.out.println(Double.parseDouble("3") == 3);
    }
}
