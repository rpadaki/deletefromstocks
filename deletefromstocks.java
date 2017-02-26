import java.lang.*;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;

public class deletefromstocks {
    public static void main(String[] args) {
        try {
            String hostname = "test-exch-deletefromstocks";
            if(args.length == 3){
                hostname = args[2];
            }
            System.out.println("HOST: " + hostname);
            Interface.init(hostname);
            Interface.run();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
