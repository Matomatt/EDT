//source de là où j'ai trouvé la barre pour les semaines
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
import Groupes.Groupe;
import Salles.Salle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EdtGrillePanel extends Panel 
{
	private static final long serialVersionUID = -4510731458552817257L;
        JScrollPane scroll = null;
        JComboBox<Object> cb = null;
        JTable table = null;
    
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
        
        table = new JTable(data,columns);
        
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
        
        

           // user.ListeSeances().getBySalleAtWeek(NOMSALLE, Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date())));
            //user.ListeSeances().getByGroupeAtWeek(groupe, Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date())));
            //user.ListeSeances().getByPromoAtWeek(promo, Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date())));
            
        
        display_courses(user.ListeSeances().getByUtilisateurAtWeek(user.getUtilisateurConnecte(), Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date()) )));
        
        
        
        
        table.getTableHeader().setBackground(new java.awt.Color(255, 255, 255));
        scroll = new JScrollPane(table);
        
        c.gridy = 2;
        c.gridwidth = 3;
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scroll.setBackground(Color.white);
        this.add(scroll,c);
        
        
        
        String [] objListe = new String [] {"Nom","Classe", "Salle", "Promo"};
        JComboBox<String> liste = new JComboBox<String>(objListe);
        
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        c.ipadx = 30;
        c.ipady = 20;
        c.weightx = 0.5;
        c.weighty= 0.1;
        
        this.add(liste,c);
 
        
        nomsAddToComboBox(c);
        
       
        
    	liste.addActionListener(new ActionListener() {     
			@Override
			public void actionPerformed(ActionEvent e) 
			{ 
				System.out.println("Valeur: " + liste.getSelectedItem().toString());
				removeAll();
				switch (liste.getSelectedItem().toString()) 
				{
					case "Salle": sallesAddToComboBox(c); 
                                            display_courses(user.ListeSeances().getBySalleAtWeek((Salle) cb.getSelectedItem(), Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date()))));
                                        break;
					
					case "Nom": nomsAddToComboBox(c); 
                                        
                                        break;
					
					case "Classe": classesAddToComboBox(c); 
                                            display_courses(user.ListeSeances().getByGroupeAtWeek((Groupe) cb.getSelectedItem(), Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date()))));
                                        break;
					
					case "Promo": promosAddToComboBox(c); 
                                            
                                        break;
							
					default: break;
				}
				repaintAll();
			}
    	});
        
       
    }
    
    public void repaintAll()
    {
      this.revalidate();
    }
    
        @Override
    public void removeAll()
    {
        this.remove(cb);
        resetTable();
    }
    
    public void resetTable(){
       for(int i = 0; i<table.getRowCount(); i++)
               for(int j = 0; j<table.getColumnCount(); j++)
                   table.setValueAt("", i, j); 
    }
    
    public void edtsalles(Salle s)
    {
        
    }
    public void tableAdd(GridBagConstraints c)
    {
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 2;
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
        table.getTableHeader().setBackground(new java.awt.Color(255, 255, 255));
        scroll = new JScrollPane(table);
        
        c.gridy = 2;
        c.gridwidth = 3;
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scroll.setBackground(Color.white);
        this.add(scroll,c);
         

    }
    
    public void sallesAddToComboBox(GridBagConstraints c)
    {
        cb = null;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty= 0.1; 
        cb = new JComboBox<Object>(((user.ListeSalles()).getAll()).toArray()); 
        cb.setEditable(true);
        this.add(cb,c);
        
                          cb.addActionListener(new ActionListener() {     
         @Override
         public void actionPerformed(ActionEvent ae) 
         {   
             resetTable();
            display_courses(user.ListeSeances().getBySalleAtWeek((Salle) cb.getSelectedItem(), Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date()))));
            
         }
            });
        
       // edtsalles(s);
         

    }
    
    public void nomsAddToComboBox(GridBagConstraints c)
    {
        cb = null;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty= 0.1; 
        cb = new JComboBox<Object>(((user.ListeUtilisateurs().getEnseignants().toArray()))); 
        cb.setEditable(true);
        this.add(cb,c);
        
        
          cb.addActionListener(new ActionListener() {     
         @Override
         public void actionPerformed(ActionEvent ae) 
         {       
            System.out.println("nom SELEC ==" + cb.getSelectedItem());
            
         }
            });

    }
    
    public void classesAddToComboBox(GridBagConstraints c)
    {
        
        cb = null;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty= 0.1; 
        cb = new JComboBox<Object>(((user.ListeGroupes().getAll().toArray()))); 
        cb.setEditable(true);
        this.add(cb,c);
                  cb.addActionListener(new ActionListener() {     
         @Override
         public void actionPerformed(ActionEvent ae) 
         {       
            System.out.println("classe SELEC ==" + cb.getSelectedItem());
            
         }
            });
        
    }
        
    public void promosAddToComboBox(GridBagConstraints c)
    {
        cb = null;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty= 0.1; 
        cb = new JComboBox<Object>(((user.ListePromotion().getAll().toArray()))); 
        cb.setEditable(true);
        this.add(cb,c);
        
                          cb.addActionListener(new ActionListener() {     
         @Override
         public void actionPerformed(ActionEvent ae) 
         {       
            System.out.println("promos SELEC ==" + cb.getSelectedItem());
            
         }
            });
    }
        
        
    @SuppressWarnings({"deprecation"})
    
	public void display_courses(List<Seance> seance_cours)
    {
        int nb;
        int caseHeure;
        String info;
        String strTime;
        DateFormat heureformat = new SimpleDateFormat("HH.mm.ss");
        Calendar calendar = Calendar.getInstance();
        
        for(Seance s : seance_cours)
        {   
            
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

            getNomEns(s.getEnseignants().toString());
            String NomEns = getNomEns(s.getEnseignants().toString());;
            
            info = s.getCours().toString() + "\n"+ NomEns+"\n" + s.getSalles().toString() + "\n" +s.getType().toString() ;
            table.getModel().setValueAt(info, caseHeure, nb-1);
        }
    }
        
    public String getNomEns(String enseignant)
    {
        final String espace =" ";
        String mots[]=enseignant.split(espace);
        String NomEns = null;
        
           // System.out.println(mots[i]);
            System.out.println("mot0 ===="+mots[0]);
            String zut = null;
            zut = "[Enseignant";
            if(mots[0].equals(zut))
            {
                NomEns = mots[1]+ " " +mots[2];
                System.out.println("ici ==== "+NomEns);
            }
            else
                NomEns = mots[2]+ " " +mots[3];
            
            return  NomEns;
    }
}