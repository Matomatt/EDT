package Controllers.dataModifierWindowsControllers;

import javax.swing.JFrame;
import Controllers.ModifAdminPanelController;
import Donnees.Donnee;
import View.dataModifierWindows.addDonneeWindow;

public class modifyDonneeWindowController extends dataModifierController 
{
	addDonneeWindow window = null;
	Donnee donnee = null;
	
	public modifyDonneeWindowController(ModifAdminPanelController panelController, Donnee donnee)
	{
		super(panelController);
		this.donnee = donnee;
	}
	
	@Override
	protected void add() { }

	@Override
	protected void modify() {
		Donnee donnee = null;
		
		try {
			donnee = new Donnee(window.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		this.donnee.copy(donnee);
		panelController.updateDonnee(this.donnee, window.getTypeDonnee());
		window.dispose();
	}
	
	@Override
	public void setControlledView(JFrame window) {
		this.window = (addDonneeWindow) window;
		
		this.window.fillFields(donnee);
	}
}
