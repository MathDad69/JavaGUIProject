package Storage.Data;

import Storage.Model.Manufacturer;

import java.util.ArrayList;

public final class MockData {
    public static ArrayList<Manufacturer> getManufacturers() {
        ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();

        manufacturers.add(new Manufacturer("Orest"));
        manufacturers.add(new Manufacturer("Vasyl"));
        manufacturers.add(new Manufacturer("Oksana"));
        manufacturers.add(new Manufacturer("Vova"));

        manufacturers.get(0).getProducts().put("Koks1", 12);
        manufacturers.get(0).getProducts().put("Koks2", 3);

        manufacturers.get(1).getProducts().put("Koks1", 5);

        manufacturers.get(2).getProducts().put("Koks1", 4);
        manufacturers.get(2).getProducts().put("Koks3", 32);

        manufacturers.get(3).getProducts().put("Koks3", 42);
        manufacturers.get(3).getProducts().put("Koks2", 22);
        manufacturers.get(3).getProducts().put("Koks4", 12);

        return manufacturers;
    }

    public static ArrayList<String> getCustomersNames() {
        ArrayList<String> customers = new ArrayList<String>();

        customers.add("Olya");
        customers.add("Diana");
        customers.add("Igora");
        customers.add("Robert");

        return customers;
    }
}
