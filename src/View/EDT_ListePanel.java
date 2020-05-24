/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import UI_Elements.Button;
import UI_Elements.JScrollListe;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import Utilisateurs.User;

/**
 *
 * @author Léonie
 */
public class EDT_ListePanel extends JPanel
{    
    private static final long serialVersionUID = 3736956335101565252L;
    User user = null;

    public EDT_ListePanel(User _user) 
    {
	user = _user;
	this.setLayout(new GridBagLayout());		
		
        initComponents();//code de la page
        
        this.setVisible(true);
        
        validate();
    }
	
    private void initComponents() 
    {
        JTabbedPane tabbedPanes = new JTabbedPane();

        tabbedPanes.addTab("Enseignants", new JScrollListe(user.ListeUtilisateurs(), User.UserType.Enseignant));

        tabbedPanes.addTab("Etudiants", new JScrollListe(user.ListeUtilisateurs(), User.UserType.Etudiant));

        tabbedPanes.addTab("Salles", new JScrollListe(user.ListeSalles()));

        tabbedPanes.addTab("Groupes", new JScrollListe(user.ListeGroupes()));

        tabbedPanes.addTab("Promotions", new JScrollListe(user.ListePromotion(), "promotion"));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 1;
        this.add(tabbedPanes, c);
        

        JToolBar toolBar  = new JToolBar();
        Button bAjouter = new Button(" Ajouter ");
        Button bSupprimer = new Button(" Supprimer ");
        Button bModifier = new Button(" Modifier ");
        
        toolBar.setRollover(false);
        toolBar.setFloatable(false);
        toolBar.setBackground(new java.awt.Color(255, 255, 255));
        toolBar.add(bAjouter);
        toolBar.add(bSupprimer);
        toolBar.add(bModifier);

        c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
        this.add(toolBar, c);

        validate();
    }
}