import java.util.*;

public class Reject extends Message {
  int id;

  public Reject(StringTokenizer st) {
    id = Integer.parseInt(st.nextToken());
  }
}
