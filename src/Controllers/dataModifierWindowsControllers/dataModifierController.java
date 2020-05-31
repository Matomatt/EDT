package Controllers.dataModifierWindowsControllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import Controllers.ModifAdminPanelController;
import UI_Elements.Button;

public abstract class dataModifierController implements ActionListener {

	protected ModifAdminPanelController panelController = null;
	
	public dataModifierController(ModifAdminPanelController panelController) {
		this.panelController = panelController;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().getClass() == Button.class)
		{
			Button bt = (Button) e.getSource();
			//System.out.println(bt.getName());
			switch (bt.getName())
			{
				case "btAdd": add(); modify(); break;
				
				default: break;
			}
		}
	}

	protected abstract void add();
	protected abstract void modify();

	public abstract void setControlledView(JFrame window);
}
