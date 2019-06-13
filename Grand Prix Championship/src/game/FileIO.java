package game;

import java.io.*;
import java.util.ArrayList;

public class FileIO
{

    public ArrayList<String[]> readFile(String file)  // This method calls reader to get information from a file.
    {
        String line = "";
        ArrayList<String[]> result = new ArrayList<String[]>();   // String[] is usd to store the information of a line separately 

        try
        {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);         

            while((line = bufferedReader.readLine()) != null) // buffererReader.readLine() returns a line of the file, after all the file has been read, buffererReader.readLine() returns null 
            {               
                line = line.trim();   //to remove unnecessary space
                String[] element = line.split(",");     // because lines in the file are splited by comma              
                result.add(element);               

            }
            try
            {
                bufferedReader.close();
            }
            catch(Exception e)
            {
                System.out.println("Unable to clsoe (in read mode)");
            }

        }     
        catch(FileNotFoundException ex) 
        {
            System.out.println("Unable to open file: "  + file); 
        }
        catch(IOException ex) 
        {
            System.out.println("Error reading file: "+ file);                             
        }

        return result;
    }   

    public void fileOverWrite(String filename, String content) // The method is used to write the first line,  code was gotten from the tutor, Shams
    {
        FileOutputStream file;

        try
        {
            file = new FileOutputStream(filename, false);
            try
            {
                byte b[]= content.getBytes();     // str.getBytes()  returns a byte,  
                file.write(b);
                file.write("\r\n".getBytes());    // "\r\n" is line break,to start a new line
            }

            catch (Exception e)
            {
                System.out.println("Unable to write");                
            }

            try
            {
                file.close();
            }

            catch (Exception e)
            {
                System.out.println("Unable to clsoe (in write mode)");                
            }
        }

        catch(Exception e)
        {
            System.out.println("Unable to open (in write mode)");  
        }
    }
    
     public void fileWrite(String filename, String content) // The method is used to write the remain part. 
    {
        FileOutputStream file;

        try
        {
            file = new FileOutputStream(filename, true);
            try
            {
                byte b[]= content.getBytes();
                file.write(b);
                file.write("\r\n".getBytes());
            }

            catch (Exception e)
            {
                System.out.println("Unable to write");                
            }

            try
            {
                file.close();
            }

            catch (Exception e)
            {
                System.out.println("Unable to clsoe (in write mode)");                
            }
        }

        catch(Exception e)
        {
            System.out.println("Unable to open (in write mode)");  
        }
    }

}