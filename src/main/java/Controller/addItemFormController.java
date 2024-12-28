package Controller;

import DBConnection.DBConnection;
import Model.Customer;
import Model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addItemFormController implements Initializable {

    public TextField txtCode;
    public TextField txtDes;
    public TextField txtPri;
    public TextField txtQty;
    public TableView tblItem;
    public TableColumn colCode;
    public TableColumn colDes;
    public TableColumn colUniPri;
    public TableColumn colQoh;

    public void btnAddItemonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement prepareStm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");
        prepareStm.setObject(1, txtCode.getText());
        prepareStm.setObject(2, txtDes.getText());
        prepareStm.setObject(3, Double.parseDouble(txtPri.getText()));
        prepareStm.setObject(4,Integer.parseInt(txtQty.getText()));

        prepareStm.executeUpdate();
        txtCode.clear();
        txtDes.clear();
        txtPri.clear();
        txtQty.clear();

        loadTable();
    }

    List<Item> itemList= new ArrayList<>();

    public void loadTable() throws SQLException, ClassNotFoundException {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUniPri.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQoh.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        Statement statement = DBConnection.getInstance().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from item");

        while(resultSet.next()){
            itemList.add(new Item(resultSet.getString(1),resultSet.getString(2),Double.parseDouble((resultSet.getString(3))),Integer.parseInt(resultSet.getString(4))));
        }

        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();

        itemList.forEach(item -> {
            itemObservableList.add(item);
        });


        tblItem.setItems(itemObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUniPri.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQoh.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        try {
            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
