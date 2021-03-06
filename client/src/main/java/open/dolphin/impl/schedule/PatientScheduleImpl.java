package open.dolphin.impl.schedule;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.beans.EventHandler;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import open.dolphin.client.*;
import open.dolphin.delegater.ScheduleDelegater;
import open.dolphin.helper.SimpleWorker;
import open.dolphin.infomodel.PatientModel;
import open.dolphin.infomodel.PatientVisitModel;
import open.dolphin.infomodel.PostSchedule;
import open.dolphin.infomodel.SimpleDate;
import open.dolphin.project.Project;
import open.dolphin.table.ColumnSpec;
import open.dolphin.table.ColumnSpecHelper;
import open.dolphin.table.ListTableModel;
import open.dolphin.table.ListTableSorter;
import open.dolphin.table.StripeTableCellRenderer;
import open.dolphin.util.AgeCalculator;

/**
 * 患者検索PatientSearchPlugin
 * (予定カルテ対応)
 *
 * @author Kazushi Minagawa
 */
public class PatientScheduleImpl extends AbstractMainComponent { 
    
    private final String[] COLUMN_NAMES;
    private final String[] PROPERTY_NAMES;
    private final Class[] COLUMN_CLASSES;
    private final int[] COLUMN_WIDTH;
   
    // カラム仕様名
    private static final String COLUMN_SPEC_NAME = "patientScheduleTable.withoutAddress.column.spec";
     
    // 状態カラムの識別名
    private static final String COLUMN_IDENTIFIER_STATE = "stateColumn";
    
    private static final String KEY_AGE_DISPLAY = "patientScheduleTable.withoutAddress.ageDisplay";
    
    // カラム仕様ヘルパー
    private ColumnSpecHelper columnHelper;
    
    // 予定日
    private GregorianCalendar scheduleDate;
    
    private boolean assignedOnly;

    // 選択されている患者情報
    private PatientVisitModel selectedVisit;

    // 年齢表示
    private boolean ageDisplay;
    
    // 年齢生年月日メソッド
    private final String[] AGE_METHOD = {"getPatientAgeBirthday", "getPatientBirthday"};

    // View
    private PatientScheduleView view;

    private int ageColumn;
    private int stateColumn;

    private ListTableModel tableModel;
    private ListTableSorter sorter;

    // Copy Action
    private AbstractAction copyAction;
    
    // 未来処方適用ボタン
    private AbstractAction applyRpAction;
    
    // 更新 Action
    private AbstractAction updateAction;

    // 処方適用カルテ作成と同時にCLAIM送信するかどうか
    // デフォルトは false
    private boolean sendClaim;
    

