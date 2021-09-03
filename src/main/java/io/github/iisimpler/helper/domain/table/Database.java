package io.github.iisimpler.helper.domain.table;

import io.github.iisimpler.helper.domain.clazz.Clazz;
import lombok.Data;

import java.util.List;

@Data
public class Database {

    private String databaseName = "db_name";
    private List<Table> tableList;

    public static Database parse(List<Clazz> clazzList) {
        List<Table> tableList = Table.parse(clazzList);
        Database database = new Database();
        database.setTableList(tableList);
        return database;
    }
}
