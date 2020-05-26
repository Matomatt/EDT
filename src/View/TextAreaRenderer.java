package View;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class TextAreaRenderer extends JTextArea implements TableCellRenderer 
{
	private static final long serialVersionUID = -4904333864225399155L;

public TextAreaRenderer() {
    setLineWrap(true);
    setWrapStyleWord(true);
  }

  @Override
  public Component getTableCellRendererComponent(JTable jTable,
      Object obj, boolean isSelected, boolean hasFocus, int row,
      int column) {
    setText((String)obj);
    return this;
  }
}