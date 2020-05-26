package Controllers.dataModifierWindowsControllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import Controllers.ModifAdminPanelController;
import Seances.Seance;
import UI_Elements.Button;
import View.dataModifierWindows.addSeanceWindow;

public class addSeanceWindowController implements ActionListener
{
	addSeanceWindow window = null;
	ModifAdminPanelController panelController = null;
	
	public addSeanceWindowController(ModifAdminPanelController panelController) {
		this.panelController = panelController;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass() == Button.class)
		{
			Button bt = (Button) e.getSource();
			//System.out.println(bt.getName());
			switch (bt.getName())
			{
				case "btAdd": addSeance(); break;
				
				default: break;
			}
		}
		else if (e.getSource().getClass() == JComboBox.class)
			window.ChangeListEnseignant();
		
	}
	
	private void addSeance() {
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

	public void setControlledView(addSeanceWindow addSeanceWindow) {
		window = addSeanceWindow;
	}

}
