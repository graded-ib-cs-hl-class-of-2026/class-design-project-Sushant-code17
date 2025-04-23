public class Library {
    private Book[] books;//Private array of book
    private int i=0;//variable used in append method

    public Library(Book[] books){ //Constructor to create library objects
        this.books= books;  //array of book inputed as parameter saved into the declared but empty 
    }
    

    public void append(Book book){//adds book to the array of books
        books[i]=book;//adds book to the index of i which is the marks the next free space in the array
        i++;//increases value of i so that it is 1 more than the index of the last book
    }

    public int getLastIndex(){//returns the next free space where a book can be added
        return i;
    }

    public void decrement() {
        i--;//When a book is removed this ensures the value of i is accurate
    }

    public Book[] borrowedBooks(){//An array only of books that are borrowed 
        int count = 0; 

        // Count how many books are borrowed
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && books[i].getborrowed()) {
                count++;
            }
        }
    
        // Create array of exact size
        Book[] borrowedBooks = new Book[count];

        int borrowedBookIndex = 0;

    // Fill the array with borrowed books
    for (int i = 0; i < books.length; i++) {
        if (books[i] != null && books[i].getborrowed()) {
            borrowedBooks[borrowedBookIndex] = books[i];
            borrowedBookIndex++;
        }
    }

    return borrowedBooks;//returns the array that is made
    }


    public String[] print(){
        /*This method prints out a big array that has the (title, author name, borrowed status, borrower name) like this
         * This is for all books in one array, which is then formatted in a program readable way
         */
        int j=0;//loop variable
        String[] fileOutput= new String[i*4];//string space timed by 4 because each book has 4 attibutes
        for(int k=0; k<i; k++){//for loop going through all books added in the append method
        fileOutput[j]=books[k].getTitle();
        j++;
        fileOutput[j]=books[k].getAuthor();
        j++;
        fileOutput[j]=String.valueOf(books[k].getborrowed());
        j++;
        fileOutput[j]=books[k].getBorName();
        j++;
    }//code above just saves the values of the attributes in strings
    return fileOutput;
    }

    public Book[] getBooks(){ //getter to get the array of books
        return books;
    }


}
