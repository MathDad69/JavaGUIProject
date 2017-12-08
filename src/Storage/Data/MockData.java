package Storage.Data;

import Storage.Model.Manufacturer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public final class MockData {
    public static ArrayList<Manufacturer> getManufacturers(){
        Gson gson = new Gson();
        ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
        try {
            String filePath = new File("").getAbsolutePath();
            filePath +=("\\JavaGUIProject\\src\\Storage\\Data\\Orders.json");
            manufacturers = gson.fromJson(new FileReader(filePath), new TypeToken<ArrayList<Manufacturer>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }

    public static ArrayList<String> getCustomersNames() {
        ArrayList<String> customers = new ArrayList<String>();

        customers.add("Olya");
        customers.add("Diana");
        customers.add("Robert");

        return customers;
    }
}
