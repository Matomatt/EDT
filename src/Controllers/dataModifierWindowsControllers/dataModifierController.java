package Controllers.dataModifierWindowsControllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import Controllers.ModifAdminPanelController;
import UI_Elements.Button;

public abstract class dataModifierController implements ActionListener {

	protected ModifAdminPanelController panelController = null;
	
	public dataModifierController(ModifAdminPanelController panelController) {
		this.panelController = panelController;
	}

	/**
	 * Apelle la fonction add ou modifiy du controller qui en herite quand le bouton add est appuyé. Permets d'ajouter ou de modifier l'element
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().getClass() == Button.class)
		{
			Button bt = (Button) e.getSource();
			//System.out.println(bt.getName());
			switch (bt.getName())
			{
				case "btAdd": add(); modify(); break;
				
				default: break;
			}
		}
	}

	protected abstract void add();
	protected abstract void modify();

	public abstract void setControlledView(JFrame window);
}
