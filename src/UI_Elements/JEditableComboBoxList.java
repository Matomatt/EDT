package UI_Elements;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class JEditableComboBoxList extends JPanel 
{
	private static final long serialVersionUID = 387607015065088919L;
	
	private List<JComboBox<Object>> comboBoxs = new ArrayList<JComboBox<Object>>();
	private Object[] list;
	private JEditableComboBoxList thisComboBoxList = this;
	private String objectsNames = "";
	
	ActionListener controller = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource().getClass() == XButton.class)
			{
    			XButton bt = (XButton) e.getSource();
    			thisComboBoxList.remove(bt);
    			thisComboBoxList.remove(bt.getComboBox());
    			comboBoxs.remove(bt.getComboBox());
    			bt=null;
    			thisComboBoxList.validate();
    			thisComboBoxList.getParent().validate();
			}
			else if (e.getSource().getClass() == Button.class)
			{
				addComboBox();
			}
		}
	};
	
	public JEditableComboBoxList(Object[] list, String objectsNames) 
	{
		this.setLayout(new GridBagLayout());
		this.list = list;
		this.objectsNames = objectsNames;
		
		addAddButton();
	}
	
	private void addAddButton()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		Button addButton = new Button("btAdd", "Add " + objectsNames, controller);
		addButton.setFont( new Font(addButton.getFont().getName(), Font.PLAIN, addButton.getFont().getSize()/2) );
		add(addButton, c);
	}
	
	private JComboBox<Object> addComboBox()
	{
		JComboBox<Object> comboBox = new JComboBox<Object>(list);
		comboBoxs.add(comboBox);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = comboBoxs.size();
		c.gridwidth = 1;
		this.add(comboBox, c);
		
		c.gridx = 1;
		this.add(new XButton(comboBox, controller), c);
		
		validate();
		getParent().validate();
		
		return comboBox;
	}

	public List<Object> getSelectedItems() 
	{
		List<Object> selectedItems = new ArrayList<Object>();
		
		for (JComboBox<Object> comboBox : comboBoxs)
			selectedItems.add(comboBox.getSelectedItem());
		
		return selectedItems;
	}

	public void setSelectedItems(Object[] array) 
	{
		
		thisComboBoxList.removeAll();
		comboBoxs.removeAll(comboBoxs);
		
		addAddButton();
		
		for (Object object : array) {
			addComboBox().setSelectedItem(object);
		}
	}

}
