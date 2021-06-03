/**
 * This class represents a patron, which has both first and last names,  three weights he assigns to the three
 * respective literary aspects of books and an enjoyment threshold.
 */
public class Patron {

    /**
     * The first name of the patron.
     */
    final String firstName;

    /**
     * The last name of the patron.
     */
    final String lastName;

    /**
     * The tendency of comic value, chosen by the patron.
     */
    int comic;

    /**
     * The tendency of dramatic value, chosen by the patron.
     */
    int dramatic;

    /**
     * The tendency of educational value, chosen by the patron.
     */
    int educational;

    /**
     * The minimal literary value a book must have for this patron to enjoy it.
     */
    int enjoymentThreshold;

    /**
     * Creates a new patron with the given characteristic.
     *
     * @param patronFirstName          The first name of the patron.
     * @param patronLastName           The last name of the patron.
     * @param comicTendency            The weight the patron assigns to the comic aspects of books.
     * @param dramaticTendency         The weight the patron assigns to the dramatic aspects of books.
     * @param educationalTendency      The weight the patron assigns to the educational aspects of books.
     * @param patronEnjoymentThreshold The minimal literary value a book must have for this patron to enjoy
     *                                 it.
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency, int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold) {
        firstName = patronFirstName;
        lastName = patronLastName;
        comic = comicTendency;
        dramatic = dramaticTendency;
        educational = educationalTendency;
        enjoymentThreshold = patronEnjoymentThreshold;
    }

    /**
     * Returns a string representation of the patron, which is a sequence of its first and last name,
     * separated by a single white space. For example, if the patron's first name is "Ricky" and his last
     * name is "Bobby",
     * this method will return the String "Ricky Bobby".
     *
     * @return the String representation of this patron.
     */
    String stringRepresentation() {
        return firstName + " " + lastName;
    }

    /**
     * @param book The book to asses.
     * @return the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book) {
        return (comic * book.comicValue) + (dramatic * book.dramaticValue) + (educational * book.educationalValue);
    }

    /**
     * @param book the book to asses.
     * @return true of this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book) {
        return getBookScore(book) > enjoymentThreshold;
    }
}
