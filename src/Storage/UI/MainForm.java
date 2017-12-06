package Storage.UI;

import Storage.Model.Order;
import Storage.Model.StorageManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainForm extends JPanel  {
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
            JButton newButton = new JButton();
            newButton.setName(Integer.toString(i));
            newButton.setText("Order!");
            newButton.setBounds(drawingParam, 600, 100, 30);
            newButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String buttonNmae = ((JButton)e.getSource()).getName();
                    manager.processOrder(getNewOrder(buttonNmae));
                }
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
        g.drawRect(280,230,100,30);
        g.setColor(Color.BLACK);
        g.drawString("Storage",300,245);
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
