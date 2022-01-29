/**
 * @author Abderraouf Mekid
 * @version 1.0
 */

import java.util.HashMap;

public class Client implements Runnable {

    private HashMap<Company, Float> shares = new HashMap<>();


    private Float balance = 0f;
    private String name;
    private StockExchange ex = new StockExchange();

    public Client(String name, StockExchange ex) {
        this.name = name;
        this.ex = ex;
    }

    
    
    public void setName(String name) {
        this.name = name;
    }

  
    public String getName() {
        return name;
    }

    
    /** 
     * @return HashMap<Company, Float>
     */
    public HashMap<Company, Float> getStocks() {
        return shares;
    }

    
    /** 
     * Sets shares for the client initially 
     * @param company
     * @param numberOfShares
     */
    public void setStocks(Company company, Float numberOfShares) {
        shares.put(company, numberOfShares);
    }

    
    /** 
     * Buys shares for the client, changes balance accordingly and adds them to their shares hashmap
     * @param company
     * @param numberOfShares
     * @return boolean
     */
    public synchronized boolean buy(Company company, Float numberOfShares) {
        System.out.println(Thread.currentThread().getName() + " wants to buy " + company.getName());
        if (company.getAvailableShares() >= numberOfShares && (balance >= numberOfShares * company.getPrice())
                && ex.getCompanies().containsKey(company)) {

            company.setAvailableShares(company.getAvailableShares() - numberOfShares);
            if(shares.containsKey(company)){
                Float newNumber = shares.get(company) + numberOfShares;
                shares.put(company, newNumber);
            }
            else{
                shares.put(company, numberOfShares);
            }
            
            balance -= numberOfShares * company.getPrice();
            System.out.println(
                    Thread.currentThread().getName() + " bought " + numberOfShares + " of " + company.getName());
            return true;
        } else if (!(company.getAvailableShares() >= numberOfShares)) {
            System.out.println("Not enough " + company.getName() + " shares available");
            return false;

        } else if (!ex.getCompanies().containsKey(company)) {
            System.out.println("Company is not listed on the Stock Exchange");
            return false;

        } else {
            System.out.println(Thread.currentThread().getName() + " is broke lol");
            return false;
        }
    }

    
    /** 
     * Sells shares for the client, changes balance accordingly and removes them from their shares hashmap
     * @param company
     * @param numberOfShares
     * @return boolean
     */
    public synchronized boolean sell(Company company, Float numberOfShares) {
        System.out.println(Thread.currentThread().getName() + " wants to sell " + company.getName());
        System.out.println(company.getAvailableShares());

        if (company.getAvailableShares() >= numberOfShares && ex.getCompanies().containsKey(company)) {
            System.out.println(Thread.currentThread().getName() + " sold " + company.getName());
            if(shares.containsKey(company)){
                Float newNumber = shares.get(company) - numberOfShares;
                shares.put(company, newNumber);
            }
            else{
                System.out.println("Don't own shares");
                return false;
            }
            Float newNumber = shares.get(company) - numberOfShares;
            shares.put(company, newNumber);
            company.setAvailableShares(company.getAvailableShares() + numberOfShares);
            balance += numberOfShares * company.getPrice();
            return true;

        } else
            System.out.println("Couldn't sell company");
        return false;
    }

    
    /** 
     * Executes a Buy low limit order. The order will only be executed once the price of the share reaches the limit requested
     * @param company
     * @param numberOfShares
     * @param limit
     * @return boolean
     * @throws InterruptedException
     */
    public boolean buyLow(Company company, Float numberOfShares, Float limit) throws InterruptedException {
        if(!ex.getCompanies().containsKey(company)){
            System.out.println("Company doesn't exist");
            return false;
        }
        else{
            if (company.getPrice() == limit) {
                buy(company, numberOfShares);
                System.out.println(getName() + " bought limit order " + company.getName() + " at " + company.getPrice());
                return true;
            } else {
                while (company.getPrice() != limit) {
                    if(company.getPrice() == limit){
                        buy(company, numberOfShares);
                        return true;
                    }
                }
            }
        }
        return true;
        //notifyAll();
    }

    
    /** Executes a Sell high limit order. The order will only be executed once the price of the share reaches the limit requested
     * @param company
     * @param numberOfShares
     * @param limit
     * @return boolean
     * @throws InterruptedException
     */
    public boolean sellHigh(Company company, Float numberOfShares, Float limit) throws InterruptedException {
        if(!ex.getCompanies().containsKey(company)){
            System.out.println("Company doesn't exist");
            return false;
        }
        else{
            if (company.getPrice() == limit) {
                sell(company, numberOfShares);
                System.out.println(getName() + " sold " + company.getName() + " at " + company.getPrice());
                return true;
            } else {
                while (company.getPrice() != limit) {
                    if(company.getPrice() == limit){
                        sell(company, numberOfShares);
                        return true;

                    } 
                }
            }
        }
        return true;
    }

    
    /** 
     * @param amount
     * @return boolean
     */
    public boolean deposit(Float amount) {
        if (amount >= 0) {
            balance += amount;
            return true;
        } else
            return false;

    }

    
    /** 
     * @param amount
     * @return boolean
     */
    public boolean withdraw(Float amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        } else
            return false;
    }

    
    /** 
     * @return float
     */
    public float getBalance() {

        return balance;
    }

    @Override
    public void run() {
        
        System.out.println("This is " + Thread.currentThread().getName());

        for (Company key : ex.getCompanies().keySet()) {
            try {
                buyLow(key, 2f, 600f);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println(Thread.currentThread().getName() + " bought " +
            // key.getName());
            System.out.println(key.getName() + " has " + key.getAvailableShares() + " left");
        }

        for (Company key : ex.getCompanies().keySet()) {
            sell(key, 1f);
             System.out.println(Thread.currentThread().getName() + " sold " +
             key.getName());
            System.out.println(key.getName() + " has " + key.getAvailableShares() + " left");
        }


        for (int counter = 0; counter < ex.getClients().size(); counter++) { 
            
            System.out.println(Thread.currentThread().getName() + " has " + ex.getClients().get(counter).getBalance()); 		
        } 

    }
}