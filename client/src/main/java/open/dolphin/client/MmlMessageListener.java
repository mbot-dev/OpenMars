package open.dolphin.client;


/**
 * MML イベントリスナ。
 *
 * @author  Kazushi Minagawa, Digital Globe, Inc.
 */
public interface MmlMessageListener extends MainService {
    
    String getCSGWPath();
    
    void setCSGWPath(String val);
    
    void mmlMessageEvent(MmlMessageEvent e);
    
}