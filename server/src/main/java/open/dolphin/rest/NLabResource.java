package open.dolphin.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import open.dolphin.converter.NLaboItemListConverter;
import open.dolphin.converter.NLaboModuleListConverter;
import open.dolphin.converter.PatientLiteListConverter;
import open.dolphin.converter.PatientModelConverter;
import open.dolphin.infomodel.*;
import open.dolphin.session.NLabServiceBean;

/**
 * REST Web Service
 *
 * @author Kazushi Minagawa, Digital Globe, Inc.
 */
@Path("/lab")
public class NLabResource extends AbstractResource {
    
    @Inject
    private NLabServiceBean nLabServiceBean;

    public NLabResource() {
    }

    @GET
    @Path("/module/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public NLaboModuleListConverter getLaboTest(@Context HttpServletRequest servletReq, @PathParam("param") String param) {

        debug(param);
        String[] params = param.split(CAMMA);
        String pid = params[0];
        int firstResult = Integer.parseInt(params[1]);
        int maxResult = Integer.parseInt(params[2]);

        String fidPid = getFidPid(servletReq.getRemoteUser(), pid);

        List<NLaboModule> result = nLabServiceBean.getLaboTest(fidPid, firstResult, maxResult);
        NLaboModuleList list = new NLaboModuleList();
        list.setList(result);
        
        NLaboModuleListConverter conv = new NLaboModuleListConverter();
        conv.setModel(list);
        
        return conv;
    }
    
//s.oh^ 2013/09/18 ラボデータの高速化
    @GET
    @Path("/module/count/{param}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLaboTestCount(@Context HttpServletRequest servletReq, @PathParam("param") String param) {
        
        String pid = param;
        String fidPid = getFidPid(servletReq.getRemoteUser(), pid);
        
        Long cnt = nLabServiceBean.getLaboTestCount(fidPid);
        String val = String.valueOf(cnt);
        
        return val;
    }
//s.oh$

    @GET
    @Path("/item/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public NLaboItemListConverter getLaboTestItem(@Context HttpServletRequest servletReq, @PathParam("param") String param) {

        debug(param);
        String[] params = param.split(CAMMA);
        String pid = params[0];
        int firstResult = Integer.parseInt(params[1]);
        int maxResult = Integer.parseInt(params[2]);
        String itemCode = params[3];

        String fidPid = getFidPid(servletReq.getRemoteUser(), pid);

        List<NLaboItem> result = nLabServiceBean.getLaboTestItem(fidPid, firstResult, maxResult, itemCode);
        NLaboItemList list = new NLaboItemList();
        list.setList(result);
        
        NLaboItemListConverter conv = new NLaboItemListConverter();
        conv.setModel(list);
        
        return conv;
    }

    @GET
    @Path("/patient/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public PatientLiteListConverter getConstrainedPatients(@Context HttpServletRequest servletReq, @PathParam("param") String param) {

        String fid = getRemoteFacility(servletReq.getRemoteUser());

        debug(param);
        String[] params = param.split(CAMMA);
        List<String> idList = new ArrayList<String>(params.length);
        idList.addAll(Arrays.asList(params));

        List<PatientLiteModel> result = nLabServiceBean.getConstrainedPatients(fid, idList);
        PatientLiteList list = new PatientLiteList();
        list.setList(result);
        
        PatientLiteListConverter conv = new PatientLiteListConverter();
        conv.setModel(list);

        return conv;
    }

    @POST
    @Path("/module")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PatientModelConverter postNLaboTest(@Context HttpServletRequest servletReq, String json) throws IOException {

        String fid = getRemoteFacility(servletReq.getRemoteUser());
        
        ObjectMapper mapper = new ObjectMapper();
        // 2013/06/24
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        NLaboModule module = mapper.readValue(json, NLaboModule.class);
       
        List<NLaboItem> items = module.getItems();
        // 関係を構築する
        if (items!=null && items.size()>0) {
            for (NLaboItem item : items) {
                item.setLaboModule(module);
            }
        }
        
        PatientModel patient = nLabServiceBean.create(fid, module);
        
        PatientModelConverter conv = new PatientModelConverter();
        conv.setModel(patient);

        return conv;
    }

    // ラボデータの削除 2013/06/24    
    @DELETE
    @Path("/module/{param}")
    public void unsubscribeTrees(@PathParam("param") String param) {

        long moduleId = Long.parseLong(param);

        int cnt = nLabServiceBean.deleteLabTest(moduleId);
        
        String cntStr = String.valueOf(cnt);
        debug(cntStr);
    }
}
