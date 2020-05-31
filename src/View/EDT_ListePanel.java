/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
 
import Seances.Seance;
import java.util.List;
import UI_Elements.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import Utilisateurs.User;
import Controllers.Controller;
import javax.swing.JTable;

/**
 *
 * @author Léonie
 */
public class EDT_ListePanel extends Panel
{    
    private static final long serialVersionUID = 3736956335101565252L;
 
    public EDT_ListePanel(User _user, Controller controller) 
    {
	super(_user, controller);		
		
        initComponents();//code de la page
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
       Object[][] table = new Object[6][taille];
        
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