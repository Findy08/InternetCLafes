package main;

import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainView;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		MainView mainView = new MainView(primaryStage);
		UserController userController = new UserController(mainView);
	}

}
