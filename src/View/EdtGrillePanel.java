//source de là où j'ai trouvé la barre pour les semaines
//https://www.developpez.net/forums/d1503732/java/interfaces-graphiques-java/debuter/faire-planning-jtable/

package View;

import Seances.Seance;
import Utilisateurs.User;
import Utilisateurs.User.UserType;
import Utilisateurs.Utilisateur;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controllers.Controller;
import Donnees.Donnee;
import Groupes.Groupe;
import Salles.Salle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author BOCHER, CADOT et GAUTIER 
 * classe qui hérite de Panel et qui créer la fenêtre de l'emploi du temps sous forme de grille 
 */
public class EdtGrillePanel extends Panel 
{
    /**
    * serialVersionUID : clé de hachage de la classe
    * scroll : scoll type JScrollPane
    * cb : JComboBox de type objet qui récupère les différentes données internes au combo box
    * table : table type JTable
    * liste : JComboBox de type String
    * columns : tableau de String avec les jours de la semaine
    * data : tableau double de String avec les horaires de la journée
    */
	private static final long serialVersionUID = -4510731458552817257L;
	
    JScrollPane scroll = null;
    JComboBox<Object> cb = null;
    JTable table = null;
    JComboBox<String> liste = null;
    JLabel semaine = new JLabel(new SimpleDateFormat("w").format(new java.util.Date()));
    
    private final String[] columns = { "", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};

    private final String[][] data = {
    {"8:30 - 10:00", "", "", "", "", "", ""},
    {"10:15 - 11:45", "", "", "", "", "", ""},
    {"12:00 - 13:30", "", "", "", "", "", ""},
    {"13:45 - 15:15", "", "", "", "", "", ""},
    {"15:30 - 17:00", "", "", "", "", "", ""},
    {"17:15 - 18:45", "", "", "", "", "", ""},
    {"19:00 - 20:30", "", "", "", "", "", ""}};
    
    /**
    * Constructeur     
    * @param _user
    * @param controller
    */
	public EdtGrillePanel(User _user, Controller controller) {
		super(_user, controller);

        initComponents();
    }

