package com.AndroidSensor.AndroidSensorGraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JCheckBox;

class Main {

	private JFrame frame;
	private JTextField txtfileselected;
	private JFileChooser chooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		frame = new JFrame("DrawGraph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chooser = new JFileChooser();
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		JButton btnfile = new JButton("Brows csv file");
		btnfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select CSV format file");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				FileNameExtensionFilter csv = new FileNameExtensionFilter(
						"csv files (*.csv)", "csv");
				chooser.setFileFilter(csv);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					txtfileselected.setText(chooser.getSelectedFile()
							.toString());
				}
			}
		});

		final JRadioButton xasix = new JRadioButton("X Asix");
		xasix.setSelected(true);
		xasix.setForeground(Color.BLACK);

		final JRadioButton yasix = new JRadioButton("Y Asix");
		yasix.setSelected(true);
		yasix.setForeground(Color.BLACK);

		final JRadioButton zasix = new JRadioButton("Z Asix");
		zasix.setSelected(true);
		zasix.setForeground(Color.BLACK);

		JLabel lblSelectTimeFrom = new JLabel("Time from");

		SpinnerDateModel model1 = new SpinnerDateModel();
		model1.setCalendarField(Calendar.MINUTE);

		SpinnerDateModel model2 = new SpinnerDateModel();
		model2.setCalendarField(Calendar.MINUTE);

		final JSpinner timefrom = new JSpinner();
		timefrom.setModel(model1);
		timefrom.setEditor(new JSpinner.DateEditor(timefrom, "HH:mm:ss"));

		JLabel lblSelectTimeTo = new JLabel("Time to");

		final JSpinner timeto = new JSpinner();
		timeto.setModel(model2);
		timeto.setEditor(new JSpinner.DateEditor(timeto, "HH:mm:ss"));

		JButton btnplot = new JButton("Plot");

		panel.setLayout(new GridLayout(0, 11, 0, 0));

		panel.add(btnfile);
		String[] type = { "ACCELEROMETER", "GYROSCOPE" };
		final JComboBox<?> comboBox = new JComboBox<Object>(type);

		final JCheckBox chckbxDrawAllTime = new JCheckBox("All data");

		panel.add(comboBox);
		panel.add(xasix);
		panel.add(yasix);
		panel.add(zasix);
		panel.add(lblSelectTimeFrom);
		panel.add(timefrom);
		panel.add(lblSelectTimeTo);
		panel.add(timeto);
		panel.add(chckbxDrawAllTime);
		panel.add(btnplot);

		final JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(0, 0, 700, 420);
		frame.getContentPane().add(scrollPane1);

		txtfileselected = new JTextField();
		frame.getContentPane().add(txtfileselected, BorderLayout.SOUTH);
		txtfileselected.setHorizontalAlignment(SwingConstants.LEFT);
		txtfileselected.setEditable(false);

		frame.setBounds(0, 0, 700, 420);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);

		btnplot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File files = chooser.getSelectedFile();
				if (files == null) {
					return;
				}
				String csvFile = chooser.getSelectedFile().toString();
				BufferedReader br = null;
				String line = "";
				String cvsSplitBy = ",";
				try {
					br = new BufferedReader(new FileReader(csvFile));
					XYSeries x_data_a = new XYSeries("X data");
					XYSeries y_data_a = new XYSeries("Y data");
					XYSeries z_data_a = new XYSeries("Z data");
					XYSeries x_data_o = new XYSeries("X data");
					XYSeries y_data_o = new XYSeries("Y data");
					XYSeries z_data_o = new XYSeries("Z data");
					Double i = 1.0;
					SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

					long timef = Time.valueOf(
							format.format(timefrom.getValue())).getTime();
					long timet = Time.valueOf(format.format(timeto.getValue()))
							.getTime();
					while ((line = br.readLine()) != null) {
						String[] dataln = line.split(cvsSplitBy);
						long curt = Time.valueOf(dataln[3]).getTime();
						if (chckbxDrawAllTime.isSelected()) {
							if (dataln[4].equals("ACCELEROMETER")) {
								x_data_a.add(i, Double.valueOf(dataln[0]));
								y_data_a.add(i, Double.valueOf(dataln[1]));
								z_data_a.add(i, Double.valueOf(dataln[2]));
							} else if (dataln[4].equals("GYROSCOPE")) {
								x_data_o.add(i, Double.valueOf(dataln[0]));
								y_data_o.add(i, Double.valueOf(dataln[1]));
								z_data_o.add(i, Double.valueOf(dataln[2]));
							}
						} else if (curt >= timef && curt <= timet) {
							if (dataln[4].equals("ACCELEROMETER")) {
								x_data_a.add(i, Double.valueOf(dataln[0]));
								y_data_a.add(i, Double.valueOf(dataln[1]));
								z_data_a.add(i, Double.valueOf(dataln[2]));
							} else if (dataln[4].equals("GYROSCOPE")) {
								x_data_o.add(i, Double.valueOf(dataln[0]));
								y_data_o.add(i, Double.valueOf(dataln[1]));
								z_data_o.add(i, Double.valueOf(dataln[2]));
							}
						}
						i++;
						// System.out.println("x:" + dataln[0]
						// + " , y:" + dataln[1]
						// + " , z:" + dataln[2] + " , time:" + dataln[3]
						// + " , mode:" + dataln[4]);
					}
					XYSeriesCollection data_series_accelerometer = new XYSeriesCollection();
					XYSeriesCollection data_series_gyroscope = new XYSeriesCollection();
					if (xasix.isSelected()) {
						data_series_accelerometer.addSeries(x_data_a);
					}
					if (yasix.isSelected()) {
						data_series_accelerometer.addSeries(y_data_a);
					}
					if (zasix.isSelected()) {
						data_series_accelerometer.addSeries(z_data_a);
					}
					if (xasix.isSelected()) {
						data_series_gyroscope.addSeries(x_data_o);
					}
					if (yasix.isSelected()) {
						data_series_gyroscope.addSeries(y_data_o);
					}
					if (zasix.isSelected()) {
						data_series_gyroscope.addSeries(z_data_o);
					}

					LineGraph graph_accelerometer = new LineGraph(
							data_series_accelerometer, "Time", "Value",
							"Android accelerometer sensor log");
					LineGraph graph_gyroscope = new LineGraph(
							data_series_gyroscope, "Time", "Value",
							"Android gyroscope sensor log");
					switch (comboBox.getSelectedIndex()) {
					case 0:
						scrollPane1.setViewportView(graph_accelerometer);
						break;
					case 1:
						scrollPane1.setViewportView(graph_gyroscope);
						break;
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				// Random random = new Random();
				// int maxDataPoints = 400;
				// Double maxScore = 10.0;
				// Double minScore = -10.0;
				// for (int i = 0; i <= maxDataPoints ; i++) {
				// scores.add( (minScore + random.nextFloat() * (maxScore-
				// minScore)));
				// }

			}
		});

	}

}
