package open.dolphin.client;

import javax.swing.ActionMap;
import javax.swing.JComponent;

/**
 *
 * @author Kazushi Minagawa. Digital Globe, Inc.
 */
public interface IKarteTransferHandler {

    JComponent getComponent();

    void enter(ActionMap map);

    void exit(ActionMap map);
}
