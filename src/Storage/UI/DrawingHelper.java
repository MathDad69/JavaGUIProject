package Storage.UI;

import Storage.Data.MockData;
import Storage.Model.Manufacturer;
import Storage.Model.StorageManager;

import java.awt.*;
import java.util.ArrayList;

public final class DrawingHelper {
    public static void drawItems(Graphics g, int startYPoint) {
        int drawingParam = Constants.CUSTOMERS_CONTROLS_DRAWING_PARAM;
        g.setColor(Color.RED);

        for (int i = 0; i < Constants.ITEMS_COUNT; i++) {
            g.drawRect(drawingParam, startYPoint, Constants.RECTANGLE_WIDTH, Constants.RECTANGLE_HEIGHT);
            drawingParam += Constants.DRAWING_PARAM_BUFFER;
        }
    }

    public static void drawStorage(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(Constants.STORAGE_X, Constants.STORAGE_Y, Constants.STORAGE_WIDTH, Constants.STORAGE_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawString(Constants.STORAGE_NAME, Constants.STORAGE_NAME_X, Constants.STORAGE_NAME_Y);
    }

    public static void drawLabels(Graphics g) {
        int drawingParam = Constants.CUSTOMERS_CONTROLS_DRAWING_PARAM;
        g.setColor(Color.BLACK);

        for (int i = 0; i < Constants.ITEMS_COUNT; i++) {
            g.drawString(Constants.PRODUCT_LABEL_TEXT, drawingParam, Constants.PRODUCT_LABEL_Y);
            g.drawString(Constants.AMOUNT_LABEL_TEXT, drawingParam, Constants.AMOUNT_LABEL_Y);
            drawingParam += Constants.DRAWING_PARAM_BUFFER;
        }
    }

    public static void drawManufacturersNames(Graphics g) {
        int drawingParam = Constants.DRAWING_NAMES_PARAM;
        g.setColor(Color.BLACK);

        for (Manufacturer entry : StorageManager.getManager().getManufacturers()) {
            g.drawString(entry.getManufacturerName(),drawingParam, Constants.MANUFACTURER_NAMES_Y);
            drawingParam += Constants.DRAWING_PARAM_BUFFER;
        }
    }

    public static void drawCustomersNames(Graphics g) {
        ArrayList<String> customers = MockData.getCustomersNames();
        int drawingParam = Constants.DRAWING_NAMES_PARAM;
        g.setColor(Color.BLACK);

        for (String entry : customers) {
            g.drawString(entry, drawingParam, Constants.CUSTOME_NAMES_Y);
            drawingParam += Constants.DRAWING_PARAM_BUFFER;
        }
    }
}
