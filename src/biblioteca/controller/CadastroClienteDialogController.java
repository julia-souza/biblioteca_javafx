/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.controller;

import biblioteca.model.domain.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static java.lang.Long.parseLong;

/**
 * FXML Controller class
 *
 * @author Yesod
 */
public class CadastroClienteDialogController implements Initializable {

    @FXML
    private Label labelClienteNome;

    @FXML
    private Label labelClienteTelefone;
    @FXML
    private TextField textFieldClienteNome;
    @FXML
    private TextField textFieldClienteTelefone;
    @FXML
    private TextField textFieldClienteCEP;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Cliente cliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.textFieldClienteNome.setText(cliente.getNome());
        this.textFieldClienteTelefone.setText(""+cliente.getTelefone());
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            cliente.setNome(textFieldClienteNome.getText());
            cliente.setTelefone(parseLong(textFieldClienteTelefone.getText()));
            cliente.setCEP(Integer.parseInt(textFieldClienteCEP.getText()));

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldClienteNome.getText() == null || textFieldClienteNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        
        if (textFieldClienteTelefone.getText() == null || textFieldClienteTelefone.getText().length() == 0) {
            errorMessage += "Telefone inválido!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
}
