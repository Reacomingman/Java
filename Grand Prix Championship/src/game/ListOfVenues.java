package game;

import java.util.ArrayList;
//import java.io.*;

public class ListOfVenues
{
  private ArrayList<Venue> venues;

  public ListOfVenues()
  {
      venues = new ArrayList<Venue>();
  }
  
  public ListOfVenues(ArrayList<Venue> venues)
  {
      this.venues = venues;
  }

  public ArrayList<Venue> getVenues()
  {
      return venues;
  }

  public void setVenues()   // call the method to assign the driver arraylist that is gotten from the "venues.txt" file to the field directly
  {
      venues = readVenues();
  }   
  
  public ArrayList<Venue> readVenues() // assign every string that is read from the file to each venues' attribute
  {
      String fileName = "venues.txt";          
      FileIO fileIO = new FileIO(); 
      ArrayList<String[]> vList = fileIO.readFile(fileName);   // this method is to read every string from the file
      
      ArrayList<Venue> arr = new ArrayList<Venue>();     // this method is to read every string from the file
      
      int loop = vList.size();
      
      for(int i = 0; i < loop; i++ )   // to avoid NullPointerException
      {
          arr.add(new Venue());
      }

      for (int i = 0; i < vList.size(); i++)
      {   
          for(int j = 0; j < 4; j++)      // we already know there is 4 attribute in the file
          {
              switch (j)
              {
                  case 0:
                  arr.get(i).setVenueName(vList.get(i)[j]);
                  break;

                  case 1:
                  arr.get(i).setNoOfLaps(Integer.parseInt(vList.get(i)[j]));
                  break;

                  case 2:
                  arr.get(i).setAverageLapTime(Integer.parseInt(vList.get(i)[j]));
                  break;

                  case 3:
                  arr.get(i).setChanceOfRain(Double.parseDouble(vList.get(i)[j]));
                  break;

                  default:
                  break;
              }
          }

      }
      
      return arr;

  } 
/** This method can be used to check the information was set correctly   
  public void checkVenues()
  {
      setVenues();
      
      for(int i = 0; i < venues.size(); i++)
      {
          System.out.println(venues.get(i).toString() );
      }
  }
*/
}