    /** Creates new PatientSearch */
    public PatientScheduleImpl() {
        // Resource Injection
        java.util.ResourceBundle bundle = ClientContext.getMyBundle(PatientScheduleImpl.class);
        setName(bundle.getString("title.scheduleKarte"));
        COLUMN_NAMES = bundle.getString("columnNames.table").split(",");
        PROPERTY_NAMES = bundle.getString("methods.table").split(",");
        COLUMN_CLASSES = new Class[]{String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
        COLUMN_WIDTH = new int[]{80, 100, 40, 130, 130, 50, 60, 40};
        assignedOnly = Project.getBoolean("PATIENT_SCHEDULE_ASSIGNED_ONLY", false);
    }

    @Override
    public void start() {
        setup();
        initComponents();
        connect();
        enter();
    }

    @Override
    public void enter() {
        controlMenu();
        SwingUtilities.invokeLater(() -> {
            view.getKeywordFld().requestFocusInWindow();
            view.getKeywordFld().selectAll();
        });
    }

    @Override
    public void stop() {  
        // ColumnSpecsを保存する
        if (columnHelper != null) {
            columnHelper.saveProperty();
        }
    }

    public PatientVisitModel getSelectedVisit() {
        return selectedVisit;
    }

    public void setSelectedVisit(PatientVisitModel model) {
        selectedVisit = model;
        controlMenu();
    }
    
    public GregorianCalendar getScheduleDate() {
        return scheduleDate;
    }
    
    public void setScheduleDate(GregorianCalendar sd) {
        scheduleDate = sd;
        if (scheduleDate==null) {
            updateAction.setEnabled(false);
            view.getKeywordFld().setText("");
            return;
        }
        String dateFmt = ClientContext.getMyBundle(PatientScheduleImpl.class).getString("DATE_FORMAT_FOR_SCHEDULE");
        SimpleDateFormat frmt = new SimpleDateFormat(dateFmt);
        view.getKeywordFld().setText(frmt.format(scheduleDate.getTime()));
        String test = stringFromCalendar(scheduleDate);
        find(test);
    }
    
    public boolean isSendClaim() {
        return sendClaim;
    }
    
    public void setSendClaim(boolean b) {
        sendClaim = b;
    }
    
    public boolean isAssignedOnly() {
        return assignedOnly;
    }
    
    public void setAssignedOnly(boolean b) {
        assignedOnly = b;
        Project.setBoolean("PATIENT_SCHEDULE_ASSIGNED_ONLY", assignedOnly);
        setScheduleDate(getScheduleDate());
    }

    public ListTableModel<PatientVisitModel> getTableModel() {
        return (ListTableModel<PatientVisitModel>)sorter.getTableModel();
    }

    /**
     * 年齢表示をオンオフする。
     */
    public void switchAgeDisplay() {           
        if (view.getTable() == null) {
            return;
        }

        ageDisplay = !ageDisplay;
        Project.setBoolean(KEY_AGE_DISPLAY, ageDisplay);
        String method = ageDisplay ? AGE_METHOD[0] : AGE_METHOD[1];
        ListTableModel tModel = getTableModel();
        tModel.setProperty(method, ageColumn);

        List<ColumnSpec> columnSpecs = columnHelper.getColumnSpecs();
        for (ColumnSpec cs : columnSpecs) {
            String test = cs.getMethod();
            if (test.toLowerCase().endsWith("birthday")) {
                cs.setMethod(method);
                break;
            }
        }
        columnHelper.saveProperty();
    }

    /**
     * メニューを制御する
     */
    private void controlMenu() {
        PatientVisitModel pvt = getSelectedVisit();
        boolean enabled = pvt!=null && canOpen(pvt.getPatientModel());
        getContext().enabledAction(GUIConst.ACTION_OPEN_KARTE, enabled);
    }

    /**
     * カルテを開くことが可能かどうかを返す。
     * @return 開くことが可能な時 true
     */
    private boolean canOpen(PatientModel patient) {
        if (patient == null) {
            return false;
        }

        return !isKarteOpened(patient);

    }

    /**
     * カルテがオープンされているかどうかを返す。
     * @return オープンされている時 true
     */
    private boolean isKarteOpened(PatientModel patient) {
        if (patient != null) {
            boolean opened = false;
            List<ChartImpl> allCharts = ChartImpl.getAllChart();
            for (ChartImpl chart : allCharts) {
                if (chart.getPatient().getId() == patient.getId()) {
                    opened = true;
                    break;
                }
            }
            return opened;
        }
        return false;
    }

    /**
     * 受付リストのコンテキストメニュークラス。
     */
    class ContextListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            mabeShowPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mabeShowPopup(e);
        }

