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
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
        
        Color color = new Color(111,199,227);
        JPanel pan = new JPanel();
        
        
		for(int i=1; i<=52; i++) {
                    
			JLabel label = new JLabel(String.valueOf(i));
                        
			label.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
			label.setOpaque(true);
			if ( i%2==0 ) {
				label.setBackground(Color.WHITE);

			}
			else {
				label.setBackground(color);
			}
			label.setEnabled(true);
			label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
                                    System.out.println(".mouseClicked()"+ label.getText());
                                    String semaineselec ;
                                    semaineselec = label.getText();
				}
                                
			});
			pan.add(label);
		}
                

		JScrollPane slider = new JScrollPane(pan,JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                c.fill = GridBagConstraints.BOTH;
                
                c.gridy = 0;
                c.weighty = 0.05;
                c.gridwidth = 2;
                this.add(slider,c);
                
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 2;
        
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
        
        Object [] objListe = new Object [] {"Nom","Classe", "Salle", "Promo"};
        JComboBox<String> liste2 = new JComboBox(objListe);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        c.ipadx = 30;
        c.ipady = 20;
        c.weightx = 0.5;
        c.weighty= 0.1;
           liste2.addActionListener(new ActionListener() {     
         @Override
         public void actionPerformed(ActionEvent e) {
        System.out.println("Valeur: " + liste2.getSelectedItem().toString());      
     }
   });
            this.add(liste2,c);

                      
            /*Object [] objListe2 = new Object [] {"P325","P318", "P333", "P329"};
            JComboBox<String> cb = new JComboBox(objListe2);
            cb.setEditable(true);*/
        JComboBox cb;
        cb = new JComboBox(((user.ListeSalles()).getAll()).toArray());
        cb.setEditable(true);
        Label test =null; 
        
    
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty= 0.1;

        this.add(cb,c);

    }
}