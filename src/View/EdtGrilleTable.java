/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Seances.Seance;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import Utilisateurs.User;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Célia BOCHER
 */
public class EdtGrilleTable extends AbstractTableModel {
    
    
    User user = null;
    

    public int getColumnCount(){
        return 5;
    }
    
    public int getRowCount(){
        return 7;
    }
    
    
    public Object getValueAt(int indiceLigne, int indiceColonne) {
        return null;
       
    }
    
    public String getColumnName(int indiceColonne)
    {
        switch(indiceColonne)
        {
            case 0: return  "Lundi";
            case 1: return  "Mardi";
            case 2: return  "Mercredi";
            case 3: return  "Jeudi";
            case 4: return  "Vendredi";
            default:return null;
        }
    }
    
    public boolean isCellEditable(int indiceLigne, int indiceColonne){
        return true;
    
    }
    
    public void setValueAt(String val, int indiceLigne, int indiceColonne){
        
    }

    /**
     *
     * @param _user
     */
    public EdtGrilleTable(User _user) {
        user = _user;
	//this.setLayout(new GridBagLayout());

        merde();            
        
        
        //validate();
    }
    
    public void merde()
    {
    System.out.println("ok");
    }



    public void definir_cours() throws ParseException
    {
            
                  String pattern = "dd-MM-yyyy";
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
          Date date = null;
          DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
          int nb;
          int caseHeure;
          String info;
          SimpleDateFormat simpleformat = new SimpleDateFormat("hh.mm.ss");
          String strTime;
          Time heure = null;
          DateFormat heureformat = new SimpleDateFormat("HH.mm.ss");
          
//System.out.println(user.ListeSeances().getByUtilisateur(user.getUtilisateurConnecte()));
          for(Seance s : user.ListeSeances().getByUtilisateur(user.ListeUtilisateurs().getByID(1709))){
          System.out.println( "merde");
              s.getDate();
              
             
        String strDate = dateFormat.format(s.getDate()); 
        date = simpleDateFormat.parse(strDate);
        System.out.println( date);
        System.out.println("strdate :" + strDate);
        System.out.println("day of the week is  : "+date.getDay()); 
        nb = date.getDay();
        if(nb == 0)
            nb=6;
        else
            nb=nb-1;
        
        
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
              
              info = s.getCours().toString() + s.getEnseignants() + s.getSalles().toString() +s.getType().toString() ;
              setValueAt(info, caseHeure, nb);
            }
    
    
    }
}