        public void mabeShowPopup(MouseEvent e) {

            if (e.isPopupTrigger()) {

                final JPopupMenu contextMenu = new JPopupMenu();

                int row = view.getTable().rowAtPoint(e.getPoint());
                ListTableModel<PatientVisitModel> tModel = getTableModel();
                PatientVisitModel obj = tModel.getObject(row);
                int selected = view.getTable().getSelectedRow();
                
                java.util.ResourceBundle bundle = ClientContext.getMyBundle(PatientScheduleImpl.class);

                if (row == selected && obj != null) {
                    String actionText = bundle.getString("actionText.openKarte");
                    contextMenu.add(new JMenuItem(new ReflectAction(actionText, PatientScheduleImpl.this, "openKarte")));
                    contextMenu.addSeparator();
                    contextMenu.add(new JMenuItem(copyAction));
                    actionText = bundle.getString("actionText.deleteSchedule");
                    contextMenu.add(new JMenuItem(new ReflectAction(actionText, PatientScheduleImpl.this, "remove")));
                    contextMenu.addSeparator();
                }
                
                if (Project.getUserModel().getOrcaId()!=null) {
                    String fmt = bundle.getString("messageFormat.showAssignedOnly");
                    String chkText = new MessageFormat(fmt).format(new String[]{Project.getUserModel().getCommonName()});
                    JCheckBoxMenuItem item = new JCheckBoxMenuItem(chkText);
                    item.setSelected(isAssignedOnly());
                    item.addActionListener((ActionEvent e1) -> setAssignedOnly(!isAssignedOnly()));
                    contextMenu.add(item);
                    contextMenu.addSeparator();
                }

                JCheckBoxMenuItem item = new JCheckBoxMenuItem(bundle.getString("actionText.showAge"));
                contextMenu.add(item);
                item.setSelected(ageDisplay);
                item.addActionListener(EventHandler.create(ActionListener.class, PatientScheduleImpl.this, "switchAgeDisplay"));

                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
    
    private void setup() {
        columnHelper = new ColumnSpecHelper(COLUMN_SPEC_NAME,
                COLUMN_NAMES, PROPERTY_NAMES, COLUMN_CLASSES, COLUMN_WIDTH);
        
        columnHelper.loadProperty();
        // Scan して ageカラムを設定する
        ageColumn = columnHelper.getColumnPositionEndsWith("irthday"); //irthday
        ageDisplay = Project.getBoolean(KEY_AGE_DISPLAY, true);
        stateColumn = columnHelper.getColumnPositionEndsWith("LastDocDate");
    }

    /**
     * GUI コンポーネントを初期化する。
     */
    private void initComponents() {

        // View
        view = new PatientScheduleView();
        setUI(view);
        
        // ColumnSpecHelperにテーブルを設定する
        columnHelper.setTable(view.getTable());

        //------------------------------------------
        // View のテーブルモデルを置き換える
        //------------------------------------------
        String[] columnNames = columnHelper.getTableModelColumnNames();
        String[] methods = columnHelper.getTableModelColumnMethods();
        Class[] cls = columnHelper.getTableModelColumnClasses();

        // TableModel
        tableModel = new ListTableModel<PatientVisitModel>(columnNames, 0, methods, cls) {
            
            @Override
            public Object getValueAt(int row, int col) {

                Object ret=null;

                if (col==stateColumn) {
                    PatientVisitModel pvt = getObject(row);
                    if (pvt!=null) {
                        Date last = pvt.getLastDocDate();
                        ret = (last!=null && last.equals(getScheduleDate().getTime()));
                    } else {
                        ret = false;
                    }
                } else if (col==ageColumn) {

                    PatientVisitModel p = getObject(row);

                    if (p != null && ageDisplay) {
                        int showMonth = Project.getInt("ageToNeedMonth", 6);
                        ret = AgeCalculator.getAgeAndBirthday(p.getPatientModel().getBirthday(), showMonth);
                    } else if (p != null){
                        ret = p.getPatientBirthday();
                    }
                    
                } else {
                    ret = super.getValueAt(row, col);
                }

                return ret;
            }
        };
        view.getTable().setModel(tableModel);
        view.getTable().getTableHeader().setReorderingAllowed(false);
        
        // Sorter
        sorter = new ListTableSorter(tableModel);
        view.getTable().setModel(sorter);
        sorter.setTableHeader(view.getTable().getTableHeader());
        
        // カラム幅更新
        columnHelper.updateColumnWidth();      
        view.getTable().getColumnModel().getColumn(stateColumn).setIdentifier(COLUMN_IDENTIFIER_STATE);       
        
        // レンダラー
        PatientListTableRenderer render = new PatientListTableRenderer();
        render.setTable(view.getTable());
        render.setDefaultRenderer();
        
        // 行高
        if (ClientContext.isWin()) {
            view.getTable().setRowHeight(ClientContext.getMoreHigherRowHeight());
        } else {
            view.getTable().setRowHeight(ClientContext.getHigherRowHeight());
        }
        
        view.getKeywordFld().setEditable(false);
        
        String method = ageDisplay ? AGE_METHOD[0] : AGE_METHOD[1];
        ListTableModel tModel = getTableModel();
        tModel.setProperty(method, ageColumn);
        List<ColumnSpec> columnSpecs = columnHelper.getColumnSpecs();
        for (ColumnSpec cs : columnSpecs) {
            String test = cs.getMethod();
            if (test.toLowerCase().endsWith("birthday")) {
                cs.setMethod(method);
                break;
            }
        }
        
//s.oh^ 2014/04/16 メニュー制御
        view.getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//s.oh$
    }

    /**
     * コンポーンントにリスナを登録し接続する。
     */
    private void connect() {

        // ColumnHelperでカラム変更関連イベントを設定する
        columnHelper.connect();
        
        EventAdapter adp = new EventAdapter(view.getKeywordFld(), view.getTable());
 
        // カレンダによる日付検索を設定する
        // 今月から３ヶ月先まで
        int[] range = {0, 3};
        // 今日以降でないと駄目
        SimpleDate[] acceptRange = new SimpleDate[2];
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DAY_OF_MONTH, 1);
        acceptRange[0] = new SimpleDate(gc);
        acceptRange[1] = null;
        PopupListener pl = new PopupListener(view.getKeywordFld(), range, acceptRange);

        // コンテキストメニューを設定する
        view.getTable().addMouseListener(new ContextListener());

        // Copy 機能を実装する
        KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        java.util.ResourceBundle bundle = ClientContext.getMyBundle(PatientScheduleImpl.class);
        String actionText = bundle.getString("actionText.copy");
        copyAction = new AbstractAction(actionText) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                copyRow();
            }
        };
        view.getTable().getInputMap().put(copy, "Copy");
        view.getTable().getActionMap().put("Copy", copyAction);
        
