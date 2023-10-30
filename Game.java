public class Game {

  private int trail = 0;
  private int doubleRun = 0;
  private int run = 0;
  private int color = 0;
  private int pair = 0;
  private int nothing = 0;
  private Players winner;

  public Game() {}

  public Game(
    int trail,
    int doubleRun,
    int run,
    int color,
    int pair,
    int nothing,
    Players winner
  ) {
    this.trail = trail;
    this.doubleRun = doubleRun;
    this.run = run;
    this.color = color;
    this.pair = pair;
    this.nothing = nothing;
    this.winner = winner;
  }

  public int getTrail() {
    return trail;
  }

  public void setTrail(int trail) {
    this.trail = trail;
  }

  public int getDoubleRun() {
    return doubleRun;
  }

  public void setDoubleRun(int doubleRun) {
    this.doubleRun = doubleRun;
  }

  public int getRun() {
    return run;
  }

  public void setRun(int run) {
    this.run = run;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public int getPair() {
    return pair;
  }

  public void setPair(int pair) {
    this.pair = pair;
  }

  public int getNothing() {
    return nothing;
  }

  public void setNothing(int nothing) {
    this.nothing = nothing;
  }

  public Players getWinner() {
    return winner;
  }

  public void setWinner(Players winner) {
    this.winner = winner;
  }
}
