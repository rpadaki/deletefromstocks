import java.util.*;

public class Ack extends Message {
  int id;

  public Ack(StringTokenizer st) {
    id = Integer.parseInt(st.nextToken());
  }
}
