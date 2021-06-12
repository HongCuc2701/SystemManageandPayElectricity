package CONTROLLERS.client;

import com.jfoenix.controls.*;
import database.adminRepo.NotificationsRepo;
import database.clientRepo.ClientRepo;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import MODELS.*;
import MODELS.Region;
import utils.helper.DataHolder;
import utils.helper.Navigator;
import utils.helper.NavigatorDetail;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

//TODO fix user interface when first time enter the client
public class ClientController implements Initializable {


    @FXML
    private StackPane main;
    @FXML
    private StackPane notification, service;
    @FXML
    private HBox home;
    @FXML
    private Pane account, bill, homecalculate, homescheduling, slider, homecalculateresult;
    @FXML
    private Label labelGreeting;
    @FXML
    private JFXTextField txtttpwconsump, txtnbohouseholds, txtecharges, txtVAT, txttt;
    @FXML
    private JFXButton btncalculate, btncalculateback;
    @FXML
    private JFXButton btnLogout, checkin, checkout, btnRemoveCredit, btnSetDefaultCredit, btnSaveProfile;
    @FXML
    private Pane btnNotification;
    @FXML
    private AnchorPane reports;
    @FXML
    private TableView<Invoice> tableBill;
    @FXML
    private TableColumn<Invoice, String> idColumn;
    @FXML
    private TableColumn<Invoice, Date> timeColumn;
    @FXML
    private TableColumn<Invoice, Boolean> status;
    @FXML
    private TableColumn<Invoice, Double> valueColumn;
    @FXML
    private JFXTextField idText, previousValue, currentValue, consumeValue, VAT, total, electricityType, fromDate, toDate;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private BarChart<?, ?> barChart;
    @FXML
    private TableView<Notification> tableNotification;
    @FXML
    private TableColumn<Notification, String> no_id, detail;
    @FXML
    private TableColumn<Notification, Date> date;
    @FXML
    private JFXComboBox<Region.REGION_ENUM> cbRegion;
    @FXML
    private JFXComboBox<Bank.BANK_ENUM> cbbPaymentBank;
    @FXML
    private JFXTextField txtFullName, txtID, txtNumber, txtAddress, txtNationalID, txtDateRegister, txtUsername,
            txtInvoiceCardHolderName, txtInvoiceCardNumber, txtInvoiceAccountNumber;
    @FXML
    private Label lblqtt1, lblqtt2, lblqtt3, lblqtt4, lblqtt5, lblqtt6, lbltt1, lbltt2, lbltt3, lbltt4, lbltt5, lbltt6;
    @FXML
    private JFXPasswordField pwfCurrentPassword, pwfNewPassword, pwfRetypePassword;
    @FXML
    private RadioButton rBtnMale, rBtnFemale, rBtnOrther;
    @FXML
    private DatePicker dpBirthday;
    @FXML
    private TableView<CreditCard> tblCreditCard;
    @FXML
    private TableColumn<CreditCard, String> tblColumnCardHolderName, tblColumnAccountNumber, tblColumnBank, tblColumnStatus;
    @FXML
    private Label lbltotalinvoices, lbltotal, lblperiodnewinvoice, lbltotalnewinvoice;


