package layout.view;

import layout.controller.Controller;
import layout.model.Model;
import layout.view.actions.ExitAction;
import layout.view.actions.LongRunningAction;
import layout.view.actions.OpenAction;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User: Alan P. Sexton
 * Date: 21/06/13
 * Time: 13:42
 */
public class View extends JFrame
{
    private Canvas canvas = null;
    private Model model = null;
    private Controller controller;
    private JScrollPane canvasScrollPane;

    public View(Model model, Controller controller)
    {
        super("Basic Java GUI Application");
        this.model = model;
        this.controller = controller;
        controller.addView(this);

        // We will use the default BorderLayout, with a scrolled panel in
        // the centre area, a tool bar in the NORTH area and a menu bar

        canvasScrollPane = new HVMouseWheelScrollPane(this);

        // The default when scrolling is very slow. Set the increment as follows:
        canvasScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        canvasScrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        canvas = new Canvas(model, this, controller);
        canvasScrollPane.setViewportView(canvas);
        getContentPane().add(canvasScrollPane, BorderLayout.CENTER);

        // exitAction has to be final because we reference it from within
        // an inner class

        final AbstractAction exitAction = new ExitAction(model, this, controller);
        AbstractAction openAction = new OpenAction(model, this, controller);
        AbstractAction longRunningAction = new LongRunningAction(model, this, controller);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                exitAction.actionPerformed(null);
            }
        });

        // Set up the menu bar
        JMenu fileMenu;
        fileMenu = new JMenu("File");
        fileMenu.add(openAction);
        fileMenu.add(longRunningAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);

        JMenuBar menuBar;

        menuBar = new JMenuBar();
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        // Set up the tool bar
        JToolBar toolBar;
        toolBar = new JToolBar();
        toolBar.setFloatable(true);
        toolBar.setRollover(true);
        toolBar.add(exitAction);
        toolBar.addSeparator();
        toolBar.add(openAction);
        toolBar.add(longRunningAction);

        getContentPane().add(toolBar, BorderLayout.NORTH);

        pack();
        setBounds(0, 0, 700, 800);
    }

    public void adaptToNewImage()
    {
        setCanvasSize();
    }


    /**
     * Adapt the settings for the ViewPort and scroll bars to the dimensions required.
     * This needs to be called anytime the image changes size.
     */
    protected void setCanvasSize()
    {
        canvas.setSize(canvas.getPreferredSize());

        // need this so that the scroll bars knows the size of the canvas that has to be scrolled over
        canvas.validate();
    }

    protected Canvas getCanvas()
    {
        return canvas;
    }

    @SuppressWarnings("UnusedDeclaration")
    protected JScrollPane getCanvasScrollPane()
    {
        return canvasScrollPane;
    }
}
