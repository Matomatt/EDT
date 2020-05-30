package View.dataModifierWindows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import Controllers.dataModifierWindowsControllers.dataModifierController;
import Donnees.Donnee;
import Groupes.Groupe;
import UI_Elements.Button;
import Utilisateurs.User;

public class addGroupeWindow extends JFrame
{
	private static final long serialVersionUID = -4570892602269961560L;
	
	User user = null;
	dataModifierController controller = null;
	
	private JTextField nomTextField = new JTextField();
	private JComboBox<Object> promotionComboBox = null;

	public addGroupeWindow(User user, dataModifierController controller) {
		this.user = user;
		this.controller = controller;

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setName("Adding a groupe");
		this.setLayout(new GridBagLayout());
		this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/3, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/4);
		
		initComponents();
		
		this.controller.setControlledView(this);
		
		this.setVisible(true);
		this.validate();
	}
	
	private void initComponents()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 10, 0, 0);
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		String[] componentsNames = {"Nom", "Promotion"};
		for (String name : componentsNames) {
			this.add(new JLabel(name), constraints);
			constraints.gridy+=1;
		}
		
		promotionComboBox = new JComboBox<Object>( user.ListePromotion().getAll().toArray() );
		
		constraints.insets = new Insets(5, 0, 5, 0);
		constraints.gridx = 1;
		constraints.gridy = 0;
		this.add(nomTextField, constraints);
		constraints.gridy = 1;
		this.add(promotionComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		this.add(new Button("btAdd", "Add", controller), constraints);
	}
	
	public String getNom() {
		return nomTextField.getText();
	}
	
	public Donnee getPromotion() {
		return (Donnee) promotionComboBox.getSelectedItem();
	}

	public void fillFields(Groupe groupe) {
		nomTextField.setText(groupe.getName());
		promotionComboBox.setSelectedItem(groupe.getPromotion());
	}
}
