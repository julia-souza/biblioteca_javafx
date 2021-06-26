/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.controller;

import biblioteca.model.dao.GeneroDAO;
import biblioteca.model.dao.LivroDAO;
import biblioteca.model.database.Database;
import biblioteca.model.database.DatabaseFactory;
import biblioteca.model.domain.Livro;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Yesod
 */
public class CadastroLivroController implements Initializable {
    
    
    @FXML
    private TableView<Livro> tableViewLivros;
    @FXML
    private TableColumn<Livro, String> tableColumnLivroTitulo;
    @FXML
    private TableColumn<Livro, Integer> tableColumnLivroEdicao;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelLivroCodigo;
    @FXML
    private Label labelLivroTitulo;
    @FXML
    private Label labelLivroAutor;
    @FXML
    private Label labelLivroEdicao;
    @FXML
    private Label labelLivroGenero;
    
    
    private List<Livro> listLivros;
    private ObservableList<Livro> observableListLivros;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final LivroDAO livroDAO = new LivroDAO();
    private final GeneroDAO generoDAO = new GeneroDAO();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
