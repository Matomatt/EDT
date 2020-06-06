package Controllers;

import java.awt.event.ActionEvent;

import UI_Elements.Button;
import View.RecapPanel;

public class ReportingPanelController extends Controller {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		
		if (e.getSource().getClass() == Button.class) if (((Button) e.getSource()).getName() == "btRefresh")
				((RecapPanel) panel).refreshTable();
	}

}
