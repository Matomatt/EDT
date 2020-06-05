//https://www.developpez.net/forums/d1503732/java/interfaces-graphiques-java/debuter/faire-planning-jtable/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Seances.Seance;
import Utilisateurs.User;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controllers.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EdtGrillePanel extends Panel 
{
	private static final long serialVersionUID = -4510731458552817257L;
    
    private final String[] columns = { "", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};

    private final String[][] data = {
    {"8:30 - 10:00", "", "", "", "", "", ""},
    {"10:15 - 11:45", "", "", "", "", "", ""},
    {"12:00 - 13:30", "", "", "", "", "", ""},
    {"13:45 - 15:15", "", "", "", "", "", ""},
    {"15:30 - 17:00", "", "", "", "", "", ""},
    {"17:15 - 18:45", "", "", "", "", "", ""},
    {"19:00 - 20:30", "", "", "", "", "", ""}};
    
	public EdtGrillePanel(User _user, Controller controller) {
		super(_user, controller);

        initComponents();
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
                                    //String semaineselec = label.getText();
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
        
        JTable table = new JTable(data,columns);
        
        int lines = 5;
        table.setRowHeight(table.getRowHeight() * lines);
        
        TextAreaRenderer render =  new TextAreaRenderer();
        ColorRenderer cr =new ColorRenderer();
        // We use our cell renderer for the third column
        for(int i = 0; i<7; i++)
        {
            table.getColumnModel().getColumn(i).setCellRenderer(render);
            table.getColumnModel().getColumn(0).setCellRenderer(cr);
        }
        display_courses(table);
        
        table.getTableHeader().setBackground(new java.awt.Color(255, 255, 255));
        JScrollPane scroll = new JScrollPane(table);
        
        c.gridy = 2;
        c.gridwidth = 3;
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scroll.setBackground(Color.white);
        this.add(scroll,c);
        
        
        
        String[] objListe = {"Nom","Classe", "Salle", "Promo"};
        JComboBox<String> liste = new JComboBox<String>(objListe);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        c.ipadx = 30;
        c.ipady = 20;
        c.weightx = 0.5;
        c.weighty= 0.1;
           liste.addActionListener(new ActionListener() {     
         @Override
         public void actionPerformed(ActionEvent e) {
        System.out.println("Valeur: " + liste.getSelectedItem().toString());      
     }
   });
            this.add(liste,c);

                      
            /*Object [] objListe2 = new Object [] {"P325","P318", "P333", "P329"};
            JComboBox<String> cb = new JComboBox(objListe2);
            cb.setEditable(true);*/
        JComboBox<Object> cb;
        cb = new JComboBox<Object>(((user.ListeSalles()).getAll()).toArray());
        cb.setEditable(true);
        //Label test =null; 
        
    
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty= 0.1;

        this.add(cb,c);
        
          
    }
        
        
    @SuppressWarnings("deprecation")
	public void display_courses(JTable table)
    {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
        int nb;
        int caseHeure;
        String info;
        String strTime;
        DateFormat heureformat = new SimpleDateFormat("HH.mm.ss");
        Calendar calendar = Calendar.getInstance();
        
        for(Seance s : user.ListeSeances().getByUtilisateurAtWeek(user.getUtilisateurConnecte(), Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date()) )))
        {
            s.getDate();

            String strDate = dateFormat.format(s.getDate()); 
            try {
				date = simpleDateFormat.parse(strDate);
			} catch (ParseException e) {
				date = new Date();
				e.printStackTrace();
			}
            System.out.println( date);
            System.out.println("strdate :" + strDate);
            System.out.println("day of the week is  : "+date.getDay());
            
	        calendar.setTime(s.getDate());
	        nb = calendar.get(Calendar.DAY_OF_WEEK);
	        
            strTime = heureformat.format(s.getDebut());
            System.out.println("heure " +strTime);

            switch(strTime)
            {
                case "08.30.00" : caseHeure = 0; break;
                case "10.15.00" : caseHeure = 1; break;
                case "12.00.00" : caseHeure = 2; break;
                case "13.45.00" : caseHeure = 3; break;
                case "15.30.00" : caseHeure = 4; break;
                case "17.15.00" : caseHeure = 5; break;
                case "19.00.00" : caseHeure = 6; break;
                default: caseHeure=99; break;
            }
            System.out.println("caseee :" +caseHeure);

            info = s.getCours().toString() + "\n" + s.getSalles().toString() + "\n" +s.getType().toString() ;
            table.getModel().setValueAt(info, caseHeure, nb-1);
        }
    }
}
    
   
    
 
    

