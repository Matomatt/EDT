package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.Panel;

/**
 * 
 */
public class Controller implements ActionListener 
{
	protected Panel panel = null;
	
	public void setControlledView(Panel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
