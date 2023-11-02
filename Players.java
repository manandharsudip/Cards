import java.util.List;

public class Players {

  private String name;
  private List<String> hand;
  private String status;
  private String state = "Lost";
  private String biggestCard;
  private String middleCard;
  private String smallestCard;

  public Players() {}

  public Players(
    String name,
    List<String> hand,
    String status,
    String state,
    String biggestCard,
    String middleCard,
    String smallestCard
  ) {
    this.name = name;
    this.hand = hand;
    this.status = status;
    this.state = state;
    this.biggestCard = biggestCard;
    this.middleCard = middleCard;
    this.smallestCard = smallestCard;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getHand() {
    return hand;
  }

  public void setHand(List<String> hand) {
    this.hand = hand;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getBiggestCard() {
    return biggestCard;
  }

  public void setBiggestCard(String biggestCard) {
    this.biggestCard = biggestCard;
  }

  public String getMiddleCard() {
    return middleCard;
  }

  public void setMiddleCard(String middleCard) {
    this.middleCard = middleCard;
  }

  public String getSmallestCard() {
    return smallestCard;
  }

  public void setSmallestCard(String smallestCard) {
    this.smallestCard = smallestCard;
  }
}
