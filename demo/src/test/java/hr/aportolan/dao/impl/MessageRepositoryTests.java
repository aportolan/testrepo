package hr.aportolan.dao.impl;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import hr.aportolan.UsermessageApplication;
import hr.aportolan.dao.InitDataLoader;
import hr.aportolan.dao.MessageRepository;
import hr.aportolan.domain.Message;
import hr.aportolan.domain.User;
import hr.aportolan.enums.DefaultUsersSetup.UserNumber;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UsermessageApplication.class)
@Rollback
public class MessageRepositoryTests {
	private static boolean setupOk = false;
	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private InitDataLoader initDataLoader;

	public MessageRepositoryTests() {

	}

	@PostConstruct
	public void loadInitialUsers() {
		if (!setupOk)
			initDataLoader.loadInitialUsers(UserNumber.TEST_USER_NUMBER);
		setupOk = true;
	}

	@Test
	public void getByUser() {
		Message message = new Message();
		message.setMid(1);
		message.setUser(new User());
		message.getUser().setTag("123");
		messageRepository.getByUser(message);
	}

	@Test
	@Transactional
	public void saveByUserData() {
		Message message = new Message();
		message.setMid(1);
		message.setUser(new User());
		message.getUser().setTag("123");
		messageRepository.saveByUserData(message);
	}

	@Test
	public void deleteByUser() {
		Message message = new Message();
		message.setMid(1);
		message.setUser(new User());
		message.getUser().setTag("123");
		messageRepository.deleteByUser(message);
	}

	@Test
	public void deleteExpired() {
		messageRepository.deleteExpired();
	}
}