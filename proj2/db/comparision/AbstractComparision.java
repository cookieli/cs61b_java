package db.comparision;

import db.Column;
import db.Type;

import java.util.regex.Pattern;

abstract class AbstractComparision implements Comparision{

    @Override
    public boolean compare(Column c1, Column c2, int row) {
        if(BothColumnsContainsNumbers(c1, c2))
            return compareNumericColumns(c1, c2, row);
        else if(BothColumnsContainsString(c1, c2))
            return compareStringColumns(c1, c2, row);
        else
            throw new IllegalArgumentException("CAN'T compare String to numbers");
    }

    @Override
    public boolean compare(Column c1, String literal, int row) {
        if(ColumnAndLiteralContainsNumber(c1, literal))
            return compareToNumber(c1, Double.parseDouble(literal), row);
        return compareToString(c1, literal, row);
    }

    public boolean ColumnAndLiteralContainsNumber(Column c1, String literal){
        String int_Pattern = "-?(\\d+)";
        String float_Pattern = "-?(\\d*\\.\\d+|\\d+\\.\\d*)";
        boolean literalIsNumber = Pattern.matches(int_Pattern, literal) || Pattern.matches(float_Pattern, literal);
        boolean ColumnContainsNumber = ((c1.getType() == Type.FLOAT) || (c1.getType() == Type.INT));
        return !literal.contains("'") &&literalIsNumber && ColumnContainsNumber;

    }

    public boolean BothColumnsContainsNumbers(Column c1, Column c2) {
        return (c1.getType() == Type.FLOAT || c1.getType() == Type.INT) && (c2.getType() == Type.FLOAT || c2.getType() == Type.INT);
    }

    public boolean BothColumnsContainsString(Column c1, Column c2) {
        return c1.getType() == Type.STRING && c2.getType() == Type.STRING;
    }



    protected abstract boolean compareToNumber(Column c1, double literal, int row);

    protected abstract boolean compareToString(Column<String> c1, String literal, int row);

    protected abstract boolean compareNumericColumns(Column c1, Column c2, int row);

    protected abstract boolean compareStringColumns(Column<String> c1, Column<String> c2, int row);

}
