package game;

import java.util.Scanner;
import java.util.Random; 
import java.util.Arrays; 
import java.util.ArrayList;


public class Game {
	
	private int trailLength; // the start point is 0, the end point is trailLength-1
    private Trail natureTrail;    
    private Player playerOne;
    private Player playerTwo;

    public Game()
    {
        trailLength = 0;
        playerOne = new Player();
        playerTwo = new Player();
        natureTrail = new Trail();
    }

	public static void main(String[] args) {
		
		Game gameObject = new Game();
		gameObject.displayWelcomeMessage();  //  print a welcome message

		gameObject.setLength();      // set the length of trail    
		gameObject.race(); // run the main part of the game       
		gameObject.decideWinner();      // print the winner

        System.out.println("Game Over");
        gameObject.resetData();   // set all values to default values, so as to run again in the same object

	}


    public void divideBlock(int length) // beautify the outputs
    {
        for(int i = 0; i < length; i++)
        {
            System.out.print("*");
        }
        System.out.println();
    }

    public int rollDice()
    {
        Random random = new Random();
        Dice dice = new Dice();

        int temp = random.nextInt(dice.getMaximumValue()) + 1; // generate a random number, random.nextInt(int x) return an integer in [0,x)
        return temp;
    }

    public int rollDice(String displayMessage)    // for computer use, to show computer rolled the dice
    {
        System.out.println(displayMessage);

        Random random = new Random();
        Dice dice = new Dice();

        int temp = random.nextInt(dice.getMaximumValue()) + 1;
        System.out.println();

        return temp;
    }

    public boolean checkNumeric(String string)  // check if the string is numeric 
    {
        int length = string.length();

        if (length == 0)
        {
            return false;
        }

        for(int position = 0; position < length; position++) 
        {
            char thisChar = string.charAt(position); 
            if(thisChar < '0' || thisChar > '9')  // A char reflects a number value in ASCII
            {
                return false;
            }

        }
        return true;
    }

    public String input(String displayMessage) // show a message and require the player to input something
    {
        Scanner console = new Scanner(System.in);

        System.out.println(displayMessage);
        String temp = console.nextLine();

        return temp;
    }

    public void displayWelcomeMessage()  // print a welcome message
    {
        String welcome = "Welcome to the nature trail game";       

        divideBlock(60);
        System.out.println(welcome);
        divideBlock(60);

        System.out.println();

    }

    public void setLength() // set the length of the trail
    {  
        divideBlock(60); 

        String temp = "Please input the number (10 ~ 20) of spaces on the trail";
        String judgement  = "false";

        while (judgement == "false") 
        {
            String tp = input(temp);

            while (checkNumeric(tp) == false) // ensure the input is numeric
            {
                System.out.println("The input is invalid");
                tp = input(temp);
            }

            int length = Integer.parseInt(tp);
            if (length >= 10 && length <= 20) // ensure the number is in between 10 and 20
            {
                trailLength = length;
                judgement = "true";
            }
            
            else
            {
                System.out.println("The input is invalid");
            }       
        }

        divideBlock(60);
        System.out.println();   
    }

    public void checkActionPositions()  // print out the positions of all features
    {
        divideBlock(60);

        for(int i = 0; i < 4; i++ ) // It's clear that there are four features
        {
            System.out.println(natureTrail.getFeatures()[i].getFeatureType() + " is: " + natureTrail.getFeatures()[i].getFeaturePosition() );
        }

        divideBlock(60);
        System.out.println();
    }

    public void setPlayerName()  // require the player to enter a name, the name is 1~6 characters long, space is counted.
    {
        divideBlock(60);

        System.out.println("Now, set up players...");

        String temp = "Please enter a name (1~6 characters)";
        String judgement  = "false";

        while (judgement == "false")
        {
            String name = input(temp);

            if (name.trim().length() > 0) // to limit the string that all characters are space 
            {
                int nameLength = name.length();

                if (nameLength <= 6)    // to limit the length of the string
                {
                    playerOne.setName(name.trim()); // to delete the spaces at the begin and end of the string
                    judgement = "true";  
                }
                else
                {
                    System.out.println("The input is invalid");  
                }
            }
            else
            {
                System.out.println("The input is invalid");
            }

            playerTwo.setName("Computer");
        }

        System.out.println("Welcome, " + playerOne.getName()); 

        divideBlock(60);
        System.out.println();
    }  

