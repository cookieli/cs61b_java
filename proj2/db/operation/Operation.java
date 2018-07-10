package db.operation;

import db.Column;

public interface Operation {

    Column operation(String name, Column c, String literal);
    Column operation(String name, Column c1, Column c2);
}
