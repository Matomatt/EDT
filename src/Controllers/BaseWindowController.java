package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import View.BaseWindow;
import View.EDT_ListePanel;
import View.EdtGrillePanel;
import View.ModifAdminPanel;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseWindowController implements ActionListener {
	
	BaseWindow baseWindow = null;
	public BaseWindowController(BaseWindow baseWindow) {
		this.baseWindow = baseWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel page = null;
    	
    	if (!baseWindow.getPages().containsKey(((JButton)e.getSource()).getText()))
    	{
    		if (e.getSource().getClass() == JButton.class)
    		{
    			JButton bt = (JButton) e.getSource();
    			//System.out.println(bt.getName());
    			switch (bt.getName()) 
    			{
					case "bt1": {
                                    try {
                                        page = new EdtGrillePanel(baseWindow.getUser());
                                    } catch (ParseException ex) {
                                        Logger.getLogger(BaseWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
break;

					case "bt11": page = new EDT_ListePanel(baseWindow.getUser()); break;
					case "bt3": Controller controller = new ModifAdminPanelController();
								page = new ModifAdminPanel(baseWindow.getUser(), controller); 
								break;
	
					default: break;
				}
    		}
    		
    		if (page != null)
    			baseWindow.getPages().put(((JButton)e.getSource()).getText(), page);
    	}
    	else
    		page = baseWindow.getPages().get(((JButton)e.getSource()).getText());
    	
    	//System.out.println(page);
    	if (page != null)
    		baseWindow.SwitchPage(page);
		
	}
}
