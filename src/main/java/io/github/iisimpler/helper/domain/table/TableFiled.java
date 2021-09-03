package io.github.iisimpler.helper.domain.table;

import cn.hutool.core.util.StrUtil;
import io.github.iisimpler.helper.domain.clazz.Filed;
import io.github.iisimpler.helper.domain.clazz.FiledTag;
import io.github.iisimpler.helper.domain.table.enums.DbColumnType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class TableFiled {

    private String name;

    private String type;

    private Object defaultVal;

    private Boolean nullAble;

    private Boolean primaryKey;

    private Boolean index;

    private Boolean unique;

    private String comment;

    private static final Map<String, DbColumnType> columnTemplateMap = new HashMap<>();
    static {
        columnTemplateMap.put(String.class.getCanonicalName(), DbColumnType.VARCHAR);
        columnTemplateMap.put(BigDecimal.class.getCanonicalName(), DbColumnType.DECIMAL);
        columnTemplateMap.put(int.class.getCanonicalName(), DbColumnType.INT);
        columnTemplateMap.put(Integer.class.getCanonicalName(), DbColumnType.INT);
        columnTemplateMap.put(long.class.getCanonicalName(), DbColumnType.BIGINT);
        columnTemplateMap.put(Long.class.getCanonicalName(), DbColumnType.BIGINT);
        columnTemplateMap.put(Boolean.class.getCanonicalName(), DbColumnType.TINYINT1);
        columnTemplateMap.put(Enum.class.getCanonicalName(), DbColumnType.TINYINT2);
        columnTemplateMap.put(Date.class.getCanonicalName(), DbColumnType.DATETIME);
        columnTemplateMap.put(LocalDate.class.getCanonicalName(), DbColumnType.DATE);
        columnTemplateMap.put(LocalDateTime.class.getCanonicalName(), DbColumnType.DATETIME);
    }

    public static List<TableFiled>parse(List<Filed> filedList) {
        return Optional.ofNullable(filedList).orElse(new ArrayList<>()).stream()
                .map(TableFiled::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }
    public static TableFiled parse(Filed filed) {
        DbColumnType dbColumnType = columnTemplateMap.get(filed.getType());
        if (dbColumnType == null) {
            return null;
        }

        TableFiled tableFiled = new TableFiled();
        tableFiled.setName(StrUtil.toUnderlineCase(filed.getName()));
        tableFiled.setComment(filed.getComment());

        FiledTag tag = filed.getTag();

        if (StrUtil.isNotBlank(tag.getDbType())) {
            tableFiled.setType(tag.getDbType());
        } else {
            tableFiled.setType(inferColumnType(filed, dbColumnType));
        }
        tableFiled.setNullAble(tag.getNullAble() != null && tag.getNullAble());
        tableFiled.setDefaultVal(inferDefaultVal(tableFiled.getName(), tag, dbColumnType));
        tableFiled.setUnique(tag.getUnique() != null && tag.getUnique());
        Boolean primaryKey = tag.getPrimaryKey();
        tableFiled.setPrimaryKey(primaryKey != null && primaryKey);
        Boolean index = tag.getIndex();
        tableFiled.setIndex(index != null && index);
        return tableFiled;
    }

    private static Object inferDefaultVal(String tableFiledName, FiledTag tag, DbColumnType dbColumnType) {
        Object defaultVal = tag.getDefaultVal();
        if (StrUtil.equalsAny(tableFiledName, "create_time")) {
            defaultVal = "current_timestamp";
        }
        if (StrUtil.equalsAny(tableFiledName, "update_time")) {
            defaultVal = "current_timestamp on update current_timestamp";
        }
        if (Objects.isNull(defaultVal)) {
            defaultVal = dbColumnType.getDefaultVal();
        }
        return defaultVal;
    }

    private static String inferColumnType(Filed filed, DbColumnType dbColumnType) {
        FiledTag tag = filed.getTag();
        Integer length = tag.getLength();
        if (length == null) {
            length = dbColumnType.getDefaultLength();
        }
        return StrUtil.format(dbColumnType.getTemplate(), length);

    }
}
