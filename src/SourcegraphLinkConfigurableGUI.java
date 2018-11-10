import com.intellij.openapi.components.ServiceManager;

import javax.swing.*;

public class SourcegraphLinkConfigurableGUI {
    private SourcegraphLinkConfig config;
    private JTextField serverUrlField;
    private JTextField devFolderField;
    private JPanel rootPanel;
    private JCheckBox openLinkInBrowserCheckbox;

    void createUI() {
        this.config = ServiceManager.getService(SourcegraphLinkConfig.class);
        serverUrlField.setText(this.config.getServerUrl());
        devFolderField.setText(this.config.getDevelopmentFolder());
        openLinkInBrowserCheckbox.setSelected(this.config.getOpenLinkInBrowser());
    }

    JPanel getRootPanel() {
        return rootPanel;
    }

    boolean isModified() {
        if (config == null) {
            return false;
        }

        boolean isModified = !(
            serverUrlField.getText().equals(this.config.getServerUrl()) &&
            devFolderField.getText().equals(this.config.getDevelopmentFolder()) &&
            openLinkInBrowserCheckbox.isSelected() == this.config.getOpenLinkInBrowser()
        );

        return isModified;
    }

    void apply() {
        this.config.setDevelopmentFolder(devFolderField.getText());
        this.config.setServerUrl(serverUrlField.getText());
        this.config.setOpenLinkInBrowser(openLinkInBrowserCheckbox.isSelected());
    }

    void reset() {
        devFolderField.setText(this.config.getDevelopmentFolder());
        serverUrlField.setText(this.config.getServerUrl());
        openLinkInBrowserCheckbox.setSelected(this.config.getOpenLinkInBrowser());
    }
}
