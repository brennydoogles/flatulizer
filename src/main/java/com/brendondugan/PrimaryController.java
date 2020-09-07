package com.brendondugan;

import com.brendondugan.config.Configuration;
import com.brendondugan.config.ConfigurationKey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PrimaryController {

	@FXML
	TextField fartCountTextBox;

	public PrimaryController() {

	}

	public void doFarts(ActionEvent actionEvent) {
		int numberOfFarts = Integer.parseInt(fartCountTextBox.getText());
		for (int i = 0; i < numberOfFarts; i++) {
			try {
				String fileName = getRandomFartSound();
				String stringVal = Configuration.getValueOrDefault(ConfigurationKey.DEFAULT_SOUND_DELAY, "700");
				App.playAudioFileWithTimeout(fileName, Integer.parseInt(stringVal));
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
