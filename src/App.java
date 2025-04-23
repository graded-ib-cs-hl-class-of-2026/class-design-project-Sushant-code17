import java.io.FileNotFoundException;

public class App {

    private FileInputOutput io = new FileInputOutput();
    private Book[] books; //An array of book objects
    private Library library; //initializing a library object so that you can use library methods 
    private String[] fileInput; //Adding 
    private Comms comms= new Comms();
    private String fileName;
    private boolean borrowedYN; //Set this variable to true if user responded yes to the question "Is the book borrowed"
    private int a=5; //Used to add a count down feature in the addBooks() method, needed something that was permanent outside the method, but dynamic with the program
    private String File1;//Adds an extra file slot so that you can add a file, and effectively have 2 libraries
     
 
/*Main only runs start, and start runs everything else */
    public static void main(String[] args) throws Exception {
        App app= new App();
        app.start();
    }



    public void start(){ //calls methods in the order that you want them to happen
        startInstructions(); //Gives starting instructions
        fileToBooks();//Converts the added file to books
        menu(); //Lets users manipulate and change books
        booksToFile(); //Saves changes back to file to create a permanent program(Program in which changes persist even after the program is closed)
     }
    
       

    public void fileToBooks(){ //All the logic behind how information is read from a file and converted into multiple book objects and a library object
        int arrSize;//Here I declare my integer value that will be responsible for deciding the size of the array
        try { //try catch method, checks whether the file actually exists before running the following operations
            io.openFile(fileName);//opens file
            arrSize = io.numlines();//saves the number of lines in the file into an array, this ensures that the array isn't bigger than it needs to be
            io.closeFile();//closes the file, so that when the program reopens the file it reads from the start
            io.openFile(fileName); // Reopen to reset scanner, and read from the start
            
            books = new Book[arrSize+5]; //Makes the array of books as big as arrSize+5, it is +5 to add 5 extra book slots
            // ... rest of your code ...
        } catch (FileNotFoundException e) {//catch part of the try catch method
            comms.consoleOutput("File not found, retry");//prints this if file not found
            startInstructions();//reruns the start instructions so that user can add a file to that actually exists
            return;
        }

        library = new Library(books); //initializes the previously declared library objects, done here as now there is an actual array
        fileInput = new String[books.length * 4]; /*initializes a previously declared array, 
        called file input. This is where all the information from the file will be saved into a big array which 
        be picked and broken up in to other arrays */
        String[] author= new String[arrSize]; //declares and initializes an array that holds all the authors mentioned in the file
        String[] titleOfBooks = new String[arrSize]; //declares and initializes an array that holds all titles mentioned in the file
        String [] borrowed= new String[arrSize]; //declares and initializes an array that holds string values of whether a book is borrowed
        String [] borNames= new String[arrSize]; //declares and initializes an array that holds the names of all borrowers mentioned in the file
        
        int o=0; //initializes and declares a variable that will loop through the while loop
        
        while (io.checkNextLine()){ //if the file has a next line loop
            String line= io.returnNextLine(); //next line is saves into a temporary string, line
            String[] tempArr=line.split(";"); //the line string is broken down using split string method, broken down parts are saved into an array
            String w= tempArr[0];//values from tempArr are assigned to temporary strings
            String x= tempArr[1];
            String y= tempArr[2];
            String z= tempArr[3];

            titleOfBooks[o]=w;//temporary strings are saved in arrays, at progressively increasing index values
            author[o]=x;
            borrowed[o]=y;
            borNames[o]=z;
            o++;//value increases
            }


        boolean[] borrowedBool = new boolean[arrSize]; //converts the borrowed String array into a boolean array so that it can be used to create book objects
        for(int i=0; i<borrowed.length; i++){
            if(borrowed[i].equalsIgnoreCase("True")){//ignore case is used to make the program more user friendly
                borrowedBool[i]=true;
            }else if(borrowed[i].equalsIgnoreCase("False")){
                borrowedBool[i]=false;
            }
        }

            for(int j=0; j< arrSize; j++){ //for loop going through all of the different attributes array to create book objects
            //these objects are then added to the book array using a library method called append, which adds book objects to the last available slot in the array
             library.append(new Book(titleOfBooks[j], author[j], borNames[j], borrowedBool[j]));
            }
        }//end fileToBook() method



