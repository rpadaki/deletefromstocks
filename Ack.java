import java.util.*;

public class Ack extends Message {
    String id;

    public Ack(StringTokenizer st) {
        id = st.nextToken();
    }
}
