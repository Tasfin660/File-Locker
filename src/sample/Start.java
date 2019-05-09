package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Start implements Initializable {

    @FXML
    private Label chngPass;

    @FXML
    private PasswordField userPasswordField;

    private File fileUserPass;

    private BufferedReader brFileUserPass;
    private BufferedReader brFilePath;
    private BufferedReader brFilePassword;

    private Alert alert = new Alert(AlertType.INFORMATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fileUserPass = new File("fileUserPassword.dat");
        File filePath = new File("filePath.dat");
        File filePassword = new File("filePassword.dat");

        try {

            if(!fileUserPass.exists()) {

                fileUserPass.createNewFile();
            }

            if(!filePath.exists()) {

                filePath.createNewFile();

            }
            if(!filePassword.exists()) {

                filePassword.createNewFile();
            }

        }catch (Exception e) {

            e.printStackTrace();
        }


        try {

            brFilePath = new BufferedReader(new FileReader(filePath));
            brFilePassword = new BufferedReader(new FileReader(filePassword));

            String file_path;
            String file_password;

            while ((file_path = brFilePath.readLine()) != null && (file_password = brFilePassword.readLine()) != null) {

                if(!file_path.equals("") || !file_password.equals(""))
                    MapData.setData(file_path, file_password);
            }


        }catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    void enterButton(ActionEvent event) {

        try {

            brFileUserPass = new BufferedReader(new FileReader(fileUserPass));

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        if(userPasswordField.getText().equals("")) {

            alert.setHeaderText("Password field is empty");
            alert.show();
        }
        else {

            try {

                if(fileUserPass.length() == 0) {

                    try(FileWriter fw = new FileWriter(fileUserPass)) {

                        fw.write(userPasswordField.getText());

                    }catch (Exception e) {

                        e.printStackTrace();
                    }

                    ((Node)event.getSource()).getScene().getWindow().hide();

                    try {

                        Stage stage = new Stage();

                        Parent root = FXMLLoader.load(getClass().getResource("/sample/home.fxml"));

                        Scene scene = new Scene(root);

                        stage.setTitle("File Locker");
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();

                    }catch (Exception e) {

                        e.printStackTrace();
                    }

                }
                else {

                    String userPass = brFileUserPass.readLine();

                    if(userPasswordField.getText().equals(userPass)) {

                        ((Node)event.getSource()).getScene().getWindow().hide();

                        try {

                            Stage stage = new Stage();

                            Parent root = FXMLLoader.load(getClass().getResource("/sample/home.fxml"));

                            stage.setTitle("File Locker");
                            stage.setScene(new Scene(root));
                            stage.setResizable(false);
                            stage.show();


                        }catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                    else {

                        alert.setHeaderText("Password doesn't match");
                        alert.setAlertType(AlertType.INFORMATION);
                        alert.show();
                    }

                }

            }catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
    @FXML
    void chngPassMClick(MouseEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/sample/reUserPass.fxml"));

            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();

        }catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    void chngPassMPress(MouseEvent event) {

        chngPass.setTextFill(Color.valueOf("#00588e"));
    }

    @FXML
    void chngPassMReles(MouseEvent event) {

        chngPass.setTextFill(Color.valueOf("#00e3fc"));
    }
}
