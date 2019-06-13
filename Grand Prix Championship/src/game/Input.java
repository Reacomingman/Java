package game;

import java.util.Scanner;

public class Input
{
    public String inputString()
    {
        Scanner console = new Scanner(System.in);        
        String temp = console.nextLine();
        return temp;
    }
    
     public int inputInteger()
    {
        Scanner console = new Scanner(System.in);
        int temp = console.nextInt();
        return temp;
    }    
   
}
