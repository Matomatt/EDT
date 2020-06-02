package View;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.BaseWindowController;
import UI_Elements.Button;
import Utilisateurs.User;
import Utilisateurs.User.UserType;

import java.util.HashMap;
import java.util.Map;



public class BaseWindow extends JFrame 
{
	private static final long serialVersionUID = 3528066671950303397L;
	
	JPanel mainWindow;
	User user = null;
	BaseWindowController controller;
	Map<String, JPanel> pages = new HashMap<String, JPanel>();

	Button  button1, button2, button3, button4, button11 = null;
        
	GridBagConstraints c = new GridBagConstraints();

	public BaseWindow(BaseWindowController controller)
	{
		this.setLayout(new GridBagLayout());
		
		controller.setControlledView(this);
		this.controller = controller;
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setVisible(true);
		this.pack();
	}
	
	public void addComponentsToPane() 
	{
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
        c.ipady=35;
        
		button1 = new Button("btEDT_Grille", "Emploi du temps", controller);
		this.add(button1, c);
		
        button11 = new Button("btEDT_Liste", "EDT liste", controller);
		c.gridx = 1;
		this.add(button11, c);
                
		button2 = new Button("btSallesLibres", "Salles libres", controller);
		c.gridx = 2;
		this.add(button2, c);
        
        button4 = new Button("btReporting", "Analytics", controller);
		c.gridx = 3;
		this.add(button4, c);
		
		if (user.getUserType() == UserType.Admin)
		{
			button3 = new Button("btModifier", "Modifier", controller);
			c.gridx = 4;
			this.add(button3, c);
		}
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 4;
		this.add(new JLabel(user.getUtilisateurConnecte().toString()), c);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setBackground(new java.awt.Color(255, 255, 255));
		this.validate();
		this.repaint();
    }
	
	public Map<String, JPanel> getPages() {
		return pages;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
