package View.DiagramPanels;

import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author BOCHER, CADOT et GAUTIER 
 * classe qui hérite de JPanel et qui créer la zone pour les statiqtiques
 */
public class XYAreaChartPanel extends JPanel 
{
        /**
        * serialVersionUID : clé de hachage de la classe
        */
	private static final long serialVersionUID = 5622443209287307211L;

        /**
         * Constructeur
         * @param title
         * @param xLabel
         * @param yLabel
         * @param map 
         */
	public XYAreaChartPanel(String title, String xLabel, String yLabel, Map<String, Double> map) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (Entry<String, Double> entry : map.entrySet()) {
			dataset.setValue(entry.getValue(), yLabel, entry.getKey());
		}

		JFreeChart chart = ChartFactory.createAreaChart(
                title,
                xLabel,
                yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                true
        );
		
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.white);
        chartPanel.setDomainZoomable(true);
        add(chartPanel);
	}
}
