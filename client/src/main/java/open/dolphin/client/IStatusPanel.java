package open.dolphin.client;

import java.beans.PropertyChangeListener;
import javax.swing.JProgressBar;

/**
 *
 * @author Kazushi Minagawa.
 */
public interface IStatusPanel extends PropertyChangeListener {
    
    void setMessage(String msg);
    
    void setRightInfo(String info);
    
    void setLeftInfo(String info);
    
    void setTimeInfo(long time);
    
    JProgressBar getProgressBar();
    
}
