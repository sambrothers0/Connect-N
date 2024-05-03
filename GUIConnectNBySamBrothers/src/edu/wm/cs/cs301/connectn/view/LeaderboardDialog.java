package edu.wm.cs.cs301.connectn.view;

import edu.wm.cs.cs301.connectn.model.*;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/*
 * this dialogue appears at the start of a new game and displays 
 * information about the current best scores for each mode.
 * it reads a static text file in the resources folder which
 * contains the name and score of the previous record holder
 */

public class LeaderboardDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private final CancelAction cancelAction;

	public LeaderboardDialog(GameBoardView view) {
		super(view.getFrame(), "Leaderboard", true);
		this.cancelAction = new CancelAction();
		
		add(createMainPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(view.getFrame());
		setVisible(true);
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		Font titleFont = AppFonts.getTitleFont();
		Font textFont = AppFonts.getTextFont();
		
		String[] leaderData = {"","","","","",""};
		try {
			leaderData = Leaderboard.getLeaders();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String smallName = leaderData[0];
		String smallScore = leaderData[1];
		String medName = leaderData[2];
		String medScore = leaderData[3];
		String largeName = leaderData[4];
		String largeScore = leaderData[5];
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 5, 5, 30);
		
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		JLabel label = new JLabel("Best Scores");
		label.setFont(titleFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		label = new JLabel("Mode:");
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel("Name:");
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel("Score:");
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		label = new JLabel("Small");
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel(smallName);
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel(smallScore);
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		label = new JLabel("Medium");
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel(medName);
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel(medScore);
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		label = new JLabel("Large");
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel(largeName);
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel(largeScore);
		label.setFont(textFont);
		panel.add(label, gbc);
		
	    int width = 600;
	    int height = 250;
	    panel.setPreferredSize(new Dimension(width, height));
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelAction");
		ActionMap actionMap = panel.getActionMap();
		actionMap.put("cancelAction", cancelAction);
		
		JButton button = new JButton("Cancel");
		button.addActionListener(cancelAction);
		panel.add(button);
		
		return panel;
	}
	
	private class CancelAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
		
	}

}
