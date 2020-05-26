package Controllers;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import View.LoginPanel;

public class LoginPanelController implements ActionListener 
{
	LoginPanel panel = null;
	BaseWindowController baseWindowController = null;
	
	public LoginPanelController(BaseWindowController baseWindowController)
	{
		this.baseWindowController = baseWindowController;
	}
	
	public void setControlledView(LoginPanel panel) {
		this.panel = panel;
	}

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
