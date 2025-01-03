import model.Book;
import repository.BookDao;
import repository.Dao;

public class App {
    public static void main(String[] args) {

        Dao<Book> bookDao = new BookDao();

        var allBooks = bookDao.findAll();
        System.out.println("all->"+allBooks);

        var book1 = bookDao.findById(1);
        System.out.println("book1->"+(book1.isPresent()?book1.get():"Not found"));


    }
}
