package View;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Controllers.BaseWindowController;
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

	JButton  button1, button2, button3, button4, button11 = null;
        
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
		c.gridy = 0;
        c.ipady=35;
		button1 = new JButton("Emploi du temps");
		button1.setName("bt1");
		c.gridx = 0;
		this.add(button1, c);
		button1.addActionListener(controller);
		
        button11 = new JButton("EDT liste");
        button11.setName("bt11");
		c.gridx = 1;
		this.add(button11, c);
        button11.addActionListener(controller);
                
		button2 = new JButton("Récapitulatif des cours");
		button2.setName("bt2");
		c.gridx = 2;
		this.add(button2, c);
        button2.addActionListener(controller);
        
        button4 = new JButton("Reporting");
        button4.setName("bt4");
		c.gridx = 3;
		this.add(button4, c);
		
		if (user.getUserType() == UserType.Admin)
		{
			button3 = new JButton("Modifier");
			button3.setName("bt3");
			c.gridx = 4;
			this.add(button3, c);
	        button3.addActionListener(controller);
		}
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
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
