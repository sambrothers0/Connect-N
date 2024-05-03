package edu.wm.cs.cs301.connectn.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import edu.wm.cs.cs301.connectn.model.AppColors;

/*
 * this dialogue is responsible for informing the player that the column
 * they have selected is currently full. it appears whenever the player 
 * clicks a button underneath a column that is full
 */

public class InvalidColumnDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private final CancelAction cancelAction;

	public InvalidColumnDialog(GameBoardView view) {
		super(view.getFrame(), "Invalid Column", true);
		this.cancelAction = new CancelAction();
		
		add(createMainPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(view.getFrame());
		setVisible(true);
	}
	
	private JPanel createMainPanel() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    Font titleFont = AppFonts.getTitleFont();
	    Font textFont = AppFonts.getTextFont();

	    JLabel titleLabel = new JLabel("That column is full!");
	    titleLabel.setFont(titleFont);
	    titleLabel.setHorizontalAlignment(JLabel.CENTER);
	    panel.add(titleLabel, BorderLayout.NORTH);

	    String text = "	    Please choose a different column";
	    
	    JTextArea textArea = new JTextArea(4, 25);
	    textArea.setBackground(AppColors.BACKGROUND);
	    textArea.setEditable(false);
	    textArea.setFont(textFont);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    textArea.setText(text);
	    panel.add(textArea, BorderLayout.SOUTH);
	    
	    int width = 500;
	    int height = 200;
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
