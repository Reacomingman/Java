package game;

public class RNG
{
    private int minimumValue;
    private int maximumValue;

    public RNG()
    {
        minimumValue = 0;
        maximumValue = 0;
    }

    public RNG(int minimumValue, int maximumValue)
    {
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
    }

    public int getMinimumValue()
    {
        return minimumValue;
    }

    public int getMaximumValue()
    {
        return maximumValue;
    }

    public void setMinimumValue(int temp)
    {
        minimumValue = temp;
    }

    public void setMaximumValue(int temp)
    {
        maximumValue = temp;;
    }

    public int randomNumber()   // generate a random int date between th minimumValue and the maximumValue;
    {
        return minimumValue + (int)(Math.random() * (maximumValue - minimumValue + 1));
    }
}