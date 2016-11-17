package com.sapient.auction.user.repository;

import com.sapient.auction.user.entity.User;
import com.sapient.auction.user.exception.UserAlreadyExistException;
import com.sapient.auction.user.exception.UserNotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository class, to perform  all User related CRUD operations and lookup.
 *
 * Created by dpadal on 11/11/2016.
 */

public interface UserRepository {

    void register(User user);

    User login(String userId, String password);

    boolean isUserAlreadyExist(String userId);
}
