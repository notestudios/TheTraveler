package com.notestudios.menus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.util.Button;

interface MenuFunctions {
    /**
     * Runs when this Menu is instantiated.
     * @author
     * <a href="https://github.com/retrozinndev">retrozinndev</a>
     */
    public void onCreate();
    /**
     * Extra tick() function. Call tick() to run it.
     * @author
     * <a href="https://github.com/retrozinndev">retrozinndev</a>
     */
    public void loop();
    /**
     * Extra render(Graphics2D) function. Call render(Graphics2D) to run it.
     * @param g
     * Java Graphics2D instance.
     * @author
     * <a href="https://github.com/retrozinndev">retrozinndev</a>
     */
    public void paint(Graphics2D g);
}


public class Menu implements Runnable, MenuFunctions {

    public List<Button> menuButtons = null;
    public boolean useTransparentBG = false;
    public UI menuHandler = Game.ui;
    private Thread thread = new Thread();
    public boolean active = false;
    private String name = this.getClass().getSimpleName();
    private boolean useBackButton = true;
    protected int renderLayer = 0;
    public int generalTransparency = 255;

    public Menu(List<Button> buttons) {
        menuButtons.addAll(buttons);
        onCreate();
    }

    public final void tick() {
        loop();
        if(menuButtons != null) {
            for(Button btn : menuButtons) 
                btn.functions();
        }
    }

    /**
     * Renders the Menu and everything that's in paint(Graphics2D).
     */
    public final void render() {
        Graphics2D g = (Graphics2D) Game.getRenderImage().getGraphics();
        UI.useAntiAliasing(g);
        if(!useTransparentBG) {
            g.setColor(new Color(39, 39, 39, generalTransparency));
        } else {
            g.setColor(new Color(39, 39, 39, 220));
        }
		g.fillRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
		g.setColor(Color.white);
		g.drawRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
        if(renderLayer == 0) paint(g);
        if(menuButtons != null) {
            for(Button btn : menuButtons) 
                btn.render(g);
        }
        if(renderLayer >= 1) paint(g);
    }

    protected synchronized void start() {
        thread.start();
        active = true;
    }

    protected synchronized void stop() {
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        } finally {
            active = false;
        }
    }

    @Override
    public void run() {
        while(isActive()) {
            tick();
            render();
        }
    }

    public void setCustomName(String customName) {
        this.name = customName;
    }
    public void disableBackButton() {
        if(useBackButton == true) 
            useBackButton = false;
    }
    public String getMenuName() {
        return name;
    }
    public boolean isActive() {
        return active;
    }

    public void onCreate() {}

    public void loop() {}

    public void paint(Graphics2D g) {}
}
