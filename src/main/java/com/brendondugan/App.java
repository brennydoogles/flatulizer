package com.brendondugan;

import com.brendondugan.config.Configuration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Scene scene;
	private static ExecutorService audioPlayer = null;

	@Override
	public void start(Stage stage) throws IOException {
		scene = new Scene(loadFXML("primary"), 800, 680);
		stage.setScene(scene);
		stage.show();
	}

	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) throws IOException {
		Configuration.initialize();
		Configuration.getDefaultFartDirectory();
		audioPlayer = Executors.newSingleThreadExecutor();
		launch();
	}

	public static void playAudioFileWithTimeout(String filename, int postDelay) {
		audioPlayer.execute(() -> {
			System.out.println("Playing " + filename);
			try {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile());
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
				while (clip.getFrameLength() != clip.getFrameLength()) {
				}
				clip = null;
				TimeUnit.MILLISECONDS.sleep(postDelay);
				System.out.println("Finished Playing " + filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		audioPlayer.shutdown();
		audioPlayer.awaitTermination(45, TimeUnit.SECONDS);
		if (!audioPlayer.isShutdown()) {
			audioPlayer.shutdownNow();
		}
	}
}