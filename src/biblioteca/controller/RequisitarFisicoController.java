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
public class RequisitarFisicoController implements Initializable {

    
    @FXML
    private TableView<Venda> tableViewFisico;
    
    @FXML
    private TableColumn<Cliente, String> tableColumnFisicoCliente;
    @FXML
    private TableColumn<Venda, Date> tableColumnFisicoVenda;
    
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    
    @FXML
    private Label labelVenda;
    @FXML
    private Label labelCliente;
    @FXML
    private Label labelClienteCEP;
    @FXML
    private Label labelVendaData;
    @FXML
    private Label labelLivros;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final ItensDeVendaDAO itensDeVendaDAO = new ItensDeVendaDAO();
    private final LivroDAO livroDAO = new LivroDAO();
    
    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;

    private List<Venda> listVendas;
    private ObservableList<Venda> observableListVendas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendaDAO.setConnection(connection);
        itensDeVendaDAO.setConnection(connection);

        carregarTableViewFisico();

        // Limpando a exibição dos detalhes da venda
        selecionarItemTableViewVendas(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewFisico.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewVendas(newValue));
    }

    public void carregarTableViewFisico() {
        tableColumnFisicoCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tableColumnFisicoVenda.setCellValueFactory(new PropertyValueFactory<>("data"));

        listVendas = vendaDAO.listarFisicos();

        observableListVendas = FXCollections.observableArrayList(listVendas);
        tableViewFisico.setItems(observableListVendas);
    }

    public void selecionarItemTableViewVendas(Venda venda) {
        Cliente cliente = new Cliente();
        if (venda != null) {
            labelVenda.setText(String.valueOf(venda.getCdVenda()));
            labelVendaData.setText(String.valueOf(venda.getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            labelClienteCEP.setText(""+venda.getCliente().getCEP());
            labelCliente.setText(venda.getCliente().toString());
            //labelLivros
        } else {
            labelVenda.setText("");
            labelVendaData.setText("");
            labelClienteCEP.setText("");
            labelCliente.setText("");
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
                livroDAO.setConnection(connection);
                for (ItensDeVenda listItemDeVenda : venda.getItensDeVenda()) {
                    Livro livro = listItemDeVenda.getLivro();
                    listItemDeVenda.setVenda(vendaDAO.buscarUltimaVenda());
                    itensDeVendaDAO.inserir(listItemDeVenda);
                    livroDAO.alterar(livro);
                }
                connection.commit();
                carregarTableViewFisico();
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
    public void handleButtonRemover() throws IOException, SQLException {
        Venda venda = tableViewFisico.getSelectionModel().getSelectedItem();
        if (venda != null) {
            connection.setAutoCommit(false);
            vendaDAO.setConnection(connection);
            vendaDAO.removerCopiaFisica(venda);
            connection.commit();
            carregarTableViewFisico();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma venda na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneProcessosVendasDialog(Venda venda) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RequisitarFisicoDialogController.class.getResource("/biblioteca/view/requisitarFisicoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registro de Vendas");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando a Venda no Controller.
        RequisitarFisicoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVenda(venda);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }  
    
}
