package assingment.javafx.dao;/*
 * @created 7/11/2021
 *
 * @Author Poran chowdury
 */

import java.util.List;
import java.util.Optional;

public interface CRUDDAO<T, I> {
    T save(T t);

    Optional<T> findById(I id);

    List<T> findAll();

    T update(I id, T object);

    boolean deleteById(I id);
}
