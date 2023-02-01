package c482.inventoryapp;

/**
 * Subclass of Part for parts made in house
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * retrieves machineId
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * sets machineId
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
