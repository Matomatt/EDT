package Controllers;

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

public class BaseWindowController implements ActionListener {
	
	BaseWindow baseWindow = null;
	JPanel mainWindowPanel = null;
	
	public BaseWindowController() {
	}
	
	public void setControlledView(BaseWindow baseWindow) {
		this.baseWindow = baseWindow;
		LaunchLoginPage();
	}
	
	private void LaunchLoginPage() 
	{
		LoginPanelController controller = new LoginPanelController(this);
		mainWindowPanel = new LoginPanel(controller);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 1;
		baseWindow.add(mainWindowPanel, c);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().getClass() == JButton.class)
		{
			JPanel page = null;
			
			System.out.println(((JButton)e.getSource()).getText());
			if (!baseWindow.getPages().containsKey(((JButton)e.getSource()).getText()))
	    	{
    			JButton bt = (JButton) e.getSource();
    			System.out.println(bt.getName());
    			switch (bt.getName()) 
    			{
					case "bt1": page = new EdtGrillePanel(baseWindow.getUser());
								break;

					case "bt11": page = new EDT_ListePanel(baseWindow.getUser()); break;
					case "bt3": Controller controller = new ModifAdminPanelController();
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
	    		SwitchPage(page);
		}
	}
	
	public void SwitchPage(JPanel newPage)
	{
		baseWindow.remove(mainWindowPanel);
		
		mainWindowPanel = newPage;
		
		GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 5;
		c.gridx = 0;
		c.gridy = 1;
		
		baseWindow.add(mainWindowPanel, c);
		
		baseWindow.validate();
		baseWindow.repaint();
	}
	
	public boolean Connect(String login, String password) 
	{
		User user = null;
		try {
			System.out.println(login + " " + password);
			user = new ConnectionViaUser(login, password);
		} catch (UserNotFoundException | ClassNotFoundException | ConnectionErrorException e) {
			return false;
		}
		
		baseWindow.setUser(user);
		baseWindow.addComponentsToPane();
        SwitchPage(new EdtGrillePanel(user));
        
		System.out.println(user.getUtilisateurConnecte());

		return true;
	}
}
