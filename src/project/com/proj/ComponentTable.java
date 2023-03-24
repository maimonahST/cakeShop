package com.proj;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Struct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;



public class ComponentTable {

    private JPanel panel;
    private JTable CompTable = null;
    private CompTableModel CompModel = null;
    private JButton addButton = null;
    public static float sumOfprice = 0 ;



    ComponentTable( int n ){
        SwingUtilities.invokeLater( new Runnable() {

            @Override
            public void run() {
                new ComponentTable(n).makeUI(n);
            }
        });
    }

    ComponentTable(){

    }

    public void makeUI( int n ) {
        CompTable = CreateCompTable(n);
    }

    public  JTable CreateCompTable( int n ) {
            CompModel = new CompTableModel(n);
            JTable table = new JTable(CompModel);
            table.setRowHeight(new CellPanel_MyOrders(n).getPreferredSize().height);
            table.setTableHeader(null);
            CompCellEditorRenderer compCellEditorRenderer = new CompCellEditorRenderer(n);
            table.setDefaultRenderer(Object.class, compCellEditorRenderer);
            table.setDefaultEditor(Object.class, compCellEditorRenderer);
            return table;
    }

    public void updateTable()throws SQLException{



        tabelSetup( CustumerHome.t , 3);
        CustumerHome.t.setRowHeight(200);

        Collections.sort(CustumerHome.selectedItems, new StockComparator());
        System.out.println(CustumerHome.selectedItems);


        String s = "select * from `Product` where ";

        if (CustumerHome.selectedItems.size() > 0 ) {
            //create the Query
            for (int i = 0; i < CustumerHome.selectedItems.size(); i++) {
                s += " Product_id = ";
                s += CustumerHome.selectedItems.get(i).itemID;

                if ((i < CustumerHome.selectedItems.size() - 1)) {
                    s += " OR ";
                }

            }

                DBConnection d = new DBConnection(s);

                int i = 0;
                sumOfprice = 0;
                while (d.resultSet.next()) {
                    byte [] img = (byte[]) d.resultSet.getObject(6);
                    ImageIcon image = new ImageIcon(img);
                    Image im = image.getImage();
                    Image myImg = im.getScaledInstance(200,300,Image.SCALE_DEFAULT);


                    int quantity = CustumerHome.selectedItems.get(i).quantity;
                    float price = (Float) d.resultSet.getObject(5);
                    sumOfprice += quantity * price;
                    CompModel.addRow((int) d.resultSet.getObject(1),(String) d.resultSet.getObject(3), quantity, price,myImg  );
                    i++;
                }
            d.closeDB();

            CustumerHome.basketPLabel.setText(String.valueOf(sumOfprice));
            CustumerHome.basketTotLabel.setText(String.valueOf((sumOfprice + 15)));

        } else {
            JOptionPane.showMessageDialog(null,"Empty basket", "", JOptionPane.WARNING_MESSAGE);
            CustumerHome.Instance.setVisiblePanel(true,false, false,false,false,false,false,false);
        }

        }

    public void updateTableO()throws SQLException{

        tabelSetup(CustumerHome.tO, 1);

        DBConnection db ;
        String v  = CustumerHome.selsectedFilter;

        if ( v.equals( "ALL")){
            db = new DBConnection("select * from `Orders` where cust_id =  " + CustumerHome.CUS.id);
        } else {
            db = new DBConnection(" select * from `Orders` where  order_states = '" + v + "' and cust_id = " + CustumerHome.CUS.id );
        }
        while (db.resultSet.next()) {
            CompModel.addRow((Integer) db.resultSet.getObject(1), "State: " + (String) db.resultSet.getObject(2), db.resultSet.getDate(3));
        }

        CustumerHome.tO.setRowHeight(50);
        db.closeDB();

    }