    public void displayPositions(int pp, int pc)  // the trail is displayed in different conditions
    {
        String[] currentPositions = new String[trailLength];
        Arrays.fill(currentPositions,"_"); // firstly, set all elements in the String[] to "_"

        if (pp >= trailLength - 1)  // Even though player's position is larger than trailLength - 1, it shows as it equals to trailLength - 1
        {
            pp = trailLength - 1;
        }

        if (pc >= trailLength - 1)
        {
            pc = trailLength - 1;
        }

        currentPositions[pp] = "H"; // change some special positioin from "_" to "H","C", ....
        currentPositions[pc] = "C";    
        currentPositions[0] = "S";
        currentPositions[trailLength - 1] = "F";

        if (pp == pc && pc == 0 )
        {
            currentPositions[0] = "SHC";
        }

        if (pp == pc && pc != 0)
        {
            currentPositions[pp] = "HC";
        }

        if (pp == 0 && pc != 0)
        {
            currentPositions[0] = "SH";
        }

        if (pc == 0 && pp != 0)
        {
            currentPositions[0] = "SC";
        }    

        if (pp >= trailLength - 1 && pc < trailLength - 1) // Because when the first player reach at the end point, the game ends, the posion cannot be equal at the end
        {
            currentPositions[trailLength - 1] = "HF";
        }

        if (pc >= trailLength - 1 && pp < trailLength - 1)
        {
            currentPositions[trailLength - 1] = "CF";
        }

        System.out.println(Arrays.toString(currentPositions).replaceAll(",","")); // to romove all "," in the string      

    }  

    public Animal[] setAnimalSight()   // return an animal[], and add animals to the animal[], index of 1, 3, 5, 7, 9 in the array are the animal
    {
        Animal[] scoreReward = new Animal[10]; 

        Animal koala = new Animal("Koala", 10);
        Animal emu = new Animal("Emu", 7);
        Animal wombat = new Animal("Wombat", 5);
        Animal kangaroo = new Animal("Kangaroo", 2);
        Animal redBackSpider = new Animal("Redback Spider", -5);
        Animal nothing = new Animal("nothing", 0);

        Arrays.fill(scoreReward,nothing);

        scoreReward[1] = koala;
        scoreReward[3] = emu;
        scoreReward[5] = wombat;
        scoreReward[7] = kangaroo;
        scoreReward[9] = redBackSpider;

        return scoreReward;
    }

    public int sightAnimal(Animal[] animals) // pass the assigned animal[] here, and use the animal[] to judge what animal the players sighted, return a bonus
    {
        Random random = new Random();
  
        int temp = random.nextInt(10); // generate an integer in [0,10)
        animals = setAnimalSight(); 

        String animal = animals[temp].getType(); // natureTrail.getAnimalSight() returns an array, animal[] (length is 10, five elements are animals, the others are nothing)
        int bonus = animals[temp].getReward();

        if (temp % 2 == 1) // index of 1,3,5,7, 9 in the animal[] are animal
        {
            System.out.println(animal + " was sighted" );
            return bonus;
        }
        else
        {            
            return bonus; // the other index in the animal[] are nothing, bonus is 0
        }     
    }  

