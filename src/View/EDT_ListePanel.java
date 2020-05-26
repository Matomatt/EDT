/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Seances.Seance;
import java.util.List;
import UI_Elements.Button;
import UI_Elements.JScrollListe;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Utilitaires.ImageManager;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import Utilisateurs.User;
import static java.util.Collections.list;
import javax.swing.JTable;

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

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());

        System.out.println("toto");
        
       /*for (int y=0; y<6;y++)
       {
           
       }*/
       
       //on récupère une liste avec toutes les séances qui se passe pendant une journée.
       List<Seance> liste = user.ListeSeances().getByUtilisateurAtDate(user.ListeUtilisateurs().getByID(1709), "2020-05-26");
       //on récupère la taille pour savoir le nombre de cours en une journée et pouvoir ensuite passer au jour d'après.
       int taille = liste.size();
       //création d'un tableau qui affichera sous forme de liste
       //ligne : cours (taille)
       //colonne : les détails (entêtes) 
       Object[][] table = new Object[taille][6];
        
       int i=0;
        
        for (Seance s : user.ListeSeances().getByUtilisateurAtDate(user.ListeUtilisateurs().getByID(1709), "2020-05-26")) 
        {
            System.out.println(s);
            table[i][0]=s.getDebut();
            table[i][1]=s.getFin();
            table[i][2]=s.getCours();
            table[i][3]=s.getEnseignants();
            table[i][4]=s.getGroupes();
            table[i][5]=s.getSalles();
            table[i][6]=s.getType();
            i++;
        }
        
        String[] entetes={"Heure Début", "Heure Fin", "Cours", "Enseignant", "Groupe", "Salle", "Type de Cours"};
        JTable tableau= new JTable(table, entetes);
        
        tabbedPanes.addTab("Enseignants",tableau);
//        tabbedPanes.addTab("Etudiants", new JScrollListe(user.ListeUtilisateurs(), User.UserType.Etudiant));
//
//        tabbedPanes.addTab("Salles", new JScrollListe(user.ListeSalles()));
//
//        tabbedPanes.addTab("Groupes", new JScrollListe(user.ListeGroupes()));
//
//        tabbedPanes.addTab("Promotions", new JScrollListe(user.ListePromotion(), "promotion"));

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