package game;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;   // to use the min and max method 

public class Championship
{
    private ListOfDrivers drivers;
    private ListOfVenues venues;

    public Championship()
    {
        drivers = new ListOfDrivers();
        venues = new ListOfVenues();    
    } 
    
    public Championship(ListOfDrivers drivers,ListOfVenues venues)
    {
        this.drivers = drivers;
        this.venues = venues;    
    } 
    

    public ListOfDrivers getListOfDrivers()
    {
        return drivers;
    }

    public ListOfVenues getListOfVenues()
    {
        return venues;
    }

    public void setListOfDrivers(ListOfDrivers temp)
    {
        drivers = temp;
    }

    public void setListOfVenues(ListOfVenues temp)
    {
        venues = temp;
    }

    /**
     *  Only this method can have access to all the data.
     *  
     *  Ranks will be updated at the end of each race by totaltime in that race.
     *  After all races finish, ranks will be given by the accumulated scores
     */
    public static void main(String[] args) {
		Championship cp = new Championship();
		cp.startProgram();
	}
    
    
    public void startProgram()
    {
        separate(100,"*");     // a method to divide the displayed information into blocks, so as to beautify 
        System.out.println("Welcome to Grand Prix Championship");
        separate(100,"*");

        //drivers = new ListOfDrivers();   // make a championship object reusable
        //venues = new ListOfVenues();    // make a championship object reusable

        int races = setRaces();   // a method to require users to select the number of races, range from 3 to 5

        venues.setVenues();    //this method will read the file and assgin the information to venues

        drivers.setDrivers();  // read the driver file here, to get the number of drivers

        int driverNo = drivers.getDrivers().size();

        ArrayList<Integer> scores = new ArrayList<Integer>();  // becasue the accumatedScore is reset to 0 every race in this design, this is used to count for the entire championship 
        for (int i = 0; i < driverNo; i++ )   //
        {
            scores.add(0);      // initiate the scores
        }       
        
        int lapNo = venues.getVenues().size();
        
        ArrayList<Integer> venueSelections = new ArrayList<Integer>();  // User can select a venues manually, the arraylist is used to check if a venues is selected before.
        for (int i = 0; i < lapNo; i++ ) // Laps whose average lap time is out of the range cannot be selected. 
        {
            int time = venues.getVenues().get(i).getAverageLapTime();
            
            if (time < 60 || time > 90)
            {
                venueSelections.add(i+1); // the selection start with 1
                
                separate(80,"x");
                System.out.println("Infomation of " + venues.getVenues().get(i).getVenueName() + " is incorrect, it cannot be used");
                separate(80,"x");
            }            
        }        
        
        for (int i = 0; i < races; i++)
        {         
            separate(70,"+");

            showVenueMenu();    // the method displays a selection menu for venues

            System.out.println(); 

            int index = selectVenue(venueSelections);   // this method returns the index of selected vennue        

            beginARace(scores, index);  // this method executes the process of a race
        } 

        separate(70,"$");

        ArrayList<Integer> finalRanks = ranking(scores,"Descend");   // ranking is a method which give the ranks for every element in a arraylist
        for (int i = 0; i < driverNo; i++)
        {       
            int rank = finalRanks.indexOf(i+1);   // ranks begin with 1

            drivers.getDrivers().get(rank).setAccumulatedScore(scores.get(rank)); // assign the final score to a driver
            drivers.getDrivers().get(rank).setRanking(finalRanks.get(rank)); // assign the final rank to a driver

            System.out.println("Rank " + (i+1) + " : "+drivers.getDrivers().get(rank).getName()+ "   " +drivers.getDrivers().get(rank).getAccumulatedScore());
        }

        separate(70,"$");

        int index = finalRanks.indexOf(1);
        System.out.println("The winner is: " + drivers.getDrivers().get(index).getName());

        FileIO file =new FileIO();
        for (int i = 0; i < driverNo; i++)
        {
            String DriverInfo = drivers.getDrivers().get(i).toString();  //to get a string which consists of a driver's name, ranking and skill 

            if(i==0)
            {
                file.fileOverWrite("drivers.txt",DriverInfo);
            }
            else
            {
                file.fileWrite("drivers.txt",DriverInfo);
            }
        }

    }

