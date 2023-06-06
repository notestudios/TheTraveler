package com.notestudios.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.notestudios.buttons.GJLogin_Button0;
import com.notestudios.buttons.GJLogin_Button1;
import com.notestudios.buttons.GJLogin_Button2;
import com.notestudios.main.Game;

public class GJLogin {

    public BufferedImage gjLogo;
    public BufferedImage noteLogo;
    public BufferedImage travelerLogo;
    public GJLogin_Button0 loginbtn;
    public GJLogin_Button1 cancelbtn;
    public GJLogin_Button2 logoutbtn;
    public static String curTextBox = "";

    public GJLogin() {
        try {
            gjLogo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/gj.png")));
            noteLogo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/note.png")));
            travelerLogo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/TRAVELER logo 200x200.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loginbtn = new GJLogin_Button0(330, 380, 300, 40, 0, "Login");
        cancelbtn = new GJLogin_Button1(330, 430, 145, 40, 0, "Cancel");
        logoutbtn = new GJLogin_Button2(330+150, 430, 149, 40, 0, "Logout");
        Game.button.add(loginbtn);
        Game.button.add(cancelbtn);
        Game.button.add(logoutbtn);
    }

    public int usernameTextBoxX = 330;
    public int usernameTextBoxY = 230;
    public int UsernameTextBoxWidth = 300;
    public int UsernameTextBoxHeight = 50;

    public int tokenTextBoxX = 330;
    public int tokenTextBoxY = 300;
    public int tokenTextBoxWidth = 300;
    public int tokenTextBoxHeight = 50;


    public void tick() {
        loginbtn.functions();
        cancelbtn.functions();
        if(Game.isLoggedIn) {
        	logoutbtn.functions();
        }
    }

    public void render(Graphics2D g) {

    	if(Game.AAEnabled) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
        //g.drawImage(noteLogo, Game.WIDTH+20, Game.HEIGHT*Game.SCALE-130, 160, 160, null);
        g.drawImage(gjLogo, 440, -30, 360, 360, null);
        g.drawImage(travelerLogo, Game.WIDTH*Game.SCALE-700, 15, 200, 200, null);

        loginbtn.render(g);
        cancelbtn.render(g);
        if(Game.isLoggedIn) {
        	logoutbtn.render(g);
        }

        //username
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(new Color(40, 40, 42));
        g.fillRoundRect(usernameTextBoxX, usernameTextBoxY, UsernameTextBoxWidth, UsernameTextBoxHeight, 40, 40);
        if(!curTextBox.equals("username")) {
            g.setColor(Color.white);
        } else {
            g.setColor(new Color(204, 255, 0));
        }
        g.drawRoundRect(usernameTextBoxX, usernameTextBoxY, UsernameTextBoxWidth, UsernameTextBoxHeight, 40, 40);
        g.setColor(Color.white);
        String usr = "User: "+Game.USER_NAME;
        g.drawString(usr, usernameTextBoxX + 10, 230 + loginbtn.getHeight() / 2 + 12);

        //token
        g.setColor(new Color(40, 40, 42));
        g.fillRoundRect(tokenTextBoxX, tokenTextBoxY, tokenTextBoxWidth, tokenTextBoxHeight, 40, 40);
        if(!curTextBox.equals("token")) {
            g.setColor(Color.white);
        } else {
            g.setColor(new Color(204, 255, 0));
        }
        g.drawRoundRect(tokenTextBoxX, tokenTextBoxY, tokenTextBoxWidth, tokenTextBoxHeight, 40, 40);
        g.setColor(Color.white);
        String tkn = "Token: "+Game.USER_TOKEN;
        g.drawString(tkn, tokenTextBoxX + 10, 300 + loginbtn.getHeight() / 2 + 12);
    }
    /*
    public void userCredentialsSave(String var) {
    	// A variável String a ser salva no arquivo
        String str = var;

        // O nome do arquivo a ser escrito
        String fileName = "usr.txt";

        // O encoding do arquivo
        String encoding = "UTF-8";

        try {
            // Criar um PrintWriter a partir do nome do arquivo e do encoding
            PrintWriter writer = new PrintWriter(fileName, encoding);

            // Escrever a variável String no arquivo
            writer.println(str);

            // Fechar o PrintWriter
            writer.close();
        } catch (IOException e) {
            // Tratar a exceção de entrada e saída
            e.printStackTrace();
        }
    }
    
    public void userCredentialsLoad(String var) {
	    // O nome do arquivo a ser lido
    	String fileName = "usr.txt";
	
	    // O encoding do arquivo
	    String encoding = "UTF-8";
	
	    // Um StringBuilder para armazenar o conteúdo do arquivo
	    StringBuilder content = new StringBuilder();
	
	    try {
	        // Criar um BufferedReader a partir de um FileInputStream e um InputStreamReader
	        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
	
	        // Ler uma linha do arquivo
	        String line = reader.readLine();
	
	        // Enquanto a linha não for nula, adicionar ao conteúdo e ler a próxima linha
	        while (line != null) {
	            content.append(line).append("\n");
	            line = reader.readLine();
	        }
	
	        // Fechar o BufferedReader
	        reader.close();
	    } catch (IOException e) {
	        // Tratar a exceção de entrada e saída
	        e.printStackTrace();
	    }
	
	    // Imprimir o conteúdo do arquivo
	    var = content.toString().trim();
	    System.out.println(content.toString().trim());
    }
    
    public void tokenCredentialsSave(String var) {
    	// A variável String a ser salva no arquivo
        String str = var;

        // O nome do arquivo a ser escrito
        String fileName = "tkn.txt";

        // O encoding do arquivo
        String encoding = "UTF-8";

        try {
            // Criar um PrintWriter a partir do nome do arquivo e do encoding
            PrintWriter writer = new PrintWriter(fileName, encoding);

            // Escrever a variável String no arquivo
            writer.println(str);

            // Fechar o PrintWriter
            writer.close();
        } catch (IOException e) {
            // Tratar a exceção de entrada e saída
            e.printStackTrace();
        }
    }
    
    public void tokenCredentialsLoad(String var) {
	    // O nome do arquivo a ser lido
    	String fileName = "tkn.txt";
	
	    // O encoding do arquivo
	    String encoding = "UTF-8";
	
	    // Um StringBuilder para armazenar o conteúdo do arquivo
	    StringBuilder content = new StringBuilder();
	
	    try {
	        // Criar um BufferedReader a partir de um FileInputStream e um InputStreamReader
	        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
	
	        // Ler uma linha do arquivo
	        String line = reader.readLine();
	
	        // Enquanto a linha não for nula, adicionar ao conteúdo e ler a próxima linha
	        while (line != null) {
	            content.append(line).append("\n");
	            line = reader.readLine();
	        }
	
	        // Fechar o BufferedReader
	        reader.close();
	    } catch (IOException e) {
	        // Tratar a exceção de entrada e saída
	        e.printStackTrace();
	    }
	
	    // Imprimir o conteúdo do arquivo
	    var = content.toString().trim();
	    System.out.println(content.toString().trim());
    }*/
}
