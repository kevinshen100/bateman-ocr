package gte.view;

import gte.controller.Controller;
import gte.model.Model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by kns10 on 4/6/16.
 */
public class CanvasKeyboardListener implements KeyListener {

    Model model;
    View view;
    Controller controller;

    int x1;
    int y1;
    int x2;
    int y2;
    boolean mouseDown = false;

    public CanvasKeyboardListener(Model model, View view, Controller controller)
    {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch( keyCode ) {
            case KeyEvent.VK_UP:
                model.selectComponent(model.nextAbove());
                System.out.println("up");
                break;
            case KeyEvent.VK_DOWN:
                model.selectComponent(model.nextBelow());
                System.out.println("d");
                break;
            case KeyEvent.VK_LEFT:
                model.selectComponent(model.nextLeft());
                System.out.println("l");
                break;
            case KeyEvent.VK_RIGHT :
                model.selectComponent(model.nextRight());
                System.out.println("r");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        System.out.println("lel");
    }

    @Override
    public void keyTyped(KeyEvent e){
        System.out.println("lol");
    }
}