    public void startInstructions(){
       
                        comms.consoleOutput(
                            "| $$       /$$ /$$                                               \n" +
                            "| $$      |__/| $$                                               \n" +
                            "| $$       /$$| $$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$  /$$   /$$ \n" +
                            "| $$      | $$| $$__  $$ /$$__  $$ |____  $$ /$$__  $$| $$  | $$ \n" +
                            "| $$      | $$| $$  \\ $$| $$  \\__/  /$$$$$$$| $$  \\__/| $$  | $$ \n" +
                            "| $$      | $$| $$  | $$| $$       /$$__  $$| $$      | $$  | $$ \n" +
                            "| $$$$$$$$| $$| $$$$$$$/| $$      |  $$$$$$$| $$      |  $$$$$$$ \n" +
                            "|________/|__/|_______/ |__/       \\_______/|__/       \\____  $$ \n" +
                            "                                                       /$$  | $$ \n" +
                            "                                                      |  $$$$$$/ \n" +
                            "                                                       \\______/  "
                        );//prints out ascii art title for the program
           

        comms.consoleOutput("Welcome to your Library. To begin you need to either create a new .txt file or you can use the pre-existing file BookList.txt(Recommended option).");
        comms.consoleOutput("");
        comms.consoleOutput("If however you still want to create a file, The formatting must be as follows:");
        comms.consoleOutput("");
        comms.consoleOutput("Book name;Author name;true/false;borrower name;");
        comms.consoleOutput("There should be no spaces before or after the semi-colons.");
        comms.consoleOutput("true/false is used to specify whether the book has been borrowed or not");
        comms.consoleOutput("If it hasnt been borrowed, there won't be a borrower name, in that case just write N/A");
        comms.consoleOutput("");
        comms.consoleOutput("enter 1 if you understand");
        comms.consoleOutput("");//gives basic instructions on how to create a file and prompts user to confirm that they understood the instructions
       //if statement making sure user understands the instructions
        if(comms.numConsoleInput()!=1){//This means if user input is not equal to 1 they will be asked to reread the instructions
            comms.consoleOutput("");
            comms.consoleOutput("Re-read the instructions :)");
            startInstructions();//user goes back to the start of this method
        } 
        else{
            addFile();//if the user understands the program and inputs 1, they are prompted to add a file
    }
    }

    
    public void addFile(){
        
            comms.consoleOutput("\r\n" + // Creates a C.U.I.(console user interface) using ascii
        "╔════════════════════════════════════════════════════════════╗\r\n" + //
        "║  Pick a file (type in the number next to the choice):      ║\r\n" + //
        "║                                                            ║\r\n" + //
        "║      > (1) Preset File -Recommended option-                ║\r\n" + //
        "║      > (2) File Slot 1 (only works if you add a file)      ║\r\n" + //
        "║      > (3) Add a file                                      ║\r\n" + //
        "╠════════════════════════════════════════════════════════════╣\r\n");

        int openingChoice= comms.numConsoleInput();
         if(openingChoice == 1){ //If the user wants they can use the pre-existing file
            //I added this because I got tired of typing in BookList.txt every single time the program ran
            fileName= "BookList.txt";
            comms.consoleOutput("Thanks for selecting a file, its contents are now being read by the program");
            return;
        } 
        
        else if(openingChoice==2){//Choice two allows user to pick their own file, this also allows them to have 2 different libraries
            if(File1==null){//If the string is empty program returns the following
                comms.consoleOutput("Add a file first"); 
                addFile();//goes back to start of this method, the CUI
                return;
            } //nested if statement ending

            else{//If the string is not null
            fileName= File1;
            try{io.openFile(fileName);//Checks whether the string added is actually a valid file name using try & catch
                io.closeFile();}catch(FileNotFoundException e){
                    comms.consoleOutput("File not found, add a file that in accessible");
                    addFile();
                }//nested else statement ending
                
            comms.consoleOutput("Thanks for selecting a file, its contents are now being read by the program");
            
            
        }//else if statement ending
    }

        else if(openingChoice==3){//Allows user to add their own file
        comms.consoleOutput("Okay, to be honest I haven't really tested this");
        comms.consoleOutput("");
        comms.consoleOutput("However, if you make the file in the correct format and add it to the correct place it should work");
        comms.consoleOutput("");
        comms.consoleOutput("Type in your file name, exactly how it is. DONT forget to add the .txt at the end");
        File1=comms.textConsoleInput();//saves user input to the private variable
        comms.consoleOutput("");
        comms.consoleOutput("File option added");
        addFile();//reruns the program with the updates value of the private variable
        }//end else if statement


        else{//Incase user input any integer value other than 1-3
        comms.consoleOutput("You messed up but that's okay, pick again");
        addFile();
        return;
        }//end else statement
    }//end method
    


    
    public void menu(){
        comms.consoleOutput("\r\n" + //
        "╔════════════════════════════════════════════════════════════╗\r\n" + //
        "║                    LIBRARY MAIN MENU                       ║\r\n" + //
        "╠════════════════════════════════════════════════════════════╣\r\n" + //
        "║  [1]  View Books                                           ║\r\n" + //
        "║  [2]  Add a New Book                                       ║\r\n" + //
        "║  [3]  Remove a Book                                        ║\r\n" + //
        "║  [4]  Change book borrowed status                          ║\r\n" + //
        "║  [5]  View borrower names                                  ║\r\n" + //
        "║  [6]  Exit program                                         ║\r\n" + //
        "╚════════════════════════════════════════════════════════════╝\r\n" + //
        "Please enter the number of your choice:\r\n");

        int x;
        x= comms.numConsoleInput();
        if(x==1){
            viewBooks();
        } else if(x==2){
            addBook();
        } else if(x==3){
            removeBook();
        }else if(x==4){
            bookBorrowedOrReturned();
        }else if(x==5){
            borrowerList();
        }
        else if(x==6){
            exitProgram();
        }

        else{
            comms.consoleOutput("Input a valid choice please");
            menu();
        }

    }

