package Controllers;

import java.awt.event.ActionEvent;
import Seances.Seance;
import UI_Elements.Button;
import UI_Elements.JScrollListe;
import Utilisateurs.Utilisateur;
import View.ModifAdminPanel;
import View.dataModifierWindows.addSeanceWindow;

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
				case "btAjouter": new addSeanceWindow(Panel().getUser(), new addSeanceWindowController(this)); break;
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
}
