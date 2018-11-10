import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import java.net.URISyntaxException;

public class SourcegraphLinkAction extends AnAction {
    SourcegraphLinkConfig config;

    public SourcegraphLinkAction() {
        super("Generate Sourcegraph Link");
        this.config = ServiceManager.getService(SourcegraphLinkConfig.class);
    }

    public void actionPerformed(AnActionEvent event) {
        SourcegraphLinkParameters params = this.parseAction(event);
        try {
            String link = this.createSourcegraphLink(params);
            this.saveLinkToClipboard(link);

            if (this.config.getOpenLinkInBrowser() && Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                } catch (Exception e) {
                    // Intentionally left blank.
                }
            }
        } catch (URISyntaxException e) {
            throw new Error(e);
        }
    }

    private SourcegraphLinkParameters parseAction(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        if (project == null) {
            throw new Error("No project selected");
        }

        String filePath = this.getFilePath(event);
        int lineNumber = this.getLineNumber(event);

        return new SourcegraphLinkParameters(project, filePath, lineNumber);
    }

    private String getFilePath(AnActionEvent event) {
        VirtualFile vFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (vFile == null) {
            throw new Error("No file selected");
        }
        return vFile.getCanonicalPath();
    }

    private int getLineNumber(AnActionEvent event) {
        Caret caret = event.getData(PlatformDataKeys.CARET);
        if (caret == null) {
            return -1;
        }
        return caret.getLogicalPosition().line + 1;
    }

    private String createSourcegraphLink(SourcegraphLinkParameters params) throws URISyntaxException {
        String lineLink = params.getLineNumber() > 0 ? String.format("#L%d", params.getLineNumber()) : "";
        String projectName = params.getProjectName();
        if (!params.getGitRevision().isEmpty()) {
            projectName = String.format("%s@%s", params.getProjectName(), params.getGitRevision());
        }

        String path = String.format("/%s/-/blob%s%s", projectName, params.getFilePathInProject(), lineLink);
        String serverUrl = this.config.getServerUrl();
        URI uri = new URI(serverUrl + path);
        return uri.normalize().toString();
    }

    private void saveLinkToClipboard(String link) {
        StringSelection selection = new StringSelection(link);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
}
