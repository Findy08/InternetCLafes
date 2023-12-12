package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class StartScene {

	Button reg = new Button("Register");
	Button login = new Button("Login");
	
	private GridPane startForm() {
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
		return form;
        
//        reg.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//            	try {
//            		
//            	}catch() {
//            		
//            	}
//            }
//        }
	}

}
