package Controllers.dataModifierWindowsControllers;

import javax.swing.JFrame;

import Controllers.ModifAdminPanelController;
import Donnees.Donnee;
import View.dataModifierWindows.addDonneeWindow;

public class addDonneeWindowController extends dataModifierController
{
	addDonneeWindow window = null;
	
	public addDonneeWindowController(ModifAdminPanelController panelController) {
		super(panelController);
	}

	@Override
	protected void add() {
		panelController.addDonnee(new Donnee(window.getValue()), window.getTypeDonnee());
		window.dispose();
	}

	@Override
	protected void modify() { }

	@Override
	public void setControlledView(JFrame window) {
		this.window = (addDonneeWindow) window;
	}
}
