/************************************************************************************************************
 * @(#) BarChart2D.java 1.1 31 Mar 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.sun.jersey.core.util.Base64;
import com.tcs.djb.rms.model.ChartDetails;


/**
 * <p>
 * 2D bar char generator Util class. It uses jfree chart api.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 31-03-2014
 * 
 */
public class BarChart2D {
	public static void main(String[] args) throws Exception {

		List<ChartDetails> dataList = new ArrayList<ChartDetails>();
		dataList.add(new ChartDetails(1000.0, "2015-02-01", "Consumption"));
		dataList.add(new ChartDetails(5000.0, "2015-04-01", "Consumption"));
		dataList.add(new ChartDetails(2000.0, "2015-08-01", "Consumption"));
		dataList.add(new ChartDetails(6000.0, "2015-10-01", "Consumption"));
		dataList.add(new ChartDetails(3000.0, "2015-12-01", "Consumption"));
		dataList.add(new ChartDetails(7000.0, "2016-02-01", "Consumption"));
		dataList.add(new ChartDetails(1000.0, "2016-02-01", "Consumption"));
		dataList.add(new ChartDetails(5000.0, "2016-04-01", "Consumption"));
		dataList.add(new ChartDetails(2000.0, "2016-08-01", "Consumption"));
		dataList.add(new ChartDetails(6000.0, "2016-10-01", "Consumption"));
		dataList.add(new ChartDetails(3000.0, "2016-12-01", "Consumption"));
		dataList.add(new ChartDetails(7000.0, "2017-02-01", "Consumption"));
		getImageAsBase64String(dataList, "Consumtion Graph", "Bill date",
				"Consumtion", 480, 420, "png");
		createImage(dataList, "Consumtion Graph", "Bill date", "Consumtion",
				480, 420, "png", "D:\\BarChart2D.png");
	}

	/**
	 * <p>
	 * Get bar chart Image As Base64 String
	 * </p>
	 * @param dataList
	 * @param chartTitle
	 * @param xLegend
	 * @param yLegend
	 * @param width
	 *            image width
	 * @param height
	 *            image height
	 * @param format
	 *            file format
	 * @return
	 * @throws IOException
	 */
	public static String getImageAsBase64String(List<ChartDetails> dataList,
			String chartTitle, String xLegend, String yLegend, int width,
			int height, String format) throws IOException {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < dataList.size(); i++) {
			dataset.addValue(dataList.get(i).getValue(), dataList.get(i)
					.getyAxis(), dataList.get(i).getxAxis());
		}
		BufferedImage objBufferedImage = getChart(dataList, chartTitle,
				xLegend, yLegend, width, height).createBufferedImage(width,
				height);
		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		try {
			ImageIO.write(objBufferedImage, format, bas);
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] encoded = Base64.encode(bas.toByteArray());
		String encodedString = new String(encoded);
		// System.out.println("encodedString>>" + encodedString);
		return encodedString;

	}

	public static JFreeChart getChart(List<ChartDetails> dataList,
			String chartTitle, String xLegend, String yLegend, int width,
			int height) throws IOException {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < dataList.size(); i++) {
			dataset.addValue(dataList.get(i).getValue(), dataList.get(i)
					.getyAxis(), dataList.get(i).getxAxis());
		}

		JFreeChart barChart = ChartFactory.createBarChart(chartTitle, xLegend,
				yLegend, dataset, PlotOrientation.VERTICAL, true, true, false);
		BarRenderer renderer = (BarRenderer) barChart.getCategoryPlot()
				.getRenderer();
		renderer.setItemMargin(.05);
		renderer.setSeriesPaint(0, Color.gray);
		CategoryAxis domainAxis = barChart.getCategoryPlot().getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 6.0));

		return barChart;
	}

	/**
	 * @param dataList
	 * @param chartTitle
	 * @param xLegend
	 * @param yLegend
	 * @param width
	 *            image width
	 * @param height
	 *            image height
	 * @param format
	 *            file format
	 * @param path
	 *            file path
	 * @return
	 * @throws IOException
	 */
	public static void createImage(List<ChartDetails> dataList,
			String chartTitle, String xLegend, String yLegend, int width,
			int height, String format, String path) throws IOException {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < dataList.size(); i++) {
			dataset.addValue(dataList.get(i).getValue(), dataList.get(i)
					.getyAxis(), dataList.get(i).getxAxis());
		}
		ChartUtilities
				.saveChartAsJPEG(
						new File(path),
						getChart(dataList, chartTitle, xLegend, yLegend, width,
								height), width, height);

	}
}