package GUIPackage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

import EnginePackage.*;
import SoundEffects.Sound;

public class Gui extends JFrame
{
	//private static final long serialVersionUID = 1L;
	
	JFrame frame;
	BackgroundPanel backgroundPanel;
	
	Engine candyCrush;
	JPanel centralPanel;

	CustomJButton newGame = new CustomJButton(null, "New game", 25, "Arial",Color.MAGENTA, Color.BLUE);
	CustomJButton movesLeft;
	CustomJButton music = new CustomJButton(null, null, 25, "Arial", Color.MAGENTA, Color.BLUE);
	JButton buttons[][];
	int stI = -1;
	int stJ = -1;
	
	Sound sound;
	boolean musicFlag = true;
	

	CustomJButton  pink, blue, green, yellow;
	
	boolean endOfGame, victory; 
	
	public Gui()
	{

		musicFlag = true;
		sound = new Sound();
		frame = new JFrame();
		frame.setTitle("Candy Crush");
        frame.setSize(1200, 900);
        frame.setLocationRelativeTo(null);
        
        //postavljanje pozadine
        ImageIcon backgroundImageIcon = new ImageIcon("Images/Back7.jpeg");
        Image backgroundImage = backgroundImageIcon.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage, 0.8f);
        backgroundPanel.setLayout(null);
        frame.getContentPane().add(backgroundPanel);
        
	
		candyCrush = new Engine();
		endOfGame = false;
		victory = false;
	
		int dimension = candyCrush.getDimension();
		buttons = new JButton[dimension][dimension];
		centralPanel = new JPanel(new GridLayout(dimension, dimension));
		
		movesLeft = new CustomJButton(null, "Moves left :" + candyCrush.getMovesLeft(), 25, "Arial", Color.CYAN, Color.CYAN);
		movesLeft.setBounds(390, 20, 400, 60 );

		newGame.setForeground(Color.WHITE);
		newGame.setBounds(940, 790, 230, 60);
		
		music.setBounds(1010, 680, 90, 90);
	
		pink = new CustomJButton(new ImageIcon("Images/pink.png"), ""+candyCrush.getPink(), 40, "Arial", Color.MAGENTA, Color.CYAN);
		pink.setBounds(200, 100, 180, 80);
		
		green = new CustomJButton(new ImageIcon("Images/green.png"), ""+ candyCrush.getGreen(), 40, "Arial", Color.GREEN, Color.CYAN);
		green.setBounds(400, 100, 180, 80);
		
		blue = new CustomJButton(new ImageIcon("Images/blue.png"), ""+candyCrush.getBLue(), 40, "Arial", Color.BLUE, Color.CYAN);
		blue.setBounds(600, 100, 180, 80);
		
		yellow = new CustomJButton(new ImageIcon("Images/yellow.png"), ""+candyCrush.getYellow(), 40, "Arial", Color.ORANGE, Color.CYAN);
		yellow.setBounds(800, 100, 180, 80);

		for(int i = 0; i<buttons.length; i++)
		{
			for(int j = 0; j<buttons.length; j++)
			{
				JButton newButton = new IndexButton(i, j);
				newButton.setPreferredSize(new Dimension(80, 80));
				newButton.setBorderPainted(false);
				newButton.setBackground(Color.cyan);
			
				newButton.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						IndexButton clickedButton = (IndexButton)e.getSource();
						if(musicFlag == true) {sound.clickSound();}
						
						if(stI == -1 && stJ == -1)
						{
							stI = clickedButton.getI();
							stJ = clickedButton.getJ();
						}
						else
						{
							boolean hit = false;
							hit = candyCrush.play(stI, stJ, clickedButton.getI(), clickedButton.getJ());

							if(hit == true) 
							{
								if(musicFlag == true) {sound.crushSound();}
									
								candyCrush.dropCandies();
								candyCrush.addNewCandies();
								while(candyCrush.fullTable() == false || candyCrush.newMatch() == true)
								{
									if(musicFlag == true && candyCrush.newMatch() == true) {sound.crushSound();}
									candyCrush.dropCandies();
									candyCrush.addNewCandies();
								}						
							}
							else
								if(musicFlag == true) sound.errorSound();
							
							
							if(candyCrush.CurrentStateOfGame() == StateOfGame.victory)
							{
								if(musicFlag == true) {sound.victorySound();}
								endOfGame = true;
								victory = true;
							}
							else if(candyCrush.CurrentStateOfGame() == StateOfGame.gameOver)
							{
								if(musicFlag == true) {sound.gameOverSound();}
								endOfGame = true;
							}

							if(endOfGame == true)
							{
								int options;
								if(victory == true)
									options = JOptionPane.showConfirmDialog(null, "VICTORY! New game?", "End of game.", JOptionPane.YES_NO_OPTION);
								else
									options = JOptionPane.showConfirmDialog(null, "GAMEOVER... New game?", "End of game.", JOptionPane.YES_NO_OPTION);

								if(options == JOptionPane.YES_OPTION)
								{
									candyCrush = new Engine();
									endOfGame = false;
									victory = false;
									refreshGui();
								}
								else
									{System.exit(0);}

							}
							refreshGui();
							stI = -1;
							stJ = -1;
						}
					}
				});
				
				centralPanel.add(newButton);
				buttons[i][j] = newButton;
			}
		}
		
		newGame.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				candyCrush = new Engine();
				refreshGui();
			}
		});
		
		music.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        if (musicFlag == true) 
		        {
		            music.setIcon(new ImageIcon("Images/soundOff.png"));
		            musicFlag = false;
		        } 
		        else 
		        {
		            music.setIcon(new ImageIcon("Images/soundOn1.png"));
		            musicFlag = true;
		        }
		    }
		});
		
		
		centralPanel.setBounds(280, 200, 640, 640);
		backgroundPanel.add(centralPanel, BorderLayout.CENTER);
		backgroundPanel.add(newGame);
		backgroundPanel.add(music);
		backgroundPanel.add(movesLeft);
		backgroundPanel.add(pink);
		backgroundPanel.add(blue);
		backgroundPanel.add(green);
		backgroundPanel.add(yellow);
		
		refreshGui();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void refreshGui()
	{
		pink.setText(""+ candyCrush.getPink());
		blue.setText(""+ candyCrush.getBLue());
		yellow.setText(""+ candyCrush.getYellow());
		green.setText(""+ candyCrush.getGreen());
		
		if(musicFlag ==  true) 
			{ music.setIcon(new ImageIcon("Images/soundOn1.png")); }
		else if(musicFlag == false)
			{ music.setIcon(new ImageIcon("Images/soundOff.png")); }
		
		if(candyCrush.getMovesLeft() <= 5)
		{
			movesLeft.setStartColor(Color.RED);
			movesLeft.setEndColor(Color.RED);
			movesLeft.setForeground(Color.WHITE);
		}
		else
		{
			movesLeft.setStartColor(Color.CYAN);
			movesLeft.setEndColor(Color.CYAN);
			movesLeft.setForeground(Color.black);
		}
			
		movesLeft.setText("Moves left: "+candyCrush.getMovesLeft());
		
		for (int i =0; i<buttons.length; i++)
			for(int j = 0; j<buttons.length; j++)
				buttons[i][j].setIcon(new ImageIcon("Images/"+candyCrush.getTableOfIndex(i, j)+".png"));
	}
}
