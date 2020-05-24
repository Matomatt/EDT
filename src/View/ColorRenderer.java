/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Seances.Seance;
import Utilisateurs.User;
import java.awt.Color;
import java.awt.Component;
 
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author Célia BOCHER
 */
 

 
public class ColorRenderer extends DefaultTableCellRenderer {

    User user = null;
 
 
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
        String cours = null;


        if(column == 0)
        {
            cell.setBackground(new Color(238,238,238));
            cell.setForeground(Color.BLACK);
        }
 
        return cell;
    }



    private Color Color(int i, int i0, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}