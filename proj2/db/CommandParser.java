package db;



import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.nio.charset.StandardCharsets.UTF_8;

public class CommandParser {
    // Various common constructs, simplifies parsing.
    private static final String REST = "\\s*(.*)\\s*",
                               COMMA = "\\s*,\\s*",
                                 AND = "\\s+and\\s+";
    // Stage 1 syntax, contains the command name.
    private static final Pattern CREATE_CMD = Pattern.compile("create table " + REST),
                                 LOAD_CMD   = Pattern.compile("load " + REST),
                                 PRINT_CMD  = Pattern.compile("print " + REST),
                                 STORE_CMD  = Pattern.compile("store " + REST),
                                 DROP_CMD   = Pattern.compile("drop table " + REST),
                                 SELECT_CMD = Pattern.compile("select "+ REST),
                                 INSERT_CMD = Pattern.compile("insert into " + REST);
    // Stage 2 syntax, contains the clauses of commands
    private static final Pattern CREATE_NEW = Pattern.compile("(\\S+)\\s+\\(\\s*(\\S+\\s+\\S+\\s*" +
                                                    "(?:,\\s*\\S+\\s+\\S+\\s*)*)\\)"),
                                 SELECT_CLS = Pattern.compile("([^,]+?(?:,[^,]+?)*)\\s+from\\s+" +
                                              "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s+where\\s+" +
                                              "([\\w\\s+\\-*/'<>=!.]+?(?:\\s+and\\s+" +
                                              "[\\w\\s+\\-*/'<>=!.]+?)*))?"),
                                 CREATE_SEL = Pattern.compile("(\\S+)\\s+as select\\s+" +
                                                   SELECT_CLS.pattern()),
                                 INSERT_CLS = Pattern.compile("(\\S+)\\s+values\\s+(.+?" +
                                              "\\s*(?:,\\s*.+?\\s*)*)");


    public static String eval(String query, Database db) {
//        System.out.println(query);
        Matcher m;
        if ((m = CREATE_CMD.matcher(query)).matches()) {
            return createTable(m.group(1), db);
        } else if ((m = LOAD_CMD.matcher(query)).matches()) {
            return loadTable(m.group(1), db);
        } else if((m = PRINT_CMD.matcher(query)).matches()) {
            return printTable(m.group(1), db);
        } else if((m = DROP_CMD.matcher(query)).matches()){
            return dropTable(m.group(1), db);
        } else if((m = STORE_CMD.matcher(query)).matches()){
            return storeTable(m.group(1), db);
        } else if((m = INSERT_CMD.matcher(query)).matches()){
            return insertRow(m.group(1), db);
        } else if((m = SELECT_CMD.matcher(query)).matches()){
            return select(m.group(1), db);
        }else{
            return "Error: can't know what command it is";
        }
    }
    private static String  createTable(String expr, Database db) {
        Matcher m;
        if ((m = CREATE_NEW.matcher(expr)).matches()) {
            return createTable(m.group(1), m.group(2).split(COMMA), db);
        } else if((m = CREATE_SEL.matcher(expr)).matches()) {
            return createSelectedTable(m.group(1), m.group(2),m.group(3),m.group(4),db);
        }
        return "ERROR: create command format can't match expr";
    }


    private static String createTable(String name, String[] cols, Database db) {
        List<String> colNames = new ArrayList<>();
        List<String> colTypes = new ArrayList<>();
        for(String col: cols) {
            String[] colParams = col.split("\\s+");
            if (colParams.length != 2) {
                return "ERROR:colParams must be name and type";
            }
            colNames.add(colParams[0]);
            colTypes.add(colParams[1]);
        }
        if(colNames.size() != colTypes.size())
            return "ERROR: colName can't match colType length";

        return db.createNewTable(name, colNames, colTypes);

    }
    private static String createSelectedTable(String name, String exprs, String tableNames, String conds, Database db){
        String[] conditions = null;
        if(conds != null){
            conditions = conds.split(COMMA);
        }

        return db.createSelectedTable(name, exprs.split(COMMA),tableNames.split(COMMA),  conditions);
    }

    /*we need to construct file name
    * find it's path, read it with line reader
    * */
    private static String loadTable(String name, Database db) {
        //what we need do here?
        String filePath = "/home/lzx/cs61b/cs61b_java/proj2/";
        List<String> lines;
        String suffix = ".tbl";
        String filename = name + suffix;
        Path path = Paths.get(filePath+filename);
        boolean pathExists = Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
        if (!pathExists)
            return "this file doesn't exists" + filePath+filename;
        try {
            lines = Files.readAllLines(path, UTF_8);
        } catch (IOException e) {
            return "ERROR: could not open" + filename;
        }

        if (lines.size() == 0 || lines.get(0) =="")
            return "ERROR: illegal .tbl format";
        String[] cols = lines.get(0).split(",");

        db.dropTable(name);
        for(int i = 0; i < cols.length; i++) {
            cols[i] = removeFirstSpace(cols[i]);
        }
        String ret = createTable(name, cols, db);
        if(ret != "") return ret;
        for(int i = 1; i < lines.size(); i++) {
            ret = insertRow(name, lines.get(i), db);
            if(ret != "") {
                db.dropTable(name);
                return ret;
            }
        }
        return "";
    }

    private static String removeFirstSpace(String expr){
        if(expr.indexOf(" ") == 0)
            return removeFirstSpace(expr.substring(1));
        return expr;
    }

    private static String insertRow(String expr, Database db){
        Matcher m = INSERT_CLS.matcher(expr);
        if(!m.matches())
            return "ERROR: insert cmd is unformed";
        String name = m.group(1);
        String row  = m.group(2);
        return insertRow(name, row, db);
    }

    private static String insertRow(String name, String row, Database db) {
        String[] rowValues = row.split(COMMA);
        return db.insertRow(name, rowValues);
    }

    private static String storeTable(String name, Database db){
        try{
            return db.storeTable(name);
        } catch (FileNotFoundException e){
            return "ERROR: can't find file name";
        }
    }
    private static String printTable(String name, Database db) {
        return db.printTable(name);
    }

    private static String dropTable(String name, Database db) {
        return db.dropTable(name);
    }

    private static String select(String command, Database db) {
        Matcher m = SELECT_CLS.matcher(command);
        if(!m.matches())
            return "ERROR: select command doesn't have right command";
        String[] expressions = m.group(1).split(COMMA);
        String[] tableList   = m.group(2).split(COMMA);
        String[] conditions  = null;
        if(m.group(3) != null)
            conditions = m.group(3).split(AND);
        return db.select(expressions,tableList, conditions);
    }
    public static void main(String[] args) {
        Database db = new Database();
        String arg = "load  temp";
        eval(arg, db);
        eval("print  temp", db);
    }

}
