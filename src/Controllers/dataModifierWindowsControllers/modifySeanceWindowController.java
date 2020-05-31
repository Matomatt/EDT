package Controllers.dataModifierWindowsControllers;

import java.awt.event.ActionEvent;
import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import Controllers.ModifAdminPanelController;
import Seances.Seance;
import View.dataModifierWindows.addSeanceWindow;

public class modifySeanceWindowController extends dataModifierController 
{
	addSeanceWindow window = null;
	Seance seance = null;
	
	public modifySeanceWindowController(ModifAdminPanelController panelController, Seance seance) 
	{
		super(panelController);
		this.seance = seance;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource().getClass() == JComboBox.class)
			window.ChangeListEnseignant();
	}
	
	@Override
	protected void add() { }

	@Override
	protected void modify() {
		Seance seance = null;
		try {
			seance = new Seance(window.getSemaine(), window.getDate(), window.getHeureDebut(), window.getHeureFin(), window.getEtat(), window.getCours(), 
					window.getTypeDeCours(), window.getGroupes(), window.getEnseignants(), window.getSalles());
		} catch (NumberFormatException | ParseException e) {
			e.printStackTrace();
			return;
		}
		
		this.seance.copy(seance);
		panelController.updateSeance(this.seance);
		window.dispose();
	}
	
	@Override
	public void setControlledView(JFrame window) {
		this.window = (addSeanceWindow) window;
		
		this.window.fillFields(seance);
	}
}
