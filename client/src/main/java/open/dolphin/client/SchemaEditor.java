package open.dolphin.client;

import java.beans.PropertyChangeListener;
import open.dolphin.infomodel.SchemaModel;

/**
 *
 * @author Kazushi Minagawa.
 */
public interface SchemaEditor {
    
    void setEditable(boolean b);
    
    void setSchema(SchemaModel model);
    
    void start();
    
    void addPropertyChangeListener(PropertyChangeListener l);
    
    void removePropertyChangeListener(PropertyChangeListener l);

}
