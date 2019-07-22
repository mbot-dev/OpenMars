package open.dolphin.delegater;

/**
 *
 * @author kazushi minagawa
 */
public class PHRDelegater extends BusinessDelegater {
    
//    public String getLastMedicine(String pid) {
//        
//        // PATH
//        StringBuilder sb = new StringBuilder();
//        sb.append("/20/adm/phr/medication/").append(pid);
//        String path = sb.toString();
//        
//        String text = getEasyText(path, String.class);
//        
//        return text;
//    }
//    
//    public String getLastLabtest(String pid) {
//        
//        // PATH
//        StringBuilder sb = new StringBuilder();
//        sb.append("/20/adm/phr/labtest/").append(pid);
//        String path = sb.toString();
//        
//        String text = getEasyText(path, String.class);
//        
//        return text;
//    }
    
    public String getPHRdata(String pid, String dataType) {
        
        // PATH
        StringBuilder sb = new StringBuilder();
        sb.append("/20/adm/phr");

        switch (dataType) {
            case "処方":
                sb.append("/medication/").append(pid);

                break;
            case "検査":
                sb.append("/labtest/").append(pid);

                break;
            case "病名":
                sb.append("/disease/").append(pid);

                break;
            case "アレルギー":
                sb.append("/allergy/").append(pid);
                break;
        }
        
        String path = sb.toString();

        return getEasyText(path);
    }
    
}
