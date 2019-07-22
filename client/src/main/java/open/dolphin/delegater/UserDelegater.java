package open.dolphin.delegater;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import open.dolphin.converter.UserModelConverter;
import open.dolphin.infomodel.ActivityModel;
import open.dolphin.infomodel.IInfoModel;
import open.dolphin.infomodel.UserList;
import open.dolphin.infomodel.UserModel;
import open.dolphin.project.Project;

/**
 * User 関連の Business Delegater　クラス。
 *
 * @author Kazushi Minagawa, Digital Globe, Inc.
 */
public final class UserDelegater extends BusinessDelegater {
    
    public UserModel login(String fid, String uid, String password) throws Exception {

        // User PK
        String userPK = fid +
                IInfoModel.COMPOSITE_KEY_MAKER +
                uid;
        
        // PATH
        String path = "/user/"+userPK;
        
        // GET

        return getEasy(path, userPK, password);
    }
    
    public UserModel getUser(String userPK) throws Exception {
        
        // PATH
        String path = "/user/"+userPK;
        
        // GET

        return getEasyJson(path, UserModel.class);
    }
    
    public ArrayList getAllUser() throws Exception {
        
        // PATH
        String path = "/user";
        
        // GET
        UserList list = getEasyJson(path, UserList.class);
        
        // List
        return (ArrayList)list.getList();
    }
    
    public int addUser(UserModel userModel) throws Exception {
        
        // PATH
        String path = "/user";
        
        // Converter
        UserModelConverter conv = new UserModelConverter();
        conv.setModel(userModel);

        // JSON
        ObjectMapper mapper = this.getSerializeMapper();
        byte[] data = mapper.writeValueAsBytes(conv);
        
        // POST
        String entityStr = postEasyJson(path, data, String.class);

        return Integer.parseInt(entityStr);
    }
    
    public int updateUser(UserModel userModel) throws Exception {
        
        // PATH
        String path = "/user";
        
        // Converter
        UserModelConverter conv = new UserModelConverter();
        conv.setModel(userModel);

        // JSON
        ObjectMapper mapper = this.getSerializeMapper();
        byte[] data = mapper.writeValueAsBytes(conv);
        
        // PUT
        String entityStr = putEasyJson(path, data, String.class);

        return Integer.parseInt(entityStr);
    }
    
    public int deleteUser(String uid) throws Exception {
        
        // PATH
        String path = "/user/"+uid;
        
        // DELETE
        deleteEasy(path);
        
        // Count
        return 1;
    }
    
    public int updateFacility(UserModel user) throws Exception {
        
        // PATH
        String path = "/user/facility";
        
        // Converter
        UserModelConverter conv = new UserModelConverter();
        conv.setModel(user);

        // JSON
        ObjectMapper mapper = this.getSerializeMapper();
        byte[] data = mapper.writeValueAsBytes(conv);
        
        // PUT
        String entityStr = putEasyJson(path, data, String.class);

        return Integer.parseInt(entityStr);
    }
    
//s.oh^ 2014/07/08 クラウド0対応
    public ActivityModel[] fetchActivities() throws Exception {
        
        // 集計終了 現在まで
        GregorianCalendar gcTo = new GregorianCalendar();
        
        // 開始日　（当月の１日）
        int year = gcTo.get(Calendar.YEAR);
        int month = gcTo.get(Calendar.MONTH);
        
        // PATH
        int numMonth = Project.getInt("activities.numMonth", 3);
        String path = "/hiuchi/activity/" +
                year + CAMMA + month + CAMMA + numMonth;
        
        // GET

        return getEasyJson(path, ActivityModel[].class);
    }
    
    public int checkLicense(String uid) throws Exception {
        String path = "/hiuchi/license";
        
        // body
        byte[] data = uid.getBytes(StandardCharsets.UTF_8);

        // POST Text
        String entityStr = postEasyText(path, data);
        
        return Integer.parseInt(entityStr);
    }
//s.oh$
}
