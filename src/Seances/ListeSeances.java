package Seances;

import java.util.List;

import Donnees.ListeDonnees;

public interface ListeSeances {
	public List<Seance> getAll();
	public void CompleterCours(ListeDonnees cours);
	public void CompleterTypes(ListeDonnees types);
}
