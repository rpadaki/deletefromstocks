import java.util.*;

public class Hello extends Message {
    ArrayList<String> symbols;
    ArrayList<Integer> positions;

    public Hello(StringTokenizer st) {
        while(st.hasMoreTokens()) {
            String token = st.nextToken();
            String[] vals = token.split(":");
            symbols.add(vals[0]);
            positions.add(Integer.parseInt(vals[1]));
        }
    }
}
