/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uaspt2;

import static com.mysql.cj.util.SaslPrep.prepare;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import uaspt2.productdata;

/**
 *
 * @author HD
 */
public class fxmlmainController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private Button import_btn;

    @FXML
    private Button clear;

    @FXML
    private Button delete;

    @FXML
    private Label displayusername;

    @FXML
    private Button home;

    @FXML
    private ImageView imageview;

    @FXML
    private TextField inventoryproduct;

    @FXML
    private TextField inventoryname;

    @FXML
    private TextField inventoryprice;

    @FXML
    private ComboBox<?> inventorystatus;

    @FXML
    private TextField inventorystock;

    @FXML
    private ComboBox<?> inventorytype;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private Button menu;

    @FXML
    private Button customerbtn;
    @FXML
    private TableColumn<productdata, String> idproduct;

    @FXML
    private TableColumn<productdata, String> price;

    @FXML
    private TableColumn<productdata, String> productname;

    @FXML
    private TableColumn<productdata, String> status;

    @FXML
    private TableColumn<productdata, String> stock;

    @FXML
    private TableColumn<productdata, String> type;

    @FXML
    private TableColumn<productdata, String> date;

    @FXML
    private TableView<productdata> tableview;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private TextField amountmenu;

    @FXML
    private Label changemenu;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private Label menu_total;

    @FXML
    private Button receipt_btn;

    @FXML
    private Button remove_btn;

    @FXML
    private TableColumn<productdata, String> menu_price;

    @FXML
    private TableColumn<productdata, String> menu_productname;

    @FXML
    private TableColumn<productdata, String> menu_quantity;

    @FXML
    private Button pay_btn;

    @FXML
    private Button inventory;

    @FXML
    private TableView<productdata> menu_tableView;

    @FXML
    private AnchorPane home_form;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private Button update;

    @FXML
    private TableView<customerData> customers_tableView;

    @FXML
    private TableColumn<customerData, String> cashierview;

    @FXML
    private TableColumn<customerData, String> customerview;

    @FXML
    private TableColumn<customerData, String> dateview;

    @FXML
    private TableColumn<customerData, String> totalview;

    @FXML
    private Label dashboard_NC;

    @FXML
    private Label numbersold;

    @FXML
    private Label todayincome;

    @FXML
    private Label totalincome;

    @FXML
    private BarChart<?, ?> chartcustomer;

    @FXML
    private AreaChart<?, ?> chartincome;

    private Alert alert;

    private Connection connect;
    private ResultSet result;
    private Image image;
    private Statement statement;
    private PreparedStatement prepare;
    private double x = 0;
    private double y = 0;

    private ObservableList<productdata> cardListData = FXCollections.observableArrayList();

    public void dashboardDisplayNC() {

        String sql = "SELECT COUNT(id) FROM receipt";
        connect = database.connectDb();

        try {
            int nc = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }
            dashboard_NC.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardDisplayTI() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM receipt WHERE date = '"
                + sqlDate + "'";

        connect = database.connectDb();

        try {
            double ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }

            todayincome.setText("Rp. " + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTotalI() {
        String sql = "SELECT SUM(total) FROM receipt";

        connect = database.connectDb();

        try {
            float ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getFloat("SUM(total)");
            }
            totalincome.setText("Rp. " + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardNSP() {

        String sql = "SELECT COUNT(quantity) FROM customer";

        connect = database.connectDb();

        try {
            int q = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                q = result.getInt("COUNT(quantity)");
            }
            numbersold.setText(String.valueOf(q));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardIncomeChart() {
        chartincome.getData().clear();

        String sql = "SELECT date, SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = database.connectDb();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getFloat(2)));
            }

            chartincome.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardCustomerChart() {
        chartcustomer.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = database.connectDb();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }

            chartcustomer.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inventoryAddBtn() {

        if (inventoryproduct.getText().isEmpty()
                || inventoryname.getText().isEmpty()
                || inventorytype.getSelectionModel().getSelectedItem() == null
                || inventorystock.getText().isEmpty()
                || inventoryprice.getText().isEmpty()
                || inventorystatus.getSelectionModel().getSelectedItem() == null
                || data.path == null) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Mohon isi dengan benar!");
            alert.showAndWait();

        } else {

            // CHECK PRODUCT ID
            String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '"
                    + inventoryproduct.getText() + "'";

            connect = database.connectDb();

            try {

                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(inventoryproduct.getText() + " Sudah Tersedia! ganti dengan yang lain");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO product "
                            + "(prod_id, prod_name, type, stock, price, status, image, date) "
                            + "VALUES(?,?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, inventoryproduct.getText());
                    prepare.setString(2, inventoryname.getText());
                    prepare.setString(3, (String) inventorytype.getSelectionModel().getSelectedItem());
                    prepare.setString(4, inventorystock.getText());
                    prepare.setString(5, inventoryprice.getText());
                    prepare.setString(6, (String) inventorystatus.getSelectionModel().getSelectedItem());

                    String path = data.path;
                    path = path.replace("\\", "\\\\");

                    prepare.setString(7, path);

                    // Menginput waktu sekarang
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Berhasil ditambahkan!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearBtn();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inventoryUpdateBtn() {
        if (inventoryproduct.getText().isEmpty()
                || inventoryname.getText().isEmpty()
                || inventorytype.getSelectionModel().getSelectedItem() == null
                || inventorystock.getText().isEmpty()
                || inventoryprice.getText().isEmpty()
                || inventorystatus.getSelectionModel().getSelectedItem() == null
                || data.path == null || data.id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Mohon isi dengan benar!");
            alert.showAndWait();

        } else {

            String path = data.path;
            path = path.replace("\\", "\\\\");

            String updateData = "UPDATE product SET "
                    + "prod_id = '" + inventoryproduct.getText() + "', prod_name = '"
                    + inventoryname.getText() + "', type = '"
                    + inventorytype.getSelectionModel().getSelectedItem() + "', stock = '"
                    + inventorystock.getText() + "', price = '"
                    + inventoryprice.getText() + "', status = '"
                    + inventorystatus.getSelectionModel().getSelectedItem() + "', image = '"
                    + path + "', date = '"
                    + data.date + "' WHERE id = " + data.id;

            connect = database.connectDb();

            try {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product ID: " + inventoryproduct.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Berhasil diperbarui!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Dibatalkan.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inventoryDeleteBtn() {
        if (data.id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Mohon diisi dengan benar!");
            alert.showAndWait();

        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Apakah Anda yakin ingin MENGHAPUS ID Produk: " + inventoryproduct.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM product WHERE id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("berhasil Dihapus!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Dibatalkan.");
                alert.showAndWait();
            }
        }
    }

    public void inventoryClearBtn() {

        inventoryproduct.setText("");
        inventoryname.setText("");
        inventorytype.getSelectionModel().clearSelection();
        inventorystock.setText("");
        inventoryprice.setText("");
        inventorystatus.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        imageview.setImage(null);

    }

    public void inventoryImportBtn() {

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 200, 204, false, true);

            imageview.setImage(image);
        }
    }

    //Menyimpan semua data di database
    public ObservableList<productdata> inventoryDataList() {
        ObservableList<productdata> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";

        connect = database.connectDb();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productdata prodData;

            while (result.next()) {

                prodData = new productdata(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prodData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    // Untuk menampilkan database di tabel
    private ObservableList<productdata> inventoryListData;

    public void inventoryShowData() {
        inventoryListData = inventoryDataList();

        idproduct.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productname.setCellValueFactory(new PropertyValueFactory<>("productName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableview.setItems(inventoryListData);
    }

    public void inventorySelectData() {

        productdata prodData = tableview.getSelectionModel().getSelectedItem();
        int num = tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        inventoryproduct.setText(prodData.getProductId());
        inventoryname.setText(prodData.getProductName());
        inventorystock.setText(String.valueOf(prodData.getStock()));
        inventoryprice.setText(String.valueOf(prodData.getPrice()));

        data.path = prodData.getImage();
        String path = "File:" + prodData.getImage();
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();

        image = new Image(path, 200, 204, false, true);
        imageview.setImage(image);
    }

    private String[] typeList = {"Gear Gaming", "PC components"};

    public void inventoryTypeList() {

        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventorytype.setItems(listData);
    }

    private String[] statusList = {"Available", "Unavailable"};

    public void inventoryStatusList() {

        List<String> statusL = new ArrayList<>();

        for (String data : statusList) {
            statusL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(statusL);
        inventorystatus.setItems(listData);

    }

    public ObservableList<productdata> MenuGetData() {
        String sql = "SELECT * FROM product";

        ObservableList<productdata> listData = FXCollections.observableArrayList();
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productdata prod;

            while (result.next()) {
                prod = new productdata(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void menuDisplayCard() {

        cardListData.clear();
        cardListData.addAll(MenuGetData());

        int row = 0;
        int column = 0;

        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for (int i = 0; i < cardListData.size(); i++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/FXML/item.fxml"));
                AnchorPane pane = load.load();
                fxmlcardController cardC = load.getController();
                cardC.setData(cardListData.get(i));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(50));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<productdata> menuGetOrder() {
        CustomerID();
        ObservableList<productdata> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer WHERE customer_id = " + cID;
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productdata prod;
            while (result.next()) {
                prod = new productdata(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<productdata> menuOrderListData;

    public void menuShowOrderData() {
        menuOrderListData = menuGetOrder();

        menu_productname.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_tableView.setItems(menuOrderListData);
    }
    private int getid;

    public void menuSelectOrder() {
        productdata prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();

    }
    private double totalP;

    public void menuGetTotal() {
        CustomerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;
        connect = database.connectDb();

        try {

            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("Rp. " + totalP);
    }
    private double amount;
    private double change;

    public void menuAmount() {
        menuGetTotal();
        if (amountmenu.getText().isEmpty() || totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(amountmenu.getText());

            if (amount < totalP) {
                amountmenu.setText("");
            } else {
                change = (amount - totalP);
                changemenu.setText("Rp. " + change);
            }
        }
    }

    public void menuPayBtn() {

        if (totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Silakan pilih pesanan Anda terlebih dahulu!");
            alert.showAndWait();
        } else {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username) "
                    + "VALUES(?,?,?,?)";

            connect = database.connectDb();

            try {

                if (amount == 0) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Messaged");
                    alert.setHeaderText(null);
                    alert.setContentText("Ada yang salah");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Apa Anda yakin?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        CustomerID();
                        menuGetTotal();
                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                        prepare.setString(3, String.valueOf(sqlDate));
                        prepare.setString(4, data.displayusername);

                        prepare.executeUpdate();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Sukses.");
                        alert.showAndWait();

                        menuShowOrderData();

                    } else {
                        alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Dibatalkan.");
                        alert.showAndWait();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void menuRemoveBtn() {

        if (getid == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Silakan pilih pesanan yang ingin Anda hapus");
            alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
            connect = database.connectDb();
            try {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda yakin ingin menghapus pesanan ini?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }

                menuShowOrderData();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void menuReceiptBtn() {

        if (totalP == 0 || amountmenu.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Mohon untuk membeli barang terlebih dahulu.");
            alert.showAndWait();
        } else {
            HashMap map = new HashMap();
            map.put("getReceipt", (cID - 1));

            try {

                JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\HD\\Documents\\NetBeansProjects\\uaspt2\\src\\newpackage\\report (1).jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);

                JasperViewer.viewReport(jPrint, false);

                menuRestart();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void menuRestart() {
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("Rp. 0.0");
        amountmenu.setText("");
        changemenu.setText("Rp. 0.0");
    }

    private int cID;

    public void CustomerID() {
        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                cID = result.getInt("MAX(customer_id)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            data.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<customerData> customersDataList() {

        ObservableList<customerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM receipt";
        connect = database.connectDb();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            customerData cData;

            while (result.next()) {
                cData = new customerData(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("em_username"));

                listData.add(cData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<customerData> customersListData;

    public void customersShowData() {
        customersListData = customersDataList();

        customerview.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        totalview.setCellValueFactory(new PropertyValueFactory<>("total"));
        dateview.setCellValueFactory(new PropertyValueFactory<>("date"));
        cashierview.setCellValueFactory(new PropertyValueFactory<>("Musername"));

        customers_tableView.setItems(customersListData);
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == home) {
            home_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(false);

        } else if (event.getSource() == inventory) {
            home_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            customers_form.setVisible(false);

            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();

        } else if (event.getSource() == menu) {
            home_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            customers_form.setVisible(false);

            menuDisplayCard();
            menuDisplayTotal();
            menuShowOrderData();
        } else if (event.getSource() == customerbtn) {
            home_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(true);

            customersShowData();
        }
    }

    public void logout() {

        try {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Apakah anda yakin ingin keluar?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/loginfxml.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayUsername() {

        String user = data.displayusername;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        displayusername.setText(user);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayUsername();

        dashboardDisplayNC();
        dashboardDisplayTI();
        dashboardTotalI();
        dashboardNSP();
        dashboardIncomeChart();
        dashboardCustomerChart();

        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();

        menuDisplayCard();
        menuGetOrder();
        menuDisplayTotal();
        menuShowOrderData();
        customersShowData();
    }

}
