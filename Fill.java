import java.util.*;

public class Fill extends Message {

    String id, symbol, type;
    Position pos;

    public Fill(StringTokenizer st) {
        id = st.nextToken();
        symbol = st.nextToken();
        type = st.nextToken();
        int price = Integer.parseInt(st.nextToken());
        int size = Integer.parseInt(st.nextToken());
        pos = new Position(price, size);
    }
}

