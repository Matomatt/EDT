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
import java.time.LocalDate;
import java.util.Calendar;
import javax.swing.JPanel;
import javax.xml.bind.DatatypeConverter;
import static javax.xml.bind.DatatypeConverter.parseDate;

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
       taille=taille+6;
       //création d'un tableau qui affichera sous forme de liste
       //ligne : cours (taille)
       //colonne : les détails (entêtes) 
       Object[][] table = new Object[taille][7];
        
       int i =0;
       
       Date sauvDate=new Date(2001-01-01);
        
       Date dateEnCours;
        
       for(Seance s : user.ListeSeances().getByUtilisateurAtWeek(user.getUtilisateurConnecte(), Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date()) )))
       {
           dateEnCours=s.getDate();
           
           if(dateEnCours.compareTo(sauvDate)!=0)
           {
               table[i][0]=dateEnCours;
               for(int j=1; j<7;++j)
               {
                   table[i][j]="";
               }
               sauvDate=dateEnCours;
               i++;
           }
           
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
       JScrollPane scroll = new JScrollPane(tableau);
       
        c.gridx = 0;
        c.gridy = 3;
        
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scroll.setBackground(Color.white);
        this.add(scroll,c);

    }
}