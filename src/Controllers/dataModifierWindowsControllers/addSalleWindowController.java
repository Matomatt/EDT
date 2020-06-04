package Controllers.dataModifierWindowsControllers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Controllers.ModifAdminPanelController;
import Salles.Salle;
import View.dataModifierWindows.addSalleWindow;

public class addSalleWindowController extends dataModifierController
{
	addSalleWindow window = null;
	
	public addSalleWindowController(ModifAdminPanelController panelController) {
		super(panelController);
	}
	
	@Override
	protected void add() {
		if (window.getCapacite() <= 0)
		{
			JOptionPane.showMessageDialog(window, "Impossible d'ajouter cette salle, la capacité doit être supérieure à 0 !");
			return;
		}

		panelController.addSalle(new Salle(window.getNom(), window.getCapacite(), window.getSite()));
		window.dispose();
	}

	@Override
	protected void modify() { }

	@Override
	public void setControlledView(JFrame window) {
		this.window = (addSalleWindow) window;
	}
}
