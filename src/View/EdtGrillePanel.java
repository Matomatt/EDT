/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Seances.Seance;
import Utilisateurs.User;
import java.awt.GridBagConstraints;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controllers.Controller;

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
    
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        
        JTable table = new JTable(data,columns);
        
        int lines = 5;
        table.setRowHeight(table.getRowHeight() * lines);
        
        TextAreaRenderer render =  new TextAreaRenderer();
        // We use our cell renderer for the third column
        for(int i = 0; i<7; i++)
        table.getColumnModel().getColumn(i).setCellRenderer(render);
        
        display_courses(table);
        
        JScrollPane scroll = new JScrollPane(table);
        
        c.gridx = 0;
        c.gridy = 3;
        
        this.add(scroll,c);
          
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
            nb = date.getDay();

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
            table.getModel().setValueAt(info, caseHeure, nb+1); 
        }
    }
}
    
   
    
 
    

