/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.controller;

import biblioteca.model.dao.AutorDAO;
import biblioteca.model.dao.GeneroDAO;
import biblioteca.model.database.Database;
import biblioteca.model.database.DatabaseFactory;
import biblioteca.model.domain.Autor;
import biblioteca.model.domain.Genero;
import biblioteca.model.domain.Livro;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yesod
 */
public class CadastroLivroDialogController implements Initializable {

    
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    
    @FXML
    private ComboBox comboBoxLivroAutor;
    @FXML
    private ComboBox comboBoxLivroGenero;
    
    @FXML
    private TextField textFieldLivroTitulo;
    @FXML
    private TextField textFieldLivroEdicao;
    /**
     * Initializes the controller class.
     */
    private List<Autor> listAutor;
    private List<Genero> listGenero;

    private List<Livro> listLivros;
    private ObservableList<Autor> observableListAutor;
    private ObservableList<Genero> observableListGenero;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final AutorDAO autorDAO = new AutorDAO();
    private final GeneroDAO generoDAO = new GeneroDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Livro livro;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generoDAO.setConnection(connection);
        autorDAO.setConnection(connection);

        carregarComboBoxGenero();
        carregarComboBoxAutor();

    }

    public void carregarComboBoxGenero() {

        listGenero = generoDAO.listar();

        observableListGenero = FXCollections.observableArrayList(listGenero);
        comboBoxLivroGenero.setItems(observableListGenero);

        //Como não será possível alterar uma Venda, não precisaremos implementar a seleção do cliente (caso seja uma alteração de venda)
    }

    public void carregarComboBoxAutor() {

        listAutor = autorDAO.listar();

        observableListAutor = FXCollections.observableArrayList(listAutor);
        comboBoxLivroAutor.setItems(observableListAutor);

        //Como não será possível alterar uma Venda, não precisaremos implementar a seleção do produto (caso seja uma alteração de venda)
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Livro getLivro() {
        return this.livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
        this.textFieldLivroTitulo.setText(livro.getTitulo());
        this.textFieldLivroEdicao.setText(""+livro.getEdicao());
        //this.comboBoxLivroGenero.getmodel(livro.getGenero());
        //this.comboBoxLivroGenero.getmodel(livro.getAutor());
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            livro.setAutor((Autor) comboBoxLivroAutor.getSelectionModel().getSelectedItem());
            livro.setGenero((Genero) comboBoxLivroGenero.getSelectionModel().getSelectedItem());
            livro.setTitulo(textFieldLivroTitulo.getText());
            livro.setEdicao(Integer.parseInt(textFieldLivroEdicao.getText()));
           
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

        if (comboBoxLivroAutor.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Autor inválido!\n";
        }
        if (comboBoxLivroGenero.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Genero inválido!\n";
        }
        if (textFieldLivroTitulo.getText() == null || textFieldLivroTitulo.getText().length() == 0) {
            errorMessage += "Titulo inválido!\n";
        }
        if (textFieldLivroEdicao.getText() == null || textFieldLivroEdicao.getText().length() == 0) {
            errorMessage += "Edicao inválida!\n";
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
