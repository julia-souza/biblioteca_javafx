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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
    }    
    
}
