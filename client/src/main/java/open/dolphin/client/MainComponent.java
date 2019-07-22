package open.dolphin.client;

import javax.swing.JPanel;

/**
 *
 * @author Kazushi Minagawa, Digital Globe, Inc.
 */
public interface MainComponent extends MainTool {
    
    String getIcon();
    
    void setIcon(String icon);
    
    JPanel getUI();
    
    void setUI(JPanel panel);
    
}
