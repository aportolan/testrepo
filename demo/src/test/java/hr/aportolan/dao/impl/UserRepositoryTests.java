package hr.aportolan.dao.impl;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import hr.aportolan.UsermessageApplication;
import hr.aportolan.dao.InitDataLoader;
import hr.aportolan.dao.UserRepository;
import hr.aportolan.domain.User;
import hr.aportolan.enums.DefaultUsersSetup.UserNumber;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UsermessageApplication.class)
@Rollback
public class UserRepositoryTests {
	private static boolean setupOk = false;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private InitDataLoader initDataLoader;

	public UserRepositoryTests() {

	}

	@PostConstruct
	public void loadInitialUsers() {
		if (!setupOk)
			initDataLoader.loadInitialUsers(UserNumber.TEST_USER_NUMBER);
		setupOk = true;
	}

	@Test
	public void getByUser() {
		User user = new User();
		// user.setTag("Ana");
		user.setName("AB%");

		Assert.notEmpty(userRepository.selectUsersByCriteria(user, 0, 1));
	}
}
