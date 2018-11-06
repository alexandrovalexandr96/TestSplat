import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class inputDirectory {

	static File f;

	public static File display() {
		DirectoryChooser DChoose = new DirectoryChooser();
		AnchorPane layout = new AnchorPane();
		Scene scene = new Scene(layout, 300, 100);
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Выбор папки поиска");
		stage.setResizable(false);
		stage.setScene(scene);
		

		// label
		Label labelerr = new Label("ОШИБКА");
		labelerr.setTextFill(javafx.scene.paint.Color.RED);
		labelerr.setPrefSize(60, 20);
		labelerr.setVisible(false);
		AnchorPane.setTopAnchor(labelerr, 10.0);
		AnchorPane.setRightAnchor(labelerr, scene.getWidth() / 2 - labelerr.getPrefWidth() / 2);
		layout.getChildren().add(labelerr);

		// button selectDirectory
		Button buttonSelectDirectory = new Button("Выбрать папку");
		buttonSelectDirectory.setOnAction(e -> {
			f = DChoose.showDialog(stage);
			if (f == null)
				labelerr.setVisible(true);
			else
				stage.close();
		});
		buttonSelectDirectory.setPrefSize(100, 20);
		AnchorPane.setRightAnchor(buttonSelectDirectory,
				scene.getWidth() / 2 - buttonSelectDirectory.getPrefWidth() / 2);
		AnchorPane.setTopAnchor(buttonSelectDirectory,
				scene.getHeight() / 2 - buttonSelectDirectory.getPrefHeight() / 2);
		layout.getChildren().add(buttonSelectDirectory);

		stage.showAndWait();
		return f;
	}

}
