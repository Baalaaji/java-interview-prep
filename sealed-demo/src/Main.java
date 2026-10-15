sealed interface PaymentResult permits Success,Failed,Pending,Reversed {}

record Success(String txnId) implements PaymentResult
{
}

record Failed(String reason) implements PaymentResult{}

record Pending(String referenceId) implements PaymentResult {}
record Reversed(String reason) implements PaymentResult{}

public class Main{
    public static void main(String[] args) {
        System.out.println("Program Started");
        PaymentResult result= new Failed("InSufficent balance");
        Reversed s2=new Reversed("Payment failed");

        System.out.println(result);

        String message = switch(result)
        {
            case Success s -> "Payment done " + s.txnId();
            case Failed f-> "Payment Failed " + f.reason();
            case Pending p-> "Payment Pending " +p.referenceId();
            case Reversed r-> "Payment Reversed " +r.reason();
        };

        System.out.println(message);
    }



}