    public int setRaces() // require users to select a venue from the venue list
    {                
        Input printer = new Input(); 

        int race = 0;
        do
        {

            try
            {
                System.out.println("Please enter the number of races between 3 to 5");

                race = printer.inputInteger(); // if the input is not numeric, error will be caught

                if(race < 3 || race > 5) throw new Exception ("Error! Please try again"); 
            }
            catch (Exception e) 
            {
                System.out.println("Error! Please try again");
                System.out.println();
            }

        }
        while (race < 3 || race > 5); 

        return race;
    }

    public void beginARace(ArrayList<Integer> scores, int index) // the process of a race, index if the index of a venue
    {        
        int avgTime = venues.getVenues().get(index).getAverageLapTime();
        double chanceOfRain = venues.getVenues().get(index).getChanceOfRain();
        int noOfLap = venues.getVenues().get(index).getNoOfLaps();

        drivers.setDrivers();  // update the driver information at the begining of a race        
        int driverNo = drivers.getDrivers().size();

        ArrayList<Integer> totalTimes = new ArrayList<Integer>(); // to store a driver's accumulated time for a race

        for(int i = 0; i < driverNo; i++)
        {
            totalTimes.add(drivers.getDrivers().get(i).getAccumulatedTime());  // At the beginning of each race, accumulated time is 0
        }     

        for (int i = 0; i < driverNo; i++) // give time penalty to simulate the postions grib
        {
            int temp = drivers.getDrivers().get(i).getRanking();
            int timePenalty;

            switch (temp)
            {                
                case 1:
                timePenalty = 0;
                break;
                case 2:
                timePenalty = 3;
                break; 
                case 3:
                timePenalty = 5;
                break;
                case 4:
                timePenalty = 7;
                break;               
                default:
                timePenalty = 10;
                break; 
            }
            increaseValue(totalTimes, i, timePenalty);  // a method to add a value to element of a specific index in a arraylist
        }

        ArrayList<Integer>  quitedDriver= new ArrayList<Integer>(); // to stores the index of drivers who met unrecoverable problems        
        boolean rain = false; // an default value       
        boolean[] wetTyres = new boolean[5]; // to stores the status of drivers if they changed wet-weather tyre 

        for (int i = 0; i < noOfLap; i++)
        { 
            System.out.println("Lap" + (i+1));       

            if (checkRaining(chanceOfRain,rain)) // the raining condition,  a method to check if it begins to rain or it is raining
            {
                rain = true;  // If it begins to rain in a lap, it keeps raining in all the remain laps
                
                separate(60,"-");

                for (int j = 0; j < 5; j++)
                {
                    if (quitedDriver.contains(j)) // The driver who suffuered unrecoverable problem will not appear in the loop anymore
                    {
                        continue;
                    }   

                    if (i == 1 && noOfLap > 2)  // If the number of laps is one or two, there is no need to change tyres.
                    {
                        boolean decision = isWetWeatherTyre(j);  // the method to decide to chance tyres or not

                        if (decision)
                        {
                            wetTyres[j] = true;  // to store the status for every driver
                            increaseValue(totalTimes, j, 10);                            
                        }
                        else
                        {
                            wetTyres[j] = false;
                        }
                    }

                    if (wetTyres[j])
                    {
                        increaseValue(totalTimes, j, avgTime);
                    }
                    else
                    {
                        increaseValue(totalTimes, j, avgTime+5);
                    }

                    int effect = useSkill(j, i+1);    // i + 1 is equal to the ordinal number of lap             
                    increaseValue(totalTimes, j, -effect);

                    int accident = occurProblem(); // return the time should be added, if unrecoverable problem occurs, it returns -1; if no problem happens, it returns 0;

                    if (accident >= 0)
                    {
                        increaseValue(totalTimes, j, accident);

                        System.out.println(drivers.getDrivers().get(j).getName()+"  "+ totalTimes.get(j));
                        System.out.println();
                    }                
                    else
                    {
                        drivers.getDrivers().get(j).setEligibleToRace(false);                        
                        totalTimes.set(j,10000);     // set the total time of a quited driver to 10000, to ensure they have the worst grade.                  
                        quitedDriver.add(j);  // to store the index of quited driver

                        System.out.println(drivers.getDrivers().get(j).getName() + " cannot continue to this race.");
                        System.out.println();
                    }       

                }                
            }
            else // the good weather condtion 
            {   
                separate(60,"-");

                for (int j = 0; j < driverNo; j++)
                {
                    if(quitedDriver.contains(j)) // The driver who suffuered unrecoverable problem will not appear in the loop anymore
                    {
                        continue;
                    }

                    if (i == 1 && noOfLap > 2) // If the number of laps is one or two, there is no need to change tyres.
                    {
                        boolean decision = isWetWeatherTyre(j);
                        if(decision)
                        {
                            wetTyres[j] = true;
                            increaseValue(totalTimes, j, 10);                            
                        }
                        else
                        {
                            wetTyres[j] = false;
                        }
                    }

                    increaseValue(totalTimes, j, avgTime);
                    int effect = useSkill(j, i+1);         // i + 1 is equal to the ordinal number of lap             
                    increaseValue(totalTimes, j, -effect);

                    int accident = occurProblem();

                    if (accident >= 0)
                    {
                        increaseValue(totalTimes, j, accident);
                        System.out.println(drivers.getDrivers().get(j).getName()+"  "+ totalTimes.get(j));
                        System.out.println();
                    }                
                    else
                    {
                        drivers.getDrivers().get(j).setEligibleToRace(false);
                        totalTimes.set(j,10000);
                        quitedDriver.add(j);
                        System.out.println(drivers.getDrivers().get(j).getName() + " cannot continue to this race.");
                        System.out.println();
                    }       
                }

                int leadDriver = totalTimes.indexOf(Collections.min(totalTimes)); // min is to select the minimum in a collect(array/arraylist/ ...)
                // If two or more drivers have the minimum totaltimes, the lead driver will be who is of the smallest index in the driver arraylist                 
                System.out.println("The lead driver is :" + drivers.getDrivers().get(leadDriver).getName());
                System.out.println();  

            }
        }

        ArrayList<Integer>  ranks= new ArrayList<Integer>();
        ranks = ranking(totalTimes,"ascend");       

        for (int i = 0; i<5;i++)
        {
            int rank = ranks.get(i);
            drivers.getDrivers().get(i).setRanking(rank);

            switch (rank)
            {
                case 1:
                drivers.getDrivers().get(i).addScore(8);                
                break;
                case 2:
                drivers.getDrivers().get(i).addScore(5);
                break;
                case 3:
                drivers.getDrivers().get(i).addScore(3);
                break;
                case 4:
                drivers.getDrivers().get(i).addScore(1);
                break;
                default:
                drivers.getDrivers().get(i).addScore(0);
                break;
            }

            increaseValue(scores, i, drivers.getDrivers().get(i).getAccumulatedScore()); // count the score for every driver
            System.out.println(drivers.getDrivers().get(i).getName() + " gets " + drivers.getDrivers().get(i).getAccumulatedScore() + " scores");
        }

        FileIO file =new FileIO();
        
        for (int i = 0; i < 5; i++)
        {
            String DriverInfo = drivers.getDrivers().get(i).toString();
            
            if(i==0)
            {

                file.fileOverWrite("drivers.txt",DriverInfo);
            }
            else
            {
                file.fileWrite("drivers.txt",DriverInfo);
            }
        }

    }

