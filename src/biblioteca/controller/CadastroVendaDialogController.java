/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.controller;

import biblioteca.model.dao.ClienteDAO;
import biblioteca.model.dao.ItensDeVendaDAO;
import biblioteca.model.dao.LivroDAO;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yesod
 */
public class CadastroVendaDialogController implements Initializable {
    @FXML
    private ComboBox comboBoxVendaCliente;
    @FXML
    private DatePicker datePickerVendaData;
    @FXML
    private CheckBox checkBoxVendaPago;
    @FXML
    private ComboBox comboBoxVendaLivro;
    @FXML
    private TableView<ItensDeVenda> tableViewItensDeVenda;
    @FXML
    private TableColumn<ItensDeVenda, Livro> tableColumnItemDeVendaLivro;
    @FXML
    private TableColumn<ItensDeVenda, Integer> tableColumnItemDeVendaQuantidade;
    @FXML
    private TableColumn<ItensDeVenda, Double> tableColumnItemDeVendaValor;
    @FXML
    private TextField textFieldVendaValor;
    @FXML
    private TextField textFieldVendaItemDeVendaQuantidade;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonAdicionar;

    private List<Cliente> listClientes;
    private List<Livro> listLivros;
    private ObservableList<Cliente> observableListClientes;
    private ObservableList<Livro> observableListLivros;
    private ObservableList<ItensDeVenda> observableListItensDeVenda;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final LivroDAO livroDAO = new LivroDAO();
    private final ItensDeVendaDAO ItensDeVendaDAO = new ItensDeVendaDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Venda venda;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        livroDAO.setConnection(connection);

        carregarComboBoxClientes();
        carregarComboBoxItensVenda();

        tableColumnItemDeVendaLivro.setCellValueFactory(new PropertyValueFactory<>("livro"));
        //tableColumnItemDeVendaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

    }

    public void carregarComboBoxClientes() {

        listClientes = clienteDAO.listar();

        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxVendaCliente.setItems(observableListClientes);

        //Como não será possível alterar uma Venda, não precisaremos implementar a seleção do cliente (caso seja uma alteração de venda)
    }

    public void carregarComboBoxItensVenda() {

        listLivros = livroDAO.listar();

        observableListLivros = FXCollections.observableArrayList(listLivros);
        comboBoxVendaLivro.setItems(observableListLivros);
        //Como não será possível alterar uma Venda, não precisaremos implementar a seleção do produto (caso seja uma alteração de venda)
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
            venda.setCliente((Cliente) comboBoxVendaCliente.getSelectionModel().getSelectedItem());
            venda.setData(datePickerVendaData.getValue());
            venda.setValor(Integer.parseInt(textFieldVendaValor.getText()));
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonAdicionar() {
        Livro livro;
        ItensDeVenda itensDeVenda = new ItensDeVenda();

        if (comboBoxVendaLivro.getSelectionModel().getSelectedItem() != null) {
            livro = (Livro) comboBoxVendaLivro.getSelectionModel().getSelectedItem();

            itensDeVenda.setLivro((Livro) comboBoxVendaLivro.getSelectionModel().getSelectedItem());

            venda.getItensDeVenda().add(itensDeVenda);

            observableListItensDeVenda = FXCollections.observableArrayList(venda.getItensDeVenda());
            tableViewItensDeVenda.setItems(observableListItensDeVenda);

            //textFieldVendaValor.setText(String.format("%.2f", venda.getValor()));
            
        }
    }
    
    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (comboBoxVendaCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente inválido!\n";
        }
        if (datePickerVendaData.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }
        if (observableListItensDeVenda == null) {
            errorMessage += "Itens de venda inválidos!\n";
        }
        
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
