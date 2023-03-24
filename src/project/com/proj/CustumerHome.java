package com.proj;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.io.FileWriter;

public class CustumerHome extends JFrame{

    private JLabel label1;
    private JLabel offersSectionLabel;
    private JPanel userIcon;
    private JPanel Home;
    private JLabel homeLabel;
    private JLabel myAccountLabel;
    private JPanel MyAccount;
    private JPanel MyOrders;
    private JPanel MyBasket;
    private JPanel AboutUs;
    private JLabel myBasketLabel;
    private JLabel aboutUsLabel;
    private JLabel myOrdersLabel;
    private JPanel MainMenu;
    private JPanel Categories;
    private JPanel MyOrdersPanel;
    private JPanel MyAccountPanel;
    private JButton saveButton;
    private JButton changePasswordButton;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPanel MyBasketPanel;
    private JPanel AboutUsPanel;
    private JPanel ProductPanel;
    private JPanel MainPanel;
    private JLabel MYACCOUNTLabel;
    private JLabel costumerIDLabel;
    private JLabel CusIDLabel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel oldPasswordLabel;
    private JLabel newPasswordLabel;
    private JLabel phoneNumberLabel;
    private JPanel P1;
    private JPanel P2;
    private JPanel P3;
    private JPanel P4;
    private JPanel Ca1;
    private JPanel Ca2;
    private JComboBox cakeShopsComboBox;
    private JButton orderButton;
    private JButton sortButton;
    private JButton RATEButton;
    private JPanel OrderDetails;
    private JPanel OrderInfoPanel ;
    private JPanel RatePanel;
    private JLabel rateOrderIDLabel;
    private JButton star1;
    private JButton star2;
    private JButton star3;
    private JButton star4;
    private JButton star5;
    private JButton submitButton;
    private JTextArea welcomeToOurCakeTextArea;
    private JLabel Logo;
    private JTextField emailText;
    private JTextField nameText;
    private JTextField phoneText;
    private JButton logOutButton;
    private JPanel Ca3;
    private JSpinner spinner1;
    private JButton addButton;
    private JTable table1;
    private JScrollPane scrollPane1;
    private JTable ProductsTable;
    private JScrollPane productsScrollPane;
    private JTable rateTable;
    private JTable basketTable;
    private JScrollPane BasketScrollPane;
    private JComboBox sortcomboBox;
    private JButton filterButton;
    private JComboBox filtercomboBox;
    private JTable ItemOrderTable;
    private JScrollPane rateScrollPane;
    private JScrollPane ItemOrderScrollPane;
    private JLabel price;
    private JLabel deliveryPrice;
    private JLabel FinalPrice;
    private JLabel orderIdLable;
    private JLabel selectedBranchLabel;
    private JLabel basketPriceLabel;
    private JLabel basketDeliveryLabel;
    private JLabel basketTotalLabel;
    private JComboBox ratingComboBox1;
    private JTextArea commentTextArea;
    private JLabel p_rate;
    private JButton doneButton;
    private JComboBox cityComboBox;
    private JLabel opinionLabel;
    private JTextArea opinionTextArea;
    private JButton OpinionsubmitButton;
    private JButton addBtn;



    public static CustumerHome Instance;
    public static JSpinner sp;
    public static JLabel PriceL;
    public static JLabel oLabel;
    public static JLabel TCostLabel;
    public static JLabel sBranchLabel;
    public static JLabel basketPLabel;
    public static JLabel basketTotLabel;
    public static JLabel p_rating;
    public static JLabel OrderID_L;
    public static JComboBox rateCompoBox;
    public static JTextArea commTextArea;
    public static JTable t;
    public static JTable tO;
    public static JTable tp;
    public static JTable tr;
    public static JTable toi;


    public static String selsectedFilter ="ALL";
    public static int orderID = 0;
    public static String basketPrice = "0.0 SAR";
    public static String basketTotal = "0.0 SAR";

    public static int SelectedProID = 0;
    public static int selectedcCa = 0;
    public static float TotalPrice;
    public static int selectedRow = 0 ;

    public static ArrayList <SelectedItem> selectedItems = new ArrayList<>();
    public static ArrayList <String> cakeShopsList = new ArrayList<>();

    // colors


