package hr.aportolan.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import hr.aportolan.UsermessageApplication;
import hr.aportolan.domain.Message;
import hr.aportolan.domain.User;
import hr.aportolan.dto.RequestObject;
import hr.aportolan.enums.TestData;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UsermessageApplication.class)
@WebAppConfiguration
@Rollback
public class ControllerIntegrationTests {

	final EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	private <T, U> String getJsonAndCreateTemplate(Class<T> clazz, Class<U> encapsulatingClass)
			throws IOException, InstantiationException, IllegalAccessException {
		T instance = enhancedRandom.nextObject(clazz);

		ArrayList<T> al = new ArrayList<>();
		RequestObject<Object> req;
		if (encapsulatingClass.equals(List.class)) {
			al.add(instance);
			req = new RequestObject<Object>(al, 0, 1);
		} else
			req = new RequestObject<Object>(instance, 0, 1);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonValue = objectMapper.writeValueAsString(req);

		// test na mom PC-u
		if (System.getProperty("user.name").equals("pron"))
			appendToJsonFiles(clazz, jsonValue);
		return jsonValue;
	}

	private <T> void appendToJsonFiles(Class<T> clazz, String jsonValue) throws IOException {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

		StringBuilder fileName = new StringBuilder().append(System.getProperty("user.dir")).append(File.separator)
				.append("src").append(File.separator).append("test").append(File.separator).append("resources")
				.append(File.separator).append("files").append(File.separator).append((stackTraceElements.length >= 3
						? stackTraceElements[3].getMethodName().toUpperCase() : "unknown"))
				.append(".json");
		StringBuilder sb = new StringBuilder("--------------------------------------------------------")
				.append(System.lineSeparator()).append(System.lineSeparator()).append(jsonValue)
				.append(System.lineSeparator()).append(System.lineSeparator())
				.append("--------------------------------------------------------");

		Files.write(Paths.get(fileName.toString()), sb.toString().getBytes(),
				Files.exists(Paths.get(fileName.toString()), LinkOption.NOFOLLOW_LINKS) ? StandardOpenOption.APPEND
						: StandardOpenOption.CREATE);

	}

	// private HttpHeaders createHeaders() {
	// return new HttpHeaders() {
	//
	// private static final long serialVersionUID = 1L;
	//
	// {
	// String auth = TestData.USER.getValue() + ":" + TestData.PASS.getValue();
	// byte[] encodedAuth =
	// Base64.getEncoder().encode(auth.getBytes(Charset.forName("UTF-8")));
	// String authHeader = "Basic " + new String(encodedAuth);
	// set("Authorization", authHeader);
	// }
	// };
	// }

	// ResponseObject<List<Message>> getByUser(RequestObject<Message>
	// ro)
	@Test
	@Transactional
	public void getByUser() throws Exception {

		mockMvc.perform(post(TestData.Endpoints.GET_BY_USER_MESSAGES.getValue())
				.content(getJsonAndCreateTemplate(Message.class, Message.class))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	// ResponseObject<VoidData> delete(RequestObject<List<Message>> ro)
	@Test
	@Transactional
	public void deleteM() throws Exception {
		mockMvc.perform(post(TestData.Endpoints.DELETE_MESSAGES.getValue())
				.content(getJsonAndCreateTemplate(Message.class, List.class))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	// ResponseObject<VoidData> saveAll(RequestObject<Message> ro)
	@Test
	@Transactional
	public void saveAll() throws Exception {
		mockMvc.perform(
				post(TestData.Endpoints.SAVE_ALL_MESSAGES.getValue()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(getJsonAndCreateTemplate(Message.class, Message.class))
						.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	// ResponseObject<Message> save(RequestObject<Message> ro)

	@Test
	@Transactional
	public void saveM() throws Exception {
		mockMvc.perform(
				post(TestData.Endpoints.SAVE_MESSAGES.getValue()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(getJsonAndCreateTemplate(Message.class, Message.class))
						.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	// ResponseObject<User> save(RequestObject<User> ro)
	@Test
	@Transactional
	public void saveU() throws Exception {
		mockMvc.perform(
				post(TestData.Endpoints.SAVE_USERS.getValue()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(getJsonAndCreateTemplate(User.class, User.class))
						.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	// ResponseObject<VoidData> saveAll(RequestObject<List<User>> ro)
	@Test
	@Transactional
	public void saveAllU() throws Exception {
		mockMvc.perform(
				post(TestData.Endpoints.SAVE_ALL_USERS.getValue()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(getJsonAndCreateTemplate(User.class, List.class))
						.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	// ResponseObject<VoidData> delete(RequestObject<List<User>> ro)
	@Test
	@Transactional
	public void deleteU() throws Exception {
		mockMvc.perform(
				post(TestData.Endpoints.DELETE_USERS.getValue()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(getJsonAndCreateTemplate(User.class, List.class))
						.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	//
	// ResponseObject<List<User>> get(RequestObject<User> ro)
	@Test
	@Transactional
	public void get() throws Exception {
		getJsonAndCreateTemplate(User.class, User.class);
		mockMvc.perform(post(TestData.Endpoints.GET_USERS.getValue()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(getJsonAndCreateTemplate(User.class, User.class))
				.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}
}
