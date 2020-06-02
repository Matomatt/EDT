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
	
	public BarChartPanel(String title, String yLabel, Map<String, Double> map) 
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Entry<String, Double> mapEntry : map.entrySet()) {
			dataset.setValue(mapEntry.getValue(), "Total", mapEntry.getKey());
		}
		
        makePannel(title, yLabel, dataset);
	}
	
	public BarChartPanel(String title, String yLabel, Map<String, Double> map, Map<String, Double> mapSecondValue, String[] labels) 
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Entry<String, Double> mapEntry : map.entrySet()) {
			dataset.setValue(mapEntry.getValue(), labels[0], mapEntry.getKey());
			if (mapSecondValue.containsKey(mapEntry.getKey()))
				dataset.setValue(mapSecondValue.get(mapEntry.getKey()), labels[1], mapEntry.getKey());
			else
				dataset.setValue(0, labels[1], mapEntry.getKey());
		}
		
        makePannel(title, yLabel, dataset);
	}
	
	private void makePannel(String title, String yLabel, DefaultCategoryDataset dataset)
	{
		JFreeChart chart = ChartFactory.createBarChart(
        		title,
                "",
                yLabel,
                dataset,
                PlotOrientation.HORIZONTAL,
                false, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        chartPanel.setBackground(Color.white);
        add(chartPanel);
	}
}
