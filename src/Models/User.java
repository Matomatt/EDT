package Models;

import Utilitaires.UserNotFoundException;

public class User {

	enum UserType { Eleve, Enseignant, Referant_pedagogique, Admin, none; };
	
	private String name = "";
	private int ID = 0;
	private UserType type = UserType.none;
	
	
	public User(String login, String password) throws UserNotFoundException
	{
		//Connection a la bdd et check si login et pw correct
		boolean loginExists = false;
		if (!loginExists)
			throw new UserNotFoundException("Login or password incorrect for user " + login);
	}
	
	public String Name() { return name; }
	public UserType Type() { return type; }
}
