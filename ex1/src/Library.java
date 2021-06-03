/**
 * This class represents a library, which hold a collection of books. Patrons can register at the library to
 * be able to check out books, if a copy of the requested book is available.
 */
public class Library {

    /**
     * The maximal number of books this library can hold.
     */
    final int maxCapacity;

    /**
     * The maximal number of books this library allows a single patron to borrow at the same time.
     */
    final int maxBorrowed;

    /**
     * The maximal number of registered patrons this library can handle.
     */
    final int maxPatrons;

    /**
     * The array that contains the books in the library.
     */
    Book[] booksArray;

    /**
     * The array of the registered patrons.
     */
    Patron[] patronsArray;

    /**
     * The array that contains the borrowed books for the patron.
     */
    Book[] borrowedBooks;

    /**
     * The array of the current books with each patron.
     */
    int[] currentBorrowed;

    /**
     * Creates a new library with the given parameters.
     *
     * @param maxBookCapacity   The maximal number of books this library can hold.
     * @param maxBorrowedBooks  The maximal number of books this library allows a single patron to borrow at
     *                          the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        maxCapacity = maxBookCapacity;
        maxBorrowed = maxBorrowedBooks;
        maxPatrons = maxPatronCapacity;
        booksArray = new Book[maxCapacity];
        patronsArray = new Patron[maxPatrons];
        borrowedBooks = new Book[maxBorrowed];
        currentBorrowed = new int[maxPatrons];
    }

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     *
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        for (int i = 0; i < maxCapacity; i++) {
            if (booksArray[i] == null) {
                booksArray[i] = book;
                return i;
            } else if (booksArray[i] == book) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     *
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        if ((0 <= bookId) && (bookId < maxCapacity) && (booksArray != null)) {
            return (booksArray[bookId] != null);
        } else {
            return false;
        }
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     *
     * @param book The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        for (int i = 0; i < booksArray.length; i++) {
            if (booksArray[i] == book) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     *
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        if ((0 <= bookId) && (bookId < maxCapacity) && (booksArray[bookId] != null)) {
            return booksArray[bookId].getCurrentBorrowerId() == -1;
        }
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully
     * registered, a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        for (int i = 0; i < maxPatrons; i++) {
            if (patronsArray[i] == null) {
                patronsArray[i] = patron;
                return i;
            } else if (patronsArray[i] == patron) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     *
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        if ((0 <= patronId) && (patronId < maxPatrons) && (patronsArray[patronId] != null)) {
            return (booksArray[patronId] != null);
        } else {
            return false;
        }
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library,
     * -1 otherwise.
     *
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        for (int i = 0; i < patronsArray.length; i++) {
            if (patronsArray[i] == patron) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this
     * book is available, the given patron isn't already borrowing the maximal number of books allowed, and
     * if the patron will enjoy this book.
     *
     * @param bookId   The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        if ((isBookIdValid(bookId)) && (isPatronIdValid(patronId))) {
            boolean available = isBookAvailable(bookId);
            boolean enjoy = patronsArray[patronId].willEnjoyBook(booksArray[bookId]);
            if ((available) && (enjoy) && (currentBorrowed[patronId] < maxBorrowed)) {
                currentBorrowed[patronId]++;
                booksArray[bookId].setBorrowerId(patronId);
                return true;
            }
        }
        return false;
    }

    /**
     * Return the given book.
     *
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId) {
        if (isBookIdValid(bookId)) {
            int patronId = booksArray[bookId].getCurrentBorrowerId();
            if (isPatronIdValid(patronId)) {
                currentBorrowed[patronId]--;
                booksArray[bookId].returnBook();
            }
        }

    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he
     * will enjoy, if any such exist.
     *
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given ID will enjoy the most. Null if no book is
     * available.
     */
    Book suggestBookToPatron(int patronId) {
        if (isPatronIdValid(patronId)) {
            int maxEnjoy = 0;
            int maxIndex = 0;
            Patron patron = patronsArray[patronId];
            for (int i = 0; i < maxCapacity; i++) {
                Book book = booksArray[i];
                if ((patron.willEnjoyBook(book)) && (patron.getBookScore(book) > maxEnjoy)
                        && (isBookAvailable(i))) {
                    maxEnjoy = patron.getBookScore(book);
                    maxIndex = i;
                }
            }
            if ((booksArray[maxIndex] != null) && (maxEnjoy != 0)) {
                return booksArray[maxIndex];
            }
        }
        return null;
    }
}
