/*  ECS 102 Final Project Mancala
topScores.java
Colin Stone
costone
Section 1 */
//class object to hold topScore and leaderBoard values for sorting by methods within Mancala.java. 
public class topScores
{
  private String[] names = new String[6];
  private int[] scores = new int[6];
  
  public String getName(int i)
  {
    return names[i];
  }
  public int getScore(int i)
  {
    return scores[i];
  }
  public void setName(int i, String n)
  {
    names[i] = n;
  }
  public void setScore(int i, int s)
  {
    scores[i] = s;
  }
}
