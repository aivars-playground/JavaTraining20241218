package jdbc_repository;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> findById(long id);
    List<T> findAll();
    T create(T t);
    T updateBatch(T t);

    int[] updateBatch(List<T> t);

    int delete(T t);
    int[] deleteBatch(List<T> t);
}
