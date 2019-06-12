package game;

public class Animal
{
    private String type;   // this is the name of animal
    private int reward;    // this is the value the animal will give reward

    public Animal()
    {
        type = "";
        reward = 0;
    }

    public Animal(String type, int reward)
    {
        this.type = type;
        this.reward = reward;
    }

    public String getType()
    {
        return type;
    }

    public int getReward()
    {
        return reward;
    }

    public void setType(String temp)
    {
        type = temp;
    }

    public void setReward(int temp)
    {
        reward = temp;
    }
}
