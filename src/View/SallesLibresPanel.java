package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Controllers.Controller;
import Salles.Salle;
import Utilisateurs.User;

public class SallesLibresPanel extends Panel
{
	private static final long serialVersionUID = -4030708239535614196L;
	
	/***
	 * 
	 * @param user
	 * @param controller
	 */
    public SallesLibresPanel(User user, Controller controller) 
    {
    	super(user, controller);
    	
        initComponents();
    }

    private void initComponents() 
    {
    	
    	JPanel boxPanel = new JPanel();
    	boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.PAGE_AXIS));
    	
    	java.util.Date today = new java.util.Date();
    	
		//try { today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-05-25 15:00:00"); } catch (ParseException e) { e.printStackTrace(); }
    	
    	Date day = new Date(today.getTime());
    	
    	int week = Integer.parseInt(new SimpleDateFormat("w").format(today));
    	
    	for (Entry<String, LinkedHashMap<Time[], List<Salle>>> dayEntry : user.ListeSalles().getSallesLibresAtWeekStartingFrom(week, day).entrySet()) 
    	{
    		JLabel dayLabel = new JLabel(dayEntry.getKey().substring(0, 1).toUpperCase() + dayEntry.getKey().substring(1));
    		dayLabel.setFont(new Font(dayLabel.getFont().getName(), Font.PLAIN, dayLabel.getFont().getSize()*3));
    		boxPanel.add(dayLabel);
    		
			for (Entry<Time[], List<Salle>> seanceEntry : dayEntry.getValue().entrySet()) 
			{
				JLabel creneauLabel = new JLabel(seanceEntry.getKey()[0] + " - " + seanceEntry.getKey()[1] + " ("+seanceEntry.getValue().size()+")");
				creneauLabel.setFont(new Font(creneauLabel.getFont().getName(), Font.PLAIN, creneauLabel.getFont().getSize()*2));
				boxPanel.add(creneauLabel);
				
				//JComboBox<Object> comboBox = new JComboBox<Object>(seanceEntry.getValue().toArray());
				boxPanel.add(new JComboBox<Object>(seanceEntry.getValue().toArray()));
				/* for (Salle salle : seanceEntry.getValue()) {
					boxPanel.add(new JLabel(salle.toString()));
				}*/
			}
		}
    	
    	GridBagConstraints c = new GridBagConstraints();
    	
    	c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
    	
		JScrollPane scroll = new JScrollPane(boxPanel);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scroll.setBackground(Color.white);
    	this.add(scroll, c);
    }
}
