package Storage.UI;

import Storage.Model.Order;
import Storage.Model.StorageManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainForm extends JPanel {
    private StorageManager manager = new StorageManager();
    private ArrayList<JTextField> textBoxes = new ArrayList<JTextField>();

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
            newButton.setBounds(drawingParam, 600, 100, 30);
            newButton.addActionListener(e -> {
                String buttonNmae = ((JButton)e.getSource()).getName();
                //manager.processOrder(getNewOrder(buttonNmae));
                    Animation animation =  new Animation(
                            panel,
                            ((JButton)e.getSource()).getLocation().x + ((JButton)e.getSource()).getWidth()/2,
                            ((JButton)e.getSource()).getLocation().y,
                            330,
                            270,
                            textBoxes.get(Integer.valueOf(buttonNmae)).getText()+ "kokc");
            });
            this.add(newButton);
            JTextField newTextBox = new JTextField();
            newTextBox.setName(Integer.toString(i));
            newTextBox.setBounds(drawingParam, 550, 100,30);
            textBoxes.add(newTextBox);
            this.add(newTextBox);

            drawingParam += 150;
        }
    }

    public void drawStorage(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(260,250,200,50);
        g.setColor(Color.BLACK);
        g.drawString("Storage",340,280);
    }


    private Order getNewOrder(String buttonNumber) {
        JTextField textBox = textBoxes.get(Integer.valueOf(buttonNumber));
        return new Order(textBox.getText(), 5);
    }

    public MainForm(){
        this.setLayout(null);
        createCustomersControls();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawStorage(g);
        drawItems(g, 500);
        drawItems(g, 50);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Oval Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainForm());
        frame.setSize(700, 700);
        frame.setVisible(true);
    }

}
