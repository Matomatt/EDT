package Controllers.dataModifierWindowsControllers;

import java.awt.event.ActionEvent;
import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Controllers.ModifAdminPanelController;
import Seances.Seance;
import View.dataModifierWindows.addSeanceWindow;

public class addSeanceWindowController extends dataModifierController
{
	addSeanceWindow window = null;
	
	public addSeanceWindowController(ModifAdminPanelController panelController) {
		super(panelController);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass() == JComboBox.class)
			window.ChangeListEnseignant();
	}
	
	@Override
	protected void add() {
		Seance seance = null;
		try {
			seance = new Seance(window.getSemaine(), window.getDate(), window.getHeureDebut(), window.getHeureFin(), window.getEtat(), window.getCours(), 
					window.getTypeDeCours(), window.getGroupes(), window.getEnseignants(), window.getSalles());
		} catch (NumberFormatException | ParseException e) {
			e.printStackTrace();
			return;
		}
		
		if (window.getUser().ListeSeances().seancePossible(seance))
		{
			panelController.addSeance(seance);
			window.dispose();
		}
		else
			JOptionPane.showMessageDialog(window, "Impossible d'ajouter cette séance");
	}

	@Override
	protected void modify() { }
	
	@Override
	public void setControlledView(JFrame window) {
		this.window = (addSeanceWindow) window;
		
	}
}