    public void updateTableR()throws SQLException{

        tabelSetup(CustumerHome.tr, 4);
        System.out.println(CustumerHome.orderID);
        String OrderItem = " Select Product_id ,Product_Name ,img From   Product Where  Product_id IN ( Select product_ID  From   Order_Item where  Order_item_id IN( Select Order_item_id From Order_Item where order_id = '"+CustumerHome.orderID+"'))";
        System.out.println(OrderItem);
        DBConnection d = new DBConnection(OrderItem);
//
        while (d.resultSet.next()) {

            byte [] img = (byte[]) d.resultSet.getObject(3);
            ImageIcon image = new ImageIcon(img);
            Image im = image.getImage();
            Image myImg = im.getScaledInstance(200,300,Image.SCALE_DEFAULT);


            CompModel.addRow((int) d.resultSet.getObject(1),(String)d.resultSet.getObject(2),0 ," comment ", myImg);
        }

        CustumerHome.tr.setRowHeight(150);
        d.closeDB();




    }

    public void updateTableP()throws SQLException{

        tabelSetup(CustumerHome.tp, 2);

        int c = CustumerHome.selectedcCa;
        DBConnection db = new DBConnection(" select * from `Product` where category = " + c );
        while (db.resultSet.next()) {
            byte [] img = (byte[]) db.resultSet.getObject(6);
            ImageIcon image = new ImageIcon(img);
            Image im = image.getImage();
            Image myImg = im.getScaledInstance(200,300,Image.SCALE_DEFAULT);
            CompModel.addRow((int)db.resultSet.getObject(1), (String)db.resultSet.getObject(3),(String)db.resultSet.getObject(4), (Float)db.resultSet.getObject(5),myImg);

        }

        CustumerHome.tp.setRowHeight(200);


        db.closeDB();

    }

    public void updateTableOD()throws SQLException{

        tabelSetup(CustumerHome.toi, 5);

        if( CustumerHome.orderID != 0 ) {
            String details = "Select Product.Product_id, Product_Name ,Quantity , Total_price, img \n" +
                    "From Product , Order_Item \n" +
                    "Where Product.product_ID = Order_Item.product_ID AND Order_Item.order_id = '" + CustumerHome.orderID + "'";
            DBConnection db = new DBConnection(details);

            CustumerHome.TotalPrice = 0;

            float price ;
            int quantity;

            while (db.resultSet.next()) {
                price = (Float) db.resultSet.getObject(4);
                quantity = (Integer) db.resultSet.getObject(3);

                byte [] img = (byte[]) db.resultSet.getObject(5);
                ImageIcon image = new ImageIcon(img);
                Image im = image.getImage();
                Image myImg = im.getScaledInstance(200,300,Image.SCALE_DEFAULT);


                CompModel.addRow((int) db.resultSet.getObject(1),(String) db.resultSet.getObject(2),quantity ,price, myImg);

            }

            CustumerHome.toi.setRowHeight(200);

            db = new DBConnection("select  sum(Total_price) , City from `Order_Item`, `Orders` , `CakeShop`  where Orders.order_id = '" + CustumerHome.orderID +"' and  Orders.shop_id = CakeShop.Shop_id And Order_Item.order_id = Orders.order_id" );
            while (db.resultSet.next()) {
                CustumerHome.PriceL.setText(String.valueOf(db.resultSet.getObject(1)));
                CustumerHome.sBranchLabel.setText((String) db.resultSet.getObject(2) );
                CustumerHome.TCostLabel.setText( String.valueOf( Float.parseFloat(CustumerHome.PriceL.getText()) + 15));
            }


            db.closeDB();

        }
    }


    //Manager Tables

    public void updateTableCSH()throws SQLException{

        tabelSetup(ManagerPage.tShops, 6);
        DBConnection db;
        if( ManagerPage.search.equals("ALL")) {
            db = new DBConnection("select Shop_id, City, Contact_info, Location from CakeShop, CakeShop_Location where Shop_id = Shop_L_id");
        }else {
            DBConnection d = new DBConnection();
            int id = d.getshopID(ManagerPage.search);

            db = new DBConnection("select Shop_id, City, Contact_info, Location from CakeShop, CakeShop_Location where Shop_id = Shop_L_id and Shop_id = "+ id );
        }
        while (db.resultSet.next()) {
            CompModel.addRow((int) db.resultSet.getObject(1), (String) db.resultSet.getObject(2), (String) db.resultSet.getObject(3), (String) db.resultSet.getObject(4));
        }

        db.closeDB();

    }