    /**
    * Méthode qui initialise le contenu de la fenêtre de l'emploi du temps sous forme de liste     
    */    
    private void initComponents()
    {
    	String[] options = {"Mon EDT", "Enseignant","Classe", "Salle", "Promo"};
		
		switch (user.getUserType())
		{
			case Etudiant: options = new String[] {"Mon EDT", "Salle", "Enseignant"}; break;
			case Enseignant: options = new String[] {"Mon EDT", "Salle", "Classe", "Promo"};
			default: break;
		}
		
		liste = new JComboBox<String>(options);
		
        Color color = new Color(111,199,227);
        JPanel pan = new JPanel();
        
        pan.add(semaine);
		for(int i=1; i<=52; i++) {
                    
			JLabel label = new JLabel(String.valueOf(i));
                        
			label.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
			label.setOpaque(true);
			if ( i%2==0 ) { label.setBackground(Color.WHITE); }
			else { label.setBackground(color); }
			label.setEnabled(true);
			label.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { semaine.setText(label.getText()); controller.actionPerformed(new ActionEvent(label, 1, "labelMouseClicked"));} });
			pan.add(label);
		}
                

		JScrollPane slider = new JScrollPane(pan,JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		GridBagConstraints c = new GridBagConstraints();
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

        for(int i = 0; i<7; i++)
        {
            table.getColumnModel().getColumn(i).setCellRenderer(render);
            table.getColumnModel().getColumn(0).setCellRenderer(cr);
        }

        //user.ListeSeances().getBySalleAtWeek(NOMSALLE, Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date())));
        //user.ListeSeances().getByGroupeAtWeek(groupe, Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date())));
        //user.ListeSeances().getByPromoAtWeek(promo, Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date())));
        display_courses(user.ListeSeances().getByUtilisateurAtWeek(user.getUtilisateurConnecte(), getSemaine()));
        
        table.getTableHeader().setBackground(new java.awt.Color(255, 255, 255));
        scroll = new JScrollPane(table);
        
        c.gridy = 2;
        c.gridwidth = 3;
        scroll.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        scroll.setBackground(Color.white);
        this.add(scroll, c);
        
        c.fill = GridBagConstraints.NONE;
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        c.ipadx = 30;
        c.ipady = 20;
        c.weightx = 0.5;
        c.weighty= 0.1;
        
        this.add(liste,c);

    	liste.addActionListener(controller);
    }
    
    /**
    * Appel de la méthode revalidate     
    */
    public void repaintAll()
    {
      this.revalidate();
    }
    
    /**
    * Appel de la méthode remove et resetTable    
    */
    @Override
    public void removeAll()
    {
        if(cb !=null)
        	this.remove(cb);
        resetTable();
    }
    
    /**
    * Méthode qui met le tableau à vide, c'est-à-dire que les cours sont remplacés par des blancs 
    */
    public void resetTable(){
       for(int i = 0; i<table.getRowCount(); i++)
               for(int j = 1; j<table.getColumnCount(); j++)
                   table.setValueAt("", i, j); 
    }

    /**
    * Méthode qui ajoute des salles dans la JComboBox
    */
	public void sallesAddToComboBox()
    {
        cb = new JComboBox<Object>(((user.ListeSalles()).getAll()).toArray());
        this.add(cb, get2ndCBconstraint());
     
        if (user.getUserType() == UserType.Enseignant || user.getUserType() == UserType.Etudiant)
        	cb.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { display_courses(user.ListeSeances().getBySalleAtWeek(user.getUtilisateurConnecte(), (Salle)cb.getSelectedItem(), getSemaine())); }});
        else
        	cb.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { display_courses(user.ListeSeances().getBySalleAtWeek((Salle)cb.getSelectedItem(), getSemaine())); }});
    }
    
	/**
    * Méthode qui ajoute des enseignants dans la JComboBox
    */
    public void nomsAddToComboBox()
    {
        cb = new JComboBox<Object>(((user.ListeUtilisateurs().getEnseignants().toArray())));
        this.add(cb, get2ndCBconstraint());
        
        if (user.getUserType() == UserType.Enseignant || user.getUserType() == UserType.Etudiant)
        	cb.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { display_courses(user.ListeSeances().getByUtilisateurAtWeek(user.getUtilisateurConnecte(), (Utilisateur)cb.getSelectedItem(), getSemaine())); }});
        else
        	cb.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { display_courses(user.ListeSeances().getByUtilisateurAtWeek((Utilisateur)cb.getSelectedItem(), getSemaine())); }});
    }
    
    /**
     * Méthode qui ajoute des groupes dans la JComboBox
     */
    public void classesAddToComboBox()
    {
        cb = new JComboBox<Object>(((user.ListeGroupes().getAll().toArray())));
        
        this.add(cb, get2ndCBconstraint());
        
        if (user.getUserType() == UserType.Enseignant || user.getUserType() == UserType.Etudiant)
        	cb.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { display_courses(user.ListeSeances().getByGroupeAtWeek(user.getUtilisateurConnecte(), (Groupe)cb.getSelectedItem(), getSemaine())); }});
        else
        	cb.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { display_courses(user.ListeSeances().getByGroupeAtWeek((Groupe)cb.getSelectedItem(), getSemaine())); }});
    }
    
    /**
     * Méthode qui ajoute des promos dans la JComboBox
     */
    public void promosAddToComboBox()
    {
        cb = new JComboBox<Object>(((user.ListePromotion().getAll().toArray())));
        this.add(cb, get2ndCBconstraint());
        
        if (user.getUserType() == UserType.Enseignant || user.getUserType() == UserType.Etudiant)
        	cb.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { display_courses(user.ListeSeances().getByPromoAtWeek(user.getUtilisateurConnecte(), (Donnee)cb.getSelectedItem(), getSemaine())); }});
        else
        	cb.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { display_courses(user.ListeSeances().getByPromoAtWeek((Donnee)cb.getSelectedItem(), getSemaine())); }});
    }
    
    private GridBagConstraints get2ndCBconstraint()
    {
    	 GridBagConstraints c = new GridBagConstraints();
         c.fill = GridBagConstraints.NONE;
         c.gridx = 1;
         c.gridy = 1;
         c.gridwidth = 1;
         c.weightx = 0.5;
         c.weighty= 0.1;
         
         return c;
    }
    
    /**
    * Méthode qui lis séances et les inscrit dans la JTable   
    * @param seance_cours
    */
	public void display_courses(List<Seance> seance_cours)
    {
		resetTable();
		
        String info;
        Calendar calendar = Calendar.getInstance();
        
        List<Seance> memeHoraireList = new ArrayList<Seance>();
        
        for(Seance s : seance_cours)
        { 
            String NomEns = getNomEns(s.getEnseignants());;
            switch(liste.getSelectedItem().toString())
            {
                case "Salle" :
                case "Enseignant" : 
                case "Classe" :
                case "Promo" :
                    info = s.getDate() +" : "+ s.getCours().toString() + "\n"+ NomEns+"\n" + s.getSalles().toString() +"\n" + s.getGroupes() +  "\n" +s.getType().toString() ;
                break; 
                
                default:
                    info = s.getDate() +" : "+ s.getCours().toString() + "\n"+ NomEns+"\n" + s.getSalles().toString() + "\n" +s.getType().toString() ;
                    break;
            }
            
            calendar.setTime(s.getDate());
            
            if (!(memeHoraireList = memeHoraireList.stream().filter(x -> x.getDebut().equals(s.getDebut()) && x.getFin().equals(s.getFin())).collect(Collectors.toList())).isEmpty())
        	{
            	info = s.getDate() + " : " + s.getCours().toString() + " " + s.getSalles().toString() +" " + s.getGroupes() + "\n";;
        		for (Seance seance : memeHoraireList) {
        			info += seance.getCours().toString() + " " + seance.getSalles().toString() +" " + seance.getGroupes() + "\n";
				}
        	}
            
            for (int i = getCaseHeure(s.getDebut()); i < getCaseHeure(s.getFin()); i++)
            	table.getModel().setValueAt(info, i, calendar.get(Calendar.DAY_OF_WEEK)-1);
			
            memeHoraireList.add(s);
        }
    }
	
	private int getCaseHeure(Time heure)
	{
		String strTime = new SimpleDateFormat("HH.mm.ss").format(heure);
		
		switch(strTime)
        {
            case "08.30.00" : return 0;
            case "10.15.00" : return 1;
            case "12.00.00" : return 2;
            case "13.45.00" : return 3;
            case "15.30.00" : return 4;
            case "17.15.00" : return 5;
            case "19.00.00" : return 6;
            case "10.00.00" : return 1;
            case "11.45.00" : return 2;
            case "13.30.00" : return 3;
            case "15.15.00" : return 4;
            case "17.00.00" : return 5;
            case "18.45.00" : return 6;
            case "20.30.00" : return 7;
            default: return -1;
        }
	}

	/**
    * Méthode qui récupère les noms des enseignants     
    * @param enseignants
    * @return NomEns, le nom de l'enseignant
    */
    public String getNomEns(List<Utilisateur> enseignants)
    {
        String NomEns = "";
        boolean first = true;
        
        for (Utilisateur enseignant : enseignants) {
			if(!first) NomEns += ", "; first = false;
			NomEns+=enseignant.getNom() + " " + enseignant.getPrenom();
		}
		
		return  NomEns;
    }

	public Object getListeSelectedItem() {
		return liste.getSelectedItem();
	}

	public int getSemaine() {
		return Integer.parseInt(semaine.getText());
	}

	public Object getSelectedItem() {
		return cb.getSelectedItem();
	}
}
