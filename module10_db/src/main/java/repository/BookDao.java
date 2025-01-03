package repository;

import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class BookDao extends AbstractDao implements Dao<Book> {

    @Override
    public List<Book> findAll() {

        List<Book> books = new ArrayList<>();
        String sql = "select * from BOOK";

        try (
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ){
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                books.add(book);
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }

        return books;
    }

    @Override
    public Optional<Book> findById(long id) {
        Optional<Book> book = Optional.empty();
        String sql = "select id,title from BOOK where id = ?";
        try (
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setLong(1, id);
            try (
                ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    Book book1 = new Book();
                    book1.setId(resultSet.getLong("id"));
                    book1.setTitle(resultSet.getString("title"));
                    book = Optional.of(book1);
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return book;
    }

    @Override
    public Book create(Book book) {
        String sql = "insert into BOOK (title) values (?)";

        try (
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }

        return book;
    }

    @Override
    public Book updateBatch(Book book) {
        String sql = "update BOOK set title = ? where id = ?";
        try (
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setLong(2, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return book;
    }

    @Override
    public int[] updateBatch(List<Book> books) {
        int[] countAffected = {};

        String sql = "update BOOK set title = ? where id = ?";
        try (
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            for (Book book : books) {
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setLong(2, book.getId());
                preparedStatement.addBatch();
            }
            countAffected = preparedStatement.executeBatch();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }

        return countAffected;
    }

    @Override
    public int delete(Book book) {
        int countAffected = 0;
        String sql = "delete from BOOK where id = ?";
        try (
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setLong(1, book.getId());
            countAffected = preparedStatement.executeUpdate();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return countAffected;
    }

    @Override
    public int[] deleteBatch(List<Book> books) {
        int[] countAffected = {};
        String sql = "delete from BOOK where id = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            for (Book book : books) {
                preparedStatement.setLong(1, book.getId());
                preparedStatement.addBatch();
            }

            countAffected = preparedStatement.executeBatch();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return countAffected;
    }
}

//create table BOOK
//(
//    id int auto_increment primary key,
//    title varchar(255) null
//);