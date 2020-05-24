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
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author Célia BOCHER
 */
public final class EdtGrilleTable extends AbstractTableModel {
    
    
    User user = null;
    private final String[] columns = { "", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};

        private final String[][] data = {
        {"8:30 - 10:00", "", "", "", "", "", ""},
        {"10:15 - 11:45", "", "", "", "", "", ""},
        {"12:00 - 13:30", "", "", "", "", "", ""},
        {"13:45 - 15:15", "", "", "", "", "", ""},
        {"15:30 - 17:00", "", "", "", "", "", ""},
        {"17:15 - 18:45", "", "", "", "", "", ""},
        {"19:00 - 20:30", "", "", "", "", "", ""}};

    @Override
    public int getColumnCount(){
        return 7;
    }
    
    @Override
    public int getRowCount(){
        return 7;
    }
    
    
     @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        
        
    @Override
    public String getColumnName(int indiceColonne)
    {
        switch(indiceColonne)
        {
            case 1: return  "Lundi";
            case 2: return  "Mardi";
            case 3: return  "Mercredi";
            case 4: return  "Jeudi";
            case 5: return  "Vendredi";
            case 6: return  "Samedi";
            default:return null;
        }
    }
    
       @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() > 0 && getValueAt(0, columnIndex) != null) {
            return getValueAt(0, columnIndex).getClass();
        }
        return super.getColumnClass(columnIndex);
    }
    
    
    
    @Override
    public boolean isCellEditable(int row, int col){ 
    return false; 
} 
    
   
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex]= (String)aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }

    /**
     *
     * @param _user
     * @throws java.text.ParseException
     */
    public EdtGrilleTable(User _user) throws ParseException {
        user = _user;
	definir_cours();  
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
              ArrayList<String> cours = new ArrayList<>();
              cours.add("arrr");
              cours.add("beee");
              
              info = s.getCours().toString() + "\n" + s.getSalles().toString() + "\n" +s.getType().toString() ;
              
               String[] strArray = new String[] {info};
                 
              
              setValueAt(info, caseHeure, nb+1); 
            }
    
    
    }
}
