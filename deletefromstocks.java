import java.lang.*;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;

public class deletefromstocks {
    public static void main(String[] args) {
        try {
            Interface.init();
            Interface.run();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
