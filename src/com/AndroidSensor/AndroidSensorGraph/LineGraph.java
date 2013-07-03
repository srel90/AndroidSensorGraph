package com.AndroidSensor.AndroidSensorGraph;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;

public class LineGraph extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * @param dataset
	 * @param ytitle
	 * @param xtitle
	 * @param title
	 */
	public LineGraph(XYDataset dataset, String ytitle, String xtitle,
			String title) {
		super();
		JFreeChart chart = createChart(dataset, ytitle, xtitle, title);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1200, 300));
		this.setLayout(new BorderLayout());
		this.add(chartPanel);
	}

	private JFreeChart createChart(final XYDataset dataset, String ytitle,
			String xtitle, String title) {
		JFreeChart chart = ChartFactory.createXYLineChart(title, ytitle,
				xtitle, dataset, PlotOrientation.VERTICAL, true, false, false);

		((XYLineAndShapeRenderer) chart.getXYPlot().getRenderer())
				.setBaseShapesVisible(false);
		((XYLineAndShapeRenderer) chart.getXYPlot().getRenderer())
				.setBaseShapesFilled(false);
		chart.getXYPlot().getRenderer()
				.setSeriesStroke(0, new BasicStroke(1.5f));
		chart.getXYPlot().getRenderer()
				.setSeriesStroke(1, new BasicStroke(1.5f));
		chart.getXYPlot().getRenderer()
				.setSeriesStroke(2, new BasicStroke(1.5f));

		// chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.orange);
		// chart.getXYPlot().getRenderer().setSeriesPaint(1, Color.red);
		// chart.getXYPlot().getRenderer().setSeriesPaint(2, Color.blue);
		return chart;

	}

}
