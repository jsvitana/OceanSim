package oceansim;
import  java.util.Random;
import  java.io.*;

/**
 *
 * @author jsvitana59
 */
public class OceanSim 
{
    public static Random r1;
    
    public static final char EMPTYSPACE = '^';
    public static final char DEADRIG = '*';
    public static final char SPILL = '!';
    public static final int BREEDCHANCE = 50;
    public static int rows = 0;
    public static int cols = 0;
    public static int amountOfFreeSpace = 0;  //The amount of free spaces the board has
    public static boolean[][] flags;   //flag array for object moving  
    
    public static void printDirections()
    {
        System.out.println("Welcome to the Ocean Simulator");
        System.out.println("Here you are all powerful and control everything");
        System.out.println("Welcome to the Ocean Simulator");
    }
    
    public static void printOcean(char[][] ocean)
    {           
        //sets the margin spacing
        System.out.print("   ");
        //sets top margin numbers
        for(int k = 1;k<=cols;k++)
        {
            if(k < 10)
            {
                System.out.print(" "+k+" ");
            }
            else if((k > 9) && (k<100))
            {
                System.out.print(k+" ");
            }
            else
            {
                System.out.print(k);
            }
            System.out.print(" ");
        }
        System.out.println();
        
        //Sets the rest of the board 
        for(int i = 0;i<rows;i++)
        {
            //sets the number in the left margin for the current row
            if((i+1) < 10)
            {
                System.out.print("  " + (i+1) + " ");
            }
            else if(((i+1) > 9) && ((i+1) < 100))
            {
                System.out.print(" " + (i+1) + " ");
            }
            else
            {
                System.out.print((i+1) + " ");
            }
            
            //prints everything out in the current row besides the number
            for(int j = 0;j<cols;j++)
            {
                System.out.print(ocean[i][j] + "   ");
            }            
            System.out.println();            
        }
    }
    
    public static void showFlags(boolean[][] flags)
    {
        for(int i = 0;i<rows;i++)
        {
            for(int j = 0;j<cols;j++)
            {
                if(flags[i][j] == true)
                {
                    System.out.print('T');
                }
                else
                {
                    System.out.print('F');
                }
            }
            System.out.println();
        }
    }
    
    public static void initOcean(char[][] ocean)
    {
        int count = (rows*cols);   //error check down below for if the division would equal 0!!!!
        int randoNum = 0;          //Used to initilize random world
        int x = 0;
        int y = 0;
        //Initilizes grid with ^
        for(int i = 0;i<rows;i++)
        {
            for(int j = 0;j<cols;j++)
            {
                ocean[i][j] = EMPTYSPACE;
            }
        }
                
        /*for(int j = 0;j<count;j++)     //Randomizes world
        {
            randoNum = r1.nextInt(10);
            x = r1.nextInt(rows);
            y = r1.nextInt(cols);
            while(ocean[x][y] != EMPTYSPACE)
            {
                x = r1.nextInt(rows);
                y = r1.nextInt(cols);
            }
            if(randoNum == 0)
            {
                ocean[x][y] = 'F';
            }
            else if(randoNum == 1)
            {
                ocean[x][y] = 'S';
            }
            else if(randoNum == 2)
            {
                ocean[x][y] = 'I';
            }
            else if(randoNum == 3)
            {
                ocean[x][y] = 'B';
            }
            else if(randoNum == 4)
            {
                ocean[x][y] = 'R';
            }
            else if(randoNum == 5)
            {
                ocean[x][y] = 'J';
            }
            else if(randoNum == 6)
            {
                ocean[x][y] = 'K';
            }
            else if (randoNum == 7)
            {
                ocean[x][y] = 'L';
            }
            else if(randoNum == 8)
            {
                ocean[x][y] = 'A';
            }
            else
            {
                ocean[x][y] = 'O';
            }
        }*/
    }
    
    public static char getObject()
    {
        char objectChoice = ' ';
        
        System.out.println("[F]  Fish\n"
                         + "[S]  Shark\n"  
                         + "[I]  Iceburg\n"
                         + "[B]  Boat\n"
                         + "[R]  Rock\n"
                         + "[J]  Jellyfish\n"
                         + "[K]  Kraken\n"
                         + "[L]  Lobster\n"
                         + "[A]  Bird\n"    // A for avian
                         + "[O]  Oil Rig");
        
        objectChoice = SavitchIn.readLineNonwhiteChar();
        objectChoice = Character.toUpperCase(objectChoice);  //converts to uppercase
        //error check the chars
        while(!((objectChoice == 'F') || (objectChoice == 'S') || (objectChoice == 'I') || (objectChoice == 'B') || (objectChoice == 'R') || (objectChoice == 'J') || (objectChoice == 'K') || (objectChoice == 'L') || (objectChoice == 'A') || (objectChoice == 'O')))
        {
            System.out.println("Not a valid choice, choose an object from the list above");
            objectChoice = SavitchIn.readLineNonwhiteChar();
            objectChoice = Character.toUpperCase(objectChoice);  //converts to uppercase
        }
        return objectChoice;
    }
    
    public static void findSpace(char[][] ocean)
    {
        amountOfFreeSpace = 0;
        
        for(int i = 0;i<rows;i++)
        {
            for(int j = 0;j<cols;j++)
            {
                if(ocean[i][j] == EMPTYSPACE)
                {
                    amountOfFreeSpace++;
                }
            }
        }
    }
    
    ///////////////////////////////////////-- START OF ADDING OBJECT METHODS --/////////////////////////////////////////////////////////////////////////
    
    public static void placeSpecificObject(char[][] ocean)
    {
        char objectChoice = ' ';   //What you are placing
        int rowPlace = 0;    //row to place object
        int colPlace = 0;    //Col to place object
        int choice = 0;      //various choices you make within the method
        
        //asks for what object you want to add to the board and takes a char value
        System.out.println("What kind of object would you like to place?: ");
        objectChoice = getObject();       
        
        //Shows board and asks for coordinates
        printOcean(ocean);
        
        //gets row and col to place object
        System.out.println("Where Where would you like to place this object?");
        System.out.print("Row: ");
        rowPlace = SavitchIn.readLineInt();
        while((rowPlace < 1) || (rowPlace > rows))
        {
            System.out.println("That is not a valid row to place an object");
            System.out.println("Where Where would you like to place this object?");
            System.out.print("Row: ");
            rowPlace = SavitchIn.readLineInt();
        }
        System.out.print("Column: ");
        colPlace = SavitchIn.readLineInt();
        while((colPlace < 1) || (colPlace > cols))
        {
            System.out.println("That is not a valid column to place an object");
            System.out.println("Where Where would you like to place this object?");
            System.out.print("Column: ");
            colPlace = SavitchIn.readLineInt();
        }
        rowPlace--;
        colPlace--;
        
        //checks coordinates for if there is something there already
        if(ocean[rowPlace][colPlace] != EMPTYSPACE)
        {
            System.out.println("There is already a " + ocean[rowPlace][colPlace] + " there, would you like to overwrite it?\n[1] Yes\n[2] No");
            choice = SavitchIn.readLineInt();
            while((choice < 1) || (choice > 2))
            {
                System.out.println("Not a valid choice, would you like to overwrite it?\n[1] Yes\n[2] No");
                choice = SavitchIn.readLineInt();
            }
            
            if(choice == 1)
            {
                System.out.println("Overwriting previous object");
                //applies coordinates        
                ocean[rowPlace][colPlace] = objectChoice;
                printOcean(ocean);
            }
            else
            {
                printOcean(ocean);
                System.out.println("No change has been made");
            }
        }
        else
        {
            //applies coordinates        
            ocean[rowPlace][colPlace] = objectChoice;
            printOcean(ocean);
        }
    }
    
