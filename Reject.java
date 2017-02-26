import java.util.*;

public class Reject extends Message {
    String id, msg;

    public Reject(StringTokenizer st) {
        id = st.nextToken();
        msg = st.nextToken();
        while(st.hasMoreTokens()) {
            msg += " " + st.nextToken();
        }
    }
}
