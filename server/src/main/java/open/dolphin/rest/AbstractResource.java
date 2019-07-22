 package open.dolphin.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import open.dolphin.infomodel.IInfoModel;

/**
 *
 * @author Kazushi Minagawa, Digital Globe, Inc.
 */
public class AbstractResource {

    static final String CAMMA = ",";
    protected static final boolean DEBUG = false;

    Date parseDate(String source) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    protected void debug(String msg) {
        Logger.getLogger("open.dolphin").fine(msg);
    }

    protected static String getRemoteFacility(String remoteUser) {
        int index = remoteUser.indexOf(IInfoModel.COMPOSITE_KEY_MAKER);
        return remoteUser.substring(0, index);
    }

    static String getFidPid(String remoteUser, String pid) {
        StringBuilder sb = new StringBuilder();
        sb.append(getRemoteFacility(remoteUser));
        sb.append(IInfoModel.COMPOSITE_KEY_MAKER);
        sb.append(pid);
        return sb.toString();
    }

    // 2013/06/24    
    protected static ObjectMapper getSerializeMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper;
    }
}