    public void showVenueMenu() // to print the venue menu
    {
        System.out.println("Please select a venues from the menu");
        
        int loop = venues.getVenues().size();
        
        for (int i = 0; i < loop; i++)
        {
            if (i == 0)
            {
                StringBuffer title = new StringBuffer();
                for(int g = 0; g < 15; g++)
                {
                    if(g == 0)
                    {
                        title.append("Select Key");
                    }
                    else
                    {
                        title.append(" ");
                    }

                }
                title.append("Venue");
                int tl = 60 - title.length();
                for(int h = 0; h < tl-2; h++)
                {
                    title.append(" ");
                }
                title.append("#laps");
                System.out.println(title.toString());

            }
            StringBuffer sb = new StringBuffer();

            for(int j = 0; j < 15; j++)
            {
                if(j == 4)
                {
                    sb.append(i+1);
                }
                else
                {
                    sb.append(" ");
                }

            }
            sb.append(venues.getVenues().get(i).getVenueName());
            int sl = 60 - sb.length();
            for(int k = 0; k < sl; k++)
            {
                sb.append(" ");
            }
            sb.append(venues.getVenues().get(i).getNoOfLaps());
            System.out.println(sb.toString());
        }

    }

    public int selectVenue(ArrayList<Integer> arr) // the lap# arraylist will be the arguement
    {
        Input printer = new Input();
        int temp = 0;
        do
        {
            try
            {
                System.out.println("Please input an integer between 1 and 8, a venue can be selected only once a race");
                temp = printer.inputInteger();
                if (temp < 1 || temp > 8) throw new Exception ("out of range"); 
                if ((arr.contains(temp))) throw new Exception ("repeating");

            }
            catch (Exception e)
            {
                System.out.println("Error! Please try again! ");
                System.out.println();
            }
        }
        while (temp < 1 || temp > 8 || arr.contains(temp));

        arr.add(temp);

        switch (temp)
        {
            case 1:
            System.out.println("You selected the venue: " + venues.getVenues().get(0).getVenueName());
            System.out.println(); 
            break;
            case 2:
            System.out.println("You selected the venue: " + venues.getVenues().get(1).getVenueName());
            System.out.println(); 
            break;
            case 3:
            System.out.println("You selected the venue: " + venues.getVenues().get(2).getVenueName());
            System.out.println(); 
            break;
            case 4:
            System.out.println("You selected the venue: " + venues.getVenues().get(3).getVenueName());
            System.out.println(); 
            break;
            case 5:
            System.out.println("You selected the venue: " + venues.getVenues().get(4).getVenueName());
            System.out.println(); 
            break;
            case 6:
            System.out.println("You selected the venue: " + venues.getVenues().get(5).getVenueName());
            System.out.println(); 
            break;
            case 7:
            System.out.println("You selected the venue: " + venues.getVenues().get(6).getVenueName());
            System.out.println(); 
            break;
            case 8:
            System.out.println("You selected the venue: " + venues.getVenues().get(7).getVenueName());
            System.out.println(); 
            break;
            default:
            System.out.println("Unknown error"); 
            System.out.println(); 
            break;                
        }        
        return temp-1;
    }

