package com.proj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ManagerPage extends JFrame{
    private JPanel MainMenu;
    private JPanel ManagerIcon;
    private JPanel MyAccount;
    private JLabel myAccountLabel;
    private JPanel CakeShops;
    private JLabel CakeShopsLabel;
    private JPanel Orders;
    private JLabel OrdersLabel;
    private JPanel Rating;
    private JLabel RatingLabel;
    private JLabel mIdLabel;
    private JLabel fnameLabel;
    private JLabel emailLabel;
    private JButton changePasswordButton;
    private JPasswordField oldpasswordField;
    private JLabel oldpassLabel;
    private JLabel newpassLabel;
    private JPasswordField newpasswordField;
    private JButton saveButton;
    private JPanel pages;
    private JPanel ManagerAccountPanel;
    private JPanel ManagerMainPanel;
    private JPanel cackshopsPanel;
    private JPanel headerPanel;
    private JButton addButton;
    private JButton searchButton;
    private JPanel OrdersPanel;
    private JButton updateStatusButton;
    private JPanel shopsPanel;
    private JPanel RatingPanel;
    private JPanel commentPanel;
    private JLabel ManagerIDL;
    private JLabel ManagerNameL;
    private JLabel ManagerEmailL;
    private JButton logOutButton;
    private JTable cakeshopTable;
    private JScrollPane cakeshopScrollPane1;
    private JTable OrdersMTable;
    private JScrollPane OrdersScrollPane;
    private JTable MOrderITable;
    private JLabel TPriceLabel;
    private JLabel shopIDLabel;
    private JLabel LocationLabel;
    private JTable MRatingTable;
    private JScrollPane MRatingScrollPane1;
    private JLabel ProImage;
    private JTable CommentTable;
    private JLabel star2;
    private JLabel star1;
    private JLabel star3;
    private JLabel star4;
    private JLabel star5;
    private JScrollPane CommentScrollPane;
    private JLabel Cakeshop_MLabel;
    private JLabel ManagedCSH_Label;
    private JLabel cakeLabel;


    public static JTable tShops;
    public static JTable tOrders;
    public static JTable tOIrders;
    public static JTable tRating;
    public static JTable tComment;
    public static JLabel TotPrice;
    public static JLabel shopID;
    public static JLabel Loc;
    public static JLabel PImgLabel;
    public static JLabel [] stars = new JLabel[5];
    public static JLabel cakeL;
    public static JButton stateButton;

    public static String search = "ALL";
    public static  String status="";
    public static int orderID = 0 ;
    public static int productID = 0 ;

    static Color selected = Color.GRAY;
    static Color notSelected = Color.white;


    public ManagerPage() {

        tShops = cakeshopTable;
        tOrders = OrdersMTable;
        tOIrders = MOrderITable;
        TotPrice = TPriceLabel;
        shopID = shopIDLabel;
        Loc = LocationLabel;
        tRating = MRatingTable;
        PImgLabel = ProImage;
        tComment = CommentTable;
        stars = new JLabel[]{star1, star2, star3, star4, star5};
        stateButton=updateStatusButton;
        cakeL = cakeLabel;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 800);
        add(ManagerMainPanel);


        manager boss = LoginPage.boss;
        try {
            DBConnection db  = new DBConnection();
            ManagerIDL.setText(String.valueOf(boss.id));
            ManagerNameL.setText(boss.FName +" " + boss.MName + " "+ boss.LName);
            ManagerEmailL.setText(boss.email);
            ManagedCSH_Label.setText(db.getShopName(boss));
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }



        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldpassLabel.setVisible(true);
                oldpasswordField.setVisible(true);
                newpassLabel.setVisible(true);
                newpasswordField.setVisible(true);
                saveButton.setVisible(true);

            }
        });

        CakeShops.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    new ComponentTable(6).updateTableCSH();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                setVisiblePanel(false,true,false,false);
                changeMenu(Methods.notSelected,Methods.selected,Methods.notSelected,Methods.notSelected);
            }
        });

        Orders.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                setVisiblePanel(false,false,true,false);
                changeMenu(Methods.notSelected,Methods.notSelected,Methods.selected,Methods.notSelected);

                try {
                    new ComponentTable(7).updateTableMO();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        Rating.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                setVisiblePanel(false,false,false,true);
                changeMenu(Methods.notSelected,Methods.notSelected,Methods.notSelected,Methods.selected);

                try {
                    new ComponentTable(9).updateTableMP(9);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        MyAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisiblePanel(true,false,false,false);
                changeMenu(Methods.selected,Methods.notSelected,Methods.notSelected,Methods.notSelected);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBConnection con =new DBConnection();
                    if(String.valueOf(oldpasswordField.getPassword()).equals(boss.password) && newpasswordField.getPassword().length > 6 ){
                        if(con.update_pass_Manger(String.valueOf(newpasswordField.getPassword()),boss.id)==1){

                            JOptionPane.showMessageDialog(null,"your password has been successfully updated","Successful",JOptionPane.INFORMATION_MESSAGE);

                        } else{
                            JOptionPane.showMessageDialog(null,"your password has been unsuccessfully updated","Unsuccessful",JOptionPane.ERROR_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,"invalid old password Or new password must be greater than 6 characters" ,"Unsuccessful",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

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

                JTextField Loc = new JTextField(20);
                JLabel city = new JLabel();

                JPanel myPanel = new JPanel();
                myPanel.setLayout(new GridLayout(3,2));
                myPanel.add(new JLabel("City:"));
                myPanel.add(city);
                myPanel.add(new JLabel("Location:"));
                myPanel.add(Loc);

                DBConnection db = null;

                try {
                    db = new DBConnection();
                    city.setText(db.getShopName(boss));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Add new shop information", JOptionPane.OK_CANCEL_OPTION);


                if (result == JOptionPane.OK_OPTION) {

                    try {
                        db = new DBConnection();
                        db.insertShop(boss,Loc.getText());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String [] cites = {"ALL","Dammam", "Jubail", "Khobar", "Riyadh", "Jeddah", "Ahsaa"};
                JComboBox City = new JComboBox(cites);

                JPanel myPanel = new JPanel();
                myPanel.setLayout(new GridLayout(1,2));
                myPanel.add(new JLabel("City:"));
                myPanel.add(City);

                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Search in specific City", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    search = (String) City.getSelectedItem();
                    try {
                        new ComponentTable(6).updateTableCSH();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        updateStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBConnection db=new DBConnection();
                    boolean update=false;
                    if(status.equals("CONFIRMED")) {
                        if(db.update_State("PROCESSING",orderID)==1) {
                            update=true;
                        }
                    } else if(status.equals("PROCESSING")) {
                        if(db.update_State("DELIVERED",orderID)==1) {
                            update=true;
                        }
                    }
                    if(update){
                        new ComponentTable(7).updateTableMO();
                        JOptionPane.showMessageDialog(null,"State has been  Successfully updated","Successful",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null," Unsuccessful update","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public void setVisiblePanel(boolean account,boolean cakeShops, boolean orders, boolean rating ){
        ManagerAccountPanel.setVisible(account);
        cackshopsPanel.setVisible(cakeShops);
        OrdersPanel.setVisible(orders);
        RatingPanel.setVisible(rating);
    }

    public void changeMenu(Color a, Color b, Color c, Color d ){
        MyAccount.setBackground(a);
        CakeShops.setBackground(b);
        Orders.setBackground(c);
        Rating.setBackground(d);

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here

        cakeshopTable = new ComponentTable(6).CreateCompTable(6);
        cakeshopScrollPane1 = new JScrollPane(cakeshopTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        OrdersMTable = new ComponentTable(7).CreateCompTable(7);
        OrdersScrollPane = new JScrollPane(OrdersMTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        MOrderITable = new ComponentTable(8).CreateCompTable(8);

        MRatingTable = new ComponentTable(9).CreateCompTable(9);
        MRatingScrollPane1= new JScrollPane(MRatingTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CommentTable = new ComponentTable(10).CreateCompTable(10);
        CommentScrollPane = new JScrollPane(CommentTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
}
