import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.diff.ItemLatestState;
import com.intellij.vcsUtil.VcsUtil;
import git4idea.history.GitHistoryUtils;


class SourcegraphLinkParameters {
    private SourcegraphLinkConfig config;
    private Project project;
    private String filePath;
    private int lineNumber;

    private String _projectName;
    private String _gitRevision;
    private String _filePathInProject;

    SourcegraphLinkParameters(Project project, String filePath, int lineNumber) {
        this.config = ServiceManager.getService(SourcegraphLinkConfig.class);
        this.project = project;
        this.filePath = filePath;
        this.lineNumber = lineNumber;
    }

    int getLineNumber() {
        return this.lineNumber;
    }

    String getProjectName() {
        if (this._projectName == null) {
            this._projectName = this._getProjectName();
        }

        return this._projectName;
    }

    private String _getProjectName() {
        String projectPath = this.project.getBasePath();
        if (projectPath == null) {
            throw new Error("Couldn't get project base path");
        }

        int indexOfPlayground = projectPath.indexOf(this.config.getDevelopmentFolder());
        if (indexOfPlayground == -1) {
            throw new Error("Project path wrong");
        }

        return projectPath.substring(indexOfPlayground + this.config.getDevelopmentFolder().length() + 1);
    }

    String getGitRevision() {
        if (this._gitRevision == null) {
            this._gitRevision = this._getGitRevision();
        }

        return this._gitRevision;
    }

    private String _getGitRevision() {
        FilePath gitFilePath = VcsUtil.getFilePath(this.filePath);

        try {
            ItemLatestState rev = GitHistoryUtils.getLastRevision(this.project, gitFilePath);
            if (rev != null) {
                return rev.getNumber().asString();
            }
        } catch (VcsException e) {
            return "";
        }

        return "";
    }

    String getFilePathInProject() {
        if (this._filePathInProject == null) {
            this._filePathInProject = this._getFilePathInProject();
        }

        return this._filePathInProject;
    }

    private String _getFilePathInProject() {
        int indexOfProjectName = filePath.indexOf(this.getProjectName());
        if (indexOfProjectName == -1) {
            throw new Error("Something went wrong");
        }
        return filePath.substring(indexOfProjectName + this.getProjectName().length());
    }
}