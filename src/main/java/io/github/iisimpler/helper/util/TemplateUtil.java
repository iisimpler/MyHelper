package io.github.iisimpler.helper.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.github.iisimpler.helper.domain.table.Database;

import java.io.StringWriter;

public class TemplateUtil {

    private static final String TEMPLATE_BASE_PATH = "/templates";
    private static final String TABLE_TEMPLATE_NAME = "tables.ftlh";
    private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);


    static {
        cfg.setClassForTemplateLoading(TemplateUtil.class, TEMPLATE_BASE_PATH);
    }

    private static Template getTemplate(String templateName) throws Exception {
        return cfg.getTemplate(templateName);
    }

    public static String render(Database database) throws Exception {
        Template tablesTemplate = getTemplate(TABLE_TEMPLATE_NAME);
        StringWriter writer = new StringWriter();
        tablesTemplate.process(database, writer);
        return writer.toString();
    }
}
