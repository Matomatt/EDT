package UI_Elements;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class XButton extends Button 
{
	private static final long serialVersionUID = -7450551516561990592L;
	
	JComboBox<Object> comboBox = null;
	XButton thisButton = this;
	
	public XButton(JComboBox<Object> comboBox, ActionListener controller) {
		super("btX", "X", controller);
		this.setFont( new Font(this.getFont().getName(), Font.PLAIN, this.getFont().getSize()/2) );
		this.comboBox = comboBox;
	}
	
	public JComboBox<Object> getComboBox()
	{
		return comboBox;
	}
}
