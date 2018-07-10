package db;

import db.comparision.*;
import db.operation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database {
    private static final String float_Pattern = "-?(\\d*\\.\\d+|\\d+\\.\\d*)",
            int_Pattern = "-?(\\d+)",
            string_Pattern = "'.+'",
            EXIT = "exit",
            PROMPT = ">  ";
    private HashMap<String, Table> tableMap;
    private Table SelectedTable;

    public Database() {
        // YOUR CODE HERE
        tableMap = new HashMap<>();
    }

    public String createNewTable(String name, List<String> colNames, List<String> colTypes) {
        if (tableMap.containsKey(name))
            return "ERROR:table name already exists";
        Table t = new Table(name);
        for (int i = 0; i < colNames.size(); i++) {
            Type colType;
            switch (colTypes.get(i)) {
                case "FLOAT":
                    colType = Type.FLOAT;
                    break;
                case "INT":
                    colType = Type.INT;
                    break;
                case "STRING":
                    colType = Type.STRING;
                    break;
                default:
                    return "ERROR:TYPE is wrong";

            }
            t.AddColumn(colNames.get(i), colType);
        }
        tableMap.put(name, t);
        return "";
    }

    public String dropTable(String tableName) {
        if (!tableMap.containsKey(tableName))
            return "ERROR:database doesn't have this table" + tableName;
        tableMap.remove(tableName);
        return "";
    }

    public String storeTable(String tableName) throws FileNotFoundException {
        if (!tableMap.containsKey(tableName))
            return "ERROR: can't find this file";
        String filename = tableName + ".tbl";
        PrintWriter out = new PrintWriter(filename);
        out.print(tableMap.get(tableName).toString());
        out.flush();
        out.close();
        return "";
    }

    public String insertRow(String name, String[] rowArray) {
        if (!tableMap.containsKey(name))
            return "ERROR:database dosen't have this table";
        Table t = tableMap.get(name);
        if (t.getColNum() != rowArray.length)
            return "ERROR: rowNum can't match colNum";
        List<String> rowValues = new ArrayList<>();
        for (int i = 0; i < rowArray.length; i++) {
            String value = rowArray[i];
            boolean isSpecialValue = (value.equals("NOVALUE") || value.equals("Nan"));
            switch (t.types(i)) {
                case FLOAT:
                    boolean isFloatPattern = Pattern.matches(float_Pattern, value);
                    if (!isFloatPattern && !isSpecialValue)
                        return "ERROR: type is float but can't match floatPattern";
                    rowValues.add(value);
                    break;
                case INT:
                    boolean isIntPattern = Pattern.matches(int_Pattern, value);
                    if (!isIntPattern && !isSpecialValue)
                        return "ERROR: type is int but can't match int Pattern";
                    rowValues.add(value);
                    break;
                case STRING:
                    boolean isStringPattern = Pattern.matches(string_Pattern, value);
                    if (!isStringPattern && !isSpecialValue)
                        return "ERROR: type is string but can't match string Pattern";
                    rowValues.add(value);
                    break;

            }
        }
        t.AddRowValues(rowValues);
        return "";

    }

    public String printTable(String name) {
        if (!tableMap.containsKey(name))
            return "ERROR: tableMap doesn't have this" + name;
        Table t = tableMap.get(name);
        return t.toString();
    }

    public String select(String[] expressions, String[] tableList, String[] conditions) {
        List<Table> tables = new ArrayList<>();
        for(String t: tableList){
            if(!tableMap.containsKey(t))
                return "ERROR:database doesn't have this table" +t;
            tables.add(tableMap.get(t));
        }
        Table result;
        if(expressions.length == 1 && expressions[0].equals("*")){
            result = Table.Join(tables);
            if(conditions != null) {
                String retVal = evaluateTableConditions(result, conditions);
                if(!retVal.equals(""))
                    return retVal;
            }
        } else{
            result = new Table("");
            Table joined = Table.Join(tables);
            if(conditions != null) {
                String retVal = evaluateTableConditions(joined, conditions);
                if(!retVal.equals(""))
                    return retVal;
            }
            String retVal = evaluateColumnExpressions(result, joined, expressions);
            if(!retVal.equals(""))
                return retVal;
        }

        SelectedTable = result;
        return result.toString();

    }

    private String evaluateTableConditions(Table t, String[] conditions) {
        for (String cond : conditions) {
            //split by spaces that not in single quotes
            String[] condition = cond.split("\\s+(?=([^']*'[^']*')*[^']*$)");

            if (condition.length != 3)
                return "ERROR:Malformed conditions.";
            if (!t.containsColumn(condition[0]))
                return "ERROR: table doesn't have this column" + condition[0];


            Comparision comparision = getComparision(condition[1]);
            if(comparision == null)
                return "ERROR:can't konw what comparison it is" + condition[1];

            boolean LiteralIsInt = Pattern.matches(int_Pattern, condition[2]);
            boolean LiteralIsFloat = Pattern.matches(float_Pattern, condition[2]);
            boolean LiteralIsString = Pattern.matches(string_Pattern, condition[2]);
            if(!t.containsColumn(condition[2]) && !LiteralIsFloat && !LiteralIsInt && !LiteralIsString) {
                return "ERROR: malformed condition " + condition[2];
            }else if(LiteralIsString){
                condition[2] = condition[2].substring(1,condition[2].length()-1);
            }

            boolean BothOperandsAreStrings = t.getColumn(condition[0]).getType() == Type.STRING;
            boolean OneOfOperandIsString = t.getColumn(condition[0]).getType() == Type.STRING;
            if(t.containsColumn(condition[2])){
                BothOperandsAreStrings &= t.getColumn(condition[2]).getType() == Type.STRING;
                OneOfOperandIsString |= t.getColumn(condition[2]).getType() == Type.STRING;
            } else{
                BothOperandsAreStrings &= LiteralIsString;
                OneOfOperandIsString |= LiteralIsString;
            }

            if(!BothOperandsAreStrings && OneOfOperandIsString){
                return "ERROR:can't compare string to int or float";
            }
            t.makeComparision(comparision, condition);
        }
        return "";
    }

    private Comparision getComparision(String expr) {
        switch (expr) {
            case "==":
                return new Equals();
            case "!=":
                return new NotEquals();
            case "<":
                return new LessThan();
            case ">":
                return new GreaterThan();
            case "<=":
                return new LessThanOrEquals();
            case ">=":
                return new GreaterThan();
            default:
                return null;
        }
    }

    private String evaluateColumnExpressions(Table t, Table joined, String[] expressions) {
        for (String colExpr : expressions) {
            String[] expr = colExpr.split("\\s+");

            if (expr.length == 1) {
                if (joined.containsColumn(expr[0]))
                    t.AddColumnFromTable(joined, expr[0]);
                else
                    return "ERROR:joined table doesn't have this column" + expr[0];
            } else if (expr.length == 3 && expr[1].equals("as") ||
                    expr.length == 5 && expr[3].equals("as")) {
                String retVal = evaluateArithmeticExpression(t, joined, colExpr.split("\\s+as\\s+"));

                if (!retVal.equals(""))
                    return retVal;
            } else {
                return "ERROR:malformed colExpr " + colExpr;
            }
        }
        return "";
    }

    private String evaluateArithmeticExpression(Table t, Table joined, String[] arithmeticExpr) {
        String firstOperand;
        String operator;
        String secondOperand;
        final Pattern operationPattern = Pattern.compile("(\\w+)\\s*([+\\-*/])\\s*(\\'*\\w+\\'*)");
        Matcher m;
        if ((m = operationPattern.matcher(arithmeticExpr[0])).matches()) {
            firstOperand = m.group(1);
            operator = m.group(2);
            secondOperand = m.group(3);
        } else {
            String ArrayString = Arrays.toString(arithmeticExpr);
            return "ERROR: illegal arithmeticExpr" + ArrayString;
        }
        if (!joined.containsColumn(firstOperand))
            return "ERROR " + firstOperand + " not in the table joined";
        boolean LiteralIsInt = Pattern.matches(int_Pattern,secondOperand);
        boolean LiteralIsFloat = Pattern.matches(float_Pattern,secondOperand);
        boolean LiteralIsString = Pattern.matches(string_Pattern, secondOperand);
        if (!joined.containsColumn(secondOperand) && !LiteralIsFloat && !LiteralIsInt && !LiteralIsString)
            return "ERROR secondOperand" + secondOperand + "is illeagal";
        boolean BothOperandsAreString = joined.getColumn(firstOperand).getType() == Type.STRING;
        boolean OneOfOperandsIsString = joined.getColumn(firstOperand).getType() == Type.STRING;
        if (joined.containsColumn(secondOperand)) {
            BothOperandsAreString &= joined.getColumn(secondOperand).getType() == Type.STRING;
            OneOfOperandsIsString |= joined.getColumn(secondOperand).getType() == Type.STRING;
        } else {
            BothOperandsAreString &= LiteralIsString;
            OneOfOperandsIsString |= LiteralIsString;
        }

        if (!operator.equals("+") && BothOperandsAreString)
            return "ERROR: only + operator can process strings";
        else if (!BothOperandsAreString && OneOfOperandsIsString)
            return "ERROR: string can't have operand with int or floats";

        Operation operation = getArithmeticOperation(operator);
        if (operation == null)
            return "ERROR: this operation doesn't exist " + operator;
        t.evaluateArithmeticExpression(joined, operation, firstOperand, secondOperand, arithmeticExpr[1]);
        return "";
    }

    private Operation getArithmeticOperation(String operator) {
        switch (operator) {
            case "+":
                return new AddOperation();
            case "-":
                return new Subtraction();
            case "*":
                return new Multiplication();
            case "/":
                return new Division();
            default:
                return null;
        }
    }
    public String createSelectedTable(String name, String[] expression, String[] tableNames, String[] condition){
        String retVal = select(expression, tableNames, condition);
        if(!retVal.equals(""))
            return retVal;
        SelectedTable.setName(name);
        tableMap.put(name, SelectedTable);
        return "";
    }
    public String transact(String query) {
        return CommandParser.eval(query, this);
    }

    public static void main(String[] args) throws IOException {
        Database db = new Database();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(PROMPT);

        String line = "";
        while ((line = in.readLine()) != null) {
            if (EXIT.equals(line)) {
                break;
            }
            if (!line.trim().isEmpty()) {
                String result = db.transact(line);
                if (result.length() > 0) {
                    System.out.println(result);
                }
            }
            System.out.println(PROMPT);
        }
        in.close();
    }
}
