package examples.altp;


import sipay.altp.Querys;
import sipay.responses.altp.querys.StatusOperation;

public class OperationsQuerys {
    public static void main(String[] args) {
        String path = "config.properties";
        Querys querys = new Querys(path);

        StatusOperation statusOperation = querys.getStatusOperation("5947b6f3e6129d0001e7dad6");
        if (statusOperation == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (statusOperation.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + statusOperation.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
    }
}