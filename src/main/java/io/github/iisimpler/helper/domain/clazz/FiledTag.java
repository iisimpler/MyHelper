package io.github.iisimpler.helper.domain.clazz;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.thoughtworks.qdox.model.DocletTag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class FiledTag {

    private Integer length;
    private String dbType;
    private Object defaultVal;
    private Boolean unique;
    private Boolean index;
    private Boolean primaryKey;
    private Boolean nullAble;

    public static FiledTag parse(List<DocletTag> clazzTags) {
        Map<String, String> tagMap = Optional.ofNullable(clazzTags).orElse(new ArrayList<>()).stream()
                .collect(Collectors.toMap(DocletTag::getName, DocletTag::getValue));
        FiledTag filedTag = new FiledTag();
        filedTag.setLength(resolveIntegerTag(tagMap, "length"));
        filedTag.setDbType(resolveValTag(tagMap, "dbType"));
        filedTag.setDefaultVal(resolveValTag(tagMap, "defaultVal"));
        filedTag.setUnique(resolveBooleanTag(tagMap, "unique"));
        filedTag.setIndex(resolveBooleanTag(tagMap, "index"));
        filedTag.setPrimaryKey(resolveBooleanTag(tagMap, "primaryKey"));
        filedTag.setNullAble(resolveBooleanTag(tagMap, "nullAble"));
        return filedTag;
    }

    private static Boolean resolveBooleanTag(Map<String, String> tagMap, String tagName) {
        String tagVal = tagMap.get(tagName);
        if (StrUtil.containsAnyIgnoreCase(tagVal, "true", "false")) {
            return Boolean.valueOf(tagVal);
        }
        return tagMap.containsKey(tagName);
    }

    private static String resolveValTag(Map<String, String> tagMap, String tagName) {
        String tagVal = tagMap.get(tagName);
        if (StrUtil.isNotBlank(tagVal)) {
            return tagVal;
        }
        return null;
    }

    private static Integer resolveIntegerTag(Map<String, String> tagMap, String tagName) {
        String tagVal = tagMap.get(tagName);
        if (StrUtil.isBlank(tagVal)) {
            return null;
        }
        if (!NumberUtil.isNumber(tagVal)) {
            return null;
        }
        return Integer.valueOf(tagVal);
    }
}
