package io.github.iisimpler.helper.domain.clazz;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Filed {

    private String name;
    private String type;
    private String comment;
    private FiledTag tag;
    private Table<String, String, Object> annotations = HashBasedTable.create();

    public static List<Filed> parse(List<JavaField> javaFieldList) {
        return Optional.ofNullable(javaFieldList).orElse(new ArrayList<>()).stream()
                .map(Filed::parse)
                .collect(Collectors.toList());

    }
    public static Filed parse(JavaField javaField) {
        Filed filed = new Filed();
        String fieldName = javaField.getName();
        filed.setName(fieldName);
        JavaClass filedType = javaField.getType();
        String typeName = filedType.getFullyQualifiedName();
        filed.setType(typeName);
        if (filedType.isEnum() || fieldName.endsWith("Enum") || typeName.endsWith("Enum")) {
            filed.setType(Enum.class.getCanonicalName());
        }
        filed.setComment(javaField.getComment());
        if (StrUtil.isBlank(filed.getComment())) {
            filed.setComment(filed.getName());
        }

        filed.setTag(FiledTag.parse(javaField.getTags()));

        Optional.ofNullable(javaField.getAnnotations()).orElse(new ArrayList<>()).forEach(it -> {
            JavaClass type = it.getType();
            Map<String, AnnotationValue> propertyMap = it.getPropertyMap();
            if (CollUtil.isEmpty(propertyMap)) {
                filed.getAnnotations().put(type.getFullyQualifiedName(), "", "");
            } else {
                propertyMap.forEach((k, v) ->
                        filed.getAnnotations().put(type.getFullyQualifiedName(), k, v + ""));
            }
        });
        return filed;
    }
}
