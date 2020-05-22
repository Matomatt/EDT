package View;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class ModifAdminPanel extends JPanel 
{
	private static final long serialVersionUID = 3736956335101565252L;

	public ModifAdminPanel() {
        initComponents();
        this.setVisible(true);
        
        validate();
    }
	
    @SuppressWarnings("serial")
	private void initComponents() {
        jTabbedPane1 = new JTabbedPane();
        jScrollPane8 = new JScrollPane();
        jList8 = new JList<>();
        jScrollPane1 = new JScrollPane();
        jList1 = new JList<>();
        jScrollPane2 = new JScrollPane();
        jList2 = new JList<>();
        jScrollPane3 = new JScrollPane();
        jList3 = new JList<>();
        jScrollPane4 = new JScrollPane();
        jList4 = new JList<>();
        jScrollPane5 = new JScrollPane();
        jList5 = new JList<>();
        jScrollPane6 = new JScrollPane();
        jList6 = new JList<>();
        jScrollPane7 = new JScrollPane();
        jList7 = new JList<>();
        jToolBar1 = new JToolBar();
        jButton4 = new JButton();
        jButton5 = new JButton();
        jButton6 = new JButton();

        setBorder(null);

        jList8.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane8.setViewportView(jList8);

        jTabbedPane1.addTab("Séances", jScrollPane8);

        jList1.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jTabbedPane1.addTab("Referants", jScrollPane1);

        jList2.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        jTabbedPane1.addTab("Enseignants", jScrollPane2);

        jList3.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList3);

        jTabbedPane1.addTab("Etudiants", jScrollPane3);

        jList4.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jList4);

        jTabbedPane1.addTab("Salles", jScrollPane4);

        jList5.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(jList5);

        jTabbedPane1.addTab("Groupes", jScrollPane5);

        jList6.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(jList6);

        jTabbedPane1.addTab("Promotions", jScrollPane6);

        jList7.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(jList7);

        jTabbedPane1.addTab("Cours", jScrollPane7);

        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

        jButton4.setText("Ajouter");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(jButton4);

        jButton5.setText("Supprimer");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(jButton5);

        jButton6.setText("Modifier");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(jButton6);

        this.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        validate();
    }
    
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
    private JList<String> jList1;
    private JList<String> jList2;
    private JList<String> jList3;
    private JList<String> jList4;
    private JList<String> jList5;
    private JList<String> jList6;
    private JList<String> jList7;
    private JList<String> jList8;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JScrollPane jScrollPane6;
    private JScrollPane jScrollPane7;
    private JScrollPane jScrollPane8;
    private JTabbedPane jTabbedPane1;
    private JToolBar jToolBar1;
}

