package edu.wm.cs.cs301.connectn.view;

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

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import edu.wm.cs.cs301.connectn.controller.ButtonAction;
import edu.wm.cs.cs301.connectn.model.GameBoardModel;


public class EnterNameDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private final ButtonAction action;
	
	private final GameBoardModel model;

	private final EnterAction enterAction;
	
	private final JTextField textField;

	/*
	 * this is the dialogue which displays at the start of the program's launch
	 * and allows the player to enter their username for the purpose of updating
	 * the leaderboard in case the player gets a new high score. it takes the 
	 * view and model as parameters
	 */
	
	public EnterNameDialog(GameBoardView view, GameBoardModel model) {
		super(view.getFrame(), "Enter Name", true);
		this.model = model;
		this.enterAction = new EnterAction();
		this.action = new ButtonAction(model, view);
		this.textField = new JTextField(20);
		
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createInputPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		pack();
		setLocationRelativeTo(view.getFrame());
		setVisible(true);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		Font titleFont = AppFonts.getTitleFont();
		
		JLabel label = new JLabel("Enter your username");
		label.setFont(titleFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		
	    int width = 500;
	    int height = 100;
	    panel.setPreferredSize(new Dimension(width, height));
		
		return panel;
	}
	
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		panel.add(textField);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
		ActionMap actionMap = panel.getActionMap();
		actionMap.put("enterAction", enterAction);
		
		JButton button = new JButton("Submit");
		button.addActionListener(enterAction);
		panel.add(button);
		
		return panel;
	}
	
	private class EnterAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.updateName(textField.getText());
			dispose();
		}
		
	}

}