        // 未来処方適用ボタン
        actionText = bundle.getString("actionText.applyRp");
        applyRpAction = new AbstractAction(actionText) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                applyRp();
            }
        };
        view.getRpButton().setAction(applyRpAction);
        view.getRpButton().setToolTipText(bundle.getString("toolTipText.applyRp"));
        applyRpAction.setEnabled(false);
        
        actionText = bundle.getString("actionText.update");
        updateAction = new AbstractAction(actionText) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setScheduleDate(getScheduleDate());
            }
        };
        view.getUpdateButton().setAction(updateAction);
        view.getUpdateButton().setToolTipText(bundle.getString("toolTipText.updateScheduleList"));
        updateAction.setEnabled(false);
        
        actionText = bundle.getString("actionText.sendClaim");
        // CLAIM 送信 Action
        AbstractAction claimAction = new AbstractAction(actionText) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setSendClaim(view.getClaimChk().isSelected());
            }
        };
        view.getClaimChk().setAction(claimAction);
        view.getClaimChk().setToolTipText(bundle.getString("toolTipText.sendClaim"));
//s.oh^ 2014/04/02 閲覧権限の制御
        //claimAction.setEnabled(Project.claimSenderIsServer());
        claimAction.setEnabled((!Project.isReadOnly()) && Project.claimSenderIsServer());
