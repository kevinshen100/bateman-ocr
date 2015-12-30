package layout.view.actions;

import layout.controller.Controller;
import layout.model.Model;
import layout.utils.SimpleFileFilter;
import layout.utils.UnsupportedImageTypeException;
import layout.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * User: Alan P. Sexton
 * Date: 20/06/13
 * Time: 21:04
 */
public class OpenAction extends AbstractAction
{
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
    private Model model;
    private View view;
    private Controller controller;

    private JFileChooser imageFileChooser = null;

    {
        putValue(NAME, "Open new project image file...");
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/layout/icons/fileopen.png")));
        putValue(SHORT_DESCRIPTION, "Opens a new image file");
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
    }

    public OpenAction(Model model, View view, Controller controller)
    {
        this.view = view;
        this.model = model;
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (imageFileChooser == null)
        {
            imageFileChooser = new JFileChooser(".");
            //            imageFileChooser = new JFileChooser(System.getProperty("user.dir"));
            SimpleFileFilter filter = new SimpleFileFilter();
            filter.addExtension(".png");
            filter.addExtension(".tif");
            filter.addExtension(".gif");
            filter.addExtension(".jpg");
            filter.addExtension(".wbmp");
            filter.addExtension(".raw");
            filter.addExtension(".bmp");
            filter.addExtension(".pnm");
            filter.addExtension(".jpeg");
            filter.setDescription("image files");
            imageFileChooser.setFileFilter(filter);
        }
        imageFileChooser.setDialogTitle("Choose an image file to open");
        int result = imageFileChooser.showOpenDialog(view);
        try
        {
            if (result == JFileChooser.APPROVE_OPTION)
            {
                File f = imageFileChooser.getSelectedFile();
                controller.loadImage(f);
            }
        }
        catch (IOException ioe)
        {
            JOptionPane.showMessageDialog(view,
                                          ioe.getMessage(),
                                          "Accessing Image File",
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch (UnsupportedImageTypeException uite)
        {
            JOptionPane.showMessageDialog(view,
                                          uite.getMessage(),
                                          "Reading Image File",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

}
