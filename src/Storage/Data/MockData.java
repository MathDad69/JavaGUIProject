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
        manufacturers.get(2).getProducts().put("Kok3", 32);

        manufacturers.get(3).getProducts().put("Kok3", 42);
        manufacturers.get(3).getProducts().put("Kok2", 22);
        manufacturers.get(3).getProducts().put("Kok4", 12);

        return manufacturers;
    }
}
