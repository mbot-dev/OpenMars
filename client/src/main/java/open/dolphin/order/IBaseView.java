package open.dolphin.order;

/**
 *
 * @author Kazushi Minagawa.
 */
public interface IBaseView {
    
    /**
     * @return the clearBtn
     */
    javax.swing.JButton getClearBtn();

    /**
     * @return the countField
     */
    javax.swing.JTextField getCountField();

    /**
     * @return the deleteBtn
     */
    javax.swing.JButton getDeleteBtn();

    /**
     * @return the infoLabel
     */
    javax.swing.JLabel getInfoLabel();

    /**
     * @return the okBtn
     */
    javax.swing.JButton getOkBtn();

    /**
     * @return the okCntBtn
     */
    javax.swing.JButton getOkCntBtn();

    /**
     * @return the rtBtn
     */
    javax.swing.JCheckBox getRtBtn();

    /**
     * @return the searchResultTabel
     */
    javax.swing.JTable getSearchResultTable();

    /**
     * @return the searchTextField
     */
    javax.swing.JTextField getSearchTextField();

    /**
     * @return the stampNameField
     */
    javax.swing.JTextField getStampNameField();

    /**
     * @return the techChk
     */
    javax.swing.JCheckBox getTechChk();

    /**
     * @return the setTable
     */
    javax.swing.JTable getSetTable();

    javax.swing.JCheckBox getPartialChk();

//s.oh^ 2014/03/31 スタンプ回数対応
javax.swing.JComboBox getNumberCombo();
//s.oh$
    
//s.oh^ 2014/10/22 Icon表示
javax.swing.JLabel getSearchLabel();
//s.oh$
    
    javax.swing.JComboBox<String> getShinkuCombo();
}
