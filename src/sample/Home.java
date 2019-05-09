package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class Home {

    private ButtonType browseBT = new ButtonType("Browse File");
    private ButtonType cancelBT = new ButtonType("Cancel");
    private Alert emptyFileAlert = new Alert(AlertType.INFORMATION, "", cancelBT, browseBT);
    private Alert alert = new Alert(AlertType.INFORMATION);
    protected static File file = new File("");
    private FileChooser fileChooser = new FileChooser();

    @FXML
    protected Label fileStatus;

    @FXML
    private TextField filePath;

    @FXML
    private Label fileName;

    @FXML
    void getFile(ActionEvent event) {

        if(filePath.getText().equals("")) {

            fileName.setText("");
            fileStatus.setText("");

            emptyFileAlert.setHeaderText("Enter your file location or browse file");

            Optional<ButtonType> alertButtons = emptyFileAlert.showAndWait();

            if(alertButtons.get() == browseBT) {

                browseFileButton(null);
            }

        }
        else {

            file = new File(filePath.getText().trim());

            if(file.isFile()) {

                fileName.setText(file.getName());
                fileStatus.setText(file.canWrite() ? "Unlock" : "Lock");
            }
            else {

                alert.setHeaderText("File could not found.");
                alert.show();
            }
        }
    }

    @FXML
    void browseFileButton(ActionEvent event) {

        file = fileChooser.showOpenDialog(null);
        fileName.setText(file.getName().trim());
        filePath.setText(file.getAbsolutePath());
        fileStatus.setText(file.canWrite() ? "Unlock" : "Lock");
    }

    @FXML
    void refreshButton(ActionEvent event) {

        fileName.setText("");
        fileStatus.setText("");
        filePath.setText("");
        file = new File("");

    }

    @FXML
    void lockButton(ActionEvent event) {

        if(file.isFile()) {

            if(!file.canWrite()) {

                alert.setAlertType(AlertType.INFORMATION);
                alert.setHeaderText("File is already locked");
                alert.show();
            }
            else {

                try {

                    Parent root = FXMLLoader.load(getClass().getResource("lockerDisplay.fxml"));

                    Stage stage = new Stage();

                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);

                    stage.setOnHiding(e -> {

                        fileStatus.setText(file.canWrite() ? "Unlock" : "Lock");

                    });

                    stage.show();

                }catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
        else {

            alert.setAlertType(AlertType.ERROR);
            alert.setHeaderText("Please slect your file first.");
            alert.show();
        }

    }

    @FXML
    void unlockButton(ActionEvent event) {

        if (file.isFile()) {

            if (file.canWrite()) {

                alert.setAlertType(AlertType.INFORMATION);
                alert.setHeaderText("File is already unlocked");
                alert.show();
            } else {

                try {

                    Parent root = FXMLLoader.load(getClass().getResource("unlockerDisplay.fxml"));

                    Stage stage = new Stage();

                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);

                    stage.setOnHiding(e -> {

                        fileStatus.setText(file.canWrite() ? "Unlock" : "Lock");

                    });

                    stage.show();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        } else {

            alert.setAlertType(AlertType.ERROR);
            alert.setHeaderText("Please slect your file first.");
            alert.show();
        }
    }
}
