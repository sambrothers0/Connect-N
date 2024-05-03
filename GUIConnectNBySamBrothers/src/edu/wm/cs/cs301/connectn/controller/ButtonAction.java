package edu.wm.cs.cs301.connectn.controller;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import edu.wm.cs.cs301.connectn.view.*;
import edu.wm.cs.cs301.connectn.model.*;


public class ButtonAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	private final GameBoardModel model;
	
	private final GameBoardView view;
	
	/*
	 * this class is responsible for handling the action events sent
	 * from the column buttons and interpreting it as updates to the
	 * model. it also checks for a win following the user and computer
	 * turns and ends the game if necessary. if the user selects a full
	 * column, the game informs them of their invalid choice and will
	 * update that column
	 */
	
	public ButtonAction(GameBoardModel model, GameBoardView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String text = button.getActionCommand();
			List<String> emptySlots = model.getEmptySlots();
			if (!(emptySlots.contains(text))) {
				new InvalidColumnDialog(view);
			}
			else {
				model.placeHumanToken(text);
				model.incrTurns();
				model.repaint();
				if (model.checkWin() == 1) {
					new PlayAgainDialog(view, model, "Win");
					return;
				}
				else if (model.checkWin() == 3) {
					new PlayAgainDialog(view, model, "Tie");
					return;
				}
				model.placeComputerToken();
				model.repaint();
				if (model.checkWin() == 2) {
					new PlayAgainDialog(view, model, "Loss");
					return;
				}
				else if (model.checkWin() == 3) {
					new PlayAgainDialog(view, model, "Tie");
					return;
				}

			}
	}
}
