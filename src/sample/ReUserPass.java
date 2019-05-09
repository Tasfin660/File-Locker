package sample;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class ReUserPass implements Initializable {

    @FXML
    private PasswordField userPassword;

    @FXML
    private PasswordField newUserPssword;

    @FXML
    private TextField passText;

    @FXML
    private JFXCheckBox checkBox;

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

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
        if(newUserPssword.getText().equals("")) {

            alert.setHeaderText("Enter you new user password");
            alert.show();
        }
        if(userPassword.getText().isBlank() && newUserPssword.getText().isBlank()) {

            alert.setHeaderText("Password fields are empty");
            alert.show();
        }
        if(!userPassword.getText().isBlank() && !newUserPssword.getText().isBlank()) {

            try {

               String userPass = brFileUserPass.readLine();

               if(userPass.equals(userPassword.getText())) {

                   if(newUserPssword.getText().length() < 4) {

                       alert.setHeaderText("Password must be 4 character long");
                       alert.show();

                   }
                   else {

                       try(FileWriter fw = new FileWriter("fileUserPassword.dat")) {

                           fw.write(newUserPssword.getText());

                       }
                       catch (Exception e) {

                           e.printStackTrace();
                       }

                       alert.setHeaderText("Password changed");
                       alert.showAndWait();

                       ((Node)event.getSource()).getScene().getWindow().hide();
                   }
               }
               else {

                   alert.setHeaderText("User Password was wrong");
                   alert.show();
               }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
    @FXML
    void shPass(ActionEvent event) {

        if(checkBox.isSelected()) {

            passText.setText(newUserPssword.getText());
            passText.setVisible(true);
            newUserPssword.setVisible(false);
            return;
        }
        newUserPssword.setText(passText.getText());
        newUserPssword.setVisible(true);
        passText.setVisible(false);
    }

}
