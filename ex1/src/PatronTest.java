
import org.junit.Assert;
import org.junit.Test;

public class PatronTest {

    @Test
    public void testString(){
        Patron p = new Patron("John", "Doe", 4, 10, 5, 20);
        Assert.assertEquals("John Doe", p.stringRepresentation());
    }

    @Test
    public void testEnjoyment(){
        Book b = new Book("1984", "George Orwell", 1949, 1, 4, 10);
        Patron p = new Patron("John", "Doe", 4, 10, 5, 20);
        Assert.assertTrue(p.willEnjoyBook(b));
        p = new Patron("Jane", "Doe", 4, 10, 5, 95);
        Assert.assertFalse(p.willEnjoyBook(b));
    }
}
