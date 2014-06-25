package Test;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.text.*;

public class Test extends Application{

    private Desktop desktop = Desktop.getDesktop();
    
    @Override
    public void start(Stage stage)
    {//Using javafx file chooser
        //set text for mainStage to initially be "file choosen"
        Text sceneText = new Text(100, 100, "File Choosen");
        //set up Scene for mainstage
       Scene scene = new Scene(new Group(sceneText));
       //create a filechooser
       //doesn't like to be closed using exit button, prior to a file being choosen
       FileChooser fc = new FileChooser();
       fc.setTitle("Choose File");
       //can use setInitialDirectory() to set initial directory as per
       //collected from command line
       //returns a File on closing of the openDialog box (the one selected)
       File retFile = fc.showOpenDialog(stage);
       
       stage.setTitle("File Choosen");
       sceneText.setText(retFile.getName());
               
       
       stage.setScene(scene);
       stage.show();
        
        //using javafx and file dialog box java
    }
  
    public static void main(String[] args) {
        
        Application.launch(args);
    }
    
}
