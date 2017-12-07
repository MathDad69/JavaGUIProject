package Storage.UI;

import Storage.Data.MockData;
import Storage.Events.ProjectEventListener;
import Storage.Events.StorageReceivedProductsEventListener;
import Storage.Model.Manufacturer;
import Storage.Model.Order;
import Storage.Model.StorageManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class MainForm extends JPanel implements StorageReceivedProductsEventListener {
    private ArrayList<JTextField> textBoxes = new ArrayList<JTextField>();
    private ArrayList<JTextField> orderTextBoxes = new ArrayList<JTextField>();
    private ArrayList<JTextArea> productsTextBoxes = new ArrayList<JTextArea>();

    private String newline = System.getProperty("line.separator");

    public void drawItems(Graphics g, int startYPoint) {
        int drawingParam = 80;
        g.setColor(Color.RED);
        for (int i = 0; i < 4; i++) {
            g.drawRect(drawingParam,startYPoint,100,30);
            drawingParam += 150;
        }
    }

    public void createCustomersControls() {
        int drawingParam = 80;
        for (int i = 0; i < 4; i++) {
            JPanel panel= this;
            JButton newButton = new JButton();
            newButton.setName(Integer.toString(i));
            newButton.setText("Order!");
            newButton.setBounds(drawingParam, 700, 100, 30);
            newButton.addActionListener(e -> {
                String buttonName = ((JButton)e.getSource()).getName();
                Order newOrder = getNewOrder(buttonName);
                Animation animation =  new Animation(
                        panel,
                        ((JButton)e.getSource()).getLocation().x + ((JButton)e.getSource()).getWidth()/2,
                        ((JButton)e.getSource()).getLocation().y,
                        330,
                        270,
                        newOrder.getProductName() + " : "  + newOrder.getAmount(),
                        StorageManager.getManager(),
                        true,
                        getNewOrder(buttonName));
            });
            this.add(newButton);

            JTextField newTextBox = new JTextField();
            newTextBox.setName(Integer.toString(i));
            newTextBox.setBounds(drawingParam, 590, 100,30);
            textBoxes.add(newTextBox);
            this.add(newTextBox);

            JTextField newOrderTextBox = new JTextField();
            newOrderTextBox.setName(Integer.toString(i));
            newOrderTextBox.setBounds(drawingParam, 650, 100,30);
            orderTextBoxes.add(newOrderTextBox);
            this.add(newOrderTextBox);

            drawingParam += 150;
        }
    }

    public void drawManufacturersValuesTextAreas() {
        int drawingParam = 80;

        for (int i = 0; i < productsTextBoxes.size(); i++) {
            this.remove(productsTextBoxes.get(i));
        }
        productsTextBoxes.clear();

        for (int i = 0; i < 4; i++) {
            JTextArea newTextArea = new JTextArea();
            newTextArea.setName(Integer.toString(i));
            newTextArea.setEditable(false);
            newTextArea.setBounds(drawingParam, 20, 100,50);
            newTextArea.setText(getManufacturersValue(i));

            productsTextBoxes.add(newTextArea);
            this.add(newTextArea);

            drawingParam += 150;
        }
    }

    public void drawStorage(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(260,250,200,50);
        g.setColor(Color.BLACK);
        g.drawString("Storage",340,280);
    }

    public  void drawLabels(Graphics g) {
        int drawingParam = 80;
        g.setColor(Color.BLACK);

        for (int i = 0; i < 4; i++) {
            g.drawString("Product Name : ",drawingParam,580);
            g.drawString("Amount : ",drawingParam,640);
            drawingParam += 150;
        }
    }

    public void drawManufacturersNames(Graphics g) {
        int drawingParam = 120;
        g.setColor(Color.BLACK);

        for (Manufacturer entry : StorageManager.getManager().getManufacturers()) {
            g.drawString(entry.getManufacturerName(),drawingParam, 120);
            drawingParam += 150;
        }
    }

    public void drawCustomersNames(Graphics g) {
        ArrayList<String> customers = MockData.getCustomersNames();
        int drawingParam = 120;
        g.setColor(Color.BLACK);

        for (String entry : customers) {
            g.drawString(entry, drawingParam, 520);
            drawingParam += 150;
        }
    }

    public String getManufacturersValue(int id) {
        String result = "";
        for ( Map.Entry<String, Integer> entry : StorageManager.getManager().getManufacturers().get(id).getProducts().entrySet() ) {
            result = result + entry.getKey() + " : " + entry.getValue() + "\n";
        }
        return result;
    }

    private Order getNewOrder(String buttonNumber) {
        JTextField textBox = textBoxes.get(Integer.valueOf(buttonNumber));
        JTextField orderTextBox = orderTextBoxes.get(Integer.valueOf(buttonNumber));
        return new Order(textBox.getText(), Integer.valueOf(orderTextBox.getText()));
    }

    public MainForm(){
        StorageManager.getManager().setManufacturers(MockData.getManufacturers());
        StorageManager.getManager().addListener(this);
        this.setLayout(null);
        createCustomersControls();
        drawManufacturersValuesTextAreas();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawStorage(g);
        drawItems(g, 500);
        drawItems(g, 100);
        drawLabels(g);
        drawManufacturersNames(g);
        drawCustomersNames(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Oval Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainForm());
        frame.setSize(700, 800);
        frame.setVisible(true);
    }

    @Override
    public void actionDone() {
        drawManufacturersValuesTextAreas();
    }
}
