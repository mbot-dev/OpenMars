package open.dolphin.client;


/**
 * CLAIM イベントリスナ。
 *
 * @author  Kazushi Minagawa, Digital Globe, Inc.
 */
public interface ClaimMessageListener extends MainService {
    
    String getHost();
    
    void setHost(String host);
    
    int getPort();
    
    void setPort(int port);
    
    String getEncoding();
    
    void setEncoding(String enc);

    void claimMessageEvent(ClaimMessageEvent e);

}