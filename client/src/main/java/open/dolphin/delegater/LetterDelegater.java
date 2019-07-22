package open.dolphin.delegater;


import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import open.dolphin.converter.LetterModuleConverter;
import open.dolphin.infomodel.LetterModule;
import open.dolphin.infomodel.LetterModuleList;

/**
 * 紹介状用のデリゲータークラス。
 * @author Kazushi Minagawa.
 */
public final class LetterDelegater extends BusinessDelegater {

    private static final String PATH_FOR_LETTER = "/odletter/letter";
    private static final String PATH_FOR_LETTER_LIST = "/odletter/list";
    
    public LetterDelegater() {
    }
    
    public long saveOrUpdateLetter(LetterModule model) throws Exception {
        
        // Converter
        LetterModuleConverter conv = new LetterModuleConverter();
        conv.setModel(model);
        
        // JSON
        ObjectMapper mapper = this.getSerializeMapper();
        byte[] data = mapper.writeValueAsBytes(conv);
        
        // PUT
        String entityStr = putEasyJson(PATH_FOR_LETTER, data, String.class);
        
        // PK
        return Long.parseLong(entityStr);
    }

    public LetterModule getLetter(long letterPk) throws Exception {

        // PATH
        String path = PATH_FOR_LETTER + "/" + letterPk;
        
        // GET

        return getEasyJson(path, LetterModule.class);
    }

    public List<LetterModule> getLetterList(long kartePk) throws Exception {

        // PATH
        String path = PATH_FOR_LETTER_LIST + "/" + kartePk;
        
        // GET
        LetterModuleList list  = getEasyJson(path, LetterModuleList.class);
        
        return list.getList();
    }

    public void delete(long pk) throws Exception {
        
        // PATH
        //s.oh^ 不具合修正
        //sb.append(PATH_FOR_LETTER);
        String path = PATH_FOR_LETTER + "/" +
//s.oh$
                pk;
        
        // DELETE
        deleteEasy(path);
        
    }
}
