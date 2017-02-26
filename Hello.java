import java.util.*;

public class Hello extends Message {
    ArrayList<String> positions;

    public Hello(StringTokenizer st) {
        while(st.hasMoreTokens()) {
            positions.add(st.nextToken());
        }
    }
}
