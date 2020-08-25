module com.brendondugan {
	requires javafx.controls;
	requires javafx.fxml;

	opens com.brendondugan to javafx.fxml;
	exports com.brendondugan;
}