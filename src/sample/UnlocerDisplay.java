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

public class UnlocerDisplay implements Initializable {

    @FXML
    private PasswordField userPassword;

    @FXML
    private PasswordField filePassword;

    @FXML
    private JFXCheckBox checkBox;

    @FXML
    private TextField passText;

    private Alert alert = new Alert(AlertType.INFORMATION);

    private BufferedReader brFileUserPass;
    private BufferedReader brFilePath;
    private BufferedReader brFilePassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.shPass(null);
    }

    @FXML
    void doneButton(ActionEvent event) {

        try {

            brFileUserPass = new BufferedReader(new FileReader("fileUserPassword.dat"));
            brFilePath = new BufferedReader(new FileReader("filePath.dat"));
            brFilePassword = new BufferedReader(new FileReader("filePassword.dat"));

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        if(userPassword.getText().isBlank()) {


            alert.setHeaderText("Give your user password");
            alert.show();
        }
        if(filePassword.getText().isBlank()) {


            alert.setHeaderText("Give your file password");
            alert.show();
        }
        if(userPassword.getText().isBlank() && filePassword.getText().isBlank()) {

            alert.setHeaderText("Password fields are empty");
            alert.show();
        }
        else {

            try {

                String userPass = brFileUserPass.readLine();

                if(userPassword.getText().equals(userPass)) {

                    if(filePassword.getText().equals(MapData.getData(Home.file.getAbsolutePath()))) {

                        Home.file.setExecutable(true);
                        Home.file.setReadable(true);
                        Home.file.setWritable(true);

                        ((Node)event.getSource()).getScene().getWindow().hide();

                        try {

                            String line1;
                            String line2;

                            String input1 = "";
                            String input2 = "";

                            while ((line1 = brFilePath.readLine()) != null)
                            {

                                if (line1.contains(Home.file.getAbsolutePath()))
                                {
                                    line1 = "";
                                }

                                input1 += line1 + '\n';
                            }

                            while ((line2 = brFilePassword.readLine()) != null)
                            {

                                if (line2.contains(MapData.getData(Home.file.getAbsolutePath())))
                                {
                                    line2 = "";
                                }

                                input2 += line2 + '\n';
                            }


                            FileOutputStream fospath = new FileOutputStream("filePath.dat");
                            fospath.write(input1.getBytes());

                            FileOutputStream fospass = new FileOutputStream("filePassword.dat");
                            fospass.write(input2.getBytes());

                            fospath.close();
                            fospass.close();

                            brFilePath.close();
                            brFilePassword.close();

                        }catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                    else {

                        alert.setHeaderText("File password was wrong");
                        alert.show();
                    }

                }
                else {

                    alert.setHeaderText("User Password was wrong");
                    alert.show();
                }

            }catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
    @FXML
    void shPass(ActionEvent event) {

        if(checkBox.isSelected()) {

            passText.setText(filePassword.getText());
            passText.setVisible(true);
            filePassword.setVisible(false);
            return;
        }
        filePassword.setText(passText.getText());
        filePassword.setVisible(true);
        passText.setVisible(false);
    }

}
