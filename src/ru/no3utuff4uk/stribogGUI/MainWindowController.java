/*
 * Implementation of hash function Stribog (GOST R 34.11.2012)
 */
package ru.no3utuff4uk.stribogGUI;

import ru.no3utuff4uk.stribog.task.HashTask;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

public class MainWindowController implements Initializable {

    File file;
    
    @FXML
    private TextField filepathField;
    @FXML
    private ChoiceBox modeChooser;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TextField hashField;
    @FXML
    private Button getHashButton;
    @FXML
    private Label statusText;
    @FXML
    private Button cancelButton;
    @FXML
    private Label easterEgg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modeChooser.setItems(FXCollections.observableArrayList("512 bit", "256 bit"));
        modeChooser.getSelectionModel().selectFirst();
    }    

    @FXML
    private void openFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open file");
        File choosedFile = chooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        
        if(choosedFile != null)
            file = choosedFile;
        if(file != null)
            filepathField.setText(file.getAbsolutePath());
        
    }
    
    HashTask hashTask;
    long startTime;
    
    @FXML
    private void getHash(ActionEvent event) {
        
        easterEgg.setVisible(false);
        statusText.setText("Execution");
        getHashButton.setVisible(false);
        cancelButton.setVisible(true);
        
        hashField.clear();
        progressBar.progressProperty().unbind();
        progressBar.setProgress(0);
        hashTask = new HashTask(file, (modeChooser.getSelectionModel().getSelectedIndex() == 0));
        progressBar.progressProperty().bind(hashTask.progressProperty());
       
        hashTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                byte[] result;
                result = (byte[]) hashTask.getValue();
                Arrays.toString(result);
                StringBuilder resultString = new StringBuilder();
                for(byte tmp: result)
                    resultString.append(Integer.toHexString(tmp & 0xff).toUpperCase()); 
                
                hashField.setText(resultString.toString());
                cancelButton.setVisible(false);
                getHashButton.setVisible(true);
                
                statusText.setText("Done! " + ((double)(System.currentTimeMillis() - startTime))/1000 + " sec");
                if(((System.currentTimeMillis() - startTime)/1000) > 10)
                    easterEgg.setVisible(true);
                
            }
        });
        startTime = System.currentTimeMillis();
        new Thread(hashTask).start();
        
    }

    @FXML
    private void overFile(DragEvent event) {
                event.acceptTransferModes(TransferMode.COPY);
                event.consume();
    }

    @FXML
    private void dropFile(DragEvent event) {
         Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    file = db.getFiles().get(0);
                    filepathField.setText(file.getAbsolutePath());
                    success = true;
                }
                
                event.setDropCompleted(success);

                event.consume();
    }

    @FXML
    private void cancelGetHash(ActionEvent event) {
        getHashButton.setVisible(true);
        cancelButton.setVisible(false);
        hashTask.cancel();
        statusText.setText("Canceled! " + ((double)(System.currentTimeMillis() - startTime))/1000 + " sec");
    }

    
}
