/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.controller;

import biblioteca.model.dao.ItensDeVendaDAO;
import biblioteca.model.dao.LivroDAO;
import biblioteca.model.dao.VendaDAO;
import biblioteca.model.database.Database;
import biblioteca.model.database.DatabaseFactory;
import biblioteca.model.domain.Cliente;
import biblioteca.model.domain.Venda;
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
public class CadastroVendaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView<Cliente> tableViewVendas;
    
    @FXML
    private TableColumn<Venda, Integer> tableColumnVendaCodigo;
    @FXML
    private TableColumn<Venda, Date> tableColumnVendaData;
    @FXML
    private TableColumn<Venda, Venda> tableColumnVendaCliente;
    
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelVendaCodigo;
    @FXML
    private Label labelVendaData;
    @FXML
    private Label labelVendaValor;
    @FXML
    private Label labelVendaCliente;
    
    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;

    private List<Venda> listVendas;
    private ObservableList<Venda> observableListVendas;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final ItensDeVendaDAO itesDeVendaDAO = new ItensDeVendaDAO();
    private final LivroDAO livroDAO = new LivroDAO();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
