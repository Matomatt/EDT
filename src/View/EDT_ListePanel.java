/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
 
import java.util.List;
import java.sql.Date;
import Seances.Seance;
import Utilisateurs.User;
import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Controllers.Controller;
import java.awt.GridBagLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JPanel;

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
        GridBagConstraints c = new GridBagConstraints();
    
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        
        //JTabbedPane tabbedPanes = new JTabbedPane();

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
       
       //on récupère une liste avec toutes les séances qui se passe pendant une journée.
       List<Seance> liste = user.ListeSeances().getByUtilisateurAtWeek(user.getUtilisateurConnecte(), Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date()) ));//user.ListeSeances().getByUtilisateurAtDate(user.ListeUtilisateurs().getByID(1709), new Date(new java.util.Date().getTime()));
       //on récupère la taille pour savoir le nombre de cours en une journée et pouvoir ensuite passer au jour d'après.
       int taille = liste.size();
       //création d'un tableau qui affichera sous forme de liste
       //ligne : cours (taille)
       //colonne : les détails (entêtes) 
       Object[][] table = new Object[7][taille];
        
       int i =0;
       
       
        
       //for (Seance s : user.ListeSeances().getByUtilisateurAtDate(user.ListeUtilisateurs().getByID(1709), new Date(new java.util.Date().getTime()))) 
       for(Seance s : user.ListeSeances().getByUtilisateurAtWeek(user.getUtilisateurConnecte(), Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date()) )))
       {
           
            System.out.println("tata 1");
            System.out.println(s);
//            System.out.println("tata 2");
//            //table[0][i]=s.getDebut();
//            System.out.println("tata 3");
//            //table[1][i]=s.getFin();
//            System.out.println("tata 4");
//            table[2][i]=s.getCours();
//            System.out.println("tata 5");
//            table[3][i]=s.getEnseignants();
//            System.out.println("tata 6");
//            table[4][i]=s.getGroupes();
//            System.out.println("tata 7");
//            table[5][i]=s.getSalles();
//            System.out.println("tata 8");
//            table[6][i]=s.getType();
//            System.out.println("tata 9");
            i++;
        }
       
       String[] entetes={"Heure Début", "Heure Fin", "Cours", "Enseignant", "Groupe", "Salle", "Type de Cours"};
       JTable tableau= new JTable(table, entetes);
       
       //JScrollPane scroll = new JScrollPane(tableau);
        
        c.gridx = 0;
        c.gridy = 3;
        
        //scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        //scroll.setBackground(Color.white);
        //this.add(c);
       
       //panel1.add(this);
        
        //this.add(tabbedPanes, c);
        

//        JToolBar toolBar  = new JToolBar();
//        Button bAjouter = new Button(" Ajouter ");
//        Button bSupprimer = new Button(" Supprimer ");
//        Button bModifier = new Button(" Modifier ");
//        
//        toolBar.setRollover(false);
//        toolBar.setFloatable(false);
//        toolBar.setBackground(new java.awt.Color(255, 255, 255));
//        toolBar.add(bAjouter);
//        toolBar.add(bSupprimer);
//        toolBar.add(bModifier);
//
//        c.fill = GridBagConstraints.HORIZONTAL;
//		c.weightx = 1.0;
//		c.weighty = 0.0;
//		c.gridx = 0;
//		c.gridy = 0;
//        this.add(toolBar, c);

        //validate();
    }
}