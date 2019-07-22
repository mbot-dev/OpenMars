package open.dolphin.client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.text.Position;

/**
 * IComponentHolder
 *
 * @author  Kauzshi Minagawa
 */
public interface ComponentHolder extends PropertyChangeListener {
    
    /** Content is stamp */
    int TT_STAMP = 0;
    
    /** Content is image */
    int TT_IMAGE = 1;
    
    /** Content is attachemnt */
    int TT_ATTACHENT = 2;
    
    int getContentType();
    
    KartePane getKartePane();
    
    boolean isSelected();
    
    void setSelected(boolean b);
    
    void edit();
    
    @Override
    void propertyChange(PropertyChangeEvent e);
    
    void setEntry(Position start, Position end);
    
    int getStartPos();
    
    int getEndPos();

}