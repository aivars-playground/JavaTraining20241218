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
    public void save(Book o) {

    }

    @Override
    public void update(Book o, String[] params) {

    }

    @Override
    public void delete(Book o) {

    }
}

//create table BOOK
//(
//    id int auto_increment primary key,
//    title varchar(255) null
//);