    public void borrowerList(){
        Book[] listOfBorrowedBooks= library.borrowedBooks();
        comms.consoleOutput("\r\n" + //
        "╔════════════════════════════════════════════════════════════╗\r\n" + //
        "║                    Names of Borrowers                      ║\r\n" + //
        "╠════════════════════════════════════════════════════════════╣\r\n" +//
        "║");
        int listNum=1;
        for(int j = 0; j<listOfBorrowedBooks.length; j++){
            if(listOfBorrowedBooks[j] != null && listOfBorrowedBooks[j].getborrowed()==true){
                comms.consoleOutput("║  "+ "[" + listNum +"]"+" "+ listOfBorrowedBooks[j].getBorName() +" ");
                listNum++;
                } 
                else{}
            }

        int listIndex;
        comms.consoleOutput("For more information on individuals type in the number in front of their name, or type 0 to go back to menu");
        int consoleInput= comms.numConsoleInput();
        listIndex= consoleInput-1;
        
        if(consoleInput==0){
            comms.consoleOutput("");
            comms.consoleOutput("Going back to menu");
            menu();
            return;
        }

        else{
        comms.consoleOutput("\r\n" + //
        "╔════════════════════════════════════════════════════════════╗\r\n" + //
        "║                    More Information                        ║\r\n" + //
        "╠════════════════════════════════════════════════════════════╣\r\n" +//
        "║");
        
        comms.consoleOutput("║ Book borrowed >" + listOfBorrowedBooks[listIndex].getTitle());
        comms.consoleOutput("║ Date borrowed > (Future extension)");

        comms.consoleOutput("");
                comms.consoleOutput("\r\n" + //
                "╔════════════════════════════════════════════════════════════╗\r\n" + //
                "║    When you are done type:                                 ║\r\n" + //
                "║      > 1 to go back to menu                                ║\r\n" + //
                "║      > 2 to go back to Names of Borrowers                  ║\r\n" + //
                "╠════════════════════════════════════════════════════════════╣\r\n");

                int response;
                response= comms.numConsoleInput();

                if(response== 1){
                    menu();
                } else if(response==2){
                    borrowerList();
                } else{
                    comms.consoleOutput("Choose a valid option");
                    viewBooks();
                }
        }
    }

