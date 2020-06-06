package Controllers;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import View.LoginPanel;

/**
 * Controller de l'emploi de la Page Login
 */
public class LoginPanelController implements ActionListener 
{
	LoginPanel panel = null;
	BaseWindowController baseWindowController = null;
	
	/**
	 * Constructeur qui a besoin du controller de la fenetre principale pour accéder a sa fonction Connect
	 * @param baseWindowController
	 */
	public LoginPanelController(BaseWindowController baseWindowController)
	{
		this.baseWindowController = baseWindowController;
	}
	
	/**
	 * Fonction contenu dans tous les controlleurs leur indiquant la vue qu'ils controllent
	 * @param panel
	 */
	public void setControlledView(LoginPanel panel) {
		this.panel = panel;
	}

	
	
    /**
	 * Gère l'appui du bouton login (appelle la fonction connect du controller de la fenetre principale)
	 * @see BaseWindowController
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().getClass() == JButton.class)
		{
			JButton bt = (JButton) e.getSource();
			//System.out.println(bt.getName());
			if (bt.getName() == "btLogin")
			{
				if (!baseWindowController.Connect(panel.getEmail(), panel.getPassword()))
		       	{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.HORIZONTAL;
					c.insets = new Insets(40, 20, 5, 20);
					c.weighty = 0.0;
					c.gridwidth = 1;
					c.weightx = 0.5;
					c.gridx = 0;
					c.gridy = 4;
					panel.getBackgroundPanel().add(new JLabel("Login or password incorrect"), c);
					panel.validate();
		       	}
			}
		}
	}
}
