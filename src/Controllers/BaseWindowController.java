package Controllers;

import UI_Elements.Button;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Utilisateurs.ConnectionViaUser;
import Utilisateurs.User;
import Utilitaires.ConnectionErrorException;
import Utilitaires.UserNotFoundException;
import View.BaseWindow;
import View.EDT_ListePanel;
import View.EdtGrillePanel;
import View.LoginPanel;
import View.ModifAdminPanel;
import View.ReportingPanel;
import View.SallesLibresPanel;


/**
 * Controlleur de la fenêtre principale. Gère le changement de page et la connection
 */
public class BaseWindowController implements ActionListener {
	
	BaseWindow baseWindow = null;
	JPanel mainWindowPanel = null;
	
	/**
	 * Fonction contenu dans tous les controlleurs leur indiquant la vue qu'ils controllent
	 * @param baseWindow
	 */
	public void setControlledView(BaseWindow baseWindow) {
		this.baseWindow = baseWindow;
		LaunchLoginPage();
	}
	
	/**
	 * Affiche la page de connexion
	 */
	private void LaunchLoginPage() 
	{
		LoginPanelController controller = new LoginPanelController(this);
		mainWindowPanel = new LoginPanel(controller);
		SwitchPage(mainWindowPanel, false);
	}
	
	/**
	 * Recupère l'action effectuée par les composants de la vue et agit en fonction
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().getClass() == Button.class)
		{
			JPanel page = null;
			
			if (!baseWindow.getPages().containsKey(((Button)e.getSource()).getText()))
	    	{
				Button bt = (Button) e.getSource();
    			//System.out.println(bt.getName());
    			Controller controller = null;
    			switch (bt.getName()) 
    			{
					case "btEDT_Grille": controller = new EdtGrillePanelController();
								page = new EdtGrillePanel(baseWindow.getUser(), controller);
								break;

					case "btEDT_Liste": controller = new EDT_ListePanelController();
								page = new EDT_ListePanel(baseWindow.getUser(), controller);
								break;
								
					case "btSallesLibres": controller = new Controller();
								page = new SallesLibresPanel(baseWindow.getUser(), controller);
								break;
								 
					case "btReporting": controller = new ReportingPanelController();
								page = new ReportingPanel(baseWindow.getUser(), controller);
								break;
 
					case "btModifier": controller = new ModifAdminPanelController();
								page = new ModifAdminPanel(baseWindow.getUser(), controller); 
								break;
	
					default: break;
				}
    			
    			if (page != null)
        			baseWindow.getPages().put(((JButton)e.getSource()).getText(), page);
	    	}
			else
				page = baseWindow.getPages().get(((JButton)e.getSource()).getText());
			
			//System.out.println(page);
			if (page != null)
	    		SwitchPage(page, true);
		}
	}
	
	/**
	 * Change de page (remplace la page actuelle par la nouvelle indiquée)
	 * @param newPage
	 * @param remove
	 */
	public void SwitchPage(JPanel newPage, boolean remove)
	{
		if (remove)
			baseWindow.remove(mainWindowPanel);
		
		mainWindowPanel = newPage;
		
		baseWindow.setBackground(Color.white);
		
		GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        //c.insets = new Insets(10, 0, 0, 0);
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 5;
		c.gridx = 0;
		c.gridy = 1;
		
		baseWindow.add(mainWindowPanel, c);
		
		baseWindow.validate();
		baseWindow.repaint();
	}
	
	/**
	 * Initialise le User qui va être utilisé par tout le programme
	 * @see User
	 * @see ConnectionViaUser
	 * @param login
	 * @param password
	 * @return si connecté avec succès
	 */
	public boolean Connect(String login, String password) 
	{
		User user = null;
		try { user = new ConnectionViaUser(login, password); } 
		catch (UserNotFoundException | ClassNotFoundException | ConnectionErrorException e) { return false; }
		
		baseWindow.setUser(user);
		baseWindow.addComponentsToPane();
        SwitchPage(new EdtGrillePanel(baseWindow.getUser(), new EdtGrillePanelController()), true);

		return true;
	}
}
