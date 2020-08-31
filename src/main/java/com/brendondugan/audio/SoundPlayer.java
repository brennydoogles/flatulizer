package com.brendondugan.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
	private AudioInputStream audioInputStream;

	public SoundPlayer() {

	}

	public void playSound(String fileName) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}
}
