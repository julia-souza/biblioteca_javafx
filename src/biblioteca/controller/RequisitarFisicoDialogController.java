/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.controller;

import biblioteca.model.dao.ClienteDAO;
import biblioteca.model.dao.ItensDeVendaDAO;
import biblioteca.model.dao.LivroDAO;
import biblioteca.model.dao.VendaDAO;
import biblioteca.model.database.Database;
import biblioteca.model.database.DatabaseFactory;
import biblioteca.model.domain.Cliente;
import biblioteca.model.domain.ItensDeVenda;
import biblioteca.model.domain.Livro;
import biblioteca.model.domain.Venda;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yesod
 */
public class RequisitarFisicoDialogController implements Initializable {
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    
    @FXML
    private Button btnCliente;
    
    @FXML
    private ComboBox comboBoxCliente;
    @FXML
    private ComboBox comboBoxVenda;
    
    @FXML
    private Label CEPlabel;
    
    @FXML
    private CheckBox checkBoxSolicitacao;
    /**
     * Initializes the controller class.
     */
    private List<Cliente> listClientes;
    private List<Venda> listVendas;
    private ObservableList<Cliente> observableListClientes;
    private ObservableList<Venda> observableListVendas;
    private ObservableList<ItensDeVenda> observableListItensDeVenda;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final LivroDAO livroDAO = new LivroDAO();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final ItensDeVendaDAO ItensDeVendaDAO = new ItensDeVendaDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Venda venda;
    Cliente clienteEscolhido;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        livroDAO.setConnection(connection);

        carregarComboBoxClientes();

    }

    public void carregarComboBoxClientes() {
        
        listClientes = clienteDAO.listar();

        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxCliente.setItems(observableListClientes);

        //Como não será possível alterar uma Venda, não precisaremos implementar a seleção do cliente (caso seja uma alteração de venda)*/
    }

    public void carregarComboBoxVenda() {
        clienteDAO.setConnection(connection);
        vendaDAO.setConnection(connection);
        
        clienteEscolhido =(Cliente) comboBoxCliente.getSelectionModel().getSelectedItem();
        
        listVendas = vendaDAO.listarPorCliente(clienteEscolhido);
        CEPlabel.setText(""+clienteEscolhido.getCEP());

        observableListVendas = FXCollections.observableArrayList(listVendas);
        comboBoxVenda.setItems(observableListVendas);
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Venda getVenda() {
        return this.venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
       if (validarEntradaDeDados()) {
            vendaDAO.gerarCopiaFisica((Integer) comboBoxVenda.getValue());
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

        if (comboBoxCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente inválido!\n";
        }
        if (comboBoxVenda.getValue() == null) {
            errorMessage += "Venda inválida!\n";
        }
        if(10000>clienteEscolhido.getCEP()||29315339<clienteEscolhido.getCEP()){
            errorMessage += "CEP inválido!\n";
        }
        //Colocar VALIDACAO DE CEP
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    } 
}
