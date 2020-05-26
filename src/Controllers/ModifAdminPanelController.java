package Controllers;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import Controllers.dataModifierWindowsControllers.addSeanceWindowController;
import Controllers.dataModifierWindowsControllers.addUtilisateurWindowController;
import Seances.Seance;
import UI_Elements.Button;
import UI_Elements.JScrollListe;
import Utilisateurs.Utilisateur;
import Utilisateurs.User.UserType;
import View.ModifAdminPanel;
import View.dataModifierWindows.addSeanceWindow;
import View.dataModifierWindows.addUtilisateurWindow;

public class ModifAdminPanelController extends Controller
{
	public ModifAdminPanelController() {
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().getClass() == Button.class)
		{
			Button bt = (Button) e.getSource();
			//System.out.println(bt.getName());
			switch (bt.getName()) 
			{
				case "btAjouter":
					if (((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getObjectClass() == Seance.class)
						new addSeanceWindow(Panel().getUser(), new addSeanceWindowController(this));
					if (((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getObjectClass() == Utilisateur.class)
						new addUtilisateurWindow(Panel().getUser(), new addUtilisateurWindowController(this));
					 break;
				case "btSupprimer": for (Object o : ((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getJList().getSelectedValuesList()) { Supprimer(o); } break;
				case "btModifier":  break;

				default: break;
			}
		}
	}
	
	private void Supprimer(Object o)
	{
		((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).Delete(o);
		if (o.getClass() == Seance.class)
			Panel().getUser().ListeSeances().Delete((Seance) o);
		if (o.getClass() == Utilisateur.class)
			Panel().getUser().ListeUtilisateurs().Delete((Utilisateur) o);
		if (o.getClass() == Seance.class)
			Panel().getUser().ListeSeances().Delete((Seance) o);
		if (o.getClass() == Seance.class)
			Panel().getUser().ListeSeances().Delete((Seance) o);
		if (o.getClass() == Seance.class)
			Panel().getUser().ListeSeances().Delete((Seance) o);
	}
	
	private ModifAdminPanel Panel()
	{
		return (ModifAdminPanel) panel;
	}

	public void addSeance(Seance seance) {
		Panel().getUser().ListeSeances().addSeance(seance);
		if (((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getObjectClass() == Seance.class)
			((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).addObject(seance);
	}

	public void addUtilisateur(Utilisateur utilisateur) 
	{
		if (utilisateur.getType() == UserType.Etudiant)
		{
			utilisateur.setNumeroEtudiant(Panel().getUser().ListeUtilisateurs().getHighestStudentNumber()+1);
			JOptionPane.showMessageDialog(panel, "This student number is : "  + utilisateur.getNumeroEtudiant());
		}
		
		Panel().getUser().ListeUtilisateurs().addUtilisateur(utilisateur);
		if (((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getObjectClass() == Utilisateur.class)
			((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).addObject(utilisateur);
	}
}
