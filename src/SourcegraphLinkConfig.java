import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(
    name = "SourcegraphLinkConfig",
    storages = {
        @Storage(
            "$APP_CONFIG$/SourcegraphLinkConfig.xml"
        )
    }
)
public class SourcegraphLinkConfig implements PersistentStateComponent<SourcegraphLinkConfig> {
    static final String DEFAULT_DEVELOPMENT_FOLDER_NAME = "pg";
    static final String DEFAULT_SERVER_URL = "https://sourcegraph.yelpcorp.com/";
    static final boolean DEFAULT_OPEN_LINK_IN_BROWSER = false;

    public String developmentFolder = DEFAULT_DEVELOPMENT_FOLDER_NAME;
    public String serverUrl = DEFAULT_SERVER_URL;
    public boolean openLinkInBrowser = DEFAULT_OPEN_LINK_IN_BROWSER;

    @Override
    public SourcegraphLinkConfig getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull SourcegraphLinkConfig state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    String getDevelopmentFolder() {
        return this.developmentFolder;
    }

    void setDevelopmentFolder(String developmentFolder) {
        this.developmentFolder = developmentFolder.replace("/", "");
    }

    String getServerUrl() {
        return this.serverUrl;
    }

    void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public boolean getOpenLinkInBrowser() {
        return openLinkInBrowser;
    }

    public void setOpenLinkInBrowser(boolean openLinkInBrowser) {
        this.openLinkInBrowser = openLinkInBrowser;
    }
}