    public void updateTableMO()throws SQLException{

        tabelSetup(ManagerPage.tOrders, 7);

        DBConnection db = new DBConnection(" select * from `Orders` where Mng_id = " + LoginPage.boss.id );

        while (db.resultSet.next()) {
            CompModel.addRow((Integer) db.resultSet.getObject(1), (String) db.resultSet.getObject(2),
                    db.resultSet.getDate(3), (Integer) db.resultSet.getObject(4), (Integer) db.resultSet.getObject(5),
                    (Integer) db.resultSet.getObject(6),(Integer) db.resultSet.getObject(7),(String)db.resultSet.getObject(8));

        }
        db.closeDB();

    }

    public void updateTableMOI()throws SQLException{

        tabelSetup(ManagerPage.tOIrders, 8);

        DBConnection db = new DBConnection("Select Product.Product_id, Product_Name ,Quantity , Total_price \n" +
                "From Product , Order_Item \n" +
                "Where Product.product_ID = Order_Item.product_ID AND Order_Item.order_id = '" + ManagerPage.orderID+ "'");

        while (db.resultSet.next()) {
            CompModel.addRow((int)db.resultSet.getObject(1),(String) db.resultSet.getObject(2),(int) db.resultSet.getObject(3), (Float) db.resultSet.getObject(4) );
        }
        db.closeDB();

    }

    public void updateTableMP(int i)throws SQLException{

        tabelSetup(ManagerPage.tRating, 9);

        DBConnection db = new DBConnection("Select Product_id, Product_Name, img  from Product ");

        ArrayList<Product> pro = new ArrayList<>();
        while (db.resultSet.next()) {

            byte[] img = (byte[]) db.resultSet.getObject(3);
            ImageIcon image = new ImageIcon(img);
            Image im = image.getImage();
            Image myImg = im.getScaledInstance(180, 280, Image.SCALE_DEFAULT);
            pro.add(new Product((int) db.resultSet.getObject(1),(String)db.resultSet.getObject(2), myImg));
            if( pro.size() == 3) {
                CompModel.addRow(pro);
                pro.clear();
            }
        }

        if( !pro.isEmpty()){
            CompModel.addRow(pro);
            pro.clear();
        }

        ManagerPage.tRating.setRowHeight(150);
        db.closeDB();

    }

    public void updateTableMRC(int i)throws SQLException{

        tabelSetup(ManagerPage.tComment, 10);
        DBConnection db = new DBConnection("Select date_recorded, feedback from Rating where pro_id = " + ManagerPage.productID );

        while (db.resultSet.next()) {
                CompModel.addRow(db.resultSet.getDate(1),(String) db.resultSet.getObject(2) );
        }

        db.closeDB();

    }

    void tabelSetup(JTable t, int n ){

        CompModel = new CompTableModel(n);
//        JTable table = new JTable(CompModel);
        t.setModel(CompModel);
        t.setRowHeight(new CellPanel_MyOrders(n).getPreferredSize().height);
        t.setTableHeader(null);
        CompCellEditorRenderer compCellEditorRenderer = new CompCellEditorRenderer(n);
        t.setDefaultRenderer(Object.class, compCellEditorRenderer);
        t.setDefaultEditor(Object.class, compCellEditorRenderer);

    }

}

class CompCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private static final long serialVersionUID = 1L;
    private CellPanel_MyOrders renderer;
    private CellPanel_MyOrders editor;
    private int type  ;

    public CompCellEditorRenderer( int n ) {
       renderer = new CellPanel_MyOrders(n);
       editor = new CellPanel_MyOrders(n);
       type = n;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        renderer.setComp(value, type);
        return renderer;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editor.setComp(value, type);
        return editor;
    }

    @Override
    public Object getCellEditorValue() {
        return editor.getOrderInfo(type);
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }
}

class CompTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;
    int c = 1 ;


    CompTableModel(int n ){
        if ( n == 9 ){
            c= 3;
        }
    }

    @Override
    public int getColumnCount() {
        return c;
    }

    //for My Orders
    public void addRow(int orderid, String state,Date date) {
        super.addRow(new Object[]{new Order(orderid, state, date)});
    }

    // for products
    public void addRow( int id, String name, String desc,float price, Image img) {
        super.addRow(new Object[]{new Product(id,name,desc,price,img )});

    }

    public void addRow(ArrayList<Product> p ) {
        if(p.size() == 1){
            super.addRow(new Object[]{new Product(p.get(0).pid, p.get(0).Name ,p.get(0).img)});
        }else if (p.size() == 2){
            super.addRow(new Object[]{new Product(p.get(0).pid,p.get(0).Name, p.get(0).img),new Product(p.get(1).pid, p.get(1).Name ,p.get(1).img)});
        }else if (p.size() == 3) {
            super.addRow(new Object[]{new Product(p.get(0).pid,p.get(0).Name ,p.get(0).img),new Product(p.get(1).pid, p.get(1).Name ,p.get(1).img),new Product(p.get(2).pid,p.get(2).Name ,p.get(2).img) });
        }else {

        }
    }


    public void addRow( Date d , String comment ) {
        super.addRow(new Object[]{ new Rating(comment,d )});
    }

    // for order item
    public void addRow(int id,String name,int quantity, float price, Image img) {
        super.addRow(new Object[]{new Order_item(id,name,quantity,price, img)});

    }

    public void addRow(int id,String name,int quantity, float price) {
        super.addRow(new Object[]{new Order_item(id,name,quantity,price)});

    }

    //rate
    public void addRow(int pid , String name,int rate,String comment, Image img) {
        super.addRow(new Object[]{new Rating(pid,name,rate, comment, img)});
    }

    //manager shops
    public void addRow(int  shopId, String contactInfo, String location, String city) {
        super.addRow(new Object[]{new CakeShop_Loc(shopId,contactInfo,location,city)});
    }

    public void addRow(int order_id, String order_states, java.util.Date order_date, int mng_id, int cust_id, int payment_id, int shop_id, String ShopLoc) {
        super.addRow(new Object[]{new Order(order_id,order_states,order_date,mng_id,cust_id,payment_id,shop_id,ShopLoc)});
    }
}


class CellPanel_MyOrders extends JPanel {

    // design the cell
    // component inside the cell panel

    Icon img = new ImageIcon("/Users/maimonah_s/Library/CloudStorage/OneDrive-SharedLibraries-IMAMABDULRAHMANBINFAISALUNIVERSITY/ساره توفيق البسام - cakeShop (1)/src/project/Defualt.png");
    String[] scoreStrings = { "1", "2", "3", "4", "5" };

    private JLabel text1 = new JLabel("");
    private JLabel text2 = new JLabel("");
    private JLabel text3 = new JLabel("");
    private JLabel text4 = new JLabel("");
    private JLabel text5 = new JLabel("");
    private JLabel text6 = new JLabel("");
    private JLabel text7 = new JLabel("");
    private JLabel text8 = new JLabel("");
    private JLabel text9 = new JLabel("");
    private JLabel text10 = new JLabel("");
    private JLabel text11 = new JLabel("");
    private JLabel text12 = new JLabel("");
    private JTextArea desc = new JTextArea("");
    private JLabel textWithIcon = new JLabel(img);
    private JButton rateButton = new JButton("RATE");
    private JButton cancelButton = new JButton("CANCEL");

    private JComboBox rate = new JComboBox(scoreStrings);
    private JTextArea comment = new JTextArea(" write your comment ", 2,20);
    int R=0;




