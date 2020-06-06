/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import java.awt.Component;
 
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * @author BOCHER, CADOT et GAUTIER 
 * classe qui hérite de DefaultTableCellRenderer et qui mets en place la couleur 
 */
public class ColorRenderer extends DefaultTableCellRenderer 
{
        /**
        * serialVersionUID : clé de hachage de la classe
        */
	private static final long serialVersionUID = 4020747767803253403L; 
 
    /**
    * Méthode qui retourne la cellule du tableau     
    * @param table
    * @param value
    * @param isSelected
    * @param hasFocus
    * @param row
    * @param column
    * @return cell
    */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {
        Component cell = super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
        
        if(column == 0)
        {
            cell.setBackground(new Color(238,238,238));
            cell.setForeground(Color.BLACK);
        }
 
        return cell;
    }
}