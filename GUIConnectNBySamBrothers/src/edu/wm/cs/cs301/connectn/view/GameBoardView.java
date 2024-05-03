package edu.wm.cs.cs301.connectn.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


import edu.wm.cs.cs301.connectn.model.*;


public class GameBoardView{
	
	private final JFrame frame;

	private final GameBoardModel model;
	
	private final ButtonPanel buttonPanel;
	
	private ConnectNGridPanel connectNGridPanel;
	
	private JPanel titlePanel;
	
	private JLabel turnLabel;
	
	private JLabel modeLabel;
	
	private int width;
	
	/* 
	 * this class is responsible for diaplsying the main window of the
	 * game, including the options menu, exit button, game board, 
	 * button panel, turn counter. it takes as a parameter the model
	 */
	
	public GameBoardView(GameBoardModel model) {
		this.model = model;
		this.width = getWidth();
		this.buttonPanel = new ButtonPanel(this, model);
		this.connectNGridPanel = new ConnectNGridPanel(this, model, width);
		this.titlePanel = createTitlePanel();
		this.frame = createAndShowGUI();
		model.addView(this);
	}
	
	private JFrame createAndShowGUI() {
		JFrame frame = new JFrame("Connect N");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setJMenuBar(createMenuBar());
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			 public void windowClosing(WindowEvent event) {
				shutdown();
			}
		});
		
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(connectNGridPanel, BorderLayout.CENTER);
		frame.add(buttonPanel.createButtonPanel(), BorderLayout.SOUTH);
		
		frame.pack();
		frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		if (model.getFirstGame()) {
			new InstructionsDialog(this);
			new EnterNameDialog(this, model);
			model.updateFirstGame();
		}
		new LeaderboardDialog(this);
		
		return frame;
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu difficultyMenu = new JMenu("Mode");
		menuBar.add(difficultyMenu);
		
		JMenuItem smallItem = new JMenuItem("Small");
		smallItem.addActionListener(event -> updateSmall());
		difficultyMenu.add(smallItem);
		
		JMenuItem mediumItem = new JMenuItem("Medium");
		mediumItem.addActionListener(event -> updateMedium());
		difficultyMenu.add(mediumItem);
		
		JMenuItem largeItem = new JMenuItem("Large");
		largeItem.addActionListener(event -> updateLarge());
		difficultyMenu.add(largeItem);
		
		JMenu exitMenu = new JMenu("Exit");
		exitMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
            	shutdown();
            }

			@Override
			public void menuDeselected(MenuEvent e) {
			}

			@Override
			public void menuCanceled(MenuEvent e) {}
		});
		
		menuBar.add(exitMenu);
		
		return menuBar;
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		JLabel label = new JLabel("Connect N", JLabel.CENTER);
		label.setFont(AppFonts.getTitleFont());
		panel.add(label);
		
		turnLabel = new JLabel("Turn: " + model.getTurns(), JLabel.CENTER);
		turnLabel.setFont(AppFonts.getTextFont());
		panel.add(turnLabel);
		
		modeLabel = new JLabel("Mode: " + model.getMode(), JLabel.CENTER);
		modeLabel.setFont(AppFonts.getTextFont());
		panel.add(modeLabel);
		
		return panel;
	}
	
	public void updateSmall() {
		frame.dispose();
		model.modeSmall();
		new GameBoardView(model);
		repaintConnectNGridPanel();
	}
	
	public void updateMedium() {
		frame.dispose();
		model.modeMedium();
		new GameBoardView(model);
		repaintConnectNGridPanel();
	}
	
	public void updateLarge() {
		frame.dispose();
		model.modeLarge();
		new GameBoardView(model);
		repaintConnectNGridPanel();
	}
	
	public void repaintTitlePanel() {
		turnLabel.setText("Turn: " + Integer.toString(model.getTurns()));
		modeLabel.setText("Mode: " + model.getMode());
		titlePanel.repaint();
	}
	
	public void repaintConnectNGridPanel() {
		connectNGridPanel.repaint();
	}
	
	public void shutdown() {
		frame.dispose();
		System.exit(0);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	private int getWidth() {
		if (model.getMode().equals("Small")) {
			return 400;
		}
		else if (model.getMode().equals("Medium")) {
			return 600;
		}
		else {
			return 800;
		}

	}
}
