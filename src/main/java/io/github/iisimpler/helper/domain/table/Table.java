package io.github.iisimpler.helper.domain.table;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.github.iisimpler.helper.domain.clazz.Clazz;
import io.github.iisimpler.helper.domain.clazz.ClazzTag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class Table {

    private String tableName;

    private String engine = "InnoDB";

    private String charset = "utf8mb4";

    private String collate = "utf8mb4_unicode_ci";

    private String comment;

    private List<TableFiled> tableFiledList;

    private Set<String> primaryKeySet;
    private Set<String> uniqueKeySet;
    private Set<String> indexSet;


    public static List<Table> parse(List<Clazz> clazzList) {
        return Optional.ofNullable(clazzList).orElse(new ArrayList<>()).stream()
                .filter(Objects::nonNull)
                .map(Table::parse)
                .collect(Collectors.toList());

    }
    public static Table parse(Clazz clazz) {
        Table table = new Table();

        ClazzTag clazzTag = clazz.getTag();
        String tableName = clazzTag.getTable();
        if (StrUtil.isBlank(tableName)) {
            tableName = StrUtil.toUnderlineCase(clazz.getName());
        }
        table.setTableName(tableName);
        table.setComment(clazz.getComment());
        List<TableFiled> tableFiledList = TableFiled.parse(clazz.getFiledList());
        if (CollUtil.isNotEmpty(tableFiledList)) {
            // 兜底主键
            Set<Boolean> primaryKeySet = tableFiledList.stream().map(TableFiled::getPrimaryKey).collect(Collectors.toSet());
            if (!primaryKeySet.contains(true)) {
                tableFiledList.forEach(it -> {
                    if (StrUtil.equals(it.getName(), "id")) {
                        it.setPrimaryKey(true);
                    }
                });
            }
            // 解析主键
            Set<String> primaryKeys = tableFiledList.stream()
                    .filter(TableFiled::getPrimaryKey)
                    .peek(it -> {
                        it.setDefaultVal(null);
                        it.setNullAble(false);
                    })
                    .map(TableFiled::getName)
                    .collect(Collectors.toSet());
            table.setPrimaryKeySet(primaryKeys);

            // 解析唯一索引
            Set<String> uniqueKeys = tableFiledList.stream()
                    .filter(TableFiled::getUnique)
                    .map(TableFiled::getName)
                    .filter(it -> !primaryKeys.contains(it))
                    .collect(Collectors.toSet());
            table.setUniqueKeySet(uniqueKeys);

            // 解析索引
            Set<String> indexes = tableFiledList.stream()
                    .filter(TableFiled::getIndex)
                    .map(TableFiled::getName)
                    .filter(it -> !primaryKeys.contains(it))
                    .filter(it -> !uniqueKeys.contains(it))
                    .collect(Collectors.toSet());
            table.setIndexSet(indexes);

            table.setTableFiledList(tableFiledList);
        }
        return table;
    }
}
