package UI_Elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class JSpoiler extends JPanel implements ActionListener
{
	private static final long serialVersionUID = -6607547661772347870L;
	
	private JButton arrowButton = new JButton(">");
	private Component toHide = null;
	
	public JSpoiler(Component toShow, Component toHide) 
	{
		this.toHide = toHide;
		
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.white);
        
		GridBagConstraints c = new GridBagConstraints();
    	c.insets = new Insets(0, 3, 3, 3);
		c.gridx = 0;
		c.gridy = 0;
		
		arrowButton.setFont(new Font(arrowButton.getFont().getName(), Font.PLAIN, arrowButton.getFont().getSize()*3/2));
		arrowButton.setBackground(Color.white);
		arrowButton.setBorder(null);
		arrowButton.addActionListener(this);
		this.add(arrowButton, c);
		
		c.insets = new Insets(0, 0, 0, 0);
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		this.add(toShow, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		this.add(toHide, c);
		toHide.setVisible(false);
		
        this.setVisible(true);
        validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		toHide.setVisible(!toHide.isVisible());
		arrowButton.setText((toHide.isVisible())?"v":">");
	}

}