    CellPanel_MyOrders( int type) {


        desc.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text3.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text4.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text5.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text6.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text7.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text8.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text9.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text10.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text11.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text12.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        switch (type){
            case 1 : // My orders cell
                rateButton.setHorizontalTextPosition(SwingConstants.RIGHT);

                setLayout(new GridLayout(1,5));
                setBackground(Color.decode("#BBB2A0"));

                rateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, (Component) e.getSource());
                        int row = table.getEditingRow();
                        Order s = (Order) table.getValueAt(row,0);
                        System.out.println(s.toString());
                        CustumerHome.orderID = s.order_id;


                        CustumerHome.Instance.setVisiblePanel(false,false,false,false,false,false,false,true);
                        try {
                            new ComponentTable().updateTableR();
                            CustumerHome.OrderID_L.setText(" Order ID : "+ String.valueOf(s.order_id));
                            CustumerHome.p_rating.setIcon(new ImageIcon("src/project/Defualt.png"));
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }


                    }
                });

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);


                        JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, (Component) e.getSource());
                        int row = table.getEditingRow();
                        Order s = (Order) table.getValueAt(row,0);
                        CustumerHome.orderID = Integer.parseInt(String.valueOf(s.order_id));

                        CustumerHome.Instance.setVisiblePanel(false,false,false,false,false,false,true,false);
                        CustumerHome.oLabel.setText(String.valueOf(CustumerHome.orderID));


                        try {
                            new ComponentTable().updateTableOD();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (NumberFormatException exception){
                            JOptionPane.showMessageDialog(null,"There is no order item in this order", "No Items", JOptionPane.WARNING_MESSAGE);
                        }


                    }
                });



                add(text1);
                add(text2);
                add(text3);
