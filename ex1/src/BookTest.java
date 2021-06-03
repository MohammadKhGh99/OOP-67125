import org.junit.*;

public class BookTest {

    @Test
    public void testString() {
        Book b = new Book("1984", "George Orwell", 1949, 1, 4, 10);
        Assert.assertEquals("[1984,George Orwell,1949,15]", b.stringRepresentation());
    }

    @Test
    public void testValue(){
        Book b = new Book("1984", "George Orwell", 1949, 1, 4, 10);
        Assert.assertEquals(15, b.getLiteraryValue());
    }

    @Test
    public void testId(){
        int borrowerId = 7;
        Book b = new Book("1984", "George Orwell", 1949, 1, 4, 10);
        Assert.assertEquals(-1, b.getCurrentBorrowerId());
        b.setBorrowerId(borrowerId);
        Assert.assertEquals(borrowerId, b.getCurrentBorrowerId());
        b.returnBook();
        Assert.assertEquals(-1, b.getCurrentBorrowerId());
    }
}
