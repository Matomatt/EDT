/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Utilisateurs.User;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Célia BOCHER
 */

public class EdtGrillePanel extends JPanel{
 
    User user = null;
    
    	public EdtGrillePanel(User _user) throws ParseException {
		user = _user;
		this.setLayout(new GridBagLayout());

        initComponents();
        
        this.setVisible(true);
        
        validate();
    }
        
        private void initComponents() throws ParseException
        {
            GridBagConstraints c = new GridBagConstraints();
        
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1.0;
            c.weighty = 1.0;
            c.gridx = 0;
            c.gridy = 1;
            
            
            MultilineCellRenderer renderer = new MultilineCellRenderer(); 
                
            EdtGrilleTable tableau = new EdtGrilleTable(user);
            
            JTable table = new JTable(tableau);
            //for(int i = 0; i<7; i++){
            //table.setRowHeight(i, 100);}
            
            int lines = 5;
            table.setRowHeight(table.getRowHeight() * lines);
            
            //set TableCellRenderer into a specified JTable column class
            table.setDefaultRenderer(String[].class, renderer);
            table.setShowHorizontalLines(true); // only HorizontalLines
            table.setShowVerticalLines(true); //  only VerticalLines
            table.setShowGrid(true);          // show Horizontal and Vertical
            
            // table.setDefaultRenderer(String.class, new MultilineCellRenderer());
            JScrollPane scroll = new JScrollPane(table);
            
            centrerTable(table);
            
            c.gridx = 0;
            c.gridy = 3;
            
            this.add(scroll, c);
              
    
        }

    private void add(EdtGrilleTable tableau, GridBagConstraints c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void centrerTable(JTable table){
	   for(int i = 0;i < table.getColumnCount();i++){
		   ColorRenderer cr = new ColorRenderer();
		   cr.setHorizontalAlignment(JLabel.CENTER);
		   table.getColumnModel().getColumn(i).setCellRenderer(cr);
	   }
    }
}
