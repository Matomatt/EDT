/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import Controllers.Controller;
import Donnees.Donnee;
import Utilisateurs.User;
import Utilisateurs.User.UserType;
import View.DiagramPanels.BarChartPanel;
import View.DiagramPanels.PieChartPanel;
import View.DiagramPanels.XYAreaChartPanel;


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

		tabbedPanes.add("Récapitulatif des cours", new JScrollPane(new RecapPanel(user, controller)));
				
		String[] labels = {"Total", "Effectuées"};
		tabbedPanes.add("Heures de cours dans l'année", new BarChartPanel("Heures de cours dans l'année", "", 
				user.ListeSeances().getNombreHeureParCours(user.getUtilisateurConnecte()), user.ListeSeances().getNombreHeureEffectueeParCours(user.getUtilisateurConnecte()), labels));
    	
		if (user.getUserType() == UserType.Admin || user.getUserType() == UserType.Referent_pedagogique)
		{
			JTabbedPane tabbedPanesPieChart = new JTabbedPane();
			for (Donnee site : user.ListeSite().getAll())
				tabbedPanesPieChart.add(site.toString(), new PieChartPanel("Capacité des salles pour " + site, user.ListeSalles().getProportionCapacitePourSite(site)));
			
			tabbedPanes.add("Capacité des salles", tabbedPanesPieChart);
			
			JTabbedPane tabbedPanesXYGraph = new JTabbedPane();
			for (Donnee site : user.ListeSite().getAll())
				tabbedPanesXYGraph.add(site.toString(), new XYAreaChartPanel("Moyenne d'occupation des salles pour " + site, "Dates", "Nombre d'heures", user.ListeSalles().getMoyenneOccupationPourSite(site)));
			
			tabbedPanes.add("Occupation des salles", tabbedPanesXYGraph);
		}
		
		this.add(tabbedPanes, c);
    }
}
