package SoundEffects;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	private URL click;
	private URL error;
	private URL crush;
	private URL victory;
	private URL gameOver;
	
	public Sound() {
		this.click = this.getClass().getClassLoader().getResource("SoundEffects/click.wav");
		this.error = this.getClass().getClassLoader().getResource("SoundEffects/notAMatch.wav");
		this.crush = this.getClass().getClassLoader().getResource("SoundEffects/crush.wav");
		this.victory = this.getClass().getClassLoader().getResource("SoundEffects/victory2.wav");
		this.gameOver = this.getClass().getClassLoader().getResource("SoundEffects/gameOver1.wav");
	}
	
	public void clickSound() { play(click); }
	
	public void crushSound() { play(crush); }
	
	public void errorSound() { play(error); }
	
	public void gameOverSound() { play(gameOver); }
	
	public void victorySound() { play(victory); }
	
	private void play(URL url) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.STOP) {
						clip.close();
						try {
							audioIn.close();
						} catch (IOException e) {
							System.err.println(e);
						}
					}
				}
			});
			clip.start();
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			System.err.println(e);
		}
	}
}