    public static void placeRandomObject(char[][] ocean, int amountToAdd)
    {
        char objectChoice = ' ';   //What you are placing
        int rowPlace = 0;    //row to place object
        int colPlace = 0;    //Col to place object
        
        //asks for what object you want to add to the board and takes a char value
        System.out.println("What kind of object would you like to place?: ");
        objectChoice = getObject();        
        
        for(int i = 0;i<amountToAdd;i++)
        {
            //places objects randomly
            rowPlace = r1.nextInt(rows);
            colPlace = r1.nextInt(cols);
            while(ocean[rowPlace][colPlace] != EMPTYSPACE)
            {
                rowPlace = r1.nextInt(rows);
                colPlace = r1.nextInt(cols);
            }
            ocean[rowPlace][colPlace] = objectChoice;           
        }
        printOcean(ocean);
    }
    
    public static void specificObject(char[][] ocean)
    {
        int choice = 0;
        int amountToAdd = 0;
        int numOfSpots = rows * cols; //Finds the number of spots left on grid, used for placing many objects
        
        //asks if you want to add one or many objects
        System.out.println("Would you like to add:\n"
                         + "[1] Just one object\n"
                         + "[2] two or more objects");
        choice = SavitchIn.readLineInt();
        while((choice < 1) || (choice > 2))
        {
            System.out.println("That is not an option");
            System.out.println("Would you like to add:\n"
                             + "[1] Just one object\n"
                             + "[2] two or more objects");
            choice = SavitchIn.readLineInt();                
        }

        //choices for one or  many objects
        if(choice == 1)   //if you want to add just one object
        {
            placeSpecificObject(ocean);
        }
        else
        {
            //asks how many objects to add
            System.out.println("How many objects would you like to add?\nThere are " + numOfSpots + " total spaces");
            amountToAdd = SavitchIn.readLineInt();
            while((amountToAdd < 0) || (amountToAdd > numOfSpots))
            {
                System.out.println("you need a number 0-" + numOfSpots + "\nHow many would you like to add?");
                amountToAdd = SavitchIn.readLineInt();
            }

            //Iterates the amount of times chosen
            for(int i = 0; i<amountToAdd;i++)
            {
                placeSpecificObject(ocean);
            }
        }
    }
    
    public static void randomObject(char[][] ocean)
    {
        int choice = 0;
        int amountToAdd = 0;
        
        //asks if you want to add one or many objects
        System.out.println("Would you like to add:\n"
                         + "[1] Just one object\n"
                         + "[2] two or more objects");
        choice = SavitchIn.readLineInt();
        while((choice < 1) || (choice > 2))
        {
            System.out.println("That is not an option");
            System.out.println("Would you like to add:\n"
                             + "[1] Just one object\n"
                             + "[2] two or more objects");
            choice = SavitchIn.readLineInt();                
        }

        if(choice == 1) //places one random object
        {
            placeRandomObject(ocean, 1);
        }
        else  //places many random objects
        {
            //asks how many objects to add
            System.out.println("How many objects would you like to add?\nThere are " + amountOfFreeSpace + " total space(s) left");
            amountToAdd = SavitchIn.readLineInt();
            while((amountToAdd < 0) || (amountToAdd > amountOfFreeSpace))
            {
                System.out.println("you need a number 0-" + amountOfFreeSpace + "\nHow many would you like to add?");
                amountToAdd = SavitchIn.readLineInt();
            }

            //uses amount to add to put that many objects in board
            if(amountToAdd > 0)
            {
                placeRandomObject(ocean, amountToAdd);
            }
        }
    }
    
    public static void addObject(char[][] ocean)
    {
        int choice = 0;               //choices within this method to where to go
        
        //asks if you want to do random or specific spots
        System.out.println("Would you like to add to:\n"
                         + "[1] Specific spot\n"
                         + "[2] Random spot");        
        choice = SavitchIn.readLineInt();
        while((choice <1) || (choice > 2))
        {
            System.out.println("That is not an option");
            System.out.println("Would you like to add to:\n"
                         + "[1] Specific spot\n"
                         + "[2] Random spot");
            choice = SavitchIn.readLineInt();
        }
        System.out.println();
        
        //choice for a specific spot
        if (choice == 1)
        {
            specificObject(ocean);            
        }
        //choice for a random spot
        else
        {
            if(amountOfFreeSpace != 0)
            {
                randomObject(ocean);                
            }
            else
            {
                System.out.println("There is no free space left, try deleting some objects first\n");
            }
        }
    }
    
    ///////////////////////////////////////-- END OF ADDING OBJECT METHODS --/////////////////////////////////////////////////////////////////////////
    
    /////////////////////////////////////-- START OF DELETING OBJECT METHODS --///////////////////////////////////////////////////////////////////////
    
    public static void removeSpecific(char[][] ocean)
    {
        int remRow = 0;    //what row to remove from
        int remCol = 0;    //What column to remove from
        
        //prints ocean for user view
        printOcean(ocean);
        
        //get the coordinates of where to remove
        System.out.println("What coordinate would you like to remove?");
        System.out.print("Row: ");
        remRow = SavitchIn.readLineInt();
        while((remRow < 1) || (remRow > rows))
        {
            System.out.println("That is not a valid row");
            System.out.println("What coordinate would you like to remove?");
            System.out.print("Row: ");
            remRow = SavitchIn.readLineInt();
        }  
        
        System.out.print("Column: ");
        remCol = SavitchIn.readLineInt();
        while((remCol < 1) || (remCol > cols))
        {
            System.out.println("That is not a valid column");
            System.out.println("What coordinate would you like to remove?");
            System.out.print("Column: ");
            remCol = SavitchIn.readLineInt();
        }
        remRow--;
        remCol--;
        
        //Removes object from specified coordinate
        if(ocean[remRow][remCol] != EMPTYSPACE)
        {
            ocean[remRow][remCol] = EMPTYSPACE;
        }
        else
        {
            System.out.println("There is nothing there...");
        }               
    }
    
