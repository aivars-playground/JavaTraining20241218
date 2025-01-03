package repository;

import model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookDao extends AbstractDao implements Dao {

    @Override
    public List findAll() {

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
    public Optional findById(long id) {
        return Optional.empty();
    }

    @Override
    public void save(Object o) {

    }

    @Override
    public void update(Object o, String[] params) {

    }

    @Override
    public void delete(Object o) {

    }
}

//create table BOOK
//(
//    id int auto_increment primary key,
//    title varchar(255) null
//);