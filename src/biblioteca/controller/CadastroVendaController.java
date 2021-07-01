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
import biblioteca.model.domain.ItensDeVenda;
import biblioteca.model.domain.Livro;
import biblioteca.model.domain.Venda;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private TableView<Venda> tableViewVendas;
    
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
    private final ItensDeVendaDAO itensDeVendaDAO = new ItensDeVendaDAO();
    private final LivroDAO livroDAO = new LivroDAO();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendaDAO.setConnection(connection);
        itensDeVendaDAO.setConnection(connection);

        carregarTableViewVendas();

        // Limpando a exibição dos detalhes da venda
        selecionarItemTableViewVendas(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewVendas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewVendas(newValue));
    }

    public void carregarTableViewVendas() {
        tableColumnVendaCodigo.setCellValueFactory(new PropertyValueFactory<>("cdVenda"));
        tableColumnVendaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnVendaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        listVendas = vendaDAO.listar();

        observableListVendas = FXCollections.observableArrayList(listVendas);
        tableViewVendas.setItems(observableListVendas);
    }

    public void selecionarItemTableViewVendas(Venda venda) {
        if (venda != null) {
            labelVendaCodigo.setText(String.valueOf(venda.getCdVenda()));
            labelVendaData.setText(String.valueOf(venda.getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            labelVendaValor.setText(String.format("%.2f", venda.getValor()));
            labelVendaCliente.setText(venda.getCliente().toString());
        } else {
            labelVendaCodigo.setText("");
            labelVendaData.setText("");
            labelVendaValor.setText("");
            labelVendaCliente.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Venda venda = new Venda();
        List<ItensDeVenda> listItensDeVenda = new ArrayList<>();
        venda.setItensDeVenda(listItensDeVenda);
        boolean buttonConfirmarClicked = showFXMLAnchorPaneProcessosVendasDialog(venda);
        if (buttonConfirmarClicked) {
            try {
                connection.setAutoCommit(false);
                vendaDAO.setConnection(connection);
                itensDeVendaDAO.setConnection(connection);
                vendaDAO.inserir(venda);
                livroDAO.setConnection(connection);
                for (ItensDeVenda listItemDeVenda : venda.getItensDeVenda()) {
                    Livro livro = listItemDeVenda.getLivro();
                    listItemDeVenda.setVenda(vendaDAO.buscarUltimaVenda());
                    itensDeVendaDAO.inserir(listItemDeVenda);
                    livroDAO.alterar(livro);
                }
                connection.commit();
                carregarTableViewVendas();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(CadastroVendaController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(CadastroVendaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {

    }

    @FXML
    public void handleButtonRemover() throws IOException, SQLException {
        Venda venda = tableViewVendas.getSelectionModel().getSelectedItem();
        if (venda != null) {
            connection.setAutoCommit(false);
            vendaDAO.setConnection(connection);
            itensDeVendaDAO.setConnection(connection);
            livroDAO.setConnection(connection);
            for(ItensDeVenda listItemDeVenda : venda.getItensDeVenda()) {
                Livro livro = listItemDeVenda.getLivro();
                //livroDAO.alterar(livro);
                itensDeVendaDAO.remover(listItemDeVenda);
            }
            vendaDAO.remover(venda);
            connection.commit();
            carregarTableViewVendas();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma venda na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneProcessosVendasDialog(Venda venda) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CadastroVendaDialogController.class.getResource("/biblioteca/view/cadastroVendaDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registro de Vendas");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando a Venda no Controller.
        CadastroVendaDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVenda(venda);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }
}
