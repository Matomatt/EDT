package View.DiagramPanels;

import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartPanel extends JPanel 
{
	private static final long serialVersionUID = -2314083011553281730L;

	public PieChartPanel(String title, Map<String, Double> map) 
	{
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Entry<String, Double> mapEntry : map.entrySet()) {
			dataset.setValue(mapEntry.getKey(), mapEntry.getValue());
		}
		
        JFreeChart chart = ChartFactory.createPieChart(title,
                dataset, true, false, false);
        
        chart.setBorderVisible(false);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.white);
        add(chartPanel);
	}
}
