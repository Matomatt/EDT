package Utilisateurs;

import Utilisateurs.ConnectionViaUser.UserType;

public class Utilisateur {
	private int ID = 0;
	private String email = "";
	private String nom = "";
	private String prenom = "";
	private UserType type = UserType.none;
	
	Utilisateur(int _ID, String _email, String _nom, String _prenom, int _type) {
		ID = _ID;
		setEmail(_email);
		setNom(_nom);
		setPrenom(_prenom);
		
		switch (_type)
		{
			case 1: type = UserType.Admin; break;
			case 2: type = UserType.Referant_pedagogique; break;
			case 3: type = UserType.Enseignant; break;
			case 4: type = UserType.Etudiant; break;
		}
	}

	public String getEmail() { return email; }
	public String getNom() { return nom; }
	public String getPrenom() { return prenom; }
	public UserType getType() { return type; }
	
	public void setEmail(String email) { this.email = email; }
	public void setNom(String nom) { this.nom = nom; }
	public void setPrenom(String prenom) { this.prenom = prenom; }
	
	@Override
	public String toString() {
		return type + " " + prenom + " " + nom + " (" + email + ")";
	}
}