    public String selectFirst() // the method is used to decide who goes first, and it returns a string, so as to confirm the order in race()
    {
        divideBlock(60);

        System.out.println("Now, it is time to decide who rolls first");
        System.out.println();

        String temp = "press \"Enter\" to continue...";
        String judgement = "false";
        String first ="" ;  // store the string

        while (judgement == "false")
        {
            input(temp);
            int playerOne = rollDice();
            System.out.println("You rolled a ... " + playerOne);
            System.out.println();

            int playerTwo = rollDice("Computer's Turn! ");
            System.out.println("Computer rolled a ... " + playerTwo);
            System.out.println();

            if (playerOne > playerTwo) // condition that player goes first
            {
                System.out.println("Congratulations! You go first");
                first = "people"; 
                judgement = "true";

                divideBlock(60);
                System.out.println();
            }

            else if (playerOne < playerTwo)
            {
                System.out.println("Computer goes first");
                first = "computer";
                judgement = "true";                

                divideBlock(60);
                System.out.println();
            }

            else
            {
                System.out.println("The results are equal! Please roll again!");
                System.out.println();
            }
        }
        return first;   
    } 

    public int takeAction(int temp, ArrayList<?> featurePositions)  // featurePositions stores the all random feature positions, check if position (temp) is on the positions that have the features, and return the penalty value
    {
        if(featurePositions.contains(temp)) 
        {
            int index = featurePositions.indexOf(temp); // return the index of temp in the arraylist, and the index of temp is the same in NatureFeature[], so the random positions and nature features are associated 
            int feature = natureTrail.getFeatures()[index].getSpacePenalty(); // natureTrail.getFeatures() return an array (NatureFeature[]), 
            String featureType = natureTrail.getFeatures()[index].getFeatureType();

            if (feature > 0)  //  show positive features and negative features in different ways
            {
                System.out.println("Wow, there is a " + featureType);
                System.out.println("The position will move forward  " + feature + " places");
                return feature;
            }

            else
            {
                System.out.println("Oooops, there is a " + featureType);
                System.out.println("The position will move back  " + Integer.toString(feature).replaceAll("-","") + " places");
                return feature;
            }
        }

        else
        {
            return 0;  // when temp is is on the positions that have the actions, return 0
        }

    }

