/*	GUI creates and organizes the graphical user interface for justplaymusic
	by Ian Zapolsky (8/15/13) */

import java.io.*;
import java.net.*;
import java.awt.Desktop;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GUI {

	private JFrame frame;
	private JPanel northPanel, leftPanel, mainPanel;
	private JButton queryButton;
	private JTextField queryField;
	private JLabel justPlay, upNext, title, time, views, user;
	private JScrollPane mainPane, upNextPane;

	private YoutubeCrawler crawler;
	private ArrayList<Video> mainVideos;
	private ArrayList<Video> upNextVideos;

	public GUI() {
		crawler = new YoutubeCrawler();
		mainVideos = new ArrayList<Video>();
		upNextVideos = new ArrayList<Video>();

		frame = new JFrame("Just Play Music");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(new Color(190,190,190));
		frame.add(createNorthPanel(), BorderLayout.NORTH);
		frame.add(createLeftPanel(), BorderLayout.WEST);
		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.pack();
		frame.setSize(800, 800);
		frame.setVisible(true);

	}

	private JPanel createNorthPanel() {
		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		northPanel.setBorder(BorderFactory.createEtchedBorder());
		justPlay = new JLabel("JUST PLAY:");
		queryField = new JTextField(40);
		queryButton = new JButton("GO");
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateQueryResults();
				}
				catch (IOException ex) {
					JOptionPane.showMessageDialog(frame, "fuuuuck");
				}
			}
		});
		northPanel.add(justPlay);
		northPanel.add(queryField);
		northPanel.add(queryButton);
		return northPanel;
	}

	private JPanel createLeftPanel() {
		leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout());
		leftPanel.setBorder(BorderFactory.createEtchedBorder());
		upNext = new JLabel("UP NEXT");
		leftPanel.add(upNext);
		return leftPanel;
	}

	private JPanel createMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));
		mainPanel.setBorder(BorderFactory.createEtchedBorder());
		for (final Video v : mainVideos) {
			JButton b = new JButton(v.getTitle());
			b.setPreferredSize(new Dimension(10, 40));
			b.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						String URL = "http://www.youtube.com/watch?v="+v.getId();
						java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			mainPanel.add(b);
		}
		return mainPanel;
	}

	private void updateQueryResults() throws IOException {
		mainVideos = crawler.query(queryField.getText());
		frame.remove(mainPanel);
		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.pack();
		frame.setSize(800, 800);
		frame.setVisible(true);
	}
}
