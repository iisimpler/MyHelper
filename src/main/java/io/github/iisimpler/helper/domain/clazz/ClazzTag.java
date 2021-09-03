package io.github.iisimpler.helper.domain.clazz;

import com.thoughtworks.qdox.model.DocletTag;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
public class ClazzTag {

    private String table;

    public static ClazzTag parse(List<DocletTag> clazzTags) {
        Map<String, String> tagMap = Optional.ofNullable(clazzTags).orElse(new ArrayList<>()).stream()
                .collect(HashMap::new, (m, v) -> m.put(v.getName(), v.getValue()), HashMap::putAll);

        ClazzTag clazzTag = new ClazzTag();
        clazzTag.setTable(tagMap.get("table"));
        if (clazzTag.getTable() == null && tagMap.containsKey("table")) {
            clazzTag.setTable(tagMap.get(""));
        }
        return clazzTag;
    }
}
