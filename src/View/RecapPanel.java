/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controllers.Controller;
import Utilisateurs.User;

public class RecapPanel extends Panel
{
	private static final long serialVersionUID = -4030708239535614196L;
	
	/***
	 * 
	 * @param user
	 * @param controller
	 */
    public RecapPanel(User user, Controller controller) 
    {
    	super(user, controller);
    	
        initComponents();
    }

    private void initComponents() 
    {
    }
}
