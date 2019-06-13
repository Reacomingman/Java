package game;

public class Venue
{
    private String venueName;
    private int noOfLaps;
    private int averageLapTime;
    private double chanceOfRain;
    
    public Venue()
    {
        venueName = "";
        noOfLaps = 0;
        averageLapTime = 0;
        chanceOfRain = 0;
    }
    
    public Venue(String name, int laps, int time, double chance)
    {
        venueName = name;
        noOfLaps = laps;
        averageLapTime = time;
        chanceOfRain = chance;
    }
    
    public String getVenueName()
    {
        return venueName;
    }
    
     public int getNoOfLaps()
    {
        return noOfLaps;
    }
    
     public int getAverageLapTime()
    {
        return averageLapTime;
    }
    
     public double getChanceOfRain()
    {
        return chanceOfRain;
    }
    
    public void setVenueName(String temp)
    {
        venueName = temp;
    }
    
     public void setNoOfLaps(int temp)
    {
        noOfLaps = temp;
    }
    
     public void setAverageLapTime(int temp)
    {
        averageLapTime = temp;
    }
    
     public void setChanceOfRain(double temp)
    {
        chanceOfRain = temp;
    }   
       
    public String toString()   // to get a string which consists of venue name, number of laps, average lap time and chance of rain
    {
        return(this.venueName + "," + this.noOfLaps + "," +this.averageLapTime + "," + this.chanceOfRain);
    }
}
