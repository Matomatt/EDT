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
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Célia BOCHER
 */

public class EdtGrillePanel extends JPanel{
 
    User user = null;
    
    	public EdtGrillePanel(User _user) {
		user = _user;
		this.setLayout(new GridBagLayout());

        initComponents();
        
        this.setVisible(true);
        
        validate();
    }
        
        private void initComponents()
        {
            GridBagConstraints c = new GridBagConstraints();
        
                c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 1;
                
            EdtGrilleTable tableau = new EdtGrilleTable(user);
            
            //add(tableau,c);
            
            JLabel a = new JLabel("coucou");
            c.gridx = 0;
            c.gridy = 0;
            this.add(a, c);
    
        }

    private void add(EdtGrilleTable tableau, GridBagConstraints c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
