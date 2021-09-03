package io.github.iisimpler.helper.domain.table.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DbColumnType {

    VARCHAR("varchar({})", 20, "''", true),
    DECIMAL("decimal({}, 4)", 20, 0, true),
    TINYINT("tinyint({})", 2, 0, true),
    TINYINT1("tinyint(1)", 1, 0, true),
    TINYINT2("tinyint(2)", 2, 0, true),
    SMALLINT("smallint", 0, 0, true),
    INT("int", 0, 0, true),
    BIGINT("bigint", 0, 0, true),
    DATE("date", 0, "'1000-01-01'", true),
    DATETIME("datetime", 0, "'1000-01-01 00:00:00'", true),
    ;

    String template;
    Integer defaultLength;
    Object defaultVal;
    Boolean notNull;
}