    public void viewBooks(){
        Book[] listOfBooks= library.getBooks(); //Saves the book array inside mehod using getter
        comms.consoleOutput("\r\n" + //CUI using ascii
        "╔════════════════════════════════════════════════════════════╗\r\n" + //
        "║                         View Books                         ║\r\n" + //
        "╠════════════════════════════════════════════════════════════╣\r\n" + //
        "║  [1]  View All Books                                       ║\r\n" + //
        "║  [2]  View borrowed book                                   ║\r\n" + //
        "║  [3]  View available books                                 ║\r\n" + //
        "╚════════════════════════════════════════════════════════════╝\r\n" + //
        "Please enter the number of your choice:\r\n");

        int x;//variable declared
        x= comms.numConsoleInput();//user input value assigned to variable
        if(x==1){//If user picks the first option
            comms.consoleOutput("\r\n" + //Heading
        "╔════════════════════════════════════════════════════════════╗\r\n" + //
        "║                         All Books                          ║\r\n" + //
        "╠════════════════════════════════════════════════════════════╣\r\n" +//
        "║");
            int bookNum=1;//A way to number all the books in the list
            for(int j = 0; j<listOfBooks.length; j++){//For loop to go through the entire array
               if(listOfBooks[j] != null){//If statement to ensure that it only prints out values that are not null
                comms.consoleOutput("║  "+ "[" + bookNum +"]"+" "+ listOfBooks[j].getTitle() +" ");//Prints out a line
               bookNum++;//increases the value making it the label of the next line
            }else{}//empty else statement
            }//end of for loop
               comms.consoleOutput(""); //prints a line break, could have used \n maybe, not sure
                comms.consoleOutput("\r\n" + //Ascii art table with more options
                "╔════════════════════════════════════════════════════════════╗\r\n" + //
                "║  When you are done looking at the list of books type:      ║\r\n" + //
                "║      > 1 to go back to menu                                ║\r\n" + //
                "║      > 2 to go back to View Books                          ║\r\n" + //
                "╠════════════════════════════════════════════════════════════╣\r\n");

                int response;//declares variable
                response= comms.numConsoleInput();//saves user input to variable

                if(response== 1){//if user responds with 1, asking to go back to menu they are sent back to menu
                    menu();
                } else if(response==2){//if they respond with 2, they are sent back to the start of this method
                    viewBooks();
                } else{//if they input an invalid option they are informed and sent back to menu
                    comms.consoleOutput("invalid option sending you back to menu");
                    menu();
                }

        }//end of first if statement
        
        else if(x==2){//Basically does the same thing as the first statement, but instead of showing a list of all books
            //it only shows borrowed books
            comms.consoleOutput("\r\n" + //
        "╔════════════════════════════════════════════════════════════╗\r\n" + //
        "║                       Borrowed books                       ║\r\n" + //
        "╠════════════════════════════════════════════════════════════╣\r\n" +//
        "║");
        int bookNum=1;//declare outside the loop
            for(int j=0; j<listOfBooks.length; j++){
                if(listOfBooks[j]!=null && listOfBooks[j].getborrowed()==true ){ //Check whether it is null or not before using the getter
                    //Also only runs the following code if the book is borrowed
                    comms.consoleOutput("║  "+ "[" + bookNum +"]"+" "+ listOfBooks[j].getTitle() +" ");
                    bookNum++;
                }else{}
                }
                    comms.consoleOutput("");
                    comms.consoleOutput("\r\n" + //
                    "╔════════════════════════════════════════════════════════════╗\r\n" + //
                    "║  When you are done looking at the list of books type:      ║\r\n" + //
                    "║      > 1 to go back to menu                                ║\r\n" + //
                    "║      > 2 to go back to View Books                          ║\r\n" + //
                    "╠════════════════════════════════════════════════════════════╣\r\n");
    
                    int response;
                    response= comms.numConsoleInput();
    
                    if(response== 1){
                        menu();
                    } else if(response==2){
                        viewBooks();
                    } else{
                        comms.consoleOutput("invalid option sending you back to menu");
                        menu();
                    }
                
            }
            
            else if(x==3){//Same as the other 2 blocks of code, but only for available books
                comms.consoleOutput("\r\n" + //
                "╔════════════════════════════════════════════════════════════╗\r\n" + //
                "║                       Available Books                      ║\r\n" + //
                "╠════════════════════════════════════════════════════════════╣\r\n" +//
                "║");
                
                int bookNum=1;//declare outside the loop

        for(int j=0; j<listOfBooks.length; j++){
            if(listOfBooks[j]!=null && listOfBooks[j].getborrowed()==false){ //Check whether it is null or not before using the getter 
                comms.consoleOutput("║  "+ "[" + bookNum +"]"+" "+ listOfBooks[j].getTitle() +" ");
                bookNum++;}else{}
            }

                comms.consoleOutput("");
                comms.consoleOutput("\r\n" + //
                "╔════════════════════════════════════════════════════════════╗\r\n" + //
                "║  When you are done looking at the list of books type:      ║\r\n" + //
                "║      > 1 to go back to menu                                ║\r\n" + //
                "║      > 2 to go back to View Books                          ║\r\n" + //
                "╠════════════════════════════════════════════════════════════╣\r\n");

                int response;
                response= comms.numConsoleInput();

                if(response== 1){
                    menu();
                } else if(response==2){
                    viewBooks();
                } else{
                    comms.consoleOutput("invalid option sending you back to menu");
                    menu();
                }
            }
            
            else{
            comms.consoleOutput("Pick a valid option");
            viewBooks();
        }
    }


