package gte.model;

import gte.utils.ImageFile;
import gte.utils.UnsupportedImageTypeException;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kns10 on 2/24/16.
 */
public class Component {

    private String src_image;
    private int page;
    private int page_width;
    private int page_height;
    private String cc_image;
    private String image_basePath = "gte/data/sample_ccs/page-";
    private Rectangle imageCoords;

    private String[] everythingElse;

    private BufferedImage heldImage;


    public Component(int x, int y, int w, int h, BufferedImage b) {
        imageCoords = new Rectangle(x, y, w, h);
        heldImage = b;
    }

    public BufferedImage getHeldImage() {
        return heldImage;
    }

    public Rectangle getImageCoords() {
        return imageCoords;
    }

    public int getPage() {
        return page;
    }
    public Component(String s) {
        String[] current = s.split(",");
        src_image = current[0].replace("\"","");
        page = Integer.parseInt(current[1]);
        page_width  = Integer.parseInt(current[2]);
        page_height  = Integer.parseInt(current[3]);
        cc_image  = current[4].replace("\"","");
        imageCoords = new Rectangle(Integer.parseInt(current[5]),
                Integer.parseInt(current[6]),
                Integer.parseInt(current[7]),
                Integer.parseInt(current[8]));
        everythingElse = new String[current.length-9];
        System.arraycopy(current, 9, everythingElse, 0, current.length-9);
        File img = new File(image_basePath+String.format("%04d", page)+"/"+cc_image);

        try {
            heldImage = new BufferedImage(page_width,page_height, BufferedImage.TYPE_INT_ARGB);
            heldImage = ImageIO.read(img);

        } catch (Exception ex) {
            System.out.println("error reading file "+image_basePath+String.format("%04d", page)+"/"+cc_image);
            System.out.println(ex);
        }

    }

}
