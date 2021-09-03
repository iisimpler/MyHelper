package io.github.iisimpler.helper.action;

import cn.hutool.core.collection.CollUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.util.ExceptionUtil;
import io.github.iisimpler.helper.domain.clazz.Clazz;
import io.github.iisimpler.helper.domain.table.Database;
import io.github.iisimpler.helper.util.ClazzUtil;
import io.github.iisimpler.helper.util.NotifyUtil;
import io.github.iisimpler.helper.util.PluginUtil;
import io.github.iisimpler.helper.util.TemplateUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CopyTableAction extends AnAction {

    private static final Logger LOG = Logger.getInstance(CopyTableAction.class);

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        FileDocumentManager.getInstance().saveAllDocuments();
        try {
            List<String> pathList = PluginUtil.resolvePath(event);
            List<Clazz> clazzList = ClazzUtil.parseClazz(pathList);
            if (CollUtil.isNotEmpty(clazzList)) {
                Database database = Database.parse(clazzList);
                String tableSql = TemplateUtil.render(database);
                PluginUtil.copy(tableSql);
                NotifyUtil.info("SQL has been copied to the clipboard.");
            }
        } catch (Exception e) {
            NotifyUtil.error("Execution exception: " + ExceptionUtil.getThrowableText(e));
            LOG.error("[MyHelper] Execution exception", e);
        }
    }

}