    public void bookBorrowedOrReturned(){//Method to borrow or return a book
        String bookName;//declares the variable
        comms.consoleOutput("In order to return or borrow a book, type in its name");
        bookName= comms.textConsoleInput();//initializes the value to user input value
        Book[] listOfBooks= library.getBooks(); //creates an array of books to be used by this method

        for(int i=0; i<listOfBooks.length; i++){//searches through the array to find the book which has the corresponding book name
            if(listOfBooks[i]!= null && listOfBooks[i].getTitle().equalsIgnoreCase(bookName)){//checks whether it is null or not
                listOfBooks[i].toggleBorrowed();//A method defined in the Book class
                if(listOfBooks[i].getborrowed()==true){//Prompts user for a name only if borrowed status has been changed to borrowed
                    comms.consoleOutput("Who has borrowed the book?");
                    String borrowerName=comms.textConsoleInput();//Variable declared and initialized, user input saved in said variable
                    listOfBooks[i].setBorName(borrowerName);
                    comms.consoleOutput("");
                    comms.consoleOutput(borrowerName+" has succesfully borrowed the book");//´personalized message
                }else{//since book has been returned, method doesnt ask for a name, and uses previously defined name
                    comms.consoleOutput("");
                    comms.consoleOutput(listOfBooks[i].getBorName()+" has successfully returned the book");//personalized message
                    listOfBooks[i].setBorName("N/A");//sets the current value of borrowed name to N/A
                }
                booksToFile();//Saves changes to the file, makes the program auto saving!
                comms.consoleOutput("");
                comms.consoleOutput("Sending you back to menu");
                menu();//sends back to menu where user can do other things
                return;

            }else{
            }//empty else statement
    }//end method

}

