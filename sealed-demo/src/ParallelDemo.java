import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ParallelDemo {

    static String check(String name) {
        System.out.println(name +" running on"+ Thread.currentThread().getName());
        sleep(2000);                          // pretend: slow external call
        return name + " OK";
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

       /* String fraud = check("Fraud");
        String kyc   = check("KYC");
        String limit = check("Limit");*/
        ExecutorService pool= Executors.newFixedThreadPool(3);
       CompletableFuture<String> f1= CompletableFuture.supplyAsync(()->check("Fraud"),pool);
       CompletableFuture<String> f2=CompletableFuture.supplyAsync(()->check("KYC"),pool);
       CompletableFuture<String>  f3=CompletableFuture.supplyAsync(()->check("LIMT"),pool);

       CompletableFuture.allOf(f1,f2,f3).join();

       String fraud=f1.join();
       String kyc=f2.join();
       String limit=f3.join();

        long time = System.currentTimeMillis() - start;
        System.out.println(fraud + ", " + kyc + ", " + limit);
        System.out.println("Time taken: " + time + " ms");
    }
}