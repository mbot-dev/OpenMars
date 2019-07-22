package open.dolphin.client;

import java.awt.Component;
import java.awt.print.PageFormat;
import java.util.HashMap;
import javax.swing.*;
import open.dolphin.helper.MenuSupport;
import open.dolphin.infomodel.PatientVisitModel;

/**
 * アプリケーションのメインウインドウインターフェイスクラス。
 *
 * @author Minagawa, Kazushi. Digital Globe, Inc.
 */
public interface MainWindow {
    
    HashMap<String, MainService> getProviders();
    
    void setProviders(HashMap<String, MainService> providers);
    
    JMenuBar getMenuBar();
    
    MenuSupport getMenuSupport();
    
    void registerActions(ActionMap actions);
    
    Action getAction(String name);
    
    void enabledAction(String name, boolean b);
    
    void openKarte(PatientVisitModel pvt);
    
    void addNewPatient();
    
    void block();
    
    void unblock();
    
    BlockGlass getGlassPane();
    
    MainService getPlugin(String name);
    
    PageFormat getPageFormat();
    
    void showStampBox();
    
    void showSchemaBox();

    JLabel getStatusLabel();

    JProgressBar getProgressBar();

    JLabel getDateLabel();

    JTabbedPane getTabbedPane();

    Component getCurrentComponent();
}
