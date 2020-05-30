package View;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import Controllers.Controller;
import Utilisateurs.User;

public class Panel extends JPanel 
{
	private static final long serialVersionUID = 8254914084932068228L;
	
	User user = null;
	Controller controller = null;
	
	public Panel(User user, Controller controller)
	{
		this.controller = controller;
		this.controller.setControlledView(this);
		
		this.user = user;
		this.setLayout(new GridBagLayout());
		this.setBackground(new java.awt.Color(255, 255, 255));
        
        this.setVisible(true);
        validate();
	}
	
	public User getUser() { return user; }
}
