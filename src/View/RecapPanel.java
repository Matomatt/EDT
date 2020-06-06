package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JList;
import javax.swing.JTable;
import com.toedter.calendar.JDateChooser;

import Controllers.Controller;
import UI_Elements.Button;
import UI_Elements.JSpoiler;
import Utilisateurs.User;

/**
 * @author BOCHER, CADOT et GAUTIER 
 * classe qui hérite de Panel et qui créér la fenêtre récapitulative des cours suivant les dates choisies
 */
public class RecapPanel extends Panel 
{
        /**
        * serialVersionUID : clé de hachage de la classe
        * firstDateChooser : création du type JDateChooser
        * lastDateChooser : création du type JDateChooser
        */
	private static final long serialVersionUID = 827841717817668131L;
	
	JDateChooser firstDateChooser = new JDateChooser();
	JDateChooser lastDateChooser = new JDateChooser();
        
        /**
        * Constructeur     
        * @param user
        * @param controller
        */
	public RecapPanel(User user, Controller controller) {
		super(user, controller);
		
		java.util.Date today = new java.util.Date();
		try { today = new SimpleDateFormat("yyyy-MM-dd").parse("2019-09-01"); } catch (ParseException e) { e.printStackTrace(); }
		firstDateChooser.setDate(today);
		try { today = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30"); } catch (ParseException e) { e.printStackTrace(); }
		lastDateChooser.setDate(today);
		
		initComponents();
	}

        /**
        * Méthode qui initialise le contenu de la fenêtre RecapPanel     
        */
	private void initComponents() 
	{
		Button refreshButton = new Button("btRefresh", "Refresh", controller);
		refreshButton.setFont(new Font(refreshButton.getFont().getName(), Font.PLAIN, refreshButton.getFont().getSize()*2/3));
		
		GridBagConstraints c = new GridBagConstraints();
    	
    	c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		
		this.add(firstDateChooser,c);
		
		c.gridx = 1;
		this.add(lastDateChooser, c);
		
		c.gridx = 2;
		this.add(refreshButton, c);
		
		c.insets = new Insets(0, 17, 0, 0);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		
		JTable table = new JTable(new String[][] {{"Matière - public", "Première séance", "Dernière séance", "Durée", "Nb."}}, new String[] {"Matière - public", "Première séance", "Dernière séance", "Durée", "Nb."});
        table.getTableHeader().setBackground(Color.white);
        table.setCellSelectionEnabled(false);
        table.setFont(new Font(table.getFont().getName(), Font.PLAIN, table.getFont().getSize()*2));
        table.setRowHeight((int) (table.getFont().getSize()*1.5));
		this.add(table, c);
		
		c.insets = new Insets(0, 0, 0, 0);
		Map<List<String>, List<String>> map = user.ListeSeances().getRecap(user.getUtilisateurConnecte(), new Date(firstDateChooser.getDate().getTime()), new Date(lastDateChooser.getDate().getTime()));
		
		for (Entry<List<String>, List<String>> entry : map.entrySet()) 
		{
			table = new JTable(new String[][] {entry.getKey().toArray(new String[entry.getKey().size()])}, entry.getKey().toArray(new String[entry.getKey().size()]));
	        table.getTableHeader().setBackground(Color.white);
	        table.setCellSelectionEnabled(false);
	        table.setFont(new Font(table.getFont().getName(), Font.PLAIN, (int) (table.getFont().getSize()*1.4)));
	        table.setRowHeight((int) (table.getFont().getSize()*1.3));
	        
			c.gridy += 1;
			this.add(new JSpoiler(table, new JList<Object>(entry.getValue().toArray())), c);
		}
	}
	
        /**
        * Méthode qui rafraichit la fenêtre avec les nouvelles données     
        */
	public void refreshTable()
	{
		removeAll();
		initComponents();
		validate();
	}
}
