import java.lang.*;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;

public class deletefromstocks {
    public static void main(String[] args) {
        try {
            String hostname = "test-exch-deletefromstocks";
            if(args.length >= 1){
                hostname = args[0];
            }
            int port = 20000;
            if(args.length >= 2){
                port = Integer.parseInt(args[1]);
            }
            System.out.println(args.length);
            System.out.println("HOST: " + hostname);
            Interface.init(hostname, port);
            Interface.run();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
