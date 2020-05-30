package Controllers.dataModifierWindowsControllers;

import javax.swing.JFrame;

import Controllers.ModifAdminPanelController;
import Groupes.Groupe;
import View.dataModifierWindows.addGroupeWindow;

public class addGroupeWindowController extends dataModifierController
{
	addGroupeWindow window = null;
	
	public addGroupeWindowController(ModifAdminPanelController panelController) {
		super(panelController);
	}
	
	@Override
	protected void add() {
		panelController.addGroupe(new Groupe(window.getNom(), window.getPromotion()));
		window.dispose();
	}

	@Override
	protected void modify() { }
	
	@Override
	public void setControlledView(JFrame window) {
		this.window = (addGroupeWindow) window;
		
	}
}
