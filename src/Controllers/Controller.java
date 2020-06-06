package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.Panel;

/**
 * Classe mère de la majorité des controller, implements ActionListener
 */
public class Controller implements ActionListener 
{
	protected Panel panel = null;
	
	/**
	 * Fonction contenu dans tous les controlleurs leur indiquant la vue qu'ils controllent
	 * @param Panel
	 */
	public void setControlledView(Panel panel) {
		this.panel = panel;
	}
	
	/**
	 * Cette classe implémente ActionListener
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
