import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class inputExpAndSearchText {

	String expansion;
	String searchText;

	public void display() {
		AnchorPane layout = new AnchorPane();
		Scene scene = new Scene(layout, 400, 100);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Расширение файла поиска");
		stage.initModality(Modality.APPLICATION_MODAL);

		TextField textField1 = new TextField();
		textField1.setPromptText("Расширение файла (log, txt ...)");
		textField1.setPrefSize(200, 20);
		textField1.setFocusTraversable(false);
		AnchorPane.setRightAnchor(textField1, scene.getWidth() / 2 - textField1.getPrefWidth() / 2);
		AnchorPane.setTopAnchor(textField1, scene.getHeight() / 2 - textField1.getPrefHeight() / 2 );
		layout.getChildren().add(textField1);

		TextField textField2 = new TextField();
		textField2.setPromptText("Искомый текст");
		textField2.setPrefSize(200, 20);
		textField2.setFocusTraversable(false);
		AnchorPane.setRightAnchor(textField2, scene.getWidth() / 2 - textField2.getPrefWidth() / 2);
		AnchorPane.setTopAnchor(textField2, scene.getHeight() / 2 - textField2.getPrefHeight() / 2 - 30);
		layout.getChildren().add(textField2);
		
		Button buttonSubmitExpansion = new Button("Задать");
		buttonSubmitExpansion.setPrefSize(100, 20);
		buttonSubmitExpansion.setOnAction(e -> {
			expansion = textField1.getText();
			if(expansion.equals("")) expansion=".log";
			searchText=textField2.getText();
			stage.close();
		});
		AnchorPane.setRightAnchor(buttonSubmitExpansion, scene.getWidth() / 2 - buttonSubmitExpansion.getPrefWidth() / 2);
		AnchorPane.setTopAnchor(buttonSubmitExpansion, scene.getHeight() / 2 - textField1.getPrefHeight() / 2 + 30);
		layout.getChildren().add(buttonSubmitExpansion);

		stage.showAndWait();
	}
	
	public String getExpansion() {
		return expansion;
	}
	
	public String getSearchText() {
		return searchText;
	}

}