    public void addBook(){//method to add books
        String title;
        String author;
        String borrowed;
        String borrowerName;
        /* Here you can add books, previously in the program I made the books array bigger than it needed to be
         * Due to which the user can add more books here, however they can only add 5 books
         * After which they have to re-run the program to add more books.
         */
        int numOfExtraSpace;
        numOfExtraSpace= a; //A way to count down how much extra space the user has
        if(a==0){//if the value is equal to 0
            comms.consoleOutput("Sorry, allocated storage is full, restart the program to add more books");
            menu();//return back to menu
        }//end if statement
        else{//If there is space to add more books
        a--;//subtract 1 from this, to mark the reduction in space
        comms.consoleOutput("In order to add a book you must answer the following questions: (Be carefull, if you mess up you have to restart the program)");
        comms.consoleOutput("Note: You can only add "+numOfExtraSpace+ " additional books, to add more you must restart the program");
        comms.consoleOutput("");//dynamic statement that displays the number of answers
        comms.consoleOutput("What is the name of the book");//prompts user to add information about the book
        title= comms.textConsoleInput();
        comms.consoleOutput("");
        comms.consoleOutput("What is the name of the author");
        author= comms.textConsoleInput();
        comms.consoleOutput("");
        comms.consoleOutput("Is it borrowed currently, type in yes or no");
        borrowed= comms.textConsoleInput();

        if(borrowed.equalsIgnoreCase("yes")){
            borrowedYN= true;//new borrowed boolean because the previous one can only turn the string values true/false to yes or no
        }else if(borrowed.equalsIgnoreCase("no")){
            borrowedYN= false;
        }else{
            comms.consoleOutput("Retry");
            addBook();
        }

        if(borrowedYN==true){//Section of code only runs if book added is borrowed
        comms.consoleOutput("");
        comms.consoleOutput("Since it is borrowed, type in the borrower's name");
        borrowerName= comms.textConsoleInput();
        }else{
            comms.consoleOutput("");
            borrowerName= "N/A";
        }

        library.append(new Book(title, author, borrowerName, borrowedYN));//append method to add book to list

        comms.consoleOutput("Your book has been added sending you back to menu");
        booksToFile();//autosave
        menu();//back to menu
    }

    }

    public void removeBook(){//method to remove books
        Book[] listOfBooks= library.getBooks();//list of book saved so that the method can use them
        String bookName;
        comms.consoleOutput("In order to remove a book type in its name");
        bookName= comms.textConsoleInput(); 

        for(int i=0; i<listOfBooks.length; i++){//loops through books to find the book that the user wants to delete
            if(listOfBooks[i]!= null && listOfBooks[i].getTitle().equalsIgnoreCase(bookName)){
                Book temp= listOfBooks[i];
                listOfBooks[i]=listOfBooks[library.getLastIndex()-1];
                listOfBooks[library.getLastIndex()-1]= temp;

                books[library.getLastIndex()-1]= null;//Switches the book user wants to delete with the last book in the array
                //then the last book in the array is set to null, effectively deleted
                library.decrement();//more on this in the library class
                comms.consoleOutput("Book deleted, sending you back to menu");
                booksToFile();
                menu();
                return;
            }
        }
        comms.consoleOutput("Book not found, returning you back to menu");
        menu();
    }


public void exitProgram(){//method to exit program, basically just saves changes back to file
    //kind of redundant as every method already does this but just to be safe
    comms.consoleOutput("");
    comms.consoleOutput("Program finished");
    booksToFile();
}

    public void booksToFile(){//Saves changes back to file, to make the program permanent
        //Writes back in to file, using a big array of all attributes, and then formatting them in a way so that the program can read them again
        fileInput= library.print();//described in library class
        io.openWriter(fileName);//opens file writer
        for(int x=0; x<fileInput.length; x=x+4){
            for(int y=x; y<x+4; y++){

            io.writeInFile(fileInput[y]);
            if (y != x + 3) {
                io.writeInFile(";"); // Separate values with semicolons
            }else{
            io.newLineInFile(); // New line after each book
            }
        }
        }
            io.closeWriter();//writer closed
        }

     }
