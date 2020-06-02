/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.GridBagConstraints;
import javax.swing.JTabbedPane;

import Controllers.Controller;
import Utilisateurs.User;
import View.DiagramPanels.BarChartPanel;


public class ReportingPanel extends Panel
{
	private static final long serialVersionUID = -3479156065256517426L;
	
	JTabbedPane tabbedPanes = new JTabbedPane();

	/**
	 * Créer la fenêtre de reporting : les stats de l'utilisateur sous forme de graph
	 * 
	 * @param user
	 * @param controller
	 */
    public ReportingPanel(User user, Controller controller) {
    	super(user, controller);
    	
        initComponents();
    }
    private void initComponents() 
    {
    	GridBagConstraints c = new GridBagConstraints();
    	
    	c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		
		//tabbedPanes.add("Nombre d'heure total par cours dans l'année", new PieChartPanel("Nombre d'heure total par cours dans l'année", user.ListeSeances().getNombreHeureParCours(user.getUtilisateurConnecte())));

		String[] labels = {"Total", "Effectuées"};
		tabbedPanes.add("Heures de cours dans l'année", new BarChartPanel("Heures de cours dans l'année", "", 
				user.ListeSeances().getNombreHeureParCours(user.getUtilisateurConnecte()), user.ListeSeances().getNombreHeureEffectueeParCours(user.getUtilisateurConnecte()), labels));
    	

		
    	this.add(tabbedPanes, c);
    	
    }
}