//        add(Box.createHorizontalStrut(100));
                add(Box.createVerticalGlue());
                add(rateButton);

                break;

            case 2 : // products

                setLayout(new BorderLayout());
                setBackground(Color.decode("#BBB2A0"));
                JPanel prod = new JPanel();

                desc.setLineWrap(true);
                desc.setWrapStyleWord(true);
                desc.setEditable(false);

                text5.setText("SAR");

                JPanel price  = new JPanel(new GridBagLayout());
                price.add(text3); // price
                price.add(text5);

                add(textWithIcon, BorderLayout.WEST);
                prod.setLayout(new GridLayout(4,1));
                prod.add(text1); // name
                prod.add(desc); // desc
                prod.add(price);
                prod.add(text4); // id


                text1.setFont(new Font("Monospaced", Font.BOLD , 16));
                text4.setVisible(false);
                add(prod);


                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);

                        setBorder(BorderFactory.createLineBorder(Color.decode("#253134"),3));
                        CustumerHome.sp.setValue(1);// reset the spinner value
                        JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, (Component) e.getSource());
                        int row = table.getEditingRow(); // get number of the row selected
                        Product s = (Product) table.getValueAt(row,0); // get comp object in the row
                        CustumerHome.SelectedProID = s.pid; // save product id

                    }
                });


                break;

            case 3: // basket
                
                setLayout(new BorderLayout());
                text7.setText("X");
                text8.setText("SAR");

                prod = new JPanel();
                prod.setLayout(new GridLayout(2,2));
                prod.add(text1); // name

                JPanel quantity = new JPanel();
                quantity.setLayout(new GridBagLayout());


                quantity.add(text7);
                quantity.add(text2); //quantity

                prod.add(quantity);

                JPanel p  = new JPanel();
                p.setLayout(new GridBagLayout());

                p.add(text3); // total price
                p.add(text8);

                prod.add(p);


                prod.add(cancelButton);

                add(text5);
                text5.setVisible(false);


                add(textWithIcon, BorderLayout.WEST);
                add(prod);

               cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, (Component) e.getSource());
                        int row = table.getEditingRow();

                        Order_item s  = (Order_item) table.getValueAt(row,0); // get comp object in the row
                        CustumerHome.SelectedProID = s.pid;
                        CustumerHome.cancelSelectedItem();

                        try {
                           new ComponentTable().updateTable();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                
                break;


            case 4:  // Rate

                setLayout(new BorderLayout());

                JPanel rateContainer = new JPanel( new GridLayout(2,1));
                rateContainer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                add(textWithIcon, BorderLayout.LINE_START);
                add(text1,BorderLayout.CENTER);
                add(rateContainer,BorderLayout.LINE_END);

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);


                        setBorder(BorderFactory.createLineBorder(Color.decode("#253134"),3));
                        JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, (Component) e.getSource());
                        int row = table.getEditingRow(); // get number of the row selected
                        CustumerHome.selectedRow = row;
                        Rating s = (Rating) table.getValueAt(row,0); // get comp object in the row
                        ImageIcon pImg = new ImageIcon(s.img);
                        CustumerHome.p_rating.setIcon(pImg);
                        CustumerHome.commTextArea.setText("Write here your Comment");
                        CustumerHome.commTextArea.setEditable(true);


                    }
                });


                break;

            case 5 : //order details
                setLayout(new BorderLayout());


                prod = new JPanel();
                prod.setLayout(new GridLayout(2,2));
                prod.add(text1); // name
                prod.add(text2); //quantity
                prod.add(text4); // total price

                add(textWithIcon, BorderLayout.WEST);
                add(prod);

                break;


            case 6: // Manager cakeShops
                setLayout( new GridLayout(2,4));

                text1.setText("Shop ID : ");
                add(text1);

                add(text2);

                text3.setText("Contact Info : ");
                add(text3);
                add(text4);

                text5.setText("Location : ");
                add(text5);

                add(text6);

                text7.setText("City : ");
                add(text7);

                add(text8);

                break;

            case 7 : // M Orders

                setLayout( new GridLayout( 2, 3));
                text1.setText("Order ID : ");
                text3.setText("state");
                text4.setText("Location :");

                add(text1);
                add(text2);
                add(text3);// set current sate
                add(text4);
                add(text5);// set branch location

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);

                        setBorder(BorderFactory.createLineBorder(Color.decode("#253134"),3));
                        JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, (Component) e.getSource());
                        int row = table.getEditingRow(); // get number of the row selected
                        Order s = (Order) table.getValueAt(row,0); // get comp object in the row
                        ManagerPage.orderID = s.order_id;


                        try {
                            DBConnection db  =new DBConnection();
                            new ComponentTable().updateTableMOI();
                            ManagerPage.TotPrice.setText(String.valueOf(db.getTotalCost(s.payment_id)));
                            ManagerPage.shopID.setText(String.valueOf(s.shop_id));
                            ManagerPage.Loc.setText(s.shopLoc);
                            ManagerPage.status=s.order_states;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }


                    }
                });

                break;

            case 8: //M order details
                setLayout( new BoxLayout(this ,BoxLayout.X_AXIS));

                text2.setText("X");
                text5.setText("SR");

                add(text1);// quantity
                add(text2);//X
                add(desc);//name
                add(text4); // price
                add(text4);// SR

                text4.setAlignmentX(Component.RIGHT_ALIGNMENT);
                text4.setAlignmentX(Component.RIGHT_ALIGNMENT);

                break;

            case 9:
                setLayout( new GridLayout( 1, 1));
                add(textWithIcon);

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);

                        JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, (Component) e.getSource());
                        int row = table.getEditingRow(); // get number of the row selected
                        int col = table.getEditingColumn();
                        Product s = (Product) table.getValueAt(row,col); // get comp object in the row
                        try {
                            ImageIcon img = new ImageIcon(s.img);
                            ManagerPage.PImgLabel.setIcon(img);
                            ManagerPage.productID = s.pid;
                            ManagerPage.cakeL.setText(s.Name);

                            try {
                                new ComponentTable(10).updateTableMRC(10);
                                DBConnection db = new DBConnection();
                                db.setRateAvr(s.pid);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                        }catch (NullPointerException exception){

                        }


                    }
                });

                break;


            case 10:
                setLayout(new GridLayout(2,1));
                add(text1);//date
                add(text2);//comment

                break;

        }

    }



    public void setComp(Object comp, int n) {

        ImageIcon newImg;

        switch (n){

            case 1: // my orders
                Order order = (Order)comp;
                text1.setText(String.valueOf(order.order_id));
                text2.setText(((Order) comp).order_states);
                text3.setText(String.valueOf(order.order_date));

                break;

            case 2: // products
                Product product = (Product) comp;
                text1.setText(product.Name);
                desc.setText(product.Description);
                text3.setText(String.valueOf(product.price) );
                text4.setText(String.valueOf(product.pid));
                newImg = new ImageIcon(product.img);
                textWithIcon.setIcon(newImg);
                textWithIcon.setSize(newImg.getIconWidth(),newImg.getIconHeight());
                break;
                
            case 3://basket & order details
            case 5:

                Order_item order_item = (Order_item) comp;
                order_item.totalP = order_item.price * order_item.Quantity;

                newImg =  new ImageIcon(order_item.img);
                textWithIcon.setIcon(newImg);
                text1.setText(order_item.Name);
                text2.setText(String.valueOf(order_item.Quantity));
                text3.setText(String.valueOf(order_item.totalP));
                text4.setText(String.valueOf(order_item.price));
                text5.setText(String.valueOf(order_item.pid));
                text6.setText(String.valueOf(order_item.id));
                break;

            case 4: // rate
                Rating rating = (Rating)comp;

                newImg =  new ImageIcon(rating.img);

                textWithIcon.setIcon(newImg);
                text1.setText(rating.Name);
                text1.setText(rating.Name);
                text2.setText(String.valueOf(rating.pid));
                comment.setText(rating.feedback);

                break;

            case 6:
                CakeShop_Loc cakeShop = (CakeShop_Loc) comp;
                text2.setText(String.valueOf(cakeShop.id));
                text4.setText(cakeShop.contactInfo);
                text6.setText(cakeShop.Location);
                text8.setText(cakeShop.city);
                break;
            case 7:
                Order MOrder= (Order) comp;
                text2.setText(String.valueOf(MOrder.order_id));
                text3.setText(String.valueOf(MOrder.order_states));
                text5.setText(String.valueOf(MOrder.shopLoc));
                text6.setText(String.valueOf(MOrder.Mng_id));
                text8.setText(String.valueOf(MOrder.cust_id));
                text9.setText(String.valueOf(MOrder.payment_id));
                text10.setText(String.valueOf(MOrder.shop_id));
                text11.setText(String.valueOf(MOrder.order_date));


                break;

            case 8: // M order details
                Order_item order_item1 = (Order_item) comp;
                text1.setText(String.valueOf(order_item1.Quantity));
                desc.setText(String.valueOf(order_item1.Name));
                text4.setText(String.valueOf(order_item1.price));
                text6.setText(String.valueOf(order_item1.pid));

                break;

            case 9: //M product
                try {
                    Product product1 = (Product) comp;
                    newImg = new ImageIcon(product1.img);
                    textWithIcon.setIcon(newImg);
                    text1.setText(String.valueOf(product1.pid));
                    text2.setText(String.valueOf(product1.Name));
                }catch (NullPointerException e){
                    textWithIcon.setIcon(new ImageIcon(""));
                    text1.setText(String.valueOf(0));
                    text2.setText("");
                }

                break;

            case 10:
                Rating rating1 = (Rating) comp;
                text1.setText(String.valueOf(rating1.date_rec));
                text2.setText(rating1.feedback);

                break;

        }

    }


    public Object getOrderInfo( int n ) {

        switch (n){
            case 1:
                return new Order(Integer.parseInt(text1.getText()),text2.getText(), Date.valueOf(text3.getText()));
            case 2:
                return new Product(Integer.parseInt(text4.getText()),text1.getText(), desc.getText() , Float.parseFloat(text3.getText()),(Icon) textWithIcon.getIcon());
            case 3:
            case 5:
                return new Order_item(Integer.parseInt(text5.getText()),text1.getText(),Integer.parseInt(text2.getText()),Float.parseFloat(text3.getText()),Integer.parseInt(text6.getText()),textWithIcon.getIcon());
            case 4:
                return new Rating(Integer.parseInt(text2.getText()),text1.getText(),R,comment.getText(), textWithIcon.getIcon());
            case 6:
                return new CakeShop_Loc(Integer.parseInt(text2.getText()),text8.getText(),text4.getText(), text6.getText());
            case 7 :
                return new Order(Integer.parseInt(text2.getText()),text3.getText(),Date.valueOf(text11.getText()), Integer.parseInt(text6.getText()),Integer.parseInt(text8.getText()),Integer.parseInt(text9.getText())
                        ,Integer.parseInt(text10.getText()),text5.getText());
            case 8 :
                return new Order_item(Integer.parseInt(text6.getText()),desc.getText(),Integer.parseInt( text1.getText()), Float.parseFloat(text4.getText()));
            case 9 :
                return new Product(Integer.parseInt(text1.getText()),text2.getText(),textWithIcon.getIcon());
            case 10:
                return new Rating(text2.getText(),Date.valueOf(text1.getText()));
        }
        return null;

    }


    }



