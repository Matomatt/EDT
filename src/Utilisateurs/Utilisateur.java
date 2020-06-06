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
	
	/**
	 * Initialise les attributs de l'utilisateur avec les infos indiquées
	 * @param _ID
	 * @param _email
	 * @param _password
	 * @param _nom
	 * @param _prenom
	 * @param _type
	 */
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
	
	/**
	 * Constructeur dédié a l'implémentation DAO (admin et referant)
	 * @param _ID
	 * @param _email
	 * @param _password
	 * @param _nom
	 * @param _prenom
	 * @param _type
	 */
	Utilisateur(int _ID, String _email, String _password, String _nom, String _prenom, int _type) {
		Init(_ID, _email, _password, _nom, _prenom, _type);
	}
	
	/**
	 * Constructeur dédié a l'implémentation DAO (enseignant)
	 * @param _ID
	 * @param _email
	 * @param _password
	 * @param _nom
	 * @param _prenom
	 * @param _type
	 * @param cours
	 */
	Utilisateur(int _ID, String _email, String _password, String _nom, String _prenom, int _type, List<Donnee> cours) {
		Init(_ID, _email, _password, _nom, _prenom, _type);
		
		if (type != User.UserType.Etudiant)
			coursDonnesSiEnseignant = cours;
	}
	
	/**
	 * Constructeur dédié a l'implémentation DAO (etudiant)
	 * @param _ID
	 * @param _email
	 * @param _password
	 * @param _nom
	 * @param _prenom
	 * @param _type
	 * @param groupe
	 * @param numero
	 */
	Utilisateur(int _ID, String _email, String _password, String _nom, String _prenom, int _type, Groupe groupe, int numero) {
		Init(_ID, _email, _password, _nom, _prenom, _type);
		
		if (type == User.UserType.Etudiant)
		{
			groupeSiEtudiant = groupe;
			numeroEtudiant = numero;
		}
			
	}
	
	/**
	 * Constructeur public (admin et referant)
	 * @param _email
	 * @param _password
	 * @param _nom
	 * @param _prenom
	 * @param _type
	 */
	public Utilisateur(String _email, String _password, String _nom, String _prenom, int _type) {
		Init(0, _email, _password, _nom, _prenom, _type);
	}
	
	/**
	 * Constructeur public (enseignant)
	 * @param _email
	 * @param _password
	 * @param _nom
	 * @param _prenom
	 * @param _type
	 * @param cours
	 */
	public Utilisateur(String _email, String _password, String _nom, String _prenom, int _type, List<Donnee> cours) {
		Init(0, _email, _password, _nom, _prenom, _type);
		
		if (type == UserType.Enseignant || type == UserType.Referent_pedagogique)
			coursDonnesSiEnseignant = cours;
	}
	
	/**
	 * Constructeur public (etudiant)
	 * @param _email
	 * @param _password
	 * @param _nom
	 * @param _prenom
	 * @param _type
	 * @param groupe
	 * @param numero
	 */
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
	
	/**
	 * Check si tous les attributs (sauf l'id) sont égaux
	 */
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

	/**
	 * Copie les attributs de l'utilisateur indiqué
	 * @param utilisateur
	 */
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
