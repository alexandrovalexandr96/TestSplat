
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Test extends Application {

	TreeView<String> treeview;
	Tree tree;
	Alert alert;
	AnchorPane layout;
	Scene scene;
	TextArea textarea;
	Reader reader;
	File file;
	Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initialization(primaryStage);

		stage.show();

		startNewTask();

	}

	private void initialization(Stage primaryStage) {
		stage = primaryStage;
		layout = new AnchorPane();
		scene = new Scene(layout, 1000, 500);
		stage.setResizable(false);
		stage.setScene(scene);

//		 treeview
		treeview = new TreeView<>(new TreeItem<>());
		treeview.setFocusTraversable(false);
		treeview.setShowRoot(false);
		treeview.setOnMouseClicked(e -> {
			try {
				file = new File(treeview.getSelectionModel().getSelectedItem().getValue());
				reader = new InputStreamReader(new FileInputStream(file), "utf-8");
				char[] str = new char[(int) file.length()];
				reader.read(str);
				String text = new String(str);
				textarea.setText(text + "\n");
			} catch (Exception e2) {
			}
		});
		layout.getChildren().add(treeview);
		AnchorPane.setTopAnchor(treeview, 20.0);
		AnchorPane.setLeftAnchor(treeview, 20.0);
		AnchorPane.setBottomAnchor(treeview, 45.0);
		AnchorPane.setRightAnchor(treeview, scene.getWidth() / 2 + 10);

		// textarea
		textarea = new TextArea();
		textarea.setFocusTraversable(false);
		AnchorPane.setTopAnchor(textarea, 20.0);
		AnchorPane.setLeftAnchor(textarea, scene.getWidth() / 2 + 10);
		AnchorPane.setBottomAnchor(textarea, 45.0);
		AnchorPane.setRightAnchor(textarea, 20.0);
		layout.getChildren().add(textarea);
		textarea.setWrapText(true);

		// buttonSelectAll
		Button buttonSelectAll = new Button("Выбрать весь текст");
		buttonSelectAll.setPrefSize(200, 20);
		buttonSelectAll.setOnAction(e -> {
			textarea.selectAll();
		});
		AnchorPane.setBottomAnchor(buttonSelectAll, 10.0);
		AnchorPane.setRightAnchor(buttonSelectAll, 20.0);
		layout.getChildren().add(buttonSelectAll);
		
		// buttonNewTask
		Button buttonNewTask= new Button("Новая задача");
		buttonNewTask.setPrefSize(200, 20);
		buttonNewTask.setOnAction(e-> {
			try {
				startNewTask();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		AnchorPane.setBottomAnchor(buttonNewTask, 10.0);
		AnchorPane.setRightAnchor(buttonNewTask, 240.0);
		layout.getChildren().add(buttonNewTask);

		// Инструкция
		alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Инструкция по применению");
		alert.setHeaderText(null);
		alert.setContentText(
				"Программа предназначена для поиска заданного текста в заданной директории в файлах с заданным расширением. \n В результате в левой части программы в виде дерева файловой системы будут указаны только те файлы, в которых был найде заданный текст. \n При выборе файла, его содержимое будет выведено в правой части программы с возможностью навигации по коду. \n Программа работает только с файлами в кодировке UTF-8, с другими кодировками она может работать некорректно \n По умолчанию поиск файлов осуществляется в файлах с расширением .log");
		alert.showAndWait();

	}
	
	private void startNewTask() throws InterruptedException {
		treeview.setRoot(new TreeItem<String>());
		// ввод директории с помощью стат метода в классе inputDirecory
		File path = inputDirectory.display();
		String expansion = "";
		String searchText = "";
		if (path == null)
			stage.close();
		else {
			// ввод искомого текста и расширения с помощью нестатичного класса inputExpAndSearchText
			inputExpAndSearchText inex = new inputExpAndSearchText();
			inex.display();
			expansion = inex.getExpansion();
			searchText = inex.getSearchText();
			if (expansion == null | searchText == null)
				stage.close();
			else {
				tree = new Tree(new Node(path, expansion, searchText, false));
				addChild(treeview.getRoot(), tree.getRoot());
			}
		}
	}

	private void addChild(TreeItem<String> nodeTreeView, Node nodeTree) {
		for (Node buf : nodeTree.getChild()) {
			if (!Thread.currentThread().isInterrupted()) {
				TreeItem<String> bufTreeItem = new TreeItem<String>(buf.getName());
				bufTreeItem.setExpanded(true);
				nodeTreeView.getChildren().add(bufTreeItem);
				addChild(bufTreeItem, buf);
				System.out.println(buf);
			}
		}
	}

	

}
