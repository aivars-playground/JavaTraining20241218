import model.Book;
import repository.BookDao;
import repository.Dao;

public class App {
    public static void main(String[] args) {

        Dao<Book> bookDao = new BookDao();

        var allBooks = bookDao.findAll();
        System.out.println(allBooks);

    }
}