    public static customer CUS = LoginPage.user;

    public  CustumerHome() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 800);
        add(MainPanel);

        Instance = this;
        sp = spinner1;
        PriceL = price;
        t = basketTable;
        tO = table1;
        tp = ProductsTable;
        tr =  rateTable;
        toi = ItemOrderTable;
        oLabel = orderIdLable;
        sBranchLabel = selectedBranchLabel;
        TCostLabel = FinalPrice;
        basketPLabel = basketPriceLabel;
        basketTotLabel = basketTotalLabel;
        rateCompoBox= ratingComboBox1;
        p_rating = p_rate;
        commTextArea =commentTextArea;
        OrderID_L = rateOrderIDLabel ;

        // set spinner in category panel start from 1 to 20
        SpinnerModel model = new SpinnerNumberModel(1,1,20,1);
        spinner1.setModel(model);

        Home.addMouseListener(new MouseAdapter() {
            @Override

            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                changeMenu(Methods.selected,Methods.notSelected,Methods.notSelected,Methods.notSelected,Methods.notSelected);
                setVisiblePanel(true,false,false,false,
                        false,false,false,false);
            }
        });


        MyAccount.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {


                super.mouseClicked(e);

                changeMenu(Methods.notSelected, Methods.selected ,Methods.notSelected,Methods.notSelected,Methods.notSelected);
                setVisiblePanel(false,true,false,false,
                        false,false,false,false);

                // set costumer information in TextFields
                CusIDLabel.setText(String.valueOf(CUS.id));
                nameText.setText(CUS.name);
                emailText.setText(CUS.email);
                phoneText.setText(CUS.phone_Num);
                cityComboBox.setSelectedItem(CUS.address);
            }
        });

        MyOrders.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                changeMenu(Methods.notSelected, Methods.notSelected, Methods.selected ,Methods.notSelected,Methods.notSelected);
                setVisiblePanel(false,false,false,true,
                        false,false,false,false);

                try {
                    new ComponentTable(5).updateTableO();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }});

        Ca1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                changeMenu(Methods.selected, Methods.notSelected, Methods.notSelected ,Methods.notSelected,Methods.notSelected);
                setVisiblePanel(false,false,false,false,
                        false,true,false,false);

                selectedcCa= 0;

                try {
                    new ComponentTable().updateTableP();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        });

        Ca2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                changeMenu(Methods.selected, Methods.notSelected, Methods.notSelected ,Methods.notSelected,Methods.notSelected);
                setVisiblePanel(false,false,false,false,
                        false,true,false,false);

                selectedcCa = 1;
                try {
                    new ComponentTable().updateTableP();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });

        Ca3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                changeMenu(Methods.selected, Methods.notSelected, Methods.notSelected ,Methods.notSelected,Methods.notSelected);
                setVisiblePanel(false,false,false,false,
                        false,true,false,false);

                selectedcCa = 2;
                try {
                    new ComponentTable().updateTableP();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });


        AboutUs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                changeMenu( Methods.notSelected, Methods.notSelected ,Methods.notSelected, Methods.notSelected ,Methods.selected);
                setVisiblePanel(false,false,false,false,
                        true,false,false,false);

                // input from textFile
                Scanner input = null;
                String about_us="";
                try {
                    input =new Scanner(Paths.get("about_Us.txt"));
                } catch (IOException ex) {
                    System.err.println("Wrong Opening the file ");
                }

                try{
                   while(input.hasNext()) {
                        about_us=about_us+input.nextLine();
                   }
                    welcomeToOurCakeTextArea.setText(about_us);
                }catch (NoSuchElementException ex){
                    System.err.println("Fail In format");
                }catch (IllegalStateException ex){
                    System.err.println("Fail In Reading ");
                }
                if(input!=null){
                    input.close();
                }


            }
        });


        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPasswordLabel.setVisible(true);
                oldPasswordLabel.setVisible(true);
                passwordField1.setVisible(true);
                passwordField2.setVisible(true);
                saveButton.setVisible(true);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(() -> {
                    LoginPage ex = new LoginPage();
                    ex.setVisible(true);
                });
                dispose();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // insert product Id and Quantity in an arrayList
                // add to  basket

                if (SelectedProID != 0 ) {
                    boolean found = false;
                    System.out.println(selectedItems.size());
                    for (int i = 0; i < selectedItems.size(); i++) {
                        if (selectedItems.get(i).itemID == SelectedProID) {
                            selectedItems.get(i).quantity += (int) spinner1.getValue();
                            found = true;
                            JOptionPane.showMessageDialog(null,"this product has been added successfully to your basket", "successful", JOptionPane.PLAIN_MESSAGE);
                            break;
                        }
                    }
                    if (!found) {
                        selectedItems.add(new SelectedItem(SelectedProID, (int) spinner1.getValue()));
                        JOptionPane.showMessageDialog(null, "this product has been added successfully to your basket", "successful", JOptionPane.PLAIN_MESSAGE);
                    }

                    SelectedProID = 0;
                }else {
                    JOptionPane.showMessageDialog(null,"Please, Select an product first ", "WARNING MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBConnection con=new DBConnection();
                    if (CUS.password.equals(String.valueOf(passwordField1.getPassword())) && Methods.isValidPass(String.valueOf(passwordField2.getPassword()))){
                    if(con.update_pass(String.valueOf(passwordField2.getPassword()),CUS.id) == 1)
                    JOptionPane.showMessageDialog(null,"password successfully changed","Successful",JOptionPane.OK_OPTION);
                    }
                    else
                        JOptionPane.showMessageDialog(null,"invalid password","unsuccessful",JOptionPane.ERROR_MESSAGE);

                } catch (SQLException ex) {
                    
                    ex.printStackTrace();
                }

            }
        });

        nameText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBConnection con = new DBConnection();
                    if ( con.update_CName( nameText.getText(), CUS.id) == 1){
                         CUS.name = (String) nameText.getText();
                        JOptionPane.showMessageDialog(null,"your information has been successfully updated","Successful",JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(null,"your information has been unsuccessfully updated","Unsuccessful",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        emailText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBConnection con = new DBConnection();
                    if( Methods.isValidEmail(emailText.getText() )) {
                        if (con.update_email(emailText.getText(), CUS.id) == 1) {
                            CUS.email = (String) emailText.getText();
                            JOptionPane.showMessageDialog(null, "your information has been successfully updated", "Successful", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "your information has been unsuccessfully updated", "Unsuccessful", JOptionPane.ERROR_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "invalid Email", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        phoneText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBConnection con = new DBConnection();
                    if(Methods.isValidPhoneNum(phoneText.getText())) {
                        if (con.update_phoneNum(phoneText.getText(), CUS.id) == 1) {
                            CUS.phone_Num = (String) phoneText.getText();
                            JOptionPane.showMessageDialog(null, "your information has been successfully updated", "Successful", JOptionPane.INFORMATION_MESSAGE);

                        } else {
                            JOptionPane.showMessageDialog(null, "your information has been unsuccessfully updated", "Unsuccessful", JOptionPane.ERROR_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "invalid Phone Number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtercomboBox.setVisible(true);
            }
        });

        filtercomboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if( e.getStateChange() == ItemEvent.SELECTED){
                    selsectedFilter = (String) filtercomboBox.getSelectedItem();
                    System.out.println(selsectedFilter);

                    try {
                        new ComponentTable().updateTableO();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }


            }
        });

        MyBasket.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                changeMenu( Methods.notSelected, Methods.notSelected ,Methods.notSelected, Methods.selected,Methods.notSelected);
                setVisiblePanel(false,false,true,false,
                        false,false,false,false);

                try {
                    System.out.println(CUS.address);
                    DBConnection db = new DBConnection();
                    cakeShopsComboBox.setModel(db.getCakeShops(CUS));
                    new ComponentTable().updateTable();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        });


        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // insert new order on the database
                try {
                    DBConnection db = new DBConnection();

                    if (!db.insertOrder("CONFIRMED",CUS,(String)cakeShopsComboBox.getSelectedItem())) {

                        System.out.println(basketTable.getRowCount());
                        for (int i = 0; i < basketTable.getRowCount(); i++) {

                            DBConnection d = new DBConnection();
                            Order_item item = (Order_item) basketTable.getValueAt(i, 0);
                            d.insertOrderItem(item);

                        }

                        JOptionPane.showMessageDialog(null, "your order has been successfully placed", "Successful", JOptionPane.INFORMATION_MESSAGE);
                        selectedItems.clear();
                        new ComponentTable(3).updateTable();
                    }
                    else
                        JOptionPane.showMessageDialog(null,"there was an error your order is not placed, please try again","Error",JOptionPane.ERROR_MESSAGE);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }


            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("row " + selectedRow);
                Rating s = (Rating) rateTable.getValueAt(selectedRow,0); // get comp object in the row
                s.score = Integer.parseInt((String)CustumerHome.rateCompoBox.getSelectedItem());
                s.feedback =CustumerHome.commTextArea.getText();

                try {
                    DBConnection db  = new DBConnection();
                    if( db.insertRate(s,CUS) == 1){
                        rateCompoBox.setSelectedIndex(0);
                        commentTextArea.setText("");
                        commentTextArea.setEditable(false);
                        p_rate.setIcon(new ImageIcon("src/project/Defualt.png"));
                    }else {
                        JOptionPane.showMessageDialog(null,"there was an error, please try again","Error",JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(null,"Comment can't be more than 200 Character " ,"Limit Warning", JOptionPane.WARNING_MESSAGE);
                }

                System.out.println(s);
            }
        });
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Thank you " + CUS.name + "\n For sharing your experience ! " ," Successful rate ", JOptionPane.PLAIN_MESSAGE);
                setVisiblePanel(true,false, false,false,false,false,false,false);
            }
        });
        cityComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBConnection db = new DBConnection();
                    if( db.updateCity(String.valueOf(cityComboBox.getSelectedItem()),CUS.id) == 1 ){
                        CUS.address = String.valueOf(cityComboBox.getSelectedItem());
                    }else {
                        JOptionPane.showMessageDialog(null, " Unsuccessful update", "Unsuccessful", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        OpinionsubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    FileWriter f = new FileWriter("Recommendation.txt", true);
                    Formatter form = new Formatter(f);
                    form.format("\n"+"customer ID: " + CUS.id + "\n" + "Recommendation : " + opinionTextArea.getText());
                    opinionTextArea.setText("");

                    if (form != null)
                        form.close();

                }
                catch (SecurityException ex) {
                    ex.printStackTrace();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }

                catch (FormatterClosedException formatterClosedException){
                    System.err.println("Wrong writing to file ");
                }
                catch (NoClassDefFoundError elementException){
                    System.err.println("Invalid input. try again  ");
                }

            }
        });
    }


    public static void cancelSelectedItem(){
        try {
            boolean found = false;
            for (int i = 0; i < selectedItems.size(); i++) {
                if (selectedItems.get(i).itemID == SelectedProID) {
                    selectedItems.remove(i);
                    found = true;
                    break;
                }
            }
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }
    }

    public void setVisiblePanel(boolean cat, boolean MyAccount, boolean MyBasket, boolean MyOrders, boolean AboutUs, boolean Product, boolean Order, boolean Rate){

        Categories.setVisible(cat);
        MyAccountPanel.setVisible(MyAccount);
        MyBasketPanel.setVisible(MyBasket);
        MyOrdersPanel.setVisible(MyOrders);
        AboutUsPanel.setVisible(AboutUs);
        ProductPanel.setVisible(Product);
        OrderDetails.setVisible(Order);
        RatePanel.setVisible(Rate);
    }

    public void changeMenu(Color a, Color b, Color c, Color d,Color e ){
        Home.setBackground(a);
        MyAccount.setBackground(b);
        MyOrders.setBackground(c);
        MyBasket.setBackground(d);
        AboutUs.setBackground(e);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        //My orders table
        table1 = new ComponentTable(1).CreateCompTable(1);
        scrollPane1 = new JScrollPane(table1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //products table
        ProductsTable = new ComponentTable(2).CreateCompTable(2);
        productsScrollPane = new JScrollPane(ProductsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //basket
        basketTable = new ComponentTable(3).CreateCompTable(3);

        // Rating
        rateTable = new ComponentTable(4).CreateCompTable(4);
        rateScrollPane = new JScrollPane(rateTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // order details
        ItemOrderTable = new ComponentTable(5).CreateCompTable(5);
        ItemOrderScrollPane = new JScrollPane(ItemOrderTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    }
}


