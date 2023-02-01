package c482.inventoryapp;

/**
 * Subclass of Part for outsourced parts
 */
public class OutSourced extends Part {

    private String companyName;

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * retrieves companyName
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * sets companyName
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
