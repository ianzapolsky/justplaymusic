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
	private JScrollPane mainPane, leftPane;

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
		frame.setBackground(new Color(220,220,220));
		frame.add(createNorthPanel(), BorderLayout.NORTH);
		frame.add(createLeftPanel(), BorderLayout.SOUTH);
		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.pack();
		frame.setSize(900, 600);
		frame.setVisible(true);

	}

	private JPanel createNorthPanel() {
		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		northPanel.setBorder(BorderFactory.createEtchedBorder());
		northPanel.setBackground(new Color(220,220,220));
		justPlay = new JLabel("JUST PLAY");
		justPlay.setFont(new Font("Impact", Font.PLAIN, 50));
		queryField = new JTextField(40);
		queryField.setFont(new Font("Courier", Font.PLAIN, 15));
		queryButton = new JButton("GO");
		queryButton.setFont(new Font("Impact", Font.PLAIN, 30));
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

	private JScrollPane createLeftPanel() {
		leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(0,1));
		leftPanel.setBorder(BorderFactory.createEtchedBorder());
		leftPanel.setBackground(new Color(160,160,160));
		upNext = new JLabel("          UP NEXT:");
		upNext.setFont(new Font("Impact", Font.PLAIN, 30));
		leftPanel.add(upNext);

		for (final Video v : upNextVideos) {
			JPanel videoPanel = createPlaylistPanel(v);
			leftPanel.add(videoPanel);
		}
		leftPane = new JScrollPane(leftPanel);
		leftPane.setPreferredSize(new Dimension(900, 200));
		return leftPane;
	}

	private JScrollPane createMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));
		mainPanel.setBorder(BorderFactory.createEtchedBorder());
		mainPanel.setBackground(new Color(160,160,160));

		for (final Video v : mainVideos) {
			JPanel videoPanel = createVideoPanel(v);
			mainPanel.add(videoPanel);
		}
		mainPane = new JScrollPane(mainPanel);
		return mainPane;
	}

	private JPanel createVideoPanel(final Video v) {
		JPanel videoPanel = new JPanel();
		videoPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		videoPanel.setPreferredSize(new Dimension(600,40));
		videoPanel.setBackground(new Color(220,220,220));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		titlePanel.setBorder(BorderFactory.createEtchedBorder());
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new FlowLayout());

		JButton play = new JButton("PLAY");
		play.setFont(new Font("Impact", Font.PLAIN, 10));

			play.addActionListener(new ActionListener() {

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

		JButton playlist = new JButton("PLAYLIST");
		playlist.setFont(new Font("Impact", Font.PLAIN, 10));

			playlist.addActionListener(new ActionListener() {
			
				public void actionPerformed(ActionEvent e) {
					updatePlaylist(v);
				}
			});
		
		JLabel title = new JLabel(v.getTitle());
		title.setFont(new Font("Courier", Font.PLAIN, 15));
		JLabel views = new JLabel(v.getViews());
		views.setFont(new Font("Courier", Font.PLAIN, 15));
		JLabel time = new JLabel(v.getTime());
		time.setFont(new Font("Courier", Font.PLAIN, 15));

		titlePanel.add(play);
		titlePanel.add(playlist);
		titlePanel.add(title);

		timePanel.add(time);
		timePanel.add(views);		

		videoPanel.add(titlePanel);
		videoPanel.add(timePanel);

		return videoPanel;
	}

	public JPanel createPlaylistPanel(final Video v) {
		JPanel videoPanel = new JPanel();
		videoPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		videoPanel.setPreferredSize(new Dimension(600,40));
		videoPanel.setBackground(new Color(220,220,220));

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		titlePanel.setBorder(BorderFactory.createEtchedBorder());

		JButton play = new JButton("PLAY");
		play.setFont(new Font("Impact", Font.PLAIN, 10));

			play.addActionListener(new ActionListener() {

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

		JButton remove = new JButton("REMOVE");
		remove.setFont(new Font("Impact", Font.PLAIN, 10));

			remove.addActionListener(new ActionListener() {
			
				public void actionPerformed(ActionEvent e) {
					removeFromPlaylist(v);
				}
			});
		
		JLabel title = new JLabel(v.getTitle());
		title.setFont(new Font("Courier", Font.PLAIN, 15));

		titlePanel.add(play);
		titlePanel.add(remove);
		titlePanel.add(title);

		videoPanel.add(titlePanel);

		return videoPanel;
	}

	private void updateQueryResults() throws IOException {
		mainVideos = crawler.query(queryField.getText());
		frame.remove(mainPanel);
		frame.remove(mainPane);
		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.pack();
		frame.setSize(900, 600);
		frame.setVisible(true);
	}

	private void updatePlaylist(Video v) {
		upNextVideos.add(v);
		frame.remove(leftPanel);
		frame.remove(leftPane);
		frame.add(createLeftPanel(), BorderLayout.SOUTH);
		frame.pack();
		frame.setSize(900, 600);
		frame.setVisible(true);
	}

	private void removeFromPlaylist(Video v) {
		upNextVideos.remove(v);
		frame.remove(leftPanel);
		frame.remove(leftPane);
		frame.add(createLeftPanel(), BorderLayout.SOUTH);
		frame.pack();
		frame.setSize(900, 600);
		frame.setVisible(true);
	}
}
