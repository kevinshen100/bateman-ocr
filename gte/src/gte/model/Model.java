package gte.model;

import gte.utils.ImageFile;
import gte.utils.UnsupportedImageTypeException;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Alan P. Sexton
 * Date: 20/06/13
 * Time: 23:36
 */

/**
 * The <code>Model</code> class manages the data of the application
 * <p/>
 * Other than possibly a draw method, which draws a representation of the object on a graphics context, and
 * possibly a toString method, which generates a <code>String</code> representation of the object, it should
 * not know about the user interface
 */
public class Model
{
    private BufferedImage image = null;
    private List<Rectangle> rects = new ArrayList<Rectangle>();
    private ArrayList<Component> comps = new ArrayList<Component>();
    private ArrayList<Component> selected = new ArrayList<Component>();
    private File currentFile;



    public Model()
    {
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public List<Rectangle> getRects()
    {
        return rects;
    }

    public ArrayList<Component> getComps() { return comps; }

    public Component whichClicked(Point p) {
        for (Component c: comps) {
            if (c.contains(p)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Component> allSelected(Rectangle r) {
        ArrayList<Component> out = new ArrayList<Component>();
        for (Component c: comps) {
            if (r.intersects(c.getImageCoords())) {
                out.add(c);
            }
        }
        return out;
    }

    public void selectComponents(ArrayList<Component> a) {
        for (Component c:a) {
            selectComponent(c);
        }
    }

    /**
     * Sets or replaces the current image in the <code>Model</code>
     *
     * @param bi the image to set in the <code>Model</code>
     */

    public void setImage(BufferedImage bi)
    {
        image = bi;
        rects.clear();
    }

    public void selectComponent(Component c) {
        if (c.getImageCoords().getHeight()>1000 && c.getImageCoords().getWidth()>1000) {
            return;
        }
        selected.add(c);
        c.select();
        System.out.println(c.getImageCoords());
    }

    public void toggleComponents(ArrayList<Component> a) {
        for (Component c:a) {
            toggleComponent(c);
        }
    }

    public void toggleComponent(Component c) {
        if (c.getImageCoords().getHeight()>1000 && c.getImageCoords().getWidth()>1000) {
            return;
        }
        if (c.getSelected()) {
            selected.remove(c);
        } else {
            selected.add(c);
        }
        c.toggleSelected();
    }

    public void deselectComponent(Component c) {
        selected.remove(c);
        c.deselect();
    }

    public void deselectAllComponents() {
        for (Component c: selected) {
            c.deselect();
        }
        selected.clear();
    }

    public void clearComps() {
        comps.clear();
    }

    public Dimension getDimensions()
    {
        if (image != null)
            return new Dimension(image.getWidth(), image.getHeight());
        else
            return new Dimension(0,0);
    }

    /**
     * Adds a new <code>Rectangle</code> to the <code>Model</code>
     *
     * @param rect the <code>Rectangle</code> to add to the <code>Model</code>
     */
    public void addRect(Rectangle rect)
    {
        rects.add(rect);
    }

    /**
     * Adds a new <code>Component</code> to the <code>Model</code>
     *
     * @param comp the <code>Component</code> to add to the <code>Model</code>
     */
    public void addComponent(Component comp)
    {
        comps.add(comp);
        //System.out.println(getComps());
    }

    /**
     * Tests if the model is active, i.e. whether it currently has an image
     *
     * @return <code>true</code> if the model has an image, false otherwise
     */
    public boolean isActive()
    {
        return image != null;
    }

    public File getCurrentFile() { return this.currentFile; }

    public void loadImage(File file)
            throws IOException, UnsupportedImageTypeException
    {
        loadImageWithNumber(file, 0);
        /*
        currentFile = file;
        ImageFile newImageFile = new ImageFile(file);
        int numImages = newImageFile.getNumImages();
        if (numImages == 0)
            throw new IOException("Image file contains no images");
        BufferedImage bi = newImageFile.getBufferedImage(0);
        setImage(bi);
        */

    }

    public void loadImageWithNumber(File file, int n)
            throws IOException, UnsupportedImageTypeException
    {
        currentFile = file;
        ImageFile newImageFile = new ImageFile(file);
        int numImages = newImageFile.getNumImages();
        if (numImages == 0)
            throw new IOException("Image file contains no images");
        BufferedImage bi = newImageFile.getBufferedImage(n);
        setImage(bi);

    }


}
