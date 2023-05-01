package utc2.itk62.store.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import utc2.itk62.store.models.*;
import utc2.itk62.store.services.CategoryService;
import utc2.itk62.store.services.ProductService;
import utc2.itk62.store.services.SupplierService;
import utc2.itk62.store.util.FormatDouble;

import java.sql.Timestamp;

public class ProductController {
    private static final ProductService productService = new ProductService();
    private static final SupplierService supplierService = new SupplierService();
    private static final CategoryService categoryService = new CategoryService();

    public TableView<Product> tableListProduct;
    public TextField id;
    public TextField name;
    public TextField quantity;
    public ComboBox<Supplier> supplier;
    public TextField price;
    public TextArea desc;
    public ComboBox<Category> category;
    public Button btnAddImage;
    public ComboBox<String> keySearch;
    public TextField valueSearch;
    public DatePicker toDate;
    public DatePicker fromDate;
    public TableColumn<Integer, Integer> stt;
    public TableColumn<Product, Integer> colId;
    public TableColumn<Product, Supplier> colSupplier;
    public TableColumn<Product, Category> colCategory;
    public TableColumn<Product, String> colName;
    public TableColumn<Product, Integer> colQuantity;
    public TableColumn<Product, String> colPrice;
    public TableColumn<Product, String> colDesc;
    public TableColumn<Product, Integer> colStatus;
    public TableColumn<Product, Timestamp> colCreatedAt;
    public TableColumn<Product, Timestamp> colUpdatedAt;
    public ImageView image;


    private ObservableList<Product> productList;
    private ObservableList<Supplier> supplierList = FXCollections.observableArrayList(supplierService.getAllSuppliers());
    private ObservableList<Category> categoryList = FXCollections.observableArrayList(categoryService.getAllCategory());

    public void initialize() {


        // set supplier
        supplier.setItems(supplierList);

        // set category
        category.setItems(categoryList);

        // setup event for table view
        setUpTableView();

        setupPriceTextField();
        reloadTableView();

    }

    private void reloadTableView() {
        // table view
        tableListProduct.getItems().clear();
        productList = FXCollections.observableArrayList(productService.getAllProduct());

        colSupplier.setCellValueFactory(new PropertyValueFactory<Product, Supplier>("supplier"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Product, Category>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        colPrice.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                Product product = param.getValue();
                return new SimpleStringProperty(FormatDouble.toString(product.getPrice()));
            }
        });
        colQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        colDesc.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        colName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        colId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<Product, Timestamp>("createdAt"));
        colUpdatedAt.setCellValueFactory(new PropertyValueFactory<Product,Timestamp>("updatedAt"));
        colStatus.setCellValueFactory(new PropertyValueFactory<Product, Integer>("status"));
        tableListProduct.setItems(productList);
        tableListProduct.getSelectionModel().selectFirst();
        updateProductCurrentRowToForm();
    }

    private void setupPriceTextField() {
        price.textProperty().addListener((observable, oldValue, newValue) -> {
            // kiểm tra xem chuỗi mới nhập vào có thể định dạng thành số không
            try {
                String valueAmount = FormatDouble.toString(FormatDouble.toDouble(newValue));
                price.setText(valueAmount);
            } catch (NumberFormatException e) {
                // nếu không thể định dạng thành số, bỏ qua và giữ nguyên chuỗi nhập vào
            }
        });
    }


    private void updateProductCurrentRowToForm() {
        Product product = tableListProduct.getSelectionModel().getSelectedItem();
        id.setText(String.valueOf(product.getId()));
        supplier.setValue(product.getSupplier());
        category.setValue(product.getCategory());
        name.setText(product.getName());
        quantity.setText(String.valueOf(product.getQuantity()));
        desc.setText(product.getDescription());
        price.setText(String.valueOf(product.getPrice()));
        image.setImage(new Image(product.getAvatar()));
    }

    private void setUpTableView( ) {
        tableListProduct.setOnMouseClicked(mouseEvent -> {
            updateProductCurrentRowToForm();
        });

        tableListProduct.setOnKeyPressed(keyPressed -> {
            updateProductCurrentRowToForm();
        });
    }
}