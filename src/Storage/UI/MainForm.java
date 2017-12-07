package Storage.UI;

import Storage.Data.MockData;
import Storage.Model.Manufacturer;
import Storage.Model.Message;
import Storage.Model.OrderDetails;
import Storage.Model.StorageManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import static java.awt.Color.*;


public class MainForm extends JPanel {
    private ArrayList<JTextField> textBoxes = new ArrayList<JTextField>();
    private ArrayList<JTextField> orderTextBoxes = new ArrayList<JTextField>();
    private ArrayList<JTextArea> productsTextBoxes = new ArrayList<JTextArea>();

    public MainForm(){
        setBackground(Constants.BACKGROUND_Color);
        StorageManager.getManager().setManufacturers(MockData.getManufacturers());
        StorageManager.getManager().setMainForm(this);
        this.setLayout(null);
        createCustomersControls();
        drawManufacturersValuesTextAreas();
    }

    public void createCustomersControls() {
        int drawingParam = Constants.ITEM_DRAWING_PARAM;
        for (int i = 0; i < Constants.ITEMS_COUNT; i++) {
            this.add(createNewButton(this, drawingParam, i));
            this.add(createNewOrderNameField(drawingParam, i));
            this.add(createNewOrderAmountField(drawingParam, i));

            drawingParam += Constants.DRAWING_PARAM_BUFFER;
        }
    }

    public String getManufacturersValue(int id) {
        String result = "";
        for ( Map.Entry<String, Integer> entry : StorageManager.getManager().getManufacturers().get(id).getProducts().entrySet() ) {
            result = result + entry.getKey() + " : " + entry.getValue() + "\n";
        }
        return result;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        DrawingHelper.drawStorage(g);
        DrawingHelper.drawItems(g, Constants.RECTANGLE_CUSTOMERS_Y);
        DrawingHelper.drawItems(g, Constants.RECTANGLE_MANUFACTURERS_Y);
        DrawingHelper.drawLabels(g);
        DrawingHelper.drawManufacturersNames(g);
        DrawingHelper.drawCustomersNames(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Storage");
        frame.setBackground(Constants.BACKGROUND_Color);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainForm());
        frame.setSize(Constants.FORM_WIDTH, Constants.FROM_HEIGHT);
        frame.setVisible(true);
    }

    private OrderDetails getNewOrder(String buttonNumber) {
        JTextField textBox = textBoxes.get(Integer.valueOf(buttonNumber));
        JTextField orderTextBox = orderTextBoxes.get(Integer.valueOf(buttonNumber));
        return new OrderDetails(textBox.getText(), Integer.valueOf(orderTextBox.getText()));
    }