    /**
     * This is the main process of the game, runs in the loops.
     * includes:
     * set the four features randomly on the trail
     * require player to enter a name
     * decide who goes first
     * run in different order (player first/ computer first)
     * Only after both players rolled the dice, the trail can be printed
     */
    public void race() 
    {              
        ArrayList<?> featurePositions = natureTrail.setFeatures(trailLength);   //  set the four features randomly on the trail, and assign the positions to the arraylist
        checkActionPositions();  // print out the positions of all features

        setPlayerName(); // require player to enter a name; computer's name is set as "computer" automatically

        divideBlock(60); // print a line of 60 "*" to divide the outputs

        System.out.println("Let's begin!");
        displayPositions(playerOne.getPosition(),playerTwo.getPosition()); // display the postitions of player and computer on the trail 

        divideBlock(60);
        System.out.println();

        Animal[] animals = setAnimalSight();  //creat an animal[], and assign it to animals 
        int temp;  // to store the dice results
        int temp2; // to store the sighting results, the bonus of animals

        String first = selectFirst(); // return a string, so as to judge who goes first, if first is "people", player goes first 

        String pp = "Your turn! press \"Enter\" to continue ..."; // to inform player to press enter to roll the dice 
        String cp = "Computer's turn!";         // to show it is the computer's turn
        String show = "press \"Enter\" to views the current positions ..."; // to inform player to press enter to display the position on the trail 

        divideBlock(60);

        if(first == "people")
        {
            while ((playerOne.getPosition() < trailLength-1) && (playerTwo.getPosition() < trailLength-1)) // when a player's position is equal to or larger than trailLength - 1, the game ends  
            {
                input(pp);

                temp = rollDice();
                System.out.println("You rolled a ... " + temp);
                playerOne.increasePosition(temp);  // the mothed will assign the new postion to the Player 

                temp2 = sightAnimal(animals);
                playerOne.increaseScore(temp2);    // the mothed will assign the new score to the Player    
                System.out.println("Your score is: " + playerOne.getScore());              

                System.out.println("Your position is: " + playerOne.getPosition());
                playerOne.increasePosition(takeAction(playerOne.getPosition(), featurePositions)); // assign the new position to players after the actions are checked

                System.out.println();                

                if (playerOne.getPosition() >= trailLength-1) // when the a player is on a position that equals to or larger to trailLength-1, the race ends
                {
                    System.out.println("You reached at the end point first, you got rewarded 10 points");
                    playerOne.increaseScore(10); // winner will get rewarded 10 points
                    break;
                }

                if (playerOne.getPosition() < 0)
                {
                    playerOne.setPosition(0);
                }

                temp = rollDice(cp);
                System.out.println("Computer rolled a ... " + temp);
                playerTwo.increasePosition(temp);

                temp2 = sightAnimal(animals);
                playerTwo.increaseScore(temp2); 

                System.out.println("Computer's score is: " + playerTwo.getScore());

                System.out.println("Computer's position is : " + playerTwo.getPosition());
                playerTwo.increasePosition(takeAction(playerTwo.getPosition(), featurePositions));

                System.out.println();

                if (playerTwo.getPosition() >= trailLength-1 )
                {
                    System.out.println("Computer reached at the end point first, computer got rewarded 10 points");
                    playerTwo.increaseScore(10);
                    break;
                }

                if (playerTwo.getPosition() < 0)
                {
                    playerTwo.setPosition(0);
                }

                input(show);              
                displayPositions(playerOne.getPosition(),playerTwo.getPosition());

                System.out.println();
            }    

        }

        else
        {
            while ((playerOne.getPosition() < trailLength-1) && (playerTwo.getPosition() < trailLength-1))
            {
                temp = rollDice(cp);
                System.out.println("Computer rolled a ... " + temp);
                playerTwo.increasePosition(temp);

                temp2 = sightAnimal(animals);
                playerTwo.increaseScore(temp2);

                System.out.println( "Computer's score is: " + playerTwo.getScore());              

                System.out.println("Computer's position is :" + playerTwo.getPosition());
                playerTwo.increasePosition(takeAction(playerTwo.getPosition(), featurePositions));

                System.out.println();

                if (playerTwo.getPosition() >= trailLength-1) 
                {
                    System.out.println("Computer reached at the end point first, computer got rewarded 10 points.");
                    playerTwo.increaseScore(10);
                    break;
                }
                if (playerTwo.getPosition() < 0)
                {
                    playerTwo.setPosition(0);
                }

                input(pp); 

                temp = rollDice();
                System.out.println("You rolled a ... " + temp);
                playerOne.increasePosition(temp);

                temp2 = sightAnimal(animals);
                playerOne.increaseScore(temp2);

                System.out.println("Your score is: " + playerOne.getScore());

                System.out.println("Your position is: " + playerOne.getPosition());
                playerOne.increasePosition(takeAction(playerOne.getPosition(), featurePositions));

                System.out.println();      

                if (playerOne.getPosition() < 0)
                {
                    playerOne.setPosition(0);
                }
                if (playerOne.getPosition() >= trailLength-1)
                {
                    System.out.println("You reached at the end point first, you got rewarded 10 points");
                    playerOne.increaseScore(10);
                    break;
                }

                input(show);                
                displayPositions(playerOne.getPosition(),playerTwo.getPosition());

                System.out.println();
            }    
        }

        displayPositions(playerOne.getPosition(),playerTwo.getPosition());

        divideBlock(60);
        System.out.println();
    }

    public void decideWinner() // decide the winner according to scores
    {
        divideBlock(60);       

        System.out.println("Your score is: " + playerOne.getScore() + ". Computer's score is: " + playerTwo.getScore() );

        if (playerOne.getScore() > playerTwo.getScore())
        {
            System.out.println("The winner is you" );
        }

        else if (playerOne.getScore() < playerTwo.getScore())
        {
            System.out.println("The winner is computer" );
        }

        else
        {
            System.out.println("Both of you are winners" );
        }

        divideBlock(60);
        System.out.println();
    }    

    public void resetData()
    {
        playerOne = new Player();
        playerTwo = new Player();
        natureTrail = new Trail();
    }

}
