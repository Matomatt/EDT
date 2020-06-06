package Controllers;

import java.awt.event.ActionEvent;

import Donnees.Donnee;
import Groupes.Groupe;
import Salles.Salle;
import Utilisateurs.Utilisateur;
import View.EdtGrillePanel;

/**
 * Controller de l'emploi du temps sous forme de grille
 */
public class EdtGrillePanelController extends Controller {
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		EdtGrillePanel pan = (EdtGrillePanel)panel;
		
		pan.removeAll();
		
		switch (pan.getListeSelectedItem().toString()) 
		{
            case "Mon EDT": pan.display_courses(panel.getUser().ListeSeances().getByUtilisateurAtWeek(panel.getUser().getUtilisateurConnecte(), pan.getSemaine()));
                    break;
                    
			case "Salle": pan.sallesAddToComboBox(); 
			pan.display_courses(panel.getUser().ListeSeances().getBySalleAtWeek((Salle) pan.getSelectedItem(), pan.getSemaine()));
                        break;
			
			case "Enseignant": pan.nomsAddToComboBox(); 
			pan.display_courses(panel.getUser().ListeSeances().getByUtilisateurAtWeek((Utilisateur) pan.getSelectedItem(), pan.getSemaine()));
                        break;
			
			case "Classe": pan.classesAddToComboBox(); 
			pan.display_courses(panel.getUser().ListeSeances().getByGroupeAtWeek((Groupe) pan.getSelectedItem(), pan.getSemaine()));
                        break;
			
			case "Promo": pan.promosAddToComboBox();
			pan.display_courses(panel.getUser().ListeSeances().getByPromoAtWeek((Donnee) pan.getSelectedItem(), pan.getSemaine()));
                        break;
					
			default: break;
		}
		
		pan.repaintAll();	
	}
}
