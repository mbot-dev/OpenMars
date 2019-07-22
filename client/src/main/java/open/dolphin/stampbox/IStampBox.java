package open.dolphin.stampbox;

import java.util.List;
import open.dolphin.infomodel.IStampTreeModel;
import open.dolphin.infomodel.ModuleInfoBean;

/**
 * IStampBox
 *
 * @author Kazushi Minagawa, Digital Globe, Inc.
 */
public interface IStampBox {
    
    StampBoxPlugin getContext();
    
    void setContext(StampBoxPlugin plugin);
    
    IStampTreeModel getStampTreeModel();
    
    void setStampTreeModel(IStampTreeModel stampTreeModel);
    
    List<StampTree> getAllTrees();
    
    StampTree getStampTree(String entity);
    
    StampTree getStampTree(int index);
    
    boolean isHasEditor(int index);
    
    void setHasNoEditorEnabled(boolean enabled);
    
    List<TreeInfo> getAllTreeInfos();
    
    List<ModuleInfoBean> getAllStamps(String entity);
    
    List<String> getEntities();
    
    String getInfo();
    
}











