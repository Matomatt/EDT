package View;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 * @author BOCHER, CADOT et GAUTIER 
 * classe qui hérite de JTextArea et qui implemente la classe TableCellRenderer 
 */
public class TextAreaRenderer extends JTextArea implements TableCellRenderer 
{
        /**
        * serialVersionUID : clé de hachage de la classe
        */
	private static final long serialVersionUID = -4904333864225399155L;

        /**
        * Constructeur 
        */
	public TextAreaRenderer() {
	    setLineWrap(true);
	    setWrapStyleWord(true);
	  }

        /**
        * Constructeur 
        * @param jTable
        * @param obj
        * @param isSelected
        * @param hasFocus
        * @param row
        * @param column
        * @return this, le component
        */
	@Override
	public Component getTableCellRendererComponent(JTable jTable, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
		setText((String)obj);
		return this;
	}
}