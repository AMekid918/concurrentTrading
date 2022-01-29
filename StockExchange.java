/**
 * @author Abderraouf Mekid
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.HashMap;

public class StockExchange {
    
    private HashMap<Company, Float> companies = new HashMap<>();
    private ArrayList<Client> clients = new ArrayList<Client>();

    public StockExchange(){

    }

    
    /** 
     * Registers company on the stock exchange
     * @param company
     * @param numberOfShares
     * @return Boolean
     */
    public Boolean registerCompany(Company company, Float numberOfShares){
        if (companies.containsKey(company)){
            return false;
        }
        else{
            companies.put(company, numberOfShares);
            company.setTotalShares(numberOfShares);
            company.setAvailableShares(numberOfShares);
            company.setName(company.getName());
            return true;
        }
    }

    
    /** 
     * @param company
     * @return Boolean
     */
    public Boolean deregister(Company company){
        if(!companies.containsKey(company)){
            return false;
        }
        else{
            companies.remove(company);

            return true;
        }
    }

    
    /** 
     * @param client
     * @return Boolean
     */
    public Boolean addClient(Client client){
        if(clients.contains(client)){
            return false;
            
        }
        else{
            clients.add(client);
            return true;
        }
    }

    
    /** 
     * @param client
     * @return Boolean
     */
    public Boolean removeClient(Client client){
        if(clients.contains(client)){
            clients.remove(client);
            System.out.println("Client removed");
            return true;
        }
        else{
            System.out.println("Client doesn't exist");
            return false;
        }
    }

    
    /** 
     * @return ArrayList<Client>
     */
    public ArrayList<Client> getClients(){
        return clients;
    }

    
    /** 
     * @return HashMap<Company, Float>
     */
    public HashMap<Company, Float> getCompanies(){
        return companies;
    }

    
    /** 
     * @param company
     * @param price
     */
    public void setPrice(Company company, Float price){
        company.setPrice(price);
    }

    
    /** 
     * Changes price of the company based on value requested
     * @param company
     * @param diff
     */
    public void changePriceBy(Company company, Float diff){
        if((company.getPrice() + diff) < 0){
            company.setPrice(0);
        }
        else{
            company.setPrice(company.getPrice() + diff);
        }
    }
}