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
 * this class is a dialog which appears when the program is first launched and
 * informs the user of some basic information about how to play
 */

public class InstructionsDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private final CancelAction cancelAction;

	public InstructionsDialog(GameBoardView view) {
		super(view.getFrame(), "Instructions", true);
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

	    JLabel titleLabel = new JLabel("Welcome to Connect N!");
	    titleLabel.setFont(titleFont);
	    titleLabel.setHorizontalAlignment(JLabel.CENTER);
	    panel.add(titleLabel, BorderLayout.NORTH);


	    String text = "This game is played on a rectangular board where "
	            + "pieces are dropped in from the top, and you must connect pieces in "
	            + "a row, either diagonally, vertically, or horizontally. The size of the board and "
	            + "number of pieces required to win is determined by the game mode - either 3 for small, "
	            + "4 for medium or 5 for large. You will play against a computer, and your pieces will be "
	            + "represented as 'X', and the computer's pieces will be represented as 'O'";
	    JTextArea textArea = new JTextArea(4, 25);
	    textArea.setBackground(AppColors.BACKGROUND);
	    textArea.setEditable(false);
	    textArea.setFont(textFont);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    textArea.setText(text);
	    panel.add(textArea, BorderLayout.CENTER);
	    
	    int width = 500;
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
