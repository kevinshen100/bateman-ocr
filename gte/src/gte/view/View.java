package gte.view;

import gte.controller.Controller;
import gte.model.Model;
import gte.view.actions.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User: Alan P. Sexton
 * Date: 21/06/13
 * Time: 13:42
 */
public class View extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Canvas canvas = null;
    @SuppressWarnings("unused")
	private Model model = null;
    @SuppressWarnings("unused")
	private Controller controller;
    private JScrollPane canvasScrollPane;

    private ToggleAction toggleAction;
    private int pageNum;

    public View(Model model, Controller controller)
    {
        super("Ground Truth Engine");
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
        toggleAction = new ToggleAction(model, this, controller);
        AbstractAction previousPage = new PreviousPage(model, this, controller);
        AbstractAction nextPage = new NextPage(model, this, controller);

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

        fileMenu.add(toggleAction);
        fileMenu.addSeparator();
        fileMenu.add(previousPage);
        fileMenu.add(nextPage);


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

        toolBar.add(toggleAction);
        toolBar.addSeparator();
        toolBar.add(previousPage);
        toolBar.add(nextPage);


        getContentPane().add(toolBar, BorderLayout.NORTH);

        pack();
        setBounds(0, 0, 700, 800);
    }

    public void adaptToNewImage()
    {
        setCanvasSize();
    }

    public boolean getToggled() {return toggleAction.isToggled();}
    public void toggle() {
        ActionEvent tempAction = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null);
        this.toggleAction.actionPerformed(tempAction);
    }

    public void setPageNum(int x) { this.pageNum = x; }
    public int getPageNum() { return this.pageNum; }
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

    protected JScrollPane getCanvasScrollPane()
    {
        return canvasScrollPane;
    }
}
