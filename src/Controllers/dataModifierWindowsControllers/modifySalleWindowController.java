package Controllers.dataModifierWindowsControllers;

import javax.swing.JFrame;
import Controllers.ModifAdminPanelController;
import Salles.Salle;
import View.dataModifierWindows.addSalleWindow;

public class modifySalleWindowController extends dataModifierController 
{
	addSalleWindow window = null;
	Salle salle = null;
	
	public modifySalleWindowController(ModifAdminPanelController panelController, Salle salle)
	{
		super(panelController);
		this.salle = salle;
	}
	
	@Override
	protected void add() { }

	@Override
	protected void modify() {
		Salle salle = null;
		
		try {
			salle = new Salle(window.getNom(), window.getCapacite(), window.getSite());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		this.salle.copy(salle);
		panelController.updateSalle(this.salle);
		window.dispose();
	}
	
	@Override
	public void setControlledView(JFrame window) {
		this.window = (addSalleWindow) window;
		
		this.window.fillFields(salle);
	}
}
