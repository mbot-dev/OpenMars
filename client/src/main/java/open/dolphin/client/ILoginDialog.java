package open.dolphin.client;

import java.beans.PropertyChangeListener;

/**
 *
 * @author Kazushi Minagawa, Digital Globe, Inc.
 */
public interface ILoginDialog {

    String LOGIN_PROP = "LOGIN_PROP";
    enum LoginStatus {AUTHENTICATED, NOT_AUTHENTICATED, CANCELD}

    void addPropertyChangeListener(String prop, PropertyChangeListener listener);

    void removePropertyChangeListener(String prop, PropertyChangeListener listener);

    void start();

    void close();

    void doSetting();
}
