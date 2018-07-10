package db.operation;

import db.Column;
import db.Type;

import java.util.regex.Pattern;

abstract class AbstractOperation implements Operation{

    @Override
    public Column operation(String name, Column c, String literal){
        if(ColumnAndLiteralContainsIntegers(c, literal)){
            int num = Integer.parseInt(literal);
            return operationInt(name, c, num);
        }

        float num = Float.parseFloat(literal);
        return operationFloat(name, c, num);
    }

    @Override
    public Column operation(String name, Column c1, Column c2) {
        if(bothColumnContainsIntegers(c1, c2))
            return operationInt(name, c1, c2);
        return operationFloat(name, c1, c2);
    }

    abstract protected Column<Integer> operationInt(String colName, Column<Integer> c, int literal);
    abstract protected Column<Integer> operationInt(String colName, Column<Integer> c1, Column<Integer> c2);
    abstract protected Column<Double>  operationFloat(String colName, Column c, double literal);
    abstract protected Column<Double>  operationFloat(String colName, Column c1, Column c2);

    protected static boolean bothColumnContainsIntegers(Column c1, Column c2){
        return (c1.getType() == Type.INT)&&(c2.getType() == Type.INT);
    }

    protected static boolean ColumnAndLiteralContainsIntegers(Column c, String literal){
        String int_Pattern = "-?(//d+)";
        boolean isIntLiteral = Pattern.matches(int_Pattern, literal);
        return  (!literal.contains("'")) && isIntLiteral && c.getType() == Type.INT;
    }

}
