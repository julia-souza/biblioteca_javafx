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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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
        livroDAO.setConnection (connection);
        
        carregarTableViewLivros();

        // Limpando a exibição dos detalhes do cliente
        selecionarItemTableViewLivros(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewLivros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewLivros(newValue));
        
    }

    public void carregarTableViewLivros() {
        tableColumnLivroTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tableColumnLivroEdicao.setCellValueFactory(new PropertyValueFactory<>("edicao"));

        listLivros = livroDAO.listar();

        observableListLivros = FXCollections.observableArrayList(listLivros);
        tableViewLivros.setItems(observableListLivros);
    }

    public void selecionarItemTableViewLivros(Livro livro) {
        if (livro != null) {
            labelLivroCodigo.setText(String.valueOf(livro.getCdLivro()));
            labelLivroTitulo.setText(livro.getTitulo());
            labelLivroAutor.setText(""+livro.getAutor());
            labelLivroEdicao.setText(""+livro.getEdicao());
            labelLivroGenero.setText(""+livro.getGenero());
        } else {
            labelLivroCodigo.setText("");
            labelLivroTitulo.setText("");
            labelLivroAutor.setText("");
            labelLivroEdicao.setText("");
            labelLivroGenero.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Livro livro = new Livro();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosLivrosDialog(livro);
        if (buttonConfirmarClicked) {
            livroDAO.inserir(livro);
            carregarTableViewLivros();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Livro livro = tableViewLivros.getSelectionModel().getSelectedItem();//Obtendo cliente selecionado
        if (livro != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosLivrosDialog(livro);
            if (buttonConfirmarClicked) {
                livroDAO.alterar(livro);
                carregarTableViewLivros();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Livro livro = tableViewLivros.getSelectionModel().getSelectedItem();
        if (livro != null) {
            livroDAO.remover(livro);
            carregarTableViewLivros();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneCadastrosLivrosDialog(Livro livro) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CadastroLivroDialogController.class.getResource("/biblioteca/view/CadastroLivroDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Clientes");
        //Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o estágio visível. A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL 
        //dialogStage.initModality(Modality.WINDOW_MODAL);//WINDOW_MODAL (possibilita minimizar)
        
        //Especifica a janela do proprietário para esta página, ou null para um nível superior.
        //dialogStage.initOwner(null); //null deixa a Tela Principal livre para ser movida
        //dialogStage.initOwner(this.tableViewClientes.getScene().getWindow()); //deixa a tela de Preenchimento dos dados como prioritária
        
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o cliente no Controller.
        CadastroLivroDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setLivro(livro);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }   
    
}
