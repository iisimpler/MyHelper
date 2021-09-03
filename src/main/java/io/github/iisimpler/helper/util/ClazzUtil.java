package io.github.iisimpler.helper.util;

import com.thoughtworks.qdox.JavaProjectBuilder;
import io.github.iisimpler.helper.domain.clazz.Clazz;

import java.io.File;
import java.util.List;

public class ClazzUtil {

    public static List<Clazz> parseClazz(List<String> pathList) {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        for (String path : pathList) {
            builder.addSourceTree(new File(path));
        }
        return Clazz.parse(builder.getClasses());
    }

}
