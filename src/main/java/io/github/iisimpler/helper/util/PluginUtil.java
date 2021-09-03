package io.github.iisimpler.helper.util;

import cn.hutool.core.collection.CollUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PluginUtil {


    public static List<String> resolvePath(AnActionEvent event) {

        DataContext dataContext = event.getDataContext();

        Editor editor = CommonDataKeys.EDITOR.getData(dataContext);
        if (editor != null) {
            PsiFile psiFile = event.getRequiredData(CommonDataKeys.PSI_FILE);
            String path = psiFile.getVirtualFile().getPath();
            return CollUtil.newArrayList(path);
        }

        VirtualFile[] files = event.getRequiredData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        return Arrays.stream(files).map(VirtualFile::getPath).collect(Collectors.toList());
    }

    public static void copy(String text) {
        CopyPasteManager.getInstance().setContents(new StringSelection(text));
    }

}
