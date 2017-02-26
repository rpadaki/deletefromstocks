import java.util.*;

public class Reject extends Message {
    String id;

    public Reject(StringTokenizer st) {
        id = st.nextToken();
    }
}
