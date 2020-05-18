package Utilisateurs;

import Utilisateurs.ConnectionViaUser.UserType;

import Donnees.ListeDonnees;

public interface User {
	public String Name();
	public UserType Type();
	
	public ListeDonnees ListeType_cours();
	public ListeDonnees ListeCours();
	public ListeDonnees ListeSite();
	public ListeDonnees ListePromotion();
}