    private JButton createNewButton(JPanel panel, int drawingParam, int elementIndex) {
        JButton newButton = new JButton();
        newButton.setName(Integer.toString(elementIndex));
        newButton.setText("OrderDetails!");
        newButton.setBounds(drawingParam, Constants.BUTTON_Y, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        newButton.addActionListener(e -> {
            String buttonName = ((JButton)e.getSource()).getName();
            OrderDetails newOrder = getNewOrder(buttonName);
            Animation animation =  new Animation(
                panel,
                ((JButton)e.getSource()).getLocation().x + ((JButton)e.getSource()).getWidth()/2,
                Constants.RECTANGLE_CUSTOMERS_Y  +  Constants.RECTANGLE_HEIGHT/2,
                Constants.STORAGE_X + Constants.STORAGE_WIDTH/2,
                Constants.STORAGE_Y + Constants.STORAGE_HEIGHT/2,
                newOrder.getProductName() + " : "  + newOrder.getAmount(),
                StorageManager.getManager(),
                true,
                getNewOrder(buttonName),
                BLACK
            );
        });

        return newButton;
    }

    private JTextField createNewOrderNameField(int drawingParam, int elementIndex) {
        JTextField newTextBox = new JTextField();
        newTextBox.setName(Integer.toString(elementIndex));
        newTextBox.setBounds(drawingParam,
                Constants.ORDER_NAME_FIELD_Y,
                Constants.TEXT_FIELD_WIDTH,
                Constants.TEXT_FIELD_HEIGHT);
        textBoxes.add(newTextBox);

        return newTextBox;
    }
    private JTextField createNewOrderAmountField(int drawingParam, int elementIndex) {
        JTextField newOrderTextBox = new JTextField();
        newOrderTextBox.setName(Integer.toString(elementIndex));
        newOrderTextBox.setBounds(drawingParam,
                Constants.ORDER_AMOUNT_FIELD_Y,
                Constants.TEXT_FIELD_WIDTH,
                Constants.TEXT_FIELD_HEIGHT);
        orderTextBoxes.add(newOrderTextBox);

        return newOrderTextBox;
    }

    public void drawManufacturersValuesTextAreas() {
        int drawingParam = Constants.ITEM_DRAWING_PARAM;

       for (int i = 0; i < productsTextBoxes.size(); i++) {
            this.remove(productsTextBoxes.get(i));
        }
        productsTextBoxes.clear();

       for (int i = 0; i < StorageManager.getManager().getManufacturers().size(); i++) {
            JTextArea newTextArea = new JTextArea();
            newTextArea.setName(StorageManager.getManager().getManufacturers().get(i).getManufacturerName());
            newTextArea.setEditable(false);
            newTextArea.setBackground(Constants.BACKGROUND_Color);
            newTextArea.setBounds(drawingParam,
                    Constants.MANUFACTURER_PRODUCT_Y,
                    Constants.MANUFACTURER_PRODUCT_WIDTH ,
                    Constants.MANUFACTURER_PRODUCT_HEIGHT );
            newTextArea.setText(getManufacturersValue(i));
            productsTextBoxes.add(newTextArea);
            this.add(newTextArea);

            drawingParam += Constants.DRAWING_PARAM_BUFFER;
        }
        this.revalidate();
        this.repaint();
    }

    public void handleDeliveringDoneOnUI(Message<Manufacturer, StorageManager> data) {
        drawManufacturersValuesTextAreas();
        JTextArea textArea = findTextArea(data.getSender().getManufacturerName());
        Animation animation = new Animation(
            this,
            textArea.getLocation().x + textArea.getWidth()/2,
            Constants.RECTANGLE_MANUFACTURERS_Y + Constants.RECTANGLE_HEIGHT/2,
            Constants.STORAGE_X + Constants.STORAGE_WIDTH/2,
            Constants.STORAGE_Y + Constants.STORAGE_HEIGHT/2,
            "[DELIVER] " + data.getOrder().getProductName() + " : "  + data.getOrder().getAmount(),
            StorageManager.getManager(),
            false,
            null,
            BLUE
        );
    }

    public JTextArea findTextArea(String name) {
        for(JTextArea textArea : productsTextBoxes) {
            if(textArea.getName().equals(name)) {
                return textArea;
            }
        }
        return null;
    }

    public void handleSendRequest(String manufacturerName, String productName) {
        JTextArea textArea = findTextArea(manufacturerName);
        Animation animation =  new Animation(
            this,
            Constants.STORAGE_X + Constants.STORAGE_WIDTH/2,
            Constants.STORAGE_Y + Constants.STORAGE_HEIGHT/2,
            textArea.getLocation().x + textArea.getWidth()/2,
            Constants.RECTANGLE_MANUFACTURERS_Y + Constants.RECTANGLE_HEIGHT/2,
            "[REQUEST] - " + productName,
            StorageManager.getManager(),
            false,
            null,
            MAGENTA
        );
    }

    public void handleAvailabilityResponseUI(Message<Manufacturer, StorageManager> response) {
        JTextArea textArea = findTextArea(response.getSender().getManufacturerName());

        Animation animation =  new Animation(
            this,
            textArea.getLocation().x + textArea.getWidth()/2,
            Constants.RECTANGLE_MANUFACTURERS_Y + Constants.RECTANGLE_HEIGHT/2,
            Constants.STORAGE_X + Constants.STORAGE_WIDTH/2,
            Constants.STORAGE_Y + Constants.STORAGE_HEIGHT/2,
            "[RESPONSE] - " + response.getOrder().getProductName() + " {" + response.getOrder().getAmount() + "} ",
            StorageManager.getManager(),
            false,
            null,
            response.getOrder().getAmount() == 0 ? RED : GREEN
        );
    }

    public void handleGiveMeProductUI(String manufacturerName, String product, int amount) {
        JTextArea textArea = findTextArea(manufacturerName);

        Animation animation =  new Animation(
            this,
            Constants.STORAGE_X + Constants.STORAGE_WIDTH/2,
            Constants.STORAGE_Y + Constants.STORAGE_HEIGHT/2,
            textArea.getLocation().x + textArea.getWidth()/2,
            Constants.RECTANGLE_MANUFACTURERS_Y + Constants.RECTANGLE_HEIGHT/2,
            "[GIVE ME] - " + product + " {" + amount + "} ",
            StorageManager.getManager(),
            false,
            null,
            ORANGE
        );
    }
}
