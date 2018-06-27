package db;

public enum Type {
    STRING("string"),
    FLOAT("float"),
    INT("int"),
    NOVALUE("noValue"),
    NaN("NaN");
    private String description;
    private Type(String description){
        this.description = description;
    }
    public String getDecription(){ return description;}
}

