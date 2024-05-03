package edu.wm.cs.cs301.connectn.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wm.cs.cs301.connectn.controller.*;
import edu.wm.cs.cs301.connectn.model.*;

public class ButtonPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	private int buttonCount;

	private final ButtonAction action;

	/*
	 * this class is responsible for displaying the numbered buttons below
	 * each column and attaching an action listener to them so that they can 
	 * appropriately update the model. it takes the view and model as parameters
	 */
	
	public ButtonPanel(GameBoardView view, GameBoardModel model) {
		this.buttonCount = model.getColumnCount();
		this.action = new ButtonAction(model, view);
		createButtonPanel();
	}
	
	public JPanel createButtonPanel() {
		
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		Font textfont = AppFonts.getTextFont();

		for (int index = 0; index < buttonCount; index++) {
			JButton button = new JButton(Integer.toString(index+1));
			button.setSize(10, 10);
			button.setPreferredSize(new Dimension(65, 40));
			button.addActionListener(action);
			button.setFont(textfont);
			panel.add(button);
		}
		return panel;
	}
}
