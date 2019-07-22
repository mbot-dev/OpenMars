package open.dolphin.client;

import java.util.concurrent.Callable;

/**
 * MainWindow の Tool プラグインクラス。
 *
 * @author Kazushi Minagawa, Digital Globe, Inc.
 */
public interface MainTool extends MainService {
    
    void enter();
    
    Callable<Boolean> getStartingTask();
    
    Callable<Boolean> getStoppingTask();
    
}
