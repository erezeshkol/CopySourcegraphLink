import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


public class SourcegraphLinkConfigurable implements SearchableConfigurable {
    SourcegraphLinkConfigurableGUI gui;

    @Override
    public JComponent createComponent() {
        if (gui == null) {
            gui = new SourcegraphLinkConfigurableGUI();
            gui.createUI();
        }
        return gui.getRootPanel();
    }

    @Override
    public String getDisplayName() {
        return "Copy SourceGraph Link Plugin";
    }

    @Override
    @NotNull
    public String getHelpTopic() {
        return "preference.SourcegraphLinkConfigurable";
    }

    @Override
    public boolean isModified() {
        return gui.isModified();
    }

    @Override
    public void apply() {
        gui.apply();
    }

    @Override
    public void reset() {
        gui.reset();
    }

    @Override
    @NotNull
    public String getId() {
        return getHelpTopic();
    }

}
