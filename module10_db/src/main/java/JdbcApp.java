import jdbc_repository.model.Book;
import jdbc_repository.BookDao;
import jdbc_repository.Dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JdbcApp {
    public static void main(String[] args) {

        Dao<Book> bookDao = new BookDao();

        boolean shouldInsert = false;
        if (shouldInsert) {
            Book newBook = new Book();
            newBook.setTitle("Java");
            bookDao.create(newBook);
            System.out.println("new->"+newBook);
        }



        var allBooks = bookDao.findAll();
        System.out.println("all->"+allBooks);

        var book1 = bookDao.findById(1);
        System.out.println("book1->"+(book1.isPresent()?book1.get():"Not found"));

        if (book1.isPresent()) {
            var toBeUpdated = book1.get();
            toBeUpdated.setTitle("New Title:" + new Random().nextInt(100));

            var updated = bookDao.updateBatch(toBeUpdated);

            System.out.println("updated book1->"+updated);
        } else {
            System.out.println("book1 not found, ignoring update");
        }

        var allBooksAfterSingleUpdate = bookDao.findAll();
        System.out.println("allBooksAfterSingleUpdate->"+allBooksAfterSingleUpdate);


        if (book1.isPresent()) {
            List<Book> allBooksToUpdateInBatch = new ArrayList<>();
            var toBeUpdated = book1.get();
            toBeUpdated.setTitle("Back 2 Java");
            allBooksToUpdateInBatch.add(toBeUpdated);

            var ids = bookDao.updateBatch(allBooksToUpdateInBatch);
            System.out.println("ids->"+ Arrays.toString(ids));
        }

        var allBooksAfterBatchUpdate = bookDao.findAll();
        System.out.println("allBooksAfterBatchUpdate->"+allBooksAfterBatchUpdate);

        Book newBookToBeDeleted = new Book();
        newBookToBeDeleted.setTitle("deleteme");
        bookDao.create(newBookToBeDeleted);
        System.out.println("allBooksAfterDeletable->"+bookDao.findAll());

        var deletedCount = bookDao.delete(newBookToBeDeleted);
        System.out.println("deletedCount->"+deletedCount);
        var deletedCountRepeated = bookDao.delete(newBookToBeDeleted);
        System.out.println("deletedCountRepeated->"+deletedCountRepeated);


        Book newBookToBeDeleted2 = new Book();
        newBookToBeDeleted2.setTitle("deleteme");
        bookDao.create(newBookToBeDeleted2);
        System.out.println("newBookToBeDeleted2->"+bookDao.findAll());
        System.out.println("Batchdelete:"+Arrays.toString(bookDao.deleteBatch(List.of(newBookToBeDeleted, newBookToBeDeleted2))));


        List<Book> allBooksWithTemplateApproach = ((BookDao)bookDao).findAllWithTemplate();
        System.out.println("allBooksWithTemplateApproach->"+allBooksWithTemplateApproach);

    }
}
