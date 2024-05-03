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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

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
import edu.wm.cs.cs301.connectn.model.GameBoardModel;
import edu.wm.cs.cs301.connectn.model.Leaderboard;

public class PlayAgainDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private final ExitAction exitAction;
	
	private final PlayAgainAction playAgainAction;
	
	private final GameBoardModel model;
	
	private final GameBoardView view;
	
	private final String outcome;

	/*
	 * this dialogue displays when the game finishes and allows the player to either 
	 * play a new game or exit, which ends the program entirely. 
	 * 
	 * as arguments, it takes the view, model, and a string which respresents the
	 * outcome of the game - either win loss or tie
	 */
	
	public PlayAgainDialog(GameBoardView view, GameBoardModel model, String outcome) {
		super(view.getFrame(), "Play Again", true);
		this.exitAction = new ExitAction();
		this.playAgainAction = new PlayAgainAction();
		this.model = model;
		this.view = view;
		this.outcome = outcome;
		
		add(createMainPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
		
		pack();
		setLocationRelativeTo(view.getFrame());
		setVisible(true);
	}
	
	private JPanel createMainPanel() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    Font titleFont = AppFonts.getTitleFont();
	    Font textFont = AppFonts.getTextFont();
	    JLabel titleLabel = new JLabel();
	    JLabel outcomeLabel = new JLabel();
	    JLabel playAgainLabel = new JLabel("Play Again?");
	    
	    switch (outcome) {
	    	case "Win":
	    	    titleLabel.setText("Congrautlations!");
	    	    outcomeLabel.setText("You won in " + model.getTurns() + " turns.");
	    	    break;
	    	case "Loss":
	    	    titleLabel.setText("Game Over!");
	    	    outcomeLabel.setText("You lost in " + model.getTurns() + " turns.");
	    		break;
	    	case "Tie":
	    	    titleLabel.setText("It's a tie!");
	    	    outcomeLabel.setText("Game was tied in " + model.getTurns() + " turns.");
	    	    break;
	    }
	    titleLabel.setFont(titleFont);
	    titleLabel.setHorizontalAlignment(JLabel.CENTER);
	    panel.add(titleLabel, BorderLayout.NORTH);
	    
	    outcomeLabel.setFont(textFont);
	    outcomeLabel.setHorizontalAlignment(JLabel.CENTER);
	    panel.add(outcomeLabel, BorderLayout.CENTER);
	    
	    playAgainLabel.setFont(textFont);
	    playAgainLabel.setHorizontalAlignment(JLabel.CENTER);
	    panel.add(playAgainLabel, BorderLayout.SOUTH);
	    
	    int width = 500;
	    int height = 200;
	    panel.setPreferredSize(new Dimension(width, height));

	    return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exitAction");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "playAgainAction");
		
		ActionMap actionMap = panel.getActionMap();
		actionMap.put("exitAction", exitAction);
		actionMap.put("playAgainAction", playAgainAction);

		JButton playAgainButton = new JButton("Play Again");
		playAgainButton.addActionListener(playAgainAction);
		panel.add(playAgainButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(exitAction);
		panel.add(exitButton);
		return panel;
	}
	
	private class ExitAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
			view.shutdown();
		}
		
	}
	
	private class PlayAgainAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
			try {
				Leaderboard.updateLeaders(model.getMode(), model.getName(), model.getTurns());
			} catch (IOException e) {
				e.printStackTrace();
			}
			model.initializeGameBoard();
			model.repaint();
			model.updateFirstGame();
			new LeaderboardDialog(view);
		}
		
	}

}
