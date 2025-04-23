public class Book {
//private variables needed to make book objects
    private String title; 
    private String author;
    private String borName;
    private boolean borrowed;
    private boolean available;//not really of importance but could be usefull for future extensions

    public Book(String title, String author, String borName, boolean borrowed){//constructer for a book object
        //sets parameter input to instance variables
        this.title = title;
        this.author= author;
        this.borName = borName;
        this.borrowed = borrowed;
        available= !borrowed;//opposite of borrowed
    }
  

    public String getTitle(){//getter to get title
        return title;
    }

    public String getAuthor(){//getter to get author name
        return author;
    }

    public String getBorName(){//getter to get the name of the borrower
        return borName;
    }

    public boolean getborrowed(){//getter to get borrowed status
        return borrowed;
    }

    public void toggleBorrowed() {//changes whether a book is borrowed or not by reversing current borrowed value
        borrowed = !borrowed;
        this.available = !borrowed;  // Keep 'available' in sync
    }

    public void setBorName(String borName){//Setter to change borrower name once a book is borrowed or returned
        this.borName= borName;
    }
    

    public boolean geAvailable(){//getter to get available value
        return available;
    }
}