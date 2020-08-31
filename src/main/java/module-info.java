module com.brendondugan {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;

	opens com.brendondugan to javafx.fxml;
	exports com.brendondugan;
}