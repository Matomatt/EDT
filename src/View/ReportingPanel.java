/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controllers.Controller;
import Utilisateurs.User;


public class ReportingPanel extends Panel
{
	private static final long serialVersionUID = -3479156065256517426L;

	/**
     * Creates new form Reporting
     */
    public ReportingPanel(User user, Controller controller) {
    	super(user, controller);
    	
        initComponents();
    }
    private void initComponents() 
    {
    }
}
