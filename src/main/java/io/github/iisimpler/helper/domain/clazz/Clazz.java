package io.github.iisimpler.helper.domain.clazz;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import io.github.iisimpler.helper.util.NotifyUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Data
public class Clazz {

    private String name;
    private String comment;
    private String pkgName;
    private ClazzTag tag;
    private Table<String, String, String> annotations = HashBasedTable.create();

    private List<Filed> filedList;

    public static List<Clazz> parse(Collection<JavaClass> javaClassList) {
        return Optional.ofNullable(javaClassList).orElse(new ArrayList<>()).stream()
                .map(Clazz::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static Clazz parse(JavaClass javaClass) {
        ClazzTag clazzTag = ClazzTag.parse(javaClass.getTags());
        if (clazzTag.getTable() == null) {
            NotifyUtil.warn("Skip as there is no @table tag: " + javaClass.getName());
            return null;
        }

        Clazz clazz = new Clazz();
        clazz.setName(javaClass.getName());
        clazz.setComment(javaClass.getComment());
        clazz.setPkgName(javaClass.getPackageName());
        clazz.setTag(clazzTag);
        Optional.ofNullable(javaClass.getAnnotations()).orElse(new ArrayList<>()).forEach(it -> {
            JavaClass type = it.getType();
            Map<String, AnnotationValue> propertyMap = it.getPropertyMap();
            if (CollUtil.isEmpty(propertyMap)) {
                clazz.getAnnotations().put(type.getFullyQualifiedName(), "", "");
            } else {
                propertyMap.forEach((k, v) ->
                        clazz.getAnnotations().put(type.getFullyQualifiedName(), k, v + ""));
            }
        });
        clazz.setFiledList(Filed.parse(javaClass.getFields()));
        return clazz;
    }
}