    public static void removeByType(char[][] ocean)
    {
        char objectChoice = ' ';   //choice to be replaced
        int amountFound = 0;  //if the loop found a spot, it adds on for ending message
        
        //asks for what type of object to remove from ocean completely
        System.out.println("What type of object would you like to remove all of from your ocean?");
        objectChoice = getObject();
        
        //Removes all of that type of object
        for(int i = 0;i<rows;i++)
        {
            for(int j = 0;j<cols;j++)
            {
                if(ocean[i][j] == objectChoice)
                {
                    ocean[i][j] = EMPTYSPACE;
                    amountFound++;
                }
            }
        }
        
        //checks if there was any deleted, if so prints out how many were deleted
        if(amountFound > 0)
        {
            System.out.println("you deleted " + amountFound + " object(s)");
        }
        else
        {           
            System.out.println("There was none of that type of object in the ocean, try checking again");
        }
    }
    
    public static void removeObject(char[][] ocean)
    {
        int choice = 0;
        
        //Asks how you would like to remove objects
        System.out.println("How Would you like to remove objects:\n"
                         + "[1] Coordinate\n"
                         + "[2] By Type\n"
                         + "[3] Remove Everything");
        choice = SavitchIn.readLineInt();
        while((choice < 1) || (choice > 3))
        {
            System.out.println("Not a valid option try again");
            System.out.println("How Would you like to remove objects:\n"
                         + "[1] Coordinate\n"
                         + "[2] By Type\n"
                         + "[3] Remove Everything");
            choice = SavitchIn.readLineInt();
        }
        
        //All of the removal options
        switch(choice)
        {
            //Removes specific coordinate
            case 1:
                removeSpecific(ocean);
                printOcean(ocean);
                break;
                
            //Removes by object type
            case 2:
                removeByType(ocean);
                printOcean(ocean);
                break;
                
            //removes everything
            case 3:
                initOcean(ocean);
                printOcean(ocean);
                System.out.println("Everything has been cleared\n");
                break;
             
            default:
                System.out.println("This should never show up: error, removeObject()\n");
                break;
        }
    }
    
    /////////////////////////////////////-- END OF DELETING OBJECT METHODS --/////////////////////////////////////////////////////////////////////////
    
    /////////////////////////////////////////-- START OF SAVING WORLD --//////////////////////////////////////////////////////////////////////////////
    
