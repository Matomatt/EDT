package Controllers.dataModifierWindowsControllers;

import javax.swing.JFrame;
import Controllers.ModifAdminPanelController;
import Groupes.Groupe;
import View.dataModifierWindows.addGroupeWindow;

public class modifyGroupeWindowController extends dataModifierController 
{
	addGroupeWindow window = null;
	Groupe groupe = null;
	
	public modifyGroupeWindowController(ModifAdminPanelController panelController, Groupe groupe)
	{
		super(panelController);
		this.groupe = groupe;
	}
	
	@Override
	protected void add() { }

	@Override
	protected void modify() {
		Groupe groupe = null;
		
		try {
			groupe = new Groupe(window.getNom(), window.getPromotion());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		this.groupe.copy(groupe);
		panelController.updateGroupe(this.groupe);
		window.dispose();
	}
	
	@Override
	public void setControlledView(JFrame window) {
		this.window = (addGroupeWindow) window;
		
		this.window.fillFields(groupe);
	}
}
