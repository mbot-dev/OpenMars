/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package open.dolphin.order;

/**
 *
 * @author kazushi
 */
public interface IDiseaseView {
    
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
    javax.swing.JCheckBox getDiseaseCheck();

    /**
     * @return the setTable
     */
    javax.swing.JTable getSetTable();

    javax.swing.JCheckBox getPartialChk();
    
//s.oh^ 2014/10/22 Icon表示
javax.swing.JLabel getSearchLabel();
//s.oh$
    
}