    public static void saveWorld(char[][] ocean, String fileName)
    {              
        //Get the file name of the world
            //System.out.println("What would you like to name your world?");
            //fileName = SavitchIn.readLine();
        
        //saves the world
        try(PrintWriter writer = new PrintWriter(new File("C:\\Intel\\" + fileName + ".csv")))
        {
            StringBuilder sb = new StringBuilder();
            //sets the info about the world at the top
            sb.append(""+rows);
            sb.append(',');
            sb.append(""+cols);
            sb.append('\n');
            
            //set the actual world in csv
            for(int i = 0;i<rows;i++)
            {
                for(int j=0;j<cols;j++)
                {
                    sb.append(ocean[i][j]);
                    if(j!=(cols-1))
                    {
                        sb.append(',');
                    }                    
                }
                sb.append('\n');
            }
            
            //writes the file
            writer.write(sb.toString());
            
            System.out.println("World Saved as: " + fileName + ".csv");
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    //////////////////////////////////////////-- END OF SAVING WORLD --///////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////-- START OF LOADING WORLD --///////////////////////////////////////////////////////////////////////////////
    
    public static char[][] loadWorld(String fileName)
    {
        int j = 0;
        char [][] ocean = null;
        String[] arrOfStr;
        String[] arrOfStr2;
           
        
        BufferedReader bufferedReader = null;
        try 
        {
            String sCurrentLine;
            bufferedReader = new BufferedReader(new FileReader("C:\\Intel\\" + fileName + ".csv"));
            //reads in first line
            sCurrentLine = bufferedReader.readLine();
            //splits into array at every ','
            arrOfStr = sCurrentLine.split(",");
            //Sets the global variables
            rows = Integer.parseInt(arrOfStr[0]);
            cols = Integer.parseInt(arrOfStr[1]);
            
            //sets new ocean up
            ocean = new char[rows][cols];
            while((sCurrentLine = bufferedReader.readLine()) != null)
            {
                arrOfStr2 = sCurrentLine.split(",");
                for(int i = 0;i < arrOfStr2.length;i++)
                {
                    ocean[j][i] = arrOfStr2[i].charAt(0);
                }
                j++;
            }
        }
        catch(Exception ex)
        {
            
        }
        return ocean;
    }
    
    /////////////////////////////////////////-- END OF LOADING WORLD --///////////////////////////////////////////////////////////////////////////////
    
    /////////////////////////////////////////-- START OF TIME ADVANCE --//////////////////////////////////////////////////////////////////////////////
    
    public static char deathChance(char[][] ocean, char obj, int r, int c)
    {
        int chance = 0;   //chance for if the object will die
        
        chance = r1.nextInt(1000);
        
        if(obj == 'F')
        {
            if(chance < 50)    //5% chance
            {
                obj = EMPTYSPACE;
            }
        }
        else if(obj == 'S')   
        {
            if(chance < 100)    //10% chance
            {
                obj = EMPTYSPACE;
            }
        }
        else if(obj == 'I')
        {
            if(chance < 5)   //0.5% chance
            {
                obj = EMPTYSPACE;
            }
        }
        else if(obj == 'B')
        {
            if(chance < 5)   //0.5 chance
            {
                obj = EMPTYSPACE;
            }
        }
        else if(obj == 'J')
        {
            if(chance < 100)   //10% chance
            {
                obj = EMPTYSPACE;
            }
            else if (chance >939)
            {
                breed(ocean,obj,r,c);
            }
            else
            {
                //Nothing happens
            }
        }
        else if(obj == 'K')
        {
            if(chance < 50)    //5% chance
            {
                obj = EMPTYSPACE;
            }
        }
        else if(obj == 'L')
        {
            if(chance < 100)    //10% chance
            {
                obj = EMPTYSPACE;
            }
        }
        else if(obj == 'A')
        {
            if(chance < 50)    //5% chance
            {
                obj = EMPTYSPACE;
            }
        }
        else if(obj == 'O')
        {
            if(chance < 1)    //0.1% chance
            {
                obj = EMPTYSPACE;
            }
        }
        else
        {
            System.out.println("error in deathChance... No valid char");
        }
        
        return obj;
    }
    
    public static void breed(char[][] ocean, char obj, int r, int c)
    {
        if(r-1>=0)
        {
            if(ocean[r-1][c] == EMPTYSPACE)
            {     
                ocean[r-1][c] = obj;
                flags[r-1][c] = true;
                return;
            }
        }
        if(c+1<cols)
        {
            if(ocean[r][c+1] == EMPTYSPACE)
            {     
                ocean[r][c+1] = obj;
                flags[r][c+1] = true;
                return;
            }
        }
        if(r+1<rows)
        {
           if(ocean[r+1][c] == EMPTYSPACE)
            {    
                ocean[r+1][c] = obj;
                flags[r+1][c] = true;
                return;
            } 
        }
        if(c-1>=0)
        {
            if(ocean[r][c-1] == EMPTYSPACE)
            {   
                ocean[r][c-1] = obj;
                flags[r][c-1] = true;
                return;
            }
        }

    }
    
    public static boolean fishInteraction(char[][] ocean, char obj, int currentRow, int currentCol, int intRow, int intCol)
    {
        boolean tookSpot = false;   //if something dies 
        boolean intMove = false;    //if interaction object is able to  move, such as when being crushed by iceburg
        int intChance = 0;       //Chance for interaction       
        
        intChance = r1.nextInt(99);
        
        if(obj == 'F')
        {
            if(intChance<50)
            {
                breed(ocean,obj,intRow,intCol);
            }
        }
        else if(obj == 'S')
        {
            if(intChance < 75)
            {
                //System.out.println("fish ate");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if(obj == 'I')
        {
            intMove = iceburgMove(ocean, intRow, intCol);
            if(!intMove)
            {
                ocean[intRow][intCol] = EMPTYSPACE;
                //System.out.println("crushed");
            }
            tookSpot = true;
        }
        else if (obj == 'B')
        {
            if(intChance < 60)
            {
                //System.out.println("fish Caught!");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if (obj == 'J')
        {
            if(intChance>69)
            {
                //System.out.println("Jelly ate fish!");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
            if(intChance<25)
            {
                //System.out.println("fish ate jelly");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'K')
        {
            if(intChance < 90)
            {
                //System.out.println("fish ate");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if (obj == 'L')
        {
            if(intChance>74)
            {
                //System.out.println("fish eaten!");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
            if(intChance<50)
            {
                //System.out.println("Lobster eaten!");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'A')
        {
            if(intChance <50)
            {
                //System.out.println("fish eaten!");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else
        {
            System.out.println("error in fish Interaction");
        }
        return tookSpot;
    }
    
    public static boolean sharkInteraction(char[][] ocean, char obj, int currentRow, int currentCol, int intRow, int intCol)
    {
        boolean tookSpot = false;   //if something dies 
        boolean intMove = false;    //if interaction object is able to  move, such as when being crushed by iceburg
        int intChance = 0;       //Chance for interaction       
        
        intChance = r1.nextInt(99);
        
        if(obj == 'F')
        {
            if(intChance<50)   //50% chance
            {
                //System.out.println("Shark ate fish");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if(obj == 'S')
        {
            if(intChance<50)
            {
                breed(ocean,obj,intRow,intCol);
            }
        }
        else if(obj == 'I')
        {
            intMove = iceburgMove(ocean, intRow, intCol);
            if(!intMove)
            {
                ocean[intRow][intCol] = EMPTYSPACE;
                //System.out.println("crushed");
            }
            tookSpot = true;
        }
        else if (obj == 'B')
        {
            if(intChance>89)
            {
                //System.out.println("Shark Caught!");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
            if(intChance<5)
            {
                //System.out.println("Boat flipped!");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'J')
        {
            if(intChance <30)
            {
                //System.out.println("Jelly ate");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'K')
        {
            if(intChance <60)
            {
                //System.out.println("shark ate");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if (obj == 'L')
        {
            if(intChance <10)
            {
                //System.out.println("Lob ate");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'A')
        {
            if(intChance <20)
            {
                //System.out.println("bird ate");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else
        {
            System.out.println("error in Shark Interaction");
        }
        return tookSpot;
    }
    
    public static boolean iceburgInteraction(char[][] ocean, char obj, int currentRow, int currentCol, int intRow, int intCol)
    {
        boolean tookSpot = false;
        int intChance = 0;       //Chance for interaction       
        
        intChance = r1.nextInt(99);
        
        if(obj == 'I')
        {
            if(intChance > 49)
            {
                //System.out.println("iceburg breaks on iceburg");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
            else if(intChance < 25)
            {
                breed(ocean,obj,intRow,intCol);
            }
        }
        else if(obj == 'B')
        {
            if(intChance < 25)
            {
                //System.out.println("boat sinks on iceburg");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else
        {
            if((obj != 'F') && (obj != 'S') && (obj != 'J') && (obj != 'K') && (obj != 'L') && (obj != 'A'))
            {
                System.out.println(obj + " tried to move on the the iceburg!!");
            }
        }
        
        return tookSpot;
    }
    
    public static boolean boatInteraction(char[][] ocean, char obj, int currentRow, int currentCol, int intRow, int intCol)
    {
        boolean tookSpot = false;
        
        boolean intMove = false;    //if interaction object is able to  move, such as when being crushed by iceburg
        int intChance = 0;       //Chance for interaction       
        
        intChance = r1.nextInt(99);
        
        if(obj == 'F')
        {
            if(intChance<50)   //50% chance
            {
                //System.out.println("Fish Caught");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if(obj == 'S')
        {
            if(intChance>89)
            {
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
            if(intChance < 15)
            {
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if(obj == 'I')
        {
            intMove = iceburgMove(ocean, intRow, intCol);
            if(!intMove)
            {
                ocean[intRow][intCol] = EMPTYSPACE;
                //System.out.println("crushed");
            }
            tookSpot = true;
        }
        else if (obj == 'B')
        {
            if(intChance>74)
            {
                //System.out.println("boat sank!");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
            if(intChance<25)
            {
                //System.out.println("both boats sank!");
                ocean[currentRow][currentCol] = EMPTYSPACE;
                ocean[intRow][intCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'J')
        {
            if(intChance <10)
            {
                //System.out.println("Jelly caught");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'K')
        {
            if(intChance <98)
            {
                //System.out.println("kraken flip boat");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if (obj == 'L')
        {
            if(intChance <15)
            {
                //System.out.println("Lob caught");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'A')
        {
            if(intChance <5)
            {
                //System.out.println("bird shot");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else
        {
            System.out.println("error in boat Interaction");
        }
        return tookSpot;
    }
    
    public static boolean rockInteraction(char[][] ocean, char obj, int currentRow, int currentCol, int intRow, int intCol)
    {
        boolean tookSpot = false;
        
        boolean intMove = false;    //if interaction object is able to  move, such as when being crushed by iceburg
        int intChance = 0;       //Chance for interaction       
        
        intChance = r1.nextInt(99);
        
        if(obj == 'I')
        {
            if(intChance>89)
            {
                //System.out.println("Rock Broke!");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
            if(intChance<25)
            {
                breed(ocean,obj,intRow,intCol);
            }
        }
        else if (obj == 'B')
        {
            if(intChance<25)
            {
                //System.out.println("boat sank!");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else
        {
            if((obj != 'F') && (obj != 'S') && (obj != 'J') && (obj != 'K') && (obj != 'L') && (obj != 'A'))
            {
                System.out.println(obj + " tried to move on the the rock!!");
            }
        }
        return tookSpot;
    }
    
    public static boolean jellyInteraction(char[][] ocean, char obj, int currentRow, int currentCol, int intRow, int intCol)
    {
        boolean tookSpot = false;
        
        boolean intMove = false;    //if interaction object is able to  move, such as when being crushed by iceburg
        int intChance = 0;       //Chance for interaction       
        
        intChance = r1.nextInt(99);
        
        if(obj == 'F')
        {
            if(intChance>79)
            {
                //System.out.println("Fish eaten");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
            if(intChance<25)
            {
                //System.out.println("Jelly Eaten");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if(obj == 'S')
        {
            if(intChance<75)
            {
                //System.out.println("Shark ate jelly");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if(obj == 'I')
        {
            intMove = iceburgMove(ocean, intRow, intCol);
            if(!intMove)
            {
                ocean[intRow][intCol] = EMPTYSPACE;
                //System.out.println("crushed");
            }
            tookSpot = true;
        }
        else if (obj == 'B')
        {
            if(intChance<10)
            {
                //System.out.println("jelly Caught");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if (obj == 'K')
        {
            if(intChance <98)
            {
                //System.out.println("kraken Eat jelly");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if (obj == 'L')
        {
            if(intChance <15)
            {
                //System.out.println("Lob killed");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else
        {
            if((obj != 'A') && (obj != 'J'))
            {
                System.out.println(obj + " tried to move on the the jelly!!");
            }
        }
        return tookSpot;
    }
    
    public static boolean krakenInteraction(char[][] ocean, char obj, int currentRow, int currentCol, int intRow, int intCol)
    {
        boolean tookSpot = false;
        
        boolean intMove = false;    //if interaction object is able to  move, such as when being crushed by iceburg
        int intChance = 0;       //Chance for interaction       
        
        intChance = r1.nextInt(99);
        
        if(obj == 'F')
        {
            if(intChance<90)
            {
                //System.out.println("Fish eaten");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if(obj == 'S')
        {
            if(intChance<50)
            {
                //System.out.println("Shark dies");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if(obj == 'I')
        {
            intMove = iceburgMove(ocean, intRow, intCol);
            if(!intMove)
            {
                ocean[intRow][intCol] = EMPTYSPACE;
                //System.out.println("Kraken crushed");
            }
            tookSpot = true;
        }
        else if (obj == 'B')
        {
            if(intChance<80)
            {
                //System.out.println("Boat Flipped");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'J')
        {
            if(intChance<80)
            {
                //System.out.println("Jelly ate");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'K')
        {
            if(intChance<10)
            {
                breed(ocean,obj,intRow,intCol);
            }
        }
        else if (obj == 'L')
        {
            if(intChance <60)
            {
                //System.out.println("Lob killed");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'A')
        {
            if(intChance <80)
            {
                //System.out.println("bird killed");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else
        {
            System.out.println("error in kraken Interaction");
        }
        return tookSpot;
    }
    
    public static boolean lobsterInteraction(char[][] ocean, char obj, int currentRow, int currentCol, int intRow, int intCol)
    {
        boolean tookSpot = false;
        
        boolean intMove = false;    //if interaction object is able to  move, such as when being crushed by iceburg
        int intChance = 0;       //Chance for interaction       
        
        intChance = r1.nextInt(99);
        
        if(obj == 'S')
        {
            if(intChance<30)
            {
                //System.out.println("lob ate");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if(obj == 'I')
        {
            intMove = iceburgMove(ocean, intRow, intCol);
            if(!intMove)
            {
                ocean[intRow][intCol] = EMPTYSPACE;
                //System.out.println("crushed");
            }
            tookSpot = true;
        }
        else if (obj == 'B')
        {
            if(intChance<25)
            {
                //System.out.println("lob caught");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if (obj == 'K')
        {
            if(intChance<80)
            {
                //System.out.println("lob ate");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if (obj == 'L')
        {
            if(intChance<50)
            {
                breed(ocean,obj,intRow,intCol);
            }
        }
        else
        {
            if((obj != 'F')&&(obj != 'J') && (obj != 'A'))
            {
                System.out.println(obj + " tried to move on the the Lobster!!");
            }
        }
        return tookSpot;
    }
    
    public static boolean birdInteraction(char[][] ocean, char obj, int currentRow, int currentCol, int intRow, int intCol)
    {
        boolean tookSpot = false;
        
        boolean intMove = false;    //if interaction object is able to  move, such as when being crushed by iceburg
        int intChance = 0;       //Chance for interaction       
        
        intChance = r1.nextInt(99);
        
        if(obj == 'F')
        {
            if(intChance<25)
            {
                //System.out.println("lob ate");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if(obj == 'S')
        {
            if(intChance<25)
            {
                //System.out.println("lob ate");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if(obj == 'I')
        {
            intMove = iceburgMove(ocean, intRow, intCol);
            if(!intMove)
            {
                ocean[intRow][intCol] = EMPTYSPACE;
                //System.out.println("crushed");
            }
            tookSpot = true;
        }
        else if (obj == 'B')
        {
            if(intChance<5)
            {
                //System.out.println("Bird shot!");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if (obj == 'J')
        {
            if(intChance<25)
            {
                //System.out.println("jelly ate");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
        }
        else if (obj == 'K')
        {
            if(intChance<80)
            {
                //System.out.println("bird ate");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
        }
        else if (obj == 'A')
        {
            if(intChance<35)
            {
                breed(ocean,obj,intRow,intCol);
            }
        }
        else
        {
            if(obj != 'L')
            {
                System.out.println(obj + " tried to move on the the bird!!");
            }
        }
        return tookSpot;
    }
    
    public static boolean oilrigInteraction(char[][] ocean, char obj, int currentRow, int currentCol, int intRow, int intCol)
    {
        boolean tookSpot = false;
        
        boolean intMove = false;    //if interaction object is able to  move, such as when being crushed by iceburg
        int intChance = 0;       //Chance for interaction       
        
        intChance = r1.nextInt(99);
        
        if(obj == 'I')
        {
            intMove = iceburgMove(ocean, intRow, intCol);
            if(!intMove)
            {
                ocean[intRow][intCol] = EMPTYSPACE;
                //System.out.println("crushed");
            }
            tookSpot = true;
        }
        else if (obj == 'B')
        {
            if(intChance<25)
            {
                //System.out.println("boat sank!");
                ocean[currentRow][currentCol] = EMPTYSPACE;
            }
            else if(intChance>74)
            {
                //System.out.println("oil rig blows!");
                ocean[intRow][intCol] = EMPTYSPACE;
                tookSpot = true;
            }
            else if((intChance >49) && (intChance < 75))
            {
                //System.out.println("Both blow!");
                ocean[currentRow][currentCol] = EMPTYSPACE;
                ocean[intRow][intCol] = EMPTYSPACE;
            }
            else
            {
                //Nothing happens
            }
        }
        else if (obj == 'K')
        {
            if(intChance<98)
            {
                System.out.println("oil Rig Attacked! Kraken dies too! Oil Spill!");
                ocean[intRow][intCol] = DEADRIG;
                ocean[currentRow][currentCol] = EMPTYSPACE;             
            }
        }
        else
        {
            if((obj != 'F') && (obj != 'S')&&(obj != 'J')&&(obj != 'L')&&(obj != 'A'))
            {
                System.out.println(obj + " tried to move on the the oilrig!!");
            }
        }
        return tookSpot;
    }
    
    public static boolean interact(char[][] ocean, int currentR, int currentC, int objR, int objC)   //Current object positions and what it is running onto
    {
        //LET OFF HERE FIGURE OUT INTERACTIONS, MAKE 10 METHODS FOR EACH OBJECT
        //UPDATE ALL THE MOVES ONCE YOU KNOW UP WORKS
        char obj = ocean[currentR][currentC];  //The object that is making the move
        char targetObj = ocean[objR][objC];  
        boolean canMove = false;
        
        if(targetObj == 'F')     //target Object is the object that does not move, but something moves onto it
        {
            canMove = fishInteraction(ocean, obj, currentR, currentC, objR, objC);
        }
        else if(targetObj == 'S')   
        {
            canMove = sharkInteraction(ocean, obj, currentR, currentC, objR, objC);
        }
        else if(targetObj == 'I')
        {
            canMove = iceburgInteraction(ocean, obj, currentR, currentC, objR, objC);
        }
        else if(targetObj == 'B')
        {
            canMove = boatInteraction(ocean, obj, currentR, currentC, objR, objC);
        }
        else if(targetObj == 'R')
        {
            canMove = rockInteraction(ocean, obj, currentR, currentC, objR, objC);
        }
        else if(targetObj == 'J')
        {
            canMove = jellyInteraction(ocean, obj, currentR, currentC, objR, objC);
        }
        else if(targetObj == 'K')
        {
            canMove = krakenInteraction(ocean, obj, currentR, currentC, objR, objC);
        }
        else if(targetObj == 'L')
        {
            canMove = lobsterInteraction(ocean, obj, currentR, currentC, objR, objC);
        }
        else if(targetObj == 'A')
        {
            canMove = birdInteraction(ocean, obj, currentR, currentC, objR, objC);
        }
        else if(targetObj == 'O')
        {
            canMove = oilrigInteraction(ocean, obj, currentR, currentC, objR, objC);
        }
        else
        {
            System.out.println("error in Interactions... No valid char");
        }
        return canMove;
    }
    
    public static boolean iceburgMove(char[][] ocean, int r, int c)   //What an object does whenever an iceburg tries to crush it
    {
        boolean canMove = true;
        
        if((r-1>=0) && (ocean[r-1][c] == EMPTYSPACE))    //moves up
        {
            ocean[r-1][c] = ocean[r][c];
            ocean[r][c] = EMPTYSPACE; 
        }
        else if((c+1 < cols) && ocean[r][c+1] == EMPTYSPACE)   // moves right
        {
            ocean[r][c+1] = ocean[r][c];
            ocean[r][c] = EMPTYSPACE;
        }
        else if((r+1 < rows) && ocean[r+1][c] == EMPTYSPACE)   // moves down
        {
            ocean[r+1][c] = ocean[r][c];
            ocean[r][c] = EMPTYSPACE;
        }
        else if((c-1 >=0) && ocean[r][c-1] == EMPTYSPACE)   // moves Left
        {
            ocean[r][c-1] = ocean[r][c];
            ocean[r][c] = EMPTYSPACE;
        }
        else
        {
            canMove = false;
        } 
        
        return canMove;
    }
    
    public static boolean moveUp(char[][] ocean, int r, int c)
    {
        boolean canMove = false;    //if it can move and is not obstructed
        boolean tookSpot = false;
        //System.out.println("Moved up" + ocean[r][c]);
        if(r-1>=0)
        {
            if(ocean[r-1][c] == EMPTYSPACE)
            {                
                canMove = true;
                tookSpot = true;
            }
            else
            {
                tookSpot = interact(ocean,r,c,r-1,c);       //if it can take its spot or not
                canMove = true;                             //it can move so it will not try another interaction
            }
        }
        else  //if it tried to move off edge of ocean
        {
            //System.out.println("tried to move off");
        }
        
        //If something moved to that spot it will mark it done
        //System.out.println(tookSpot + "" + ocean[r][c]);
        if(tookSpot)
        {           
            ocean[r-1][c] = ocean[r][c];
            ocean[r][c] = EMPTYSPACE;
            flags[r-1][c] = true;   //sets this flag to true so it cannot be repeated
        }
        return canMove;
    }
    
    public static boolean moveRight(char[][] ocean, int r, int c)
    {
        boolean canMove = false;    //if it can move and is not obstructed
        boolean tookSpot = false;
        //System.out.println("Moved Right" + ocean[r][c]);
        if(c+1<cols)
        {
            if(ocean[r][c+1] == EMPTYSPACE)
            {                
                canMove = true;
                tookSpot = true;
            }
            else
            {
                tookSpot = interact(ocean,r,c,r,c+1);       //if it can take its spot or not
                canMove = true;                             //it can move so it will not try another interaction
            }
        }
        else  //if it tried to move off edge of ocean
        {
            //System.out.println("tried to move off");
        }
        
        //If something moved to that spot it will mark it done
        //System.out.println(tookSpot + "" + ocean[r][c]);
        if(tookSpot)
        {           
            ocean[r][c+1] = ocean[r][c];
            ocean[r][c] = EMPTYSPACE;
            flags[r][c+1] = true;   //sets this flag to true so it cannot be repeated
        }
        return canMove;
    }
    
    public static boolean moveDown(char[][] ocean, int r, int c)
    {
        boolean canMove = false;    //if it can move and is not obstructed
        boolean tookSpot = false;
        //System.out.println("Moved down" + ocean[r][c]);
        if(r+1<rows)
        {
            if(ocean[r+1][c] == EMPTYSPACE)
            {                
                canMove = true;
                tookSpot = true;
            }
            else
            {
                tookSpot = interact(ocean,r,c,r+1,c);       //if it can take its spot or not
                canMove = true;                             //it can move so it will not try another interaction
            }
        }
        else  //if it tried to move off edge of ocean
        {
            //System.out.println("tried to move off");
        }
        
        //If something moved to that spot it will mark it done
        //System.out.println(tookSpot + "" + ocean[r][c]);
        if(tookSpot)
        {           
            ocean[r+1][c] = ocean[r][c];
            ocean[r][c] = EMPTYSPACE;
            flags[r+1][c] = true;   //sets this flag to true so it cannot be repeated
        }
        return canMove;
    }
    
    public static boolean moveLeft(char[][] ocean, int r, int c)
    {
        boolean canMove = false;    //if it can move and is not obstructed
        boolean tookSpot = false;
        //System.out.println("Moved left" + ocean[r][c]);
        if(c-1>=0)
        {
            if(ocean[r][c-1] == EMPTYSPACE)
            {                
                canMove = true;
                tookSpot = true;
            }
            else
            {
                tookSpot = interact(ocean,r,c,r,c-1);       //if it can take its spot or not
                canMove = true;                             //it can move so it will not try another interaction
            }
        }
        else  //if it tried to move off edge of ocean
        {
            //System.out.println("tried to move off");
        }
        
        //If something moved to that spot it will mark it done
        //System.out.println(tookSpot + "" + ocean[r][c]);
        if(tookSpot)
        {           
            ocean[r][c-1] = ocean[r][c];
            ocean[r][c] = EMPTYSPACE;
            flags[r][c-1] = true;   //sets this flag to true so it cannot be repeated
        }
        return canMove;
    }
    
    public static boolean moveObject(char[][] ocean, int r, int c)
    {
        int moveSpot = 0;   //Number 0-3 which will determine which spot it will move around it
        boolean canMove = false;   //find out if object can move or not  //possibly use later to log info about ocean if user chooses, but no use as of yet
        
        moveSpot = r1.nextInt(4);    //random number 0-3
        moveSpot = 0; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        if(moveSpot == 0)   //Up
        {
            canMove = moveUp(ocean, r, c);
            if(!canMove)
            {
                //System.out.println("1");
                canMove = moveRight(ocean, r, c);
            }
            if(!canMove)
            {
                //System.out.println("2");
                canMove = moveDown(ocean, r, c);                  //cycles through every possible move
            }
            if(!canMove)
            {
                //System.out.println("3");
                canMove = moveLeft(ocean, r, c);
            }
        }
        else if(moveSpot == 1)  //Right
        {
            canMove = moveRight(ocean, r, c);
            if(!canMove)
            {
                //System.out.println("4");
                canMove = moveDown(ocean, r, c);
            }
            if(!canMove)
            {
                //System.out.println("5");
                canMove = moveLeft(ocean, r, c);
            }
            if(!canMove)
            {
                //System.out.println("6");
                canMove = moveUp(ocean, r, c);
            }
        }
        else if(moveSpot == 2)  //Down
        {
            canMove = moveDown(ocean, r, c);
            if(!canMove)
            {
                //System.out.println("7");
                canMove = moveLeft(ocean, r, c);
            }
            if(!canMove)
            {
                //System.out.println("8");
                canMove = moveUp(ocean, r, c);
            }
            if(!canMove)
            {
                //System.out.println("9");
                canMove = moveRight(ocean, r, c);
            }
        }
        else if(moveSpot == 3)  //Left
        {
            canMove = moveLeft(ocean, r, c);
            if(!canMove)
            {
                //System.out.println("10");
                canMove = moveUp(ocean, r, c);
            }
            if(!canMove)
            {
                //System.out.println("11");
                canMove = moveRight(ocean, r, c);
            }
            if(!canMove)
            {
                //System.out.println("12");
                canMove = moveDown(ocean, r, c);
            }
        }
        else
        {
            System.out.println("Error: moveObject");
        }
        return canMove;
    }
    
    public static void moveChance(char[][] ocean, char obj, int r, int c)
    {
        int chance = 0;   //chance for if the object will move
        boolean willMove = false;   //if flipped it will enter into the moveObject method
        
        //System.out.println("MoveChance");
        chance = r1.nextInt(100);   //random number 0-99       
        
        if(obj == 'F')
        {
            if(chance < 50)    //50% chance
            {
                willMove = true;
            }
        }
        else if(obj == 'S')   
        {
            if(chance < 50)    //50% chance
            {
                willMove = true;
            }
        }
        else if(obj == 'I')
        {
            if(chance < 5)    //5% chance
            {
                willMove = true;
            }
        }
        else if(obj == 'B')
        {
            if(chance < 80)   //80% chance
            {
                willMove = true;
            }
        }
        else if(obj == 'J')
        {
            if(chance < 15)   //15% chance
            {
                willMove = true;
            }
        }
        else if(obj == 'K')
        {
            if(chance < 85)    //85% chance
            {
                willMove = true;
            }
        }
        else if(obj == 'L')
        {
            if(chance < 10)    //10% chance
            {
                willMove = true;
            }
        }
        else if(obj == 'A')
        {
            if(chance < 90)    //90% chance
            {
                willMove = true;
            }
        }
        else
        {
            System.out.println("error in moveChance... No valid char");
            System.out.println(obj);
        }
        
        //Method for moving
        if(willMove)
        {
            moveObject(ocean,r,c);
        }
    }
    
    public static void oilSpill(char[][] ocean)
    {
        flags = new boolean[rows][cols];    //to make it know when to stop have it count how many spots are left until 5 spots out once it reaches that spot erase it all
        
        for(int i =0;i<rows;i++)
        {
            for(int j = 0;j<cols;j++)
            {
                if((ocean[i][j] == DEADRIG) || (ocean[i][j] == SPILL))
                {                 
                    if(flags[i][j] != true)
                    {
                        if(i-1>0)                       //above
                        {
                            ocean[i-1][j] = SPILL;
                            flags[i-1][j] = true;
                        }
                        if((i-1>0) && (j+1 < cols))     //above to right
                        {
                            ocean[i-1][j+1] = SPILL;
                            flags[i-1][j+1] = true;
                        }
                        if(j+1 < cols)
                        {
                            ocean[i][j+1] = SPILL;       //right
                            flags[i][j+1] = true;
                        }
                        if((i+1 < rows) && (j+1 < cols)) //down to right
                        {
                            ocean[i+1][j+1] = SPILL;
                            flags[i+1][j+1] = true;
                        }
                        if(i+1 < rows)                  //down
                        {
                            ocean[i+1][j] = SPILL;
                            flags[i+1][j] = true;
                        }
                        if((i+1< rows) && (j-1>0))
                        {
                            ocean[i+1][j-1] = SPILL;    //down to left
                            flags[i+1][j-1] = true;
                        }
                        if(j-1>0)
                        {
                            ocean[i][j-1] = SPILL;     //left
                            flags[1][j-1] = true;
                        }
                        if((i-1 > 0) && (j-1 > 0))
                        {
                            ocean[i-1][j-1] = SPILL;
                            flags[i-1][j-1] = true;
                        }
                    }
                }
            }
        }
    }
    
    public static void timeAdvance(char[][] ocean)
    {
        int amountOfTicks = 0;
        
        System.out.print("How many cycles would you like the ocean to run through: ");
        amountOfTicks = SavitchIn.readLineInt();      
        
        for(int k = 0;k<amountOfTicks;k++)
        {  
            flags = new boolean[rows][cols];    //setting up flag array   //Starts here to keep it in scope of current tick
            
            for(int i = 0;i<rows;i++)
            {                
                for(int j = 0;j<cols;j++)
                {
                    if(flags[i][j] != true)     //if a spot has already had a turn it cannot go again this will prevent that
                    {
                        //look for an object in the ocean, if it finds one modify it
                        if((ocean[i][j] != EMPTYSPACE) && (ocean[i][j] != 'R') && (ocean[i][j] != DEADRIG) && (ocean[i][j] != SPILL))  //Rocks cant die so it wont even go in the method
                        {                           
                            //Method for dying
                            ocean[i][j] = deathChance(ocean, ocean[i][j], i, j);                                   
                        }
                        
                        //Here again because deathChance could produce an emptySpace character within its own loop
                        if((ocean[i][j] != EMPTYSPACE) && (ocean[i][j] != 'O') && (ocean[i][j] != 'R') && (ocean[i][j] != DEADRIG) && (ocean[i][j] != SPILL))  //Rocks and oil rigs cannot move
                        {
                            //Method for moving                       
                            moveChance(ocean, ocean[i][j], i, j);
                        }                                             
                        flags[i][j] = true;    //This spot has made its turn
                    }
                }
            }
            oilSpill(ocean);
        }
        printOcean(ocean);
    }
    
    //////////////////////////////////////////-- END OF TIME ADVANCE --//////////////////////////////////////////////////////////////////////////////
    
    public static void runOceanSim()
    {        
        int choice = 0;  //choice for the switch board
        int amountOfSpaces = 0;
        boolean endFlag = false;   //flag to end the program in the switch board
        boolean startLoadWorld = false;
        boolean newWorld = false;
        String fileName = " ";
        char[][] ocean = null;
        
        printDirections();
        
        //switch board to save or load a world        
        while(!startLoadWorld)
        {
            System.out.println("Would you like to:\n[1] Start a New World\n[2] Load an Existing World");
            choice = SavitchIn.readLineInt();
            switch(choice)
            {
                case 1:
                    newWorld = true;
                    startLoadWorld = true;
                    break;
                case 2:
                    //Ask what world you would like to load
                    System.out.println("What World would you like to load?\nPress [Enter] to Load the Default World");
                    fileName = SavitchIn.readLine();
                    //check for the default world if chosen
                    if(fileName.equals(""))
                    {
                        fileName = "Default";
                    }
                    
                    ocean = loadWorld(fileName);
                    
                    //if ocean is set it will leave the while loop
                    if(ocean != null)
                    {
                        startLoadWorld = true;
                    }
                    else
                    {
                        System.out.println("World " + fileName + " does not exist, try again\n");
                    }
                    break;
                default:
                    System.out.println("Not a valid choice try again");
                    break;
            }
        }
        //only runs when you choose to create a new world
        if(newWorld == true)
        {
            //setting ocean size and creates ocean and names it
            System.out.println("What would you like to name your world?\nTo Set Default World press [Enter] only");
            fileName = SavitchIn.readLine();
            //if enter is pressed sets the name as the default world
            if(fileName.equals(""))
            {
                fileName = "Default";
            }
            
            System.out.print("How large would you like your world?\nRows: ");
            rows= SavitchIn.readLineInt();
            System.out.print("Columns: ");
            cols= SavitchIn.readLineInt(); 
            amountOfSpaces = rows * cols;
            ocean = new char [rows][cols];
            initOcean(ocean);                   //initilizes board
        }
        printOcean(ocean);

        //Switch board to run main program
        while(!endFlag)
        {
            findSpace(ocean);
            
            System.out.println("What would you like to do with your world?\n"
                              +"[1] Advance Time\n"
                              +"[2] Add an Object\n"
                              +"[3] Remove an Object\n"
                              +"[4] Save World\n"
                              +"[5] Load World\n"
                              +"[6] Exit");
            System.out.println("Free space " + amountOfFreeSpace);
            choice = SavitchIn.readLineInt();
            switch(choice)
            {
                case 1:
                    timeAdvance(ocean);
                    break;
                case 2:
                    addObject(ocean);
                    break;
                case 3:
                    if(amountOfSpaces != amountOfFreeSpace)
                    {
                        removeObject(ocean);
                    }
                    else
                    {
                        System.out.println("There are no objects to remove\n");
                    }
                    break;
                case 4:
                    saveWorld(ocean,fileName);
                    break;
                case 5:
                    System.out.println("What World would you like to load?\nPress [Enter] only to load the Default World");
                    fileName = SavitchIn.readLine();
                    if(fileName.equals(""))
                    {
                        fileName = "Default";
                    }
                    ocean = loadWorld(fileName);
                    
                    printOcean(ocean);
                    break;
                case 6:
                    endFlag = true;
                    break;
                default:
                    System.out.println("Please choose a valid option\n");
                    break;
            }
        }
        System.out.println("\nYour final world looked like:");
        printOcean(ocean);
    }

    public static void main(String[] args) 
    {
        r1 = new Random();
        runOceanSim();
    }
    
}
