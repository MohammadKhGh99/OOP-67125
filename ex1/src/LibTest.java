import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LibTest {
    private Library lib;
    private Patron pat1;
    private Patron pat2;
    private Patron pat3;
    private Patron pat4;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;

    @Before
    public void before(){
        lib = new Library(3, 2, 3);
        pat1 = new Patron("John", "Doe", 1, 2, 3, 10);
        pat2 = new Patron("Jane", "Doe", 3, 8, 10, 81);
        pat3 = new Patron("Real", "Person", 8, 4, 2, 55);
        pat4 = new Patron("John", "Doe", 1, 2, 3, 90);
        book1 = new Book("1984", "George Orwell", 1949, 1, 4, 10);
        book2 = new Book("Brave new world", "Aldous Huxley", 1931, 1, 7, 8);
        book3 = new Book("To kill a mockingbird", "Harper Lee", 1960, 1, 2, 2);
        book4 = new Book("A tale of two cities", "Charles Dickens", 1859, 8, 6, 4);

    }

    @Test
    public void testAddBook() {
        int Id = lib.addBookToLibrary(book1);
        Assert.assertEquals(0, Id);
        //Identical books
        Assert.assertEquals(-1, lib.addBookToLibrary(book1));
        Id = lib.addBookToLibrary(book2);
        Assert.assertEquals(1, Id);
        lib.addBookToLibrary(book3);
        //Library capacity reached
        Assert.assertEquals(-1, lib.addBookToLibrary(book4));
    }

    @Test
    public void testAddPatron() {
        Assert.assertEquals(0, lib.registerPatronToLibrary(pat1));
        //Identical Patrons
        Assert.assertEquals(-1, lib.registerPatronToLibrary(pat1));
        Assert.assertEquals(1, lib.registerPatronToLibrary(pat2));
        lib.registerPatronToLibrary(pat3);
        //Library capacity reached
        Assert.assertEquals(-1, lib.registerPatronToLibrary(pat4));
    }

    @Test
    public void testGetIds(){
        lib.registerPatronToLibrary(pat1);
        lib.registerPatronToLibrary(pat2);
        int Id = lib.registerPatronToLibrary(pat3);
        Assert.assertEquals(Id, lib.getPatronId(pat3));
        //Following line is not needed (simplifying assumptions)
//        Assert.assertEquals(-1, lib.getPatronId(pat4));
        lib.addBookToLibrary(book1);
        Id = lib.addBookToLibrary(book2);
        lib.addBookToLibrary(book3);
        Assert.assertEquals(Id, lib.getBookId(book2));
        //Following line is not needed (simplifying assumptions)
//        Assert.assertEquals(-1, lib.getBookId(book4));
    }

    @Test
    public void testValid(){
        lib.registerPatronToLibrary(pat1);
        Assert.assertTrue(lib.isPatronIdValid(0));
        Assert.assertFalse(lib.isPatronIdValid(1));
        lib.addBookToLibrary(book1);
        Assert.assertTrue(lib.isBookIdValid(0));
        Assert.assertFalse(lib.isBookIdValid(1));
        //out of bounds, may not be needed
        Assert.assertFalse(lib.isBookIdValid(-1));
        Assert.assertFalse(lib.isPatronIdValid(100000));
    }

    @Test
    public void testBorrow(){
        lib.addBookToLibrary(book1);
        lib.addBookToLibrary(book2);
        lib.addBookToLibrary(book3);
        lib.registerPatronToLibrary(pat1);
        lib.registerPatronToLibrary(pat2);
        lib.registerPatronToLibrary(pat3);
        //Borrow a non registered book
        Assert.assertFalse(lib.borrowBook(lib.getBookId(book4), lib.getPatronId(pat2)));
        Assert.assertEquals(-1, book4.getCurrentBorrowerId());
        //Borrow an uninteresting book
        Assert.assertFalse(lib.borrowBook(lib.getBookId(book3), lib.getPatronId(pat2)));
        //Borrow a book successfully
        Assert.assertTrue(lib.borrowBook(lib.getBookId(book2), lib.getPatronId(pat2)));
        Assert.assertEquals(lib.getPatronId(pat2), book2.getCurrentBorrowerId());
        //Borrow a borrowed book
        Assert.assertFalse(lib.borrowBook(lib.getBookId(book2), lib.getPatronId(pat1)));
        lib.returnBook(lib.getBookId(book2));
        //Borrow after return
        Assert.assertTrue(lib.borrowBook(lib.getBookId(book2), lib.getPatronId(pat1)));
        lib.borrowBook(lib.getBookId(book1), lib.getPatronId(pat1));
        //Max borrow limit
        Assert.assertFalse(lib.borrowBook(lib.getBookId(book3), lib.getPatronId(pat1)));
        lib.returnBook(lib.getBookId(book1));
        Assert.assertTrue(lib.borrowBook(lib.getBookId(book3), lib.getPatronId(pat1)));
    }

    @Test
    public void testReturn(){
        lib.registerPatronToLibrary(pat1);
        lib.addBookToLibrary(book2);
        lib.borrowBook(lib.getBookId(book2), lib.getPatronId(pat1));
        lib.returnBook(10);
        Assert.assertTrue(-1 != book2.getCurrentBorrowerId());
        lib.returnBook(lib.getBookId(book2));
        Assert.assertEquals(-1, book2.getCurrentBorrowerId());
    }

    @Test
    public void testIsAvailable(){
        lib.addBookToLibrary(book2);
        lib.registerPatronToLibrary(pat2);
        lib.borrowBook(lib.getBookId(book2), lib.getPatronId(pat2));
        Assert.assertFalse(lib.isBookAvailable(lib.getBookId(book2)));
        lib.returnBook(lib.getBookId(book2));
        Assert.assertTrue(lib.isBookAvailable(lib.getBookId(book2)));
        //Following line is not needed (simplifying assumptions)
//        Assert.assertFalse(lib.isBookAvailable(lib.getBookId(book4)));
    }

    @Test
    public void testSuggestedBook(){
        lib.registerPatronToLibrary(pat3);
        lib.addBookToLibrary(book3);
        lib.addBookToLibrary(book4);
        Assert.assertEquals(book4, lib.suggestBookToPatron(lib.getPatronId(pat3)));
        Assert.assertNull(lib.suggestBookToPatron(lib.getPatronId(pat1)));
        lib.registerPatronToLibrary(pat1);
        Assert.assertEquals(book4, lib.suggestBookToPatron(lib.getPatronId(pat1)));
        lib.registerPatronToLibrary(pat4);
        Assert.assertNull(lib.suggestBookToPatron(lib.getPatronId(pat4)));
    }

}