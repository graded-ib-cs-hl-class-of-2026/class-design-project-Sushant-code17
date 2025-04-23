//All the necessary files that needed to be imported
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class FileInputOutput {
//varibles that I am using in this method to convert file into book item
    private File file;//declares a file
    private Scanner reader;//declares a reader
    private PrintStream fileWriter;//declares a writer
    private File outputFile;//declares output file, writes back to the same file, just used to create a logical divide
    private Comms comms= new Comms();//creates a new Comms object called comms




    public void openFile(String filename) throws FileNotFoundException {//Opens file
            file = new File(filename);
            reader = new Scanner(file);
    }

    public void closeFile(){//Closes file
        reader.close();
    }

    public boolean checkNextLine(){//Checks whether the file has a next line
      if(reader.hasNextLine()==true){
        return true;
        } else{
            return false;
        }
    }

    public String returnNextLine(){//returns the next line of the file
        return reader.nextLine();
    }

//Used to count the number of lines in a file
    public int numlines(){
        int x=0;
        while (reader.hasNextLine()){
            reader.nextLine(); //This moves forward to the next line, ensuring we are not stuck in a never ending loop.
            x++; 
        }
        int numlin=x;
        return numlin;
    }

   
        
     public void openWriter(String filename) {//Opens writer which will write to file
        try {
            outputFile = new File(filename);
            fileWriter = new PrintStream(outputFile);
        }catch (Exception e) {
            comms.consoleOutput("Output file was not opened - error " + e.toString());
            outputFile = null;
            fileWriter = null;
        }
    }


    public void writeInFile(String s){//does the writing in the file
        fileWriter.print(s);//writes the string inputed as the parameter in the file
    }

    public void newLineInFile(){
        fileWriter.println();//Prints a line break in the file
    }


     public void closeWriter() {//closes writer
        fileWriter.close();
        outputFile = null;//file set to null
        fileWriter = null;//writer set to null
    }


}
