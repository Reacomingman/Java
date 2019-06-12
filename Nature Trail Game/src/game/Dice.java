package game;

public class Dice
{
    private int maximumValue;  
    private int minimumValue;

    public Dice()
    {
        minimumValue = 1;
        maximumValue = 4;
    }

    public int getMaximumValue()
    {
        return maximumValue;
    }

    public int getMinimumValue()
    {
        return minimumValue;
    }

    public void setMaximumValue(int maximumValue)
    {
        this.maximumValue = maximumValue;
    }

    public void setMinimumValue(int minimumValue)
    {
        this.minimumValue = minimumValue;
    }
}