//s.oh$
        view.getClaimChk().setVisible(Project.claimSenderIsServer());
    }

    class EventAdapter implements ListSelectionListener, MouseListener {

        public EventAdapter(JTextField tf, JTable tbl) {
            tbl.getSelectionModel().addListSelectionListener(EventAdapter.this);
            tbl.addMouseListener(EventAdapter.this);
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                JTable table = view.getTable();
                int row = table.getSelectedRow();
                PatientVisitModel patient = (PatientVisitModel)sorter.getObject(row);
                setSelectedVisit(patient);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount()==2) {
                JTable table = (JTable) e.getSource();
                ListTableModel<PatientVisitModel> tableModel = getTableModel();
                PatientVisitModel value = tableModel.getObject(table.getSelectedRow());
                if (value != null) {
                    openKarte();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent arg0) {
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
        }

        @Override
        public void mouseExited(MouseEvent arg0) {
        }
    }

    /**
     * 選択されている行をコピーする。
     */
    public void copyRow() {
        
        StringBuilder sb = new StringBuilder();
        int numRows = view.getTable().getSelectedRowCount();
        int[] rowsSelected = view.getTable().getSelectedRows();
        int numColumns =   view.getTable().getColumnCount();

        for (int i = 0; i < numRows; i++) {
            if (tableModel.getObject(rowsSelected[i]) != null) {
                StringBuilder s = new StringBuilder();
                for (int col = 0; col < numColumns; col++) {
                    Object o = view.getTable().getValueAt(rowsSelected[i], col);
                    if (o!=null) {
                        s.append(o.toString());
                    }
                    s.append(",");
                }
                if (s.length()>0) {
                    s.setLength(s.length()-1);
                }
                sb.append(s.toString()).append("\n");
            }
        }
        if (sb.length() > 0) {
            StringSelection stsel = new StringSelection(sb.toString());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel, stsel);
        }
    }
    
    /**
     * 予定患者の全てに未来処方を適用する。
     */
    public void applyRp() {
        
        SimpleWorker worker = new SimpleWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                List<PatientVisitModel> list = tableModel.getDataProvider();
                for (PatientVisitModel pvt : list) {
                    if (pvt.getLastDocDate()!=null) {
                        continue;
                    }
                    PostSchedule ps = new PostSchedule();
                    ps.setPvtPK(pvt.getId());
                    ps.setPtPK(pvt.getPatientModel().getId());
                    ps.setPhPK(Project.getUserModel().getId());
                    // 00:00:00 この時刻でstartedが作成される
                    // = で検索が可能
                    ps.setScheduleDate(getScheduleDate().getTime());
                    ps.setSendClaim(isSendClaim());
                    ScheduleDelegater ddl = ScheduleDelegater.getInstance();
                    int cnt = ddl.postSchedule(ps);
                    if (cnt==1) {
                        // ScheduleDateでカルテが作成されている
                        pvt.setLastDocDate(getScheduleDate().getTime());
                        SwingUtilities.invokeLater(() -> tableModel.fireTableDataChanged());
                    }
                }
                return null;
            }

            @Override
            protected void succeeded(Void result) {
                //setScheduleDate(getScheduleDate());
            }
        
            @Override
            protected void failed(Throwable cause) {
            }

            @Override
            protected void startProgress() {
                doStartProgress();
            }

            @Override
            protected void stopProgress() {
                doStopProgress();
            }
        };
        
        worker.execute();
    }

    /**
     * カルテを開くalue 対象患者
     */
    public void openKarte() {

        if (canOpen(getSelectedVisit().getPatientModel())) {

            // 来院情報を生成する
            PatientVisitModel pvt = getSelectedVisit();
            pvt.setFromSchedule(true);

            // カルテコンテナを生成する
            getContext().openKarte(pvt);
        }
    }

    // EVT から
    private void doStartProgress() {
        updateAction.setEnabled(false);
        view.getCountLbl().setText("0 件");
        getContext().getProgressBar().setIndeterminate(true);
        getContext().getGlassPane().block();
        applyRpAction.setEnabled(false);
    }

    // EVT から
    private void doStopProgress() {
        getContext().getProgressBar().setIndeterminate(false);
        getContext().getProgressBar().setValue(0);
        getContext().getGlassPane().unblock();
        updateAction.setEnabled(true);
        updateStatusLabel();
        List<PatientVisitModel> list = tableModel.getDataProvider();
        if (list==null || list.isEmpty()) {
            applyRpAction.setEnabled(false);
            return;
        }
        for (PatientVisitModel pvt : list) {
            if (pvt.getLastDocDate()==null) {
//s.oh^ 2014/04/02 閲覧権限の制御
                //applyRpAction.setEnabled(true);
                applyRpAction.setEnabled(!Project.isReadOnly());
//s.oh$
                break;
            }
        }   
    }

    /**
     * 検索を実行する。
     * @param text キーワード
     */
    private void find(final String text) {

        SimpleWorker worker = new SimpleWorker<Collection, Void>() {

            @Override
            protected Collection doInBackground() throws Exception {
                ScheduleDelegater sdl = ScheduleDelegater.getInstance();
                Collection result;
                if (isAssignedOnly()) {
                    result = sdl.getAssingedPvtList(text, Project.getUserModel().getOrcaId(), "18080");
                } else {
                    result = sdl.getPvtList(text);
                }
                return result;
            }

            @Override
            protected void succeeded(Collection result) {
                
                List<PatientVisitModel> list = (List<PatientVisitModel>)result;
                
                if (list != null && list.size() > 0) {
                    boolean sorted = true;
                    for (int i=0; i < COLUMN_NAMES.length; i++) {
                        if (sorter.getSortingStatus(i)==0) {
                            sorted = false;
                            break;
                        }
                    }
                    if (!sorted) {
                        Comparator c = (Comparator<PatientVisitModel>) (o1, o2) -> o1.getPatientModel().getPatientId().compareTo(o2.getPatientModel().getPatientId());
                        list.sort(c);
                    }
                    tableModel.setDataProvider(list);
                    
                } else {
                    tableModel.clear();
                }
            }

            @Override
            protected void failed(Throwable cause) {
            }

            @Override
            protected void startProgress() {
                doStartProgress();
            }

            @Override
            protected void stopProgress() {
                doStopProgress();
            }
        };

        worker.execute();
    }
    
    // ステータスラベルに検索件数を表示
    private void updateStatusLabel() {
        int count = tableModel.getObjectCount();
        String fmt = ClientContext.getMyBundle(PatientScheduleImpl.class).getString("messageFormat.numRecords");
        String text = new MessageFormat(fmt).format(new Object[]{String.valueOf(count)});
        view.getCountLbl().setText(text);
    }
    
    private String stringFromCalendar(GregorianCalendar gc) {
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
        return frmt.format(gc.getTime());
    }
    
    private class PopupListener extends PopupCalendarListener {
        
        private PopupListener(JTextField tf, int[] range, SimpleDate[] disabled) {
            super(tf, range, disabled);
        }     

        @Override
        public void setValue(SimpleDate sd) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.clear();
            gc.set(GregorianCalendar.YEAR, sd.getYear());
            gc.set(GregorianCalendar.MONTH, sd.getMonth());
            gc.set(GregorianCalendar.DATE, sd.getDay());
            setScheduleDate(gc);
        }
    }
    
    public void remove() {
        
        PatientVisitModel pvtModel = getSelectedVisit();
        final long pvtPK = pvtModel.getId();
        final long ptPK = pvtModel.getPatientModel().getId();
        final String startDate = stringFromCalendar(getScheduleDate());

        // ダイアログを表示し確認する
        String fmt = ClientContext.getMyBundle(PatientScheduleImpl.class).getString("messageFormat.deletePatientSchedule");
        String question = new MessageFormat(fmt).format(new Object[]{pvtModel.getPatientName()});
        
        if (!showCancelDialog(question)) {
            return;
        }
        
        SimpleWorker worker = new SimpleWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                ScheduleDelegater sdl = ScheduleDelegater.getInstance();
                return sdl.removePvt(pvtPK, ptPK, startDate);
            }

            @Override
            protected void succeeded(Integer result) {
                setScheduleDate(getScheduleDate());
            }

            @Override
            protected void failed(Throwable cause) {
            }

            @Override
            protected void startProgress() {
                doStartProgress();
            }

            @Override
            protected void stopProgress() {
                doStopProgress();
            }
        };

        worker.execute(); 
    }
    
    private boolean showCancelDialog(String msg) {

        String optinDelete = ClientContext.getMyBundle(PatientScheduleImpl.class).getString("optionText.delete");
        
        final String[] cstOptions = new String[]{optinDelete, GUIFactory.getCancelButtonText()};

        int select = JOptionPane.showOptionDialog(
                SwingUtilities.getWindowAncestor(view.getTable()),
                msg,
                ClientContext.getFrameTitle(getName()),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                ClientContext.getImageIconArias("icon_caution"),           
                cstOptions, cstOptions[1]);
        
        return (select == 0);
    } 
        
    private class PatientListTableRenderer extends StripeTableCellRenderer {

        public PatientListTableRenderer() {
            super();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value,
                boolean isSelected,
                boolean isFocused,
                int row, int col) {

            super.getTableCellRendererComponent(table, value, isSelected, isFocused, row, col);
            
            PatientVisitModel pm = (PatientVisitModel)sorter.getObject(row);           
            boolean bStateColumn = (view.getTable().getColumnModel().getColumn(col).getIdentifier()!=null &&
                                    view.getTable().getColumnModel().getColumn(col).getIdentifier().equals(COLUMN_IDENTIFIER_STATE));
               
            if (pm != null && bStateColumn) {            
                setHorizontalAlignment(JLabel.CENTER);
                if (value != null && ((Boolean)value)) {
                    setIcon(ClientContext.getImageIconArias("icon_star_small"));          
                }
                else {
                    setIcon(null);
                }
                setText("");
            } else {
                setHorizontalAlignment(JLabel.LEFT);
                setIcon(null);
                setText(value == null ? "" : value.toString());
            }

            return this;
        }
    }
}