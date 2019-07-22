package open.dolphin.client;

import java.util.List;
import javax.swing.JFrame;
import open.dolphin.infomodel.*;

/**
 *
 * @author Kazushi Minagawa.
 */
public interface Chart extends MainTool {
    
    enum NewKarteOption {BROWSER_NEW, BROWSER_COPY_NEW, BROWSER_MODIFY, EDITOR_NEW, EDITOR_COPY_NEW, EDITOR_MODIFY}

    enum NewKarteMode {EMPTY_NEW, APPLY_RP, ALL_COPY}

    KarteBean getKarte();
    
    void setKarte(KarteBean karte);
    
    PatientModel getPatient();
    
    PatientVisitModel getPatientVisit();
    
    void setPatientVisit(PatientVisitModel model);
    
    int getChartState();
    
    void setChartState(int state);
    
    boolean isReadOnly();
    
    void setReadOnly(boolean b);
    
    void close();
    
    JFrame getFrame();
    
    IStatusPanel getStatusPanel();
    
    void setStatusPanel(IStatusPanel statusPanel);
    
    ChartMediator getChartMediator();
    
    void enabledAction(String name, boolean enabled);
    
    DocumentHistory getDocumentHistory();
    
    void showDocument(int index);
    
    boolean isDirty();
    
    PVTHealthInsuranceModel[] getHealthInsurances();

    PVTHealthInsuranceModel getHealthInsuranceToApply(String uuid);

    //---------------------------------------------------------------
    // CLAIM 再送信機能のため追加
    DocumentModel getKarteModelToEdit(NewKarteParams params);
    DocumentModel getKarteModelToEdit(DocumentModel oldModel, NewKarteParams params);
    DocumentModel getKarteModelToEdit(DocumentModel oldModel);
    ClaimMessageListener getCLAIMListener();
    boolean isSendClaim();
    //---------------------------------------------------------------
    MmlMessageListener getMMLListener();

    //public boolean isSendMml();
    //---------------------------------------------------------------
    // 検体検査オーダーのために追加
    boolean isSendLabtest();
    //---------------------------------------------------------------
    
    //---------------------------------------------------------------
    // Ppane でも病名スタンプを受け入れる。このリストにaddで追加。
    // 傷病名タブが選択された時に中味の病歴を追加。
    //---------------------------------------------------------------
    void addDroppedDiagnosis(ModuleInfoBean bean);
    List<ModuleInfoBean> getDroppedDiagnosisList();
    
}
