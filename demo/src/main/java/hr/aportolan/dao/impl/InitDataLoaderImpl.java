package hr.aportolan.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hr.aportolan.dao.InitDataLoader;
import hr.aportolan.dao.UserRepository;
import hr.aportolan.domain.User;
import hr.aportolan.enums.DefaultUsersSetup;
import hr.aportolan.enums.DefaultUsersSetup.UserNumber;

@Repository
public class InitDataLoaderImpl implements InitDataLoader {
	@Autowired
	private UserRepository userRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(InitDataLoaderImpl.class);
	//
	// @PostConstruct
	// public void loadInitialUsers() {
	// loadInitialUsers(UserNumber.USER_NUMBER);
	// }

	@Override
	public void loadInitialUsers(UserNumber userNumber) {
		BufferedReader namesBuffReader = null;
		BufferedReader lastNamesBuffReader = null;
		List<String> namesList = new ArrayList<>();
		List<String> lastNamesList = new ArrayList<>();
		int counter = 0;
		try {
			namesBuffReader = new BufferedReader(
					new InputStreamReader(new URL(DefaultUsersSetup.NAME_URL.getValue()).openStream()));
			lastNamesBuffReader = new BufferedReader(
					new InputStreamReader(new URL(DefaultUsersSetup.LAST_NAME_URL.getValue()).openStream()));

			while (true) {
				String name = namesBuffReader.readLine();
				String lastName = lastNamesBuffReader.readLine();
				System.out.println("Data:" + name + " " + lastName + " " + counter);
				// no data or enough rows?? - break
				if (name == null || lastName == null || counter == userNumber.getValue() + 1)
					break;

				// remove header row
				if (counter++ == 0)

					continue;

				namesList.add(name);
				lastNamesList.add(lastName);
				saveUser(name, lastName, counter);

			}

		} catch (Exception e) {
			LOGGER.error("Error while loading initial users!", e);

		} finally {
			try {
				if (namesBuffReader != null)
					namesBuffReader.close();
				if (lastNamesBuffReader != null)
					lastNamesBuffReader.close();
			} catch (IOException e) {

				// ignore
			}

		}

		if (counter < userNumber.getValue() + 1) {
			// for random combinations
			int maxValueArray = namesList.size() > lastNamesList.size() ? namesList.size() - 1
					: lastNamesList.size() - 1;
			Random rnd = new Random();
			while (true) {
				if (counter >= userNumber.getValue() + 1)
					break;

				saveUser(namesList.get(rnd.nextInt(maxValueArray)), lastNamesList.get(rnd.nextInt(maxValueArray)),
						counter);

				counter++;
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 2)
	private void saveUser(String name, String lastName, int rownum) {
		User user = new User(
				name == null ? RandomStringUtils.randomAlphabetic(10)
						: name + " " + lastName == null ? RandomStringUtils.randomAlphabetic(10) : lastName,
				name.substring(0, 1) + lastName + String.valueOf(rownum));
		try {
			userRepository.save(user);
		} catch (Exception e) {
			LOGGER.error("Error while persisting user: {}!", user, e);
		}

	}

}