    public void increaseValue(ArrayList<Integer> arr, int index, int value) // a method to add a value to element of a specific index in a arraylist
    {
        int temp = arr.get(index);
        temp += value;
        arr.set(index, temp);
    }

    public int useSkill(int driver, int lap) // return a value which represents the skill effect
    {
        String name = drivers.getDrivers().get(driver).getName();
        String skill = drivers.getDrivers().get(driver).getSpecialSkill();
        
        if (skill.equals("Braking"))
        {
            RNG rng = new RNG(1,8);            
            int result = rng.randomNumber();
            
            System.out.println(name +" use his/her skill " + skill +", the effect is -" + result + " seconds" );
            
            return result;
        }
        else if (skill.equals("Cornering"))
        {
            RNG rng = new RNG(1,8);            
            int result = rng.randomNumber();
            
            System.out.println(name +" use his/her skill " + skill +", the effect is -" + result + " seconds" );
            
            return result;
        }
        else
        {
            if (lap % 3 == 0)
            {
                RNG rng = new RNG(10,20);                
                int result = rng.randomNumber();
                
                System.out.println(name +" use his/her skill " + skill +", the effect is -" + result + " seconds" );
                
                return result;
            }
            else
            {
                return 0;
            }
        }
    }

    public int occurProblem() // return a value to represent the effect of a problem
    {
        RNG rng = new RNG(1,100);
        int result = rng.randomNumber();

        if(result == 100) //1%
        {
            System.out.println("An unrecoverable mechanical fault occurs");
            return -1;
        }
        else if(result<=3) //3%
        {
            System.out.println("A major mechanical fault occurs");
            return 120;
        }
        else if(result<=8) //5%
        {
            System.out.println("A minor mechanical fault occurs");
            return 20;
        }
        else
        {
            return 0;
        }        
    }
    /**
     * The ranking will generate a ranking arraylist which is of the same index of the orginal arraylist.
     * So we use a index to get the rank of element in the orginal arraylist.
     * Firstly, it will create a copy of the orginal arraylist, all changing actions will happen on the copy, 
     * so the orginal arraylist will not be changed.
     * Then, it will select the minimum/maximum value in the copy arraylist
     * if there is more than one element is the minimum/maximum value, only one index will be selected.
     * The element of the index in rank arraylist will be set to the related value, 
     * The element of the index in copy arraylist will be set to 10000/-1, so it will not be the minimum/maximum anymore.
     * The above process repeats executing until all rank values are set. 
     */
    public ArrayList<Integer> ranking(ArrayList<Integer> list, String order) 
    {
        ArrayList<Integer> ranks = new ArrayList<Integer>();
        ArrayList<Integer> arr = (ArrayList<Integer>)list.clone();     

        int length = arr.size();
        for (int i = 0; i < length; i++)
        {
            ranks.add(0);
        }
        if(order.equals("ascend"))
        {
            for(int i = 0; i < length; i++)
            {
                int min = Collections.min(arr);      
                
                ArrayList<Integer> count =new ArrayList<Integer>(); // to store the repeated minimum values

                for(int j = 0;j < length; j++)
                {
                    if(arr.get(j) == min)
                    {
                        count.add(j);   // get the index of the repeated value;
                    }
                }

                int range = count.size();
                RNG rng = new RNG(0,range-1);

                int index = count.get(rng.randomNumber());
                ranks.set(index, i+1);
                arr.set(index,10001);             
            }

        }
        else
        {
            for(int i = 0; i < length; i++)
            {
                int max = Collections.max(arr);            
                ArrayList<Integer> count =new ArrayList<Integer>();

                for(int j = 0;j < length; j++)
                {
                    if(arr.get(j) == max)
                    {
                        count.add(j);
                    }
                }

                int range = count.size();
                RNG rng = new RNG(0,range-1);

                int index = count.get(rng.randomNumber());
                ranks.set(index, i+1);
                arr.set(index,-1);             
            }

        }
        return ranks;    
    }

    public void separate(int i, String s)
    {
        StringBuffer sb = new StringBuffer();
        for(int a = 0; a < i; a++)
        {
            sb.append(s);
        }
        System.out.println(sb.toString());
    }

    public boolean checkRaining(double d, boolean isRaining) // the method is used to check if it begin raining to it keeps raining
    {
        int judge = (int)(d*100);
        RNG rng = new RNG(1,100);

        if(isRaining == false)
        {
            int temp = rng.randomNumber();
            if(temp <= judge)
            {   
                System.out.println("It begins raining");                
                return true;

            }
            else
            {
                return false;
            }        
        }
        else
        {
            System.out.println("It is raining");
            return true;
        }

    }

    public boolean isWetWeatherTyre(int i) // to decide if a driver use wet-weather tyre or not
    {
        RNG rng = new RNG(0,1);
        int temp = rng.randomNumber();
        if(temp == 0)
        {
            System.out.println(drivers.getDrivers().get(i).getName() + " keeps using dry-weather tyre");
            return false;
        }
        else
        {
            System.out.println(drivers.getDrivers().get(i).getName() + " chooses to  use wet-weather tyre + 10 seconds");
            return true;
        }
    }

}