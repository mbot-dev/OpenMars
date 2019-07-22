package open.dolphin.delegater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import open.dolphin.converter.PatientVisitModelConverter;
import open.dolphin.infomodel.*;
import open.dolphin.util.BeanUtils;

/**
 * User 関連の Business Delegater　クラス。
 *
 * @author Kazushi Minagawa, Digital Globe, Inc.
 */
public final class PVTDelegater1 extends BusinessDelegater {

    private static final String RES_PVT = "/pvt";
    
    /**
     * 受付情報 PatientVisitModel をデータベースに登録する。
     * @param pvtModel   受付情報 PatientVisitModel
     * @return 保存に成功した個数
     * @throws java.lang.Exception
     */
    public int addPvt(PatientVisitModel pvtModel) throws Exception {
        
        // Converter
        PatientVisitModelConverter conv = new PatientVisitModelConverter();
        conv.setModel(pvtModel);
        
        // JSON
        ObjectMapper mapper = this.getSerializeMapper();
        byte[] data = mapper.writeValueAsBytes(conv);
        
        // POST
        String entityStr = postEasyJson(RES_PVT, data, String.class);
        
        // Count
        return Integer.parseInt(entityStr);
    }
    
    /** * 
     * 来院情報を取得する。
     * @param date     検索する来院日
     * @param firstRecord 何番目のレコードから取得するか
     * @return
     * @throws java.lang.Exception PatientVisitModel のコレクション
     */
    public Collection<PatientVisitModel> getPvt(String[] date, int firstRecord) throws Exception {

        // PATH
        String path = RES_PVT +
                "/" +
                date[0] +
                CAMMA +
                firstRecord +
                CAMMA +
                date[1] +
                CAMMA +
                date[2];

        // GET
        PatientVisitList result = getEasyJson(path, PatientVisitList.class);
        
        // Decode
        List<PatientVisitModel> list = result.getList();
        if (list != null && list.size() > 0) {
            for (PatientVisitModel pm : list) {
                decodeHealthInsurance(pm.getPatientModel());
            }
        }
        return list;
    }

    /**
     * 担当分と未決定分の来院情報を取得する。
     * @param did    担当ID
     * @param unassigened   未決定ID 19999
     * @param date  来院日
     * @param firstRecord　最初のレコード
     * @return 来院情報リスト
     * @throws java.lang.Exception
     */
    public Collection<PatientVisitModel> getPvtForAssigned(String did, String unassigened, String[] date, int firstRecord) throws Exception {

        // PATH
        String path = RES_PVT +
                did + CAMMA +
                unassigened + CAMMA +
                date[0] + CAMMA +
                firstRecord + CAMMA +
                date[1] + CAMMA +
                date[2];

        // GET
        PatientVisitList result = getEasyJson(path, PatientVisitList.class);
        
        // Decode
        List<PatientVisitModel> list = result.getList();
        if (list != null && list.size() > 0) {
            for (PatientVisitModel pm : list) {
                decodeHealthInsurance(pm.getPatientModel());
            }
        }
        return list;
    }
    
    
    /**
     * バイナリの健康保険データをオブジェクトにデコードする。
     * @param patient 患者モデル
     */
    private void decodeHealthInsurance(PatientModel patient) {
        
        // Health Insurance を変換をする beanXML2PVT
        Collection<HealthInsuranceModel> c = patient.getHealthInsurances();

        if (c != null && c.size() > 0) {

            ArrayList<PVTHealthInsuranceModel> list = new ArrayList<>(c.size());

            for (HealthInsuranceModel model : c) {
                try {
                    // byte[] を XMLDecord
                    PVTHealthInsuranceModel hModel = (PVTHealthInsuranceModel)BeanUtils.xmlDecode(model.getBeanBytes());
                    list.add(hModel);
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }

            patient.setPvtHealthInsurances(list);
            patient.getHealthInsurances().clear();
            patient.setHealthInsurances(null);
        }
    }
}
