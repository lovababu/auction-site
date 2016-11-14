package com.sapient.auction.user.repository;

import com.sapient.auction.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository class, to perform  all User related CRUD operations and lookup.
 *
 * Created by dpadal on 11/11/2016.
 */

public interface UserRepository extends JpaRepository<User , String> {
}
