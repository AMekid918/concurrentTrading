/**
 * @author Abderraouf Mekid
 * @version 1.0
 */

public class Company {
    
    private String name;
    private float totalNumberOfShares;
    private float availableNumberOfShares;
    private float price;

    public Company(String name){
        this.name = name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getName(){
        return name;
    }

    
    /** 
     * @param totalNumberOfShares
     */
    public void setTotalShares(float totalNumberOfShares){
        this.totalNumberOfShares = totalNumberOfShares;
    }

    
    /** 
     * @return float
     */
    public float getTotalShares(){
        return totalNumberOfShares;
    }

    
    /** 
     * @param availableNumberOfShares
     */
    public void setAvailableShares(float availableNumberOfShares){
        this.availableNumberOfShares = availableNumberOfShares;
    }

    
    /** 
     * @return float
     */
    public float getAvailableShares(){
        return availableNumberOfShares;
    }

    
    /** 
     * @return float
     */
    public float getPrice(){
        return price;
    }

    
    /** 
     * @param price
     */
    public void setPrice(float price){
        this.price = price;
    }







}