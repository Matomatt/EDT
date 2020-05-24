package Utilisateurs;

import java.util.ArrayList;
import java.util.List;

import Donnees.Donnee;
import Groupes.Groupe;

public class Utilisateur {
	private int ID = 0;
	private String email = "";
	private String nom = "";
	private String prenom = "";
	private User.UserType type = User.UserType.none;
	
	private List<Donnee> coursDonnesSiEnseignant = new ArrayList<Donnee>();
	private int numeroEtudiant = 0;
	private Groupe groupeSiEtudiant = null;
	
	private void Init(int _ID, String _email, String _nom, String _prenom, int _type)
	{
		ID = _ID;
		setEmail(_email);
		setNom(_nom);
		setPrenom(_prenom);
		
		switch (_type)
		{
			case 1: type = User.UserType.Admin; break;
			case 2: type = User.UserType.Referent_pedagogique; break;
			case 3: type = User.UserType.Enseignant; break;
			case 4: type = User.UserType.Etudiant; break;
		}
	}
	
	Utilisateur(int _ID, String _email, String _nom, String _prenom, int _type) {
		Init(_ID, _email, _nom, _prenom, _type);
	}
	
	Utilisateur(int _ID, String _email, String _nom, String _prenom, int _type, List<Donnee> cours) {
		Init(_ID, _email, _nom, _prenom, _type);
		
		if (type == User.UserType.Enseignant)
			coursDonnesSiEnseignant = cours;
	}
	
	Utilisateur(int _ID, String _email, String _nom, String _prenom, int _type, Groupe groupe, int numero) {
		Init(_ID, _email, _nom, _prenom, _type);
		
		if (type == User.UserType.Etudiant)
		{
			groupeSiEtudiant = groupe;
			setNumeroEtudiant(numero);
		}
			
	}
	
	public Utilisateur(String _email, String _nom, String _prenom, int _type) {
		Init(0, _email, _nom, _prenom, _type);
	}
	
	public Utilisateur(String _email, String _nom, String _prenom, int _type, List<Donnee> cours) {
		Init(0, _email, _nom, _prenom, _type);
		
		if (type == User.UserType.Enseignant)
			coursDonnesSiEnseignant = cours;
	}
	
	public Utilisateur(String _email, String _nom, String _prenom, int _type, Groupe groupe, int numero) {
		Init(0, _email, _nom, _prenom, _type);
		
		if (type == User.UserType.Etudiant)
		{
			groupeSiEtudiant = groupe;
			setNumeroEtudiant(numero);
		}
	}

	public int getID() { return ID; }
	public String getEmail() { return email; }
	public String getNom() { return nom; }
	public String getPrenom() { return prenom; }
	public User.UserType getType() { return type; }
	public List<Donnee> getCoursDonnes() { return (type == User.UserType.Enseignant?coursDonnesSiEnseignant:null); }
	public Groupe getGroupe() { return (type == User.UserType.Etudiant?groupeSiEtudiant:null); }
	public int getNumeroEtudiant() { return numeroEtudiant; }

	public void setEmail(String email) { this.email = email; }
	public void setNom(String nom) { this.nom = nom; }
	public void setPrenom(String prenom) { this.prenom = prenom; }
	public void setCoursDonnes(List<Donnee> cours) { coursDonnesSiEnseignant = (type == User.UserType.Enseignant?cours:null); }
	public void addCours(Donnee cours) { if (type == User.UserType.Enseignant) coursDonnesSiEnseignant.add(cours); }
	public void removeCours(Donnee cours) { if (type == User.UserType.Enseignant) coursDonnesSiEnseignant.remove(cours); }
	public void setGroupe(Groupe groupe) { groupeSiEtudiant = (type == User.UserType.Etudiant?groupe:null); }
	public void setNumeroEtudiant(int numeroEtudiant) { this.numeroEtudiant = numeroEtudiant; }
	
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

	
}
