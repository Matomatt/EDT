package Utilisateurs;

import java.util.ArrayList;
import java.util.List;

import Donnees.Donnee;
import Groupes.Groupe;
import Utilisateurs.User.UserType;

public class Utilisateur {
	private int ID = 0;
	private String email = "";
	private String password = "";
	private String nom = "";
	private String prenom = "";
	private User.UserType type = User.UserType.none;
	
	private List<Donnee> coursDonnesSiEnseignant = new ArrayList<Donnee>();
	private int numeroEtudiant = 0;
	private Groupe groupeSiEtudiant = null;
	
	private void Init(int _ID, String _email, String _password, String _nom, String _prenom, int _type)
	{
		ID = _ID;
		email = _email;
		password = _password;
		nom = _nom;
		prenom = _prenom;
		
		switch (_type)
		{
			case 1: type = User.UserType.Admin; break;
			case 2: type = User.UserType.Referent_pedagogique; break;
			case 3: type = User.UserType.Enseignant; break;
			case 4: type = User.UserType.Etudiant; break;
		}
	}
	
	Utilisateur(int _ID, String _email, String _password, String _nom, String _prenom, int _type) {
		Init(_ID, _email, _password, _nom, _prenom, _type);
	}
	
	Utilisateur(int _ID, String _email, String _password, String _nom, String _prenom, int _type, List<Donnee> cours) {
		Init(_ID, _email, _password, _nom, _prenom, _type);
		
		if (type != User.UserType.Etudiant)
			coursDonnesSiEnseignant = cours;
	}
	
	Utilisateur(int _ID, String _email, String _password, String _nom, String _prenom, int _type, Groupe groupe, int numero) {
		Init(_ID, _email, _password, _nom, _prenom, _type);
		
		if (type == User.UserType.Etudiant)
		{
			groupeSiEtudiant = groupe;
			numeroEtudiant = numero;
		}
			
	}
	
	public Utilisateur(String _email, String _password, String _nom, String _prenom, int _type) {
		Init(0, _email, _password, _nom, _prenom, _type);
	}
	
	public Utilisateur(String _email, String _password, String _nom, String _prenom, int _type, List<Donnee> cours) {
		Init(0, _email, _password, _nom, _prenom, _type);
		
		if (type == UserType.Enseignant || type == UserType.Referent_pedagogique)
			coursDonnesSiEnseignant = cours;
	}
	
	public Utilisateur(String _email, String _password, String _nom, String _prenom, int _type, Groupe groupe, int numero) {
		Init(0, _email, _password, _nom, _prenom, _type);
		
		if (type == User.UserType.Etudiant)
		{
			groupeSiEtudiant = groupe;
			numeroEtudiant = numero;
		}
	}

	public int getID() { return ID; }
	public String getEmail() { return email; }
	public String getPassword() { return password; }
	public String getNom() { return nom; }
	public String getPrenom() { return prenom; }
	public User.UserType getType() { return type; }
	public List<Donnee> getCoursDonnes() { return coursDonnesSiEnseignant; }
	public Groupe getGroupe() { return (type == User.UserType.Etudiant?groupeSiEtudiant:null); }
	public int getNumeroEtudiant() { return numeroEtudiant; }

	void setID(int ID) { this.ID = ID; }
	public void setNumeroEtudiant(int numero) { numeroEtudiant = numero; }
	
	@Override
	public String toString() {
		String toReturn = type + " " + prenom + " " + nom + " (" + email + ")";
		if (type == User.UserType.Enseignant)
		{
			toReturn+="[";
			for (Donnee donnee : coursDonnesSiEnseignant)
				toReturn+=donnee+" ";
			toReturn+="]";
		}
		else if (type == User.UserType.Etudiant)
			toReturn += "[" + groupeSiEtudiant + "]";
		
		return  toReturn;
	}
	
	@Override
	public boolean equals(Object obj) {
		try { if (((Utilisateur) obj) == null) return false; }
		catch (Exception e) { return false; }
		
		Utilisateur utilisateur = (Utilisateur) obj;
		
		if (coursDonnesSiEnseignant.size() != utilisateur.getCoursDonnes().size())
			return false;
		
		if (groupeSiEtudiant == null && utilisateur.getGroupe() != null || groupeSiEtudiant != null && utilisateur.getGroupe() == null)
			return false;
		
		if (groupeSiEtudiant != null && utilisateur.getGroupe() != null)
			if (!groupeSiEtudiant.equals(utilisateur.getGroupe()))
				return false;

		return (email.contentEquals(utilisateur.getEmail()) && password.contentEquals(utilisateur.getPassword()) && 
				nom.contentEquals(utilisateur.getNom()) && prenom.contentEquals(utilisateur.getPrenom()) && numeroEtudiant == utilisateur.getNumeroEtudiant());
	}

	public void copy(Utilisateur utilisateur) {
		email = utilisateur.getEmail();
		password = utilisateur.getPassword();
		nom = utilisateur.getNom();
		prenom = utilisateur.getPrenom();
		type = utilisateur.getType();
		coursDonnesSiEnseignant = utilisateur.getCoursDonnes();
		groupeSiEtudiant = utilisateur.getGroupe();
		numeroEtudiant = utilisateur.getNumeroEtudiant();
	}
}