    private ClientRepo repo;
    private Customer user = new Customer();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username = DataHolder.getINSTANCE().getUserName();
        try {
            repo = new ClientRepo(username);
            user = repo.getCustomer();
            initComponents();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


    //Event handlers for Home
    @FXML
    private void btnHome() {
        setLblWelcome(user.getName());
        checkSum(main);
        home.setVisible(true);
    }


    @FXML
    private void btnCalendar(MouseEvent mouseEvent) {
        checkSum(service);
        homescheduling.setVisible(true);
    }

    @FXML
    private void btnCalculator(MouseEvent mouseEvent) {
        checkSum(service);
        homecalculate.setVisible(true);
    }

    @FXML
    private void btnCalculate(){
        checkSum(service);
        homecalculateresult.setVisible(true);
        refreshCalculate();

        int ttpw, nbofhouse, qtt, tt;
        int echarges = 0;
        ttpw = Integer.parseInt(txtttpwconsump.getText());
        nbofhouse = Integer.parseInt(txtnbohouseholds.getText());

        if (ttpw > 400 * nbofhouse) {
            qtt = ttpw - 400 * nbofhouse;
            lblqtt6.setText(Integer.toString(qtt));
            tt = qtt * 2927;
            lbltt6.setText(NumberFormat.getNumberInstance().format(tt));
            echarges += tt;
            ttpw = 400* nbofhouse;
        }
        if(ttpw > 300 * nbofhouse){
            qtt = ttpw - 300 * nbofhouse;
            lblqtt5.setText(Integer.toString(qtt));
            tt = qtt * 2834;
            lbltt5.setText(NumberFormat.getNumberInstance().format(tt));
            echarges += tt;
            ttpw = 300 * nbofhouse;
        }
        if(ttpw > 200 * nbofhouse){
            qtt = ttpw - 200 * nbofhouse;
            lblqtt4.setText(Integer.toString(qtt));
            tt = qtt * 2536;
            lbltt4.setText(NumberFormat.getNumberInstance().format(tt));
            echarges += tt;
            ttpw = 200 * nbofhouse;
        }
        if(ttpw > 100 * nbofhouse){
            qtt = ttpw - 100 * nbofhouse;
            lblqtt3.setText(Integer.toString(qtt));
            tt = qtt * 2014;
            lbltt3.setText(NumberFormat.getNumberInstance().format(tt));
            echarges += tt;
            ttpw = 100 * nbofhouse;
        }
        if(ttpw > 50 * nbofhouse){
            qtt = ttpw - 50 * nbofhouse;
            lblqtt2.setText(Integer.toString(qtt));
            tt = qtt * 1734;
            lbltt2.setText(NumberFormat.getNumberInstance().format(tt));
            echarges += tt;
            ttpw = 50 * nbofhouse;
        }
        if(ttpw > 0 * nbofhouse){
            qtt = ttpw;
            lblqtt1.setText(Integer.toString(qtt));
            tt = qtt * 1678;
            lbltt1.setText(NumberFormat.getNumberInstance().format(tt));
            echarges += tt;
        }

        txtecharges.setText(NumberFormat.getNumberInstance().format(echarges));
        txtVAT.setText(NumberFormat.getNumberInstance().format(echarges * 10 / 100));
        txttt.setText(NumberFormat.getNumberInstance().format(echarges * 110 / 100));
    }

    @FXML
    private void btnCalculateBack(){
        checkSum(service);
        homecalculate.setVisible(true);
    }

    //Event handlers for Account
    @FXML
    private void btnAccount() {
        labelGreeting.setText("Account");
        checkSum(main);
        account.setVisible(true);
    }

    @FXML
    private void btnAddCredit() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./../../VIEWS/client/add-credit-card_screen.fxml"));
        Parent root = loader.load();
        AddCreditCardController addCreditCardController = loader.getController();
        addCreditCardController.getRepo(repo);
        Scene scene = new Scene(root, 600, 420);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    //Event handlers for BILL
    @FXML
    private void btnBill() {
        labelGreeting.setText("Invoice");
        checkSum(main);
        bill.setVisible(true);
    }

    //Event handlers for Statistic
    @FXML
    private void btnReport() {
        labelGreeting.setText("Statistic");
        checkSum(main);
        reports.setVisible(true);
    }

    //Event handlers for Notification
    @FXML
    private void btnNotification(MouseEvent mouseEvent) {
        labelGreeting.setText("Notification");
        checkSum(main);
        notification.setVisible(true);
    }

    //Log out
    @FXML
    private void btnLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Are you sure to log out?");

        ButtonType btnTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(btnTypeYes, btnTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == btnTypeYes) {
            try {
                DataHolder.getINSTANCE().setUserName(null);
                Navigator.navigate((Stage) main.getScene().getWindow(), new NavigatorDetail("auth/auth_screen.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Others
    private void checkSum(StackPane tab) {
        for (int i = 0; i < tab.getChildren().size(); i++) {
            tab.getChildren().get(i).setVisible(false);
        }
    }

    private void setLblWelcome(String name) {
        String str = "";
        for (String s : name.split(" ")) {
            str = s;
        }
        labelGreeting.setText("Hi, " + str);
    }

    private void refreshCalculate(){
        lblqtt1.setText("0");
        lblqtt2.setText("0");
        lblqtt3.setText("0");
        lblqtt4.setText("0");
        lblqtt5.setText("0");
        lblqtt6.setText("0");
        lbltt1.setText("0");
        lbltt2.setText("0");
        lbltt3.setText("0");
        lbltt4.setText("0");
        lbltt5.setText("0");
        lbltt6.setText("0");
    }

    //Initializations

    private void initComponents() {

        setLblWelcome(user.getName());
        initComboBoxRegion();
        initComboBoxBank();
        initTables();

        btnSaveProfile.setOnMouseClicked((MouseEvent event) ->{
            Boolean gender = true;
            if (!rBtnMale.isSelected())
                gender = false;
            Date dateRegister = null;
            try {
                dateRegister = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateRegister.getText());
                repo.saveCustomer(new Customer(
                        repo.getCustomer().getId(),
                        txtFullName.getText(),
                        txtUsername.getText(),
                        txtNumber.getText(),
                        txtAddress.getText(),
                        txtNationalID.getText(),
                        gender,
                        dpBirthday.getValue(),
                        cbRegion.getValue(),
                        dateRegister

                ));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Save successful!");
                alert.showAndWait();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                repo = new ClientRepo(user.getUsername());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            user = repo.getCustomer();
            initAccountProfile();
        });
        btnRemoveCredit.setOnMouseClicked((MouseEvent event) ->{
            String id = tblCreditCard.getSelectionModel().getSelectedItem().getId();
            try {
                repo.deleteCreditCard(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        btnSetDefaultCredit.setOnMouseClicked((MouseEvent event) ->{
          String id = tblCreditCard.getSelectionModel().getSelectedItem().getId();
            try {
                repo.setDefaultCreditCard(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tableBill.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                if (tableBill.getSelectionModel().getSelectedItems() != null) {
                    Invoice invoice = tableBill.getSelectionModel().getSelectedItem();
                    idText.setText(String.valueOf(invoice.getId()));
                    previousValue.setText(String.valueOf(invoice.getPreviousValue()));
                    currentValue.setText(String.valueOf(invoice.getCurrentValue()));
                    consumeValue.setText(String.valueOf(invoice.getConsumedValue()));
                    VAT.setText(String.valueOf(invoice.getVAT()));
                    total.setText(String.valueOf(invoice.getTotal()));
                    fromDate.setText(String.valueOf(invoice.getFromDate()));
                    toDate.setText(String.valueOf(invoice.getToDate()));
                    electricityType.setText(String.valueOf(invoice.getElectricityType()));
                    checkin.setVisible(!invoice.isPaid());
                }
            }
        });


        slider.setTranslateX(400);
        checkin.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();
            slider.setTranslateX(400);
        });
        checkout.setOnMouseClicked(event -> {
            try {
                repo.updateInvoice(idText.getText());
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(400);
            slide.play();
            slider.setTranslateX(0);
        });
    }

    private void initTables() {
        initAccountProfile();
        initAccountChangePassword();
        initAccountPaymentMethod();
        initInvoiceTable();
        initInvoiceSliderPayment();
        initStatisticLineChart();
        initStatisticBarChart();
        initNotificationsTable();
        initLabelOverviewHome();
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private void initLabelOverviewHome(){
        ObservableList<Invoice> invoices = tableBill.getItems();
        lbltotalinvoices.setText(Integer.toString(invoices.size()));
        float total = 0;
        for(Invoice invoice : invoices){
            total += invoice.getTotal();
        }
        lbltotal.setText(NumberFormat.getInstance().format(total));
        lblperiodnewinvoice.setText(LocalDate.parse(invoices.get(invoices.size() - 1).getFromDate().toString(), formatter).toString());
        lbltotalnewinvoice.setText(NumberFormat.getNumberInstance().format(invoices.get(invoices.size() - 1).getTotal()));
    }

    private void initAccountProfile() {

        txtFullName.setText(user.getName());
        txtID.setText(user.getId());
        txtNumber.setText(user.getPhoneNumber());
        if (user.getGender()) {
            rBtnMale.setSelected(true);
        } else {
            rBtnFemale.setSelected(true);
        }
        dpBirthday.setValue(user.getBirthday());
        txtAddress.setText(user.getAddress());
        txtNationalID.setText(user.getNationalId());
        cbRegion.setValue(user.getRegion());
        txtDateRegister.setText(user.getDateRegister().toString());
    }

    private void initAccountChangePassword() {
        Customer customer = null;
        customer = repo.getCustomer();
        txtUsername.setText(user.getUsername());
    }


    private void initAccountPaymentMethod() {
        tblColumnCardHolderName.setCellValueFactory(new PropertyValueFactory<>("cardholdername"));
        tblColumnAccountNumber.setCellValueFactory(new PropertyValueFactory<>("accountnumber"));
        tblColumnBank.setCellValueFactory(new PropertyValueFactory<>("bankname"));
        tblColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblCreditCard.setItems(repo.getAllCreditCards());
    }

    private void initInvoiceTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("fromDate"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        status.setCellValueFactory(new PropertyValueFactory<>("paid"));

        tableBill.setItems(repo.getInvoices());
    }

    private void initInvoiceSliderPayment() {
        for (CreditCard card : tblCreditCard.getItems()) {
            if (card.getStatus().equals("Default")) {
                txtInvoiceCardHolderName.setText(card.getCardholdername());
                txtInvoiceCardNumber.setText(card.getCardnumber());
                txtInvoiceAccountNumber.setText(card.getAccountnumber());
                cbbPaymentBank.setValue(card.getBankname());
            }
        }
    }

    private void initStatisticLineChart() {
        ObservableList<Invoice> invoices = tableBill.getItems();
        int i = invoices.size();
        XYChart.Series series = new XYChart.Series();
        if (i >= 5) {
            for (int d = 5; d >= 1; d--) {
                series.getData().add(new XYChart.Data(invoices.get(i - d).getFromDate().toString().subSequence(0, 7), invoices.get(i - d).getConsumedValue()));
            }
        } else {
            for (int d = i; d >= 1; d--) {
                series.getData().add(new XYChart.Data(invoices.get(i - d).getFromDate().toString().subSequence(0, 7), invoices.get(i - d).getConsumedValue()));
            }
        }

        lineChart.getData().addAll(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        series.getNode().setStyle("-fx-stroke: #F5F5F5");
    }

    private void initStatisticBarChart() {
        ObservableList<Invoice> invoices = tableBill.getItems();
        int i = invoices.size();
        XYChart.Series series1 = new XYChart.Series();

        if (i >= 5) {
            for (int d = 5; d >= 1; d--) {
                series1.getData().add(new XYChart.Data(invoices.get(i - d).getFromDate().toString().subSequence(0, 7), invoices.get(i - d).getConsumedValue()));
            }
        } else {
            for (int d = i; d >= 1; d--) {
                series1.getData().add(new XYChart.Data(invoices.get(i - d).getFromDate().toString().subSequence(0, 7), invoices.get(i - d).getConsumedValue()));
            }
        }
        barChart.getData().addAll(series1);
        barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
    }

    private void initNotificationsTable() {
        NotificationsRepo notificationsRepo = new NotificationsRepo();
        no_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("datePublished"));
        detail.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableNotification.setItems(notificationsRepo.getNotifications());
    }


    private void initComboBoxRegion() {
        Region.REGION_ENUM[] regions = {Region.REGION_ENUM.NorthSide, Region.REGION_ENUM.MidSide, Region.REGION_ENUM.SouthSide, Region.REGION_ENUM.WestSide};

        Callback<ListView<Region.REGION_ENUM>, ListCell<Region.REGION_ENUM>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Region.REGION_ENUM region, boolean empty) {
                super.updateItem(region, empty);
                setText(empty ? "" : Region.regionToString(region));
            }
        };
        cbRegion.getItems().addAll(regions);
        cbRegion.setCellFactory(factory);
        cbRegion.setButtonCell(factory.call(new JFXListView<>()));
    }
    private void initComboBoxBank() {
        Bank.BANK_ENUM[] banks = {Bank.BANK_ENUM.VCB, Bank.BANK_ENUM.ACB, Bank.BANK_ENUM.BIDV, Bank.BANK_ENUM.ARB,};
        Callback<ListView<Bank.BANK_ENUM>, ListCell<Bank.BANK_ENUM>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Bank.BANK_ENUM bank, boolean empty) {
                super.updateItem(bank, empty);
                setText(empty ? "" : Bank.bankToString(bank));
            }
        };
        cbbPaymentBank.getItems().addAll(banks);
        cbbPaymentBank.setCellFactory(factory);
        cbbPaymentBank.setButtonCell(factory.call(new JFXListView<>()));
    }
}
