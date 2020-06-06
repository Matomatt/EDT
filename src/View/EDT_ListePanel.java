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
import Utilisateurs.Utilisateur;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Controllers.Controller;
import Donnees.Donnee;
import Groupes.Groupe;
import Salles.Salle;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Léonie
 */
public class EDT_ListePanel extends Panel
{    
	private static final long serialVersionUID = 3736956335101565252L;
	JComboBox<String> liste = null;
	JComboBox<Object> cb = null;
	JTable tableau = null;

	JLabel semaine = new JLabel(new SimpleDateFormat("w").format(new java.util.Date()));

	public EDT_ListePanel(User _user, Controller controller) 
	{
		super(_user, controller);		

		initComponents();//code de la page
	}

	private void initComponents() 
	{
		String[] options = new String[] {"Mon EDT", "Enseignant","Classe", "Salle", "Promo"};
		
		switch (user.getUserType())
		{
			case Etudiant:
			case Enseignant: options = new String[] {"Mon EDT", "Salle"};
			default: break;
		}
		
		liste = new JComboBox<String>(options);
		
		GridBagConstraints c = new GridBagConstraints();

		Color color = new Color(111,199,227);
		JPanel pan = new JPanel();

		pan.add(semaine);
		for(int i=1; i<=52; i++) 
		{         
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

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());
		
		tableau = new JTable(display_courses(user.ListeSeances().getByUtilisateurAtWeek(user.getUtilisateurConnecte(), getSemaine())), new String[]{"Heure Début", "Heure Fin", "Cours", "Enseignant", "Groupe", "Salle", "Type de Cours"});
		tableau.setFont(new Font(tableau.getFont().getName(), Font.PLAIN, (int) (tableau.getFont().getSize()*1.2)));
		tableau.setRowHeight((int) (tableau.getFont().getSize()*1.5));
		JScrollPane scroll = new JScrollPane(tableau);

		c.gridx = 0;
		c.gridy = 3;

		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		scroll.setBackground(Color.white);
		this.add(scroll,c);

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

	public void repaintAll()
	{
		this.revalidate();
	}

	@Override
	public void removeAll()
	{
		if(cb !=null)
			this.remove(cb);
		resetTable();
	}

	public void resetTable(){
		for(int i = 0; i<tableau.getRowCount(); i++)
			for(int j = 0; j<tableau.getColumnCount(); j++)
				tableau.setValueAt("", i, j); 
	}

	public void sallesAddToComboBox()
	{
		cb = new JComboBox<Object>(((user.ListeSalles()).getAll()).toArray());
		this.add(cb, get2ndCBconstraint());

		cb.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { display_courses(user.ListeSeances().getBySalleAtWeek((Salle)cb.getSelectedItem(), getSemaine())); }});
	}

	public void nomsAddToComboBox()
	{
		cb = new JComboBox<Object>(((user.ListeUtilisateurs().getEnseignants().toArray())));
		this.add(cb, get2ndCBconstraint());

		cb.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { display_courses(user.ListeSeances().getByUtilisateurAtWeek((Utilisateur)cb.getSelectedItem(), getSemaine())); }});
	}

	public void classesAddToComboBox()
	{
		cb = new JComboBox<Object>(((user.ListeGroupes().getAll().toArray())));

		this.add(cb, get2ndCBconstraint());

		cb.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { display_courses(user.ListeSeances().getByGroupeAtWeek((Groupe)cb.getSelectedItem(), getSemaine())); }});
	}

	public void promosAddToComboBox()
	{
		cb = new JComboBox<Object>(((user.ListePromotion().getAll().toArray())));
		this.add(cb, get2ndCBconstraint());

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

	public Object[][] display_courses(List<Seance> listeSeances)
	{
		if (tableau != null)
			resetTable();

		//on récupère la taille pour savoir le nombre de cours en une journée et pouvoir ensuite passer au jour d'après.
		int taille = listeSeances.size();
		taille=taille+6;
		//création d'un tableau qui affichera sous forme de liste
		//ligne : cours (taille)
		//colonne : les détails (entêtes)
		Object[][] table = new Object[taille][7];

		int i =0;

		Date sauvDate=new Date(2001-01-01);

		Date dateEnCours;

		for(Seance s : listeSeances)
		{
			dateEnCours=s.getDate();

			if(dateEnCours.compareTo(sauvDate)!=0)
			{
				table[i][0]= new SimpleDateFormat("EEEE").format(dateEnCours).substring(0, 1).toUpperCase() + new SimpleDateFormat("EEEE").format(dateEnCours).substring(1);
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
			table[i][3]= getNomEns(s.getEnseignants());
			table[i][4]=s.getGroupes();
			table[i][5]=s.getSalles();
			table[i][6]=s.getType();
			i++;
		}
		
		if(tableau != null)
		 tableau.setModel(new DefaultTableModel(table, new String[]{"Heure Début", "Heure Fin", "Cours", "Enseignant", "Groupe", "Salle", "Type de Cours"}));
			
		revalidate();
		return table;
	}

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
