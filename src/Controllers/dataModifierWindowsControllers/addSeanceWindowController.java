package Controllers.dataModifierWindowsControllers;

import java.awt.event.ActionEvent;
import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Controllers.ModifAdminPanelController;
import Groupes.Groupe;
import Salles.Salle;
import Seances.Seance;
import Utilisateurs.Utilisateur;
import View.dataModifierWindows.addSeanceWindow;

public class addSeanceWindowController extends dataModifierController
{
	addSeanceWindow window = null;
	
	public addSeanceWindowController(ModifAdminPanelController panelController) {
		super(panelController);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
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
		
		String error = "";
		int nbEleve = 0; int capacite = 0;
		
		for (Groupe groupe : seance.getGroupes()) {
			if (!window.getUser().ListeSeances().groupeLibre(groupe, seance.getDebut(), seance.getFin(), seance.getDate()))
				error += groupe.toString() + " pas libre sur cette période.\n";
			nbEleve += window.getUser().ListeGroupes().getNombreEtudiants(groupe);
		}
		for (Utilisateur enseignant : seance.getEnseignants()) {
			if (!window.getUser().ListeSeances().utilisateurLibre(enseignant, seance.getDebut(), seance.getFin(), seance.getDate()))
				error += enseignant.toString() + " pas libre sur cette période.\n";
		}
		for (Salle salle : seance.getSalles()) {
			if (!window.getUser().ListeSeances().salleLibre(salle, seance.getDebut(), seance.getFin(), seance.getDate()))
				error += salle.toString() + " pas libre sur cette période.\n";
			capacite+=salle.getCapacite();
		}
		
		if (capacite < nbEleve)
			JOptionPane.showMessageDialog(window, "Attention il n'y a pas assez de place pour tous les étudiants.");
		if (!error.isEmpty())
		{
			JOptionPane.showMessageDialog(window, error);
			return;
		}
		
		panelController.addSeance(seance);
		window.dispose();
	}

	@Override
	protected void modify() { }
	
	@Override
	public void setControlledView(JFrame window) {
		this.window = (addSeanceWindow) window;
		
	}
}
