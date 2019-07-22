package open.dolphin.server;

import open.dolphin.client.MainService;

/**
 * @author Kazushi, Minagawa
 */
public interface PVTServer extends MainService {
    
    String getBindAddress();
    
    void setBindAddress(String bindAddress);
    
    int getPort();
    
    void setPort(int port);
    
    String getEncoding();
    
    void setEncoding(String enc);
}
