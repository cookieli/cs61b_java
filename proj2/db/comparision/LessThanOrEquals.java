package db.comparision;

import db.Column;

public class LessThanOrEquals extends AbstractComparision {

    @Override
    protected boolean compareToString(Column<String> c1, String literal, int row) {
        if(c1.containsNan(row) || c1.containsNoValue(row))
            return false;
        else
            return c1.get(row).compareTo(literal) <= 0;
    }

    @Override
    protected boolean compareNumericColumns(Column c1, Column c2, int row) {
        if(c1.containsNoValue(row) || c2.containsNoValue(row))
            return false;
        else if(c2.containsNan(row))
            return true;
        else if(c1.containsNan(row))
            return false;
        else
            return Double.parseDouble(c1.get(row)) <= Double.parseDouble(c2.get(row));
    }

    @Override
    protected boolean compareStringColumns(Column<String> c1, Column<String> c2, int row) {
        if(c1.containsNoValue(row) || c2.containsNoValue(row))
            return false;
        else if(c2.containsNan(row))
            return true;
        else if(c1.containsNan(row))
            return false;
        else
            return c1.get(row).compareTo(c2.get(row)) <= 0;
    }

    @Override
    protected boolean compareToNumber(Column c1, double literal, int row) {
        if(c1.containsNan(row) || c1.containsNoValue(row))
            return false;
        else
            return Double.parseDouble(c1.get(row)) <= literal;
    }
}
