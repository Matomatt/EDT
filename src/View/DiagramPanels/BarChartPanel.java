package View.DiagramPanels;

import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartPanel extends JPanel 
{
	private static final long serialVersionUID = -6163532647075047922L;

	public BarChartPanel(String title, String yLabel, Map<String, Integer> map) 
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Entry<String, Integer> mapEntry : map.entrySet()) {
			dataset.setValue(((Integer)mapEntry.getValue()).doubleValue()/60.0, yLabel, mapEntry.getKey());
		}
		
        JFreeChart chart = ChartFactory.createBarChart(
        		title,
                "",
                yLabel,
                dataset,
                PlotOrientation.HORIZONTAL,
                false, true, false);;
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);
	}
}
