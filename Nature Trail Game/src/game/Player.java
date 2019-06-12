package game;

public class Player
{
    private String name;  // to store the player's name
    private int position; // to store the current position
    private int score; // to count the scores

    public Player()
    {
        name = ""; // set the default name as ""
        position = 0; // set the default position is 0
        score = 0; // set the default score is 0
    }

    public Player(String name, int position, int score)
    {
        this.name = name; 
        this.position = position;
        this.score = score;
    }

    public String getName()
    {
        return name;
    }

    public int getPosition()
    {
        return position;
    }

    public int getScore()
    {
        return score;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void increasePosition(int temp) // add a value to position, and return the new position  
    {
        position += temp;
    }

    public void increaseScore(int temp) // add a value to score, and return the new score 
    {
        score += temp;
    }
}
