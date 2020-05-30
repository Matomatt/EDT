/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.GridBagConstraints;
import java.util.Map;
import Controllers.Controller;
import Utilisateurs.User;
import View.DiagramPanels.BarChartPanel;


public class ReportingPanel extends Panel
{
	private static final long serialVersionUID = -3479156065256517426L;

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
		
    	//Select COUNT(cours.Nom), SUM(TIMESTAMPDIFF(minute, CAST(Heure_Debut as Datetime), CAST(Heure_Fin as Datetime)))/(60*30) as duree From seance,cours Where cours.ID = seance.ID_Cours
    	Map<String, Integer> map = user.ListeSeances().getNombreHeureParCours(user.getUtilisateurConnecte());
    	
    	this.add(new BarChartPanel("Nombre d'heure total par cours dans l'année", "Heures de cours", map), c);
    	
    }
}
