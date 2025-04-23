import java.util.Scanner;
public class Comms {
    //declares and initializes a Scanner called inputToCode
    private Scanner inputToCode = new Scanner(System.in);

    public String textConsoleInput(){//reads console and saves users input into a string variable, then returns the string variable
        String ci= inputToCode.nextLine();
        return ci;
    }

    public int numConsoleInput(){//reads console and saves users input into an int variable, then returns the int variable
        String input= inputToCode.nextLine();
        int ci= Integer.parseInt(input);
        return ci;
    }

    public boolean boolConsoleInput(){//reads console and saves users input into a boolean variable, then returns true or false
        String input= inputToCode.nextLine();
        if(input.equalsIgnoreCase("True")){
            return true;
        } else{
            return false;
        }
    }
//All of these user inputs are returned but they must be saved in to variable before logic can be applied to them
    
    public void consoleOutput(String x){//A better method to print to console
        System.out.println(x);
    }
    
}
