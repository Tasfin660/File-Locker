package sample;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LockerDisplay implements Initializable {

    @FXML
    private PasswordField userPassword;

    @FXML
    private PasswordField setFilePassword;

    @FXML
    private JFXCheckBox checkBox;

    @FXML
    private TextField passText;

    private Alert alert = new Alert(AlertType.WARNING);
    private BufferedReader brFileUserPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.shPass(null);
    }

    @FXML
    void doneButton(ActionEvent event) {

        try {

            brFileUserPass = new BufferedReader(new FileReader("fileUserPassword.dat"));

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        if(userPassword.getText().isBlank()) {

            alert.setHeaderText("Give your user password");
            alert.show();
        }
        if(setFilePassword.getText().isBlank()) {

            alert.setHeaderText("Give a password to protect your file");
            alert.show();
        }
        if(userPassword.getText().isBlank() && setFilePassword.getText().isBlank()) {

            alert.setHeaderText("Password fields are empty");
            alert.show();
        }
        else if(!userPassword.getText().isBlank() && !setFilePassword.getText().isBlank()){

            try {

                String userPass = brFileUserPass.readLine();

                if(userPassword.getText().equals(userPass)) {

                    if(setFilePassword.getText().length() < 4) {

                        alert.setAlertType(AlertType.INFORMATION);
                        alert.setHeaderText("Password must be 4 character long");
                        alert.show();
                    }
                    else {

                        MapData.setData(Home.file.getAbsolutePath(), setFilePassword.getText());

                        try {

                            FileWriter fwPath = new FileWriter("filePath.dat", true);
                            FileWriter fwPass = new FileWriter("filePassword.dat", true);

                            fwPath.write(Home.file.getAbsolutePath() + "\n");
                            fwPass.write(setFilePassword.getText() + "\n");

                            fwPath.close();
                            fwPass.close();

                        }
                        catch (Exception e) {

                            e.printStackTrace();
                        }

                        Home.file.setExecutable(false);
                        Home.file.setReadable(false);
                        Home.file.setWritable(false);

                        ((Node)event.getSource()).getScene().getWindow().hide();
                    }



                }
                else {

                    alert.setHeaderText("User Password was wrong");
                    alert.setAlertType(AlertType.INFORMATION);
                    alert.show();
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }
    @FXML
    void shPass(ActionEvent event) {

        if(checkBox.isSelected()) {

            passText.setText(setFilePassword.getText());
            passText.setVisible(true);
            setFilePassword.setVisible(false);
            return;
        }
        setFilePassword.setText(passText.getText());
        setFilePassword.setVisible(true);
        passText.setVisible(false);
    }

}
