package com.sapient.auction.user.repository.impl;

import com.sapient.auction.user.constant.UserQuery;
import com.sapient.auction.user.entity.User;
import com.sapient.auction.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * Created by dpadal on 11/17/2016.
 */
@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void register(User user) {
		log.info("Processign registration for user: {}", user.getId());
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public User login(String userid, String password) {
		Query query = sessionFactory.getCurrentSession().createQuery(UserQuery.IS_AUTHENTICATED);
		query.setString("userId", userid);
		query.setString("password", password);
		User userFetched = (User) query.uniqueResult();
		log.info("User fetched, mapping to {} and {} is {}", userid, password, userFetched);
		return userFetched;
	}

	@Override
	public boolean isUserAlreadyExist(String userId) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(UserQuery.IS_USER_ID_ALREADY_EXIST);
		query.setString("userId", userId);
		BigInteger count = (BigInteger) query.uniqueResult();
		if (count.intValue() > 0) {
			return true;
		}
		return false;
	}
}
