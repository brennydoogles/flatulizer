package com.brendondugan;

import com.brendondugan.audio.SoundPlayer;
import com.brendondugan.config.Configuration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PrimaryController {

	private SoundPlayer soundPlayer;
	@FXML
	TextField fartCountTextBox;

	public PrimaryController() {
		this.soundPlayer = new SoundPlayer();
	}

	public void doFarts(ActionEvent actionEvent) {
		int numberOfFarts = Integer.parseInt(fartCountTextBox.getText());
		for (int i = 0; i < numberOfFarts; i++) {
			try {
				String fileName = getRandomFartSound();
				soundPlayer.playSound(fileName);
				Thread.sleep(700);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getRandomFartSound() throws IOException {
		File defaultFartDirectory = Configuration.getDefaultFartDirectory();
		String[] files = defaultFartDirectory.list();
		Random rand = new Random();
		String fileName = files[rand.nextInt(files.length)];
		return String.format("%s/%s", defaultFartDirectory.getAbsolutePath(), fileName);

	}

}
