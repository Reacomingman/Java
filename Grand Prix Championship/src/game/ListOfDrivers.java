package game;

import java.util.ArrayList;

public class ListOfDrivers
{
    private ArrayList<Driver> drivers;   

    public ListOfDrivers()
    {
        drivers = new ArrayList<Driver>();
    }
    
     public ListOfDrivers(ArrayList<Driver> drivers)
    {
        this.drivers = drivers;
    }

    public ArrayList<Driver> getDrivers()
    {
        return drivers;
    }

    public void setDrivers()   // call the method to assign the driver arraylist that is gotten from the "drivers.txt" file to the field directly
    {
        drivers = readDrivers();
    }

    public ArrayList<Driver> readDrivers()  // assign every string that is read from the file to each drivers' attribute
    {
        String fileName = "drivers.txt";          
        FileIO fileIO = new FileIO(); 
        ArrayList<String[]> dList = fileIO.readFile(fileName);  // this method is to read every string from the file

        ArrayList<Driver> arr = new ArrayList<Driver>(); // arr to store all the drivers 

        int loop = dList.size();

        for (int i = 0; i < loop; i++)
        {
            arr.add(new Driver());           // to avoid NullPointerException
        }

        for (int i = 0; i < loop; i++)
        {

            for(int j = 0; j < 3; j++)   // we already know there is 4 attribute in the file
            {
                switch (j)
                {
                    case 0:
                    arr.get(i).setName(dList.get(i)[j]);
                    break;

                    case 1:
                    arr.get(i).setRanking(Integer.parseInt(dList.get(i)[j]));
                    break;

                    case 2:
                    arr.get(i).setSpecialSkill(dList.get(i)[j]);
                    break;                   

                    default:
                    break;
                }
            }

        }

        return arr;
    }
    
/** This method can be used to check the information was set correctly
    public void checkDrivers()
    {
        setDrivers(); 

        for(int i = 0; i < drivers.size();i++)
        {
            System.out.println(drivers.get(i).toString());
        }
        
    }
*/

}
