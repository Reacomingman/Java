package game;

public class Driver
{
    private String name;
    private int ranking;
    private String specialSkill;
    private boolean eligibleToRace;
    private int accumulatedScore;
    private int accumulatedTime;
    
    public Driver()
    {
        name = "";
        ranking = 0;
        specialSkill = "";
        eligibleToRace = true;
        accumulatedScore = 0;
        accumulatedTime = 0;
    }
    
     public Driver(String name,int ranking,String specialSkill,boolean eligibleToRace,int accumulatedScore,int accumulatedTime)
    {
        this.name = name;
        this.ranking =  ranking;
        this.specialSkill =  specialSkill;
        this.eligibleToRace = eligibleToRace;
        this.accumulatedScore = accumulatedScore;
        this.accumulatedTime = accumulatedTime;
    }
    
    
    public String getName()
    {   
        return name;
    }
    
    public int getRanking()
    {   
        return ranking;
    }
    
    public String getSpecialSkill()
    {   
        return specialSkill;
    }
    
    public boolean getEligibleToRace()
    {   
        return eligibleToRace;
    }
    
    public int getAccumulatedScore()
    {   
        return accumulatedScore;
    }
    
    public int getAccumulatedTime()
    {
        return accumulatedTime;
    }
    
    public void setName(String temp)
    {   
         name = temp;
    }
    
    public void setRanking(int temp)
    {   
        ranking = temp;
    }
    
    public void setSpecialSkill(String temp)
    {   
        specialSkill = temp;
    }
    
    public void setEligibleToRace(boolean temp)
    {   
        eligibleToRace = temp;
    }
    
    public void setAccumulatedScore(int temp)
    {   
        accumulatedScore = temp;
    }
    
    public void setAccumulatedTime(int temp)
    {
        accumulatedTime = temp;
    }
    
     public void addScore(int temp)
    {   
        accumulatedScore += temp;
    }
    
    public String toString()    //to get a string which consists of a driver's name, ranking and skill 
    {
        return (this.name + "," + this.ranking + "," +this.specialSkill);
    }
}
