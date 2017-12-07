package Storage.UI;

import Storage.Data.MockData;
import Storage.Model.Order;
import Storage.Model.StorageManager;
import Storage.Events.StorageReceivedProductsEventListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;


public class MainForm extends JPanel implements StorageReceivedProductsEventListener {
    private ArrayList<JTextField> textBoxes = new ArrayList<JTextField>();
    private ArrayList<JTextField> orderTextBoxes = new ArrayList<JTextField>();
    private ArrayList<JTextArea> productsTextBoxes = new ArrayList<JTextArea>();

    public MainForm(){
        StorageManager.getManager().setManufacturers(MockData.getManufacturers());
        this.setLayout(null);
        StorageManager.getManager().addListener(this);
        createCustomersControls();
        drawManufacturersValuesTextAreas();
    }

    public void createCustomersControls() {
        int drawingParam = Constants.CUSTOMERS_CONTROLS_DRAWING_PARAM;
        for (int i = 0; i < Constants.ITEMS_COUNT; i++) {
            this.add(createNewButton(this, drawingParam, i));
            this.add(createNewTextField(drawingParam, i));
            this.add(creatNewOrderField(drawingParam, i));

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
        DrawingHelper.drawItems(g, Constants.RECTANGLE_CUSTOMERS_STARTING_Y);
        DrawingHelper.drawItems(g, Constants.RECTANGLE_MANUFERTURERS_STARTING_Y);
        DrawingHelper.drawLabels(g);
        DrawingHelper.drawManufacturersNames(g);
        DrawingHelper.drawCustomersNames(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Oval Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainForm());
        frame.setSize(Constants.FORM_WIDTH, Constants.FROM_HEIGTH);
        frame.setVisible(true);
    }

    private Order getNewOrder(String buttonNumber) {
        JTextField textBox = textBoxes.get(Integer.valueOf(buttonNumber));
        JTextField orderTextBox = orderTextBoxes.get(Integer.valueOf(buttonNumber));
        return new Order(textBox.getText(), Integer.valueOf(orderTextBox.getText()));
    }

    private JButton createNewButton(JPanel panel, int drawingParam, int elementIndex) {
        JButton newButton = new JButton();
        newButton.setName(Integer.toString(elementIndex));
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

        return newButton;
    }

    private JTextField createNewTextField(int drawingParam, int elementIndex) {
        JTextField newTextBox = new JTextField();
        newTextBox.setName(Integer.toString(elementIndex));
        newTextBox.setBounds(drawingParam, Constants.TEXT_FIELD_Y, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_HEIGHT);
        textBoxes.add(newTextBox);

        return newTextBox;
    }

    private JTextField creatNewOrderField(int drawingParam, int elementIndex) {
        JTextField newOrderTextBox = new JTextField();
        newOrderTextBox.setName(Integer.toString(elementIndex));
        newOrderTextBox.setBounds(drawingParam, 650, 100,30);
        orderTextBoxes.add(newOrderTextBox);

        return newOrderTextBox;
    }

    private void drawManufacturersValuesTextAreas() {
        int drawingParam = Constants.CUSTOMERS_CONTROLS_DRAWING_PARAM;

       for (int i = 0; i < productsTextBoxes.size(); i++) {
            this.remove(productsTextBoxes.get(i));
        }
        productsTextBoxes.clear();

       for (int i = 0; i < Constants.ITEMS_COUNT; i++) {
            JTextArea newTextArea = new JTextArea();
            newTextArea.setName(Integer.toString(i));
            newTextArea.setEditable(false);
            newTextArea.setBounds(drawingParam, 20, 100,50);
            newTextArea.setText(getManufacturersValue(i));
            productsTextBoxes.add(newTextArea);
            this.add(newTextArea);

            drawingParam += Constants.DRAWING_PARAM_BUFFER;
        }
    }

    @Override
    public void actionDone() {
        drawManufacturersValuesTextAreas();
    }
}
