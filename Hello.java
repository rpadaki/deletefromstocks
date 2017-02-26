import java.util.*;

public class Hello extends Message {
    ArrayList<String> symbols = new ArrayList<>();
    ArrayList<Integer> positions = new ArrayList<>();

    public Hello(StringTokenizer st) {
        while(st.hasMoreTokens()) {
            String token = st.nextToken();
            String[] vals = token.split(":");
            symbols.add(vals[0]);
            positions.add(Integer.parseInt(vals[1]));
        }
    }
}
