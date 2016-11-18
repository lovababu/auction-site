package com.sapient.auction.user.service.impl;

import com.sapient.auction.user.entity.User;
import com.sapient.auction.user.exception.UserAlreadyExistException;
import com.sapient.auction.user.exception.UserNotFoundException;
import com.sapient.auction.user.repository.UserRepository;
import com.sapient.auction.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dpadal on 11/11/2016.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void register(User user) throws UserAlreadyExistException {
		if (!userRepository.isUserAlreadyExist(user.getId())) {
			userRepository.register(user);
		} else {
			throw new UserAlreadyExistException("user id being used, please try with other.");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User login(User user) throws UserNotFoundException {
		user = userRepository.login(user.getId(), user.getPassword());
		if (user == null) {
			throw new UserNotFoundException(String.format("User %s not found.", user.getId()));
		}
		return user;
	}
}
