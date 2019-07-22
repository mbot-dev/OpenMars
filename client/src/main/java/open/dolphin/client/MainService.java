package open.dolphin.client;

/**
 *
 * @author Kazushi Minagawa, Digital Globe, Inc.
 */
public interface MainService {
    
    String getName();
    
    void setName(String name);
    
    MainWindow getContext();
    
    void setContext(MainWindow context);
    
    void start();
    
    void stop();
    
}
