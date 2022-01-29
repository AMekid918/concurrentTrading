public class Application {

    private static StockExchange ex = new StockExchange();

    public static void main(String[] args) throws InterruptedException {

        Company apple = new Company("apple");
        Company microsoft = new Company("microsoft");
        Company tesla = new Company("tesla");
        Company ferrari = new Company("ferrari");

        ex.registerCompany(apple, 50f);
        ex.registerCompany(microsoft, 100f);
        ex.registerCompany(tesla, 50f);
        ex.registerCompany(ferrari, 100f);

        Client sam = new Client("sam", ex);
        Client john = new Client("john", ex);
        Client jenna = new Client("jenna", ex);

        ex.addClient(sam);
        ex.addClient(jenna);
        ex.addClient(john);

        apple.setPrice(150);
        tesla.setPrice(650);
        microsoft.setPrice(500);
        ferrari.setPrice(300);

        sam.deposit(5000f);
        sam.setStocks(tesla, 5f);
        jenna.deposit(5000f);
        john.deposit(7000f);

        Thread one = new Thread(sam);
        Thread two = new Thread(jenna);
        Thread three = new Thread(john);

        one.start();
        two.start();
        three.start();
        
        tesla.setPrice(600);
        apple.setPrice(600);
        microsoft.setPrice(600);
        ferrari.setPrice(600);
        one.join();
        two.join();
        three.join();
        

    }
    
}