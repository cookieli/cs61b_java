package db.comparision;

import db.Column;

public interface Comparision {

    boolean compare(Column c1, String literal, int row);

    boolean compare(Column c1, Column c2, int row);
}
