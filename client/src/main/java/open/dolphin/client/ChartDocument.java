package open.dolphin.client;

import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 * チャートドキュメントが実装するインターフェイス。
 *
 * @author  Kazushi Minagawa, Digital Globe, Inc.
 */
public interface ChartDocument  {
    
    String CHART_DOC_DID_SAVE = "ChartDocDidSave";
	
    String getTitle();
    
    void setTitle(String title);
    
    ImageIcon getIconInfo(Chart ctx);
        
    Chart getContext();
    
    void setContext(Chart ctx);

    JPanel getUI();
    
    void start();
    
    void stop();
    
    void enter();
    
    void save();
    
    void print();
    
    boolean isDirty();
    
    void setDirty(boolean dirty);
    
//minagawa^ Chart（インスペクタ画面）の closebox 押下に対応するため
    //保存終了を通知する機構
void addPropertyChangeListener(String prop, PropertyChangeListener l);
    void removePropertyChangeListener(String prop, PropertyChangeListener l);
    boolean isChartDocDidSave();
    void setChartDocDidSave(boolean b);
//minagawa$    
    
}