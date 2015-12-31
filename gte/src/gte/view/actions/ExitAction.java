package gte.view.actions;

import gte.controller.Controller;
import gte.model.Model;
import gte.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
* User: Alan P. Sexton
* Date: 20/06/13
* Time: 20:50
*/
public class ExitAction extends AbstractAction
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Model model;
    private View view;
    private Controller controller;

    {
        putValue(NAME, "Quit");
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/gte/icons/exit.png")));
        putValue(SHORT_DESCRIPTION, "Quits the program");
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));

    }

    public ExitAction(Model model, View view, Controller controller)
    {
        this.view = view;
        this.model = model;
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e)
    {
        int response = JOptionPane.showConfirmDialog(view,
                                                     "Do you really want to quit?",
                                                     "Exit",
                                                     JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION)
            controller.exit(0);
    }
}
