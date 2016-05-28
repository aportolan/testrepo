package hr.aportolan.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.aportolan.dao.UserRepository;
import hr.aportolan.domain.User;
import hr.aportolan.dto.RequestObject;
import hr.aportolan.dto.ResponseObject;
import hr.aportolan.dto.ValidationType;
import hr.aportolan.dto.VoidData;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/save")
	public ResponseObject<User> save(RequestObject<User> ro) {
		ro.amIValid(ValidationType.SAVE_USERS);
		return new ResponseObject<User>(true, userRepository.save(ro.getPayload()));
	}

	@RequestMapping("/saveAll")
	public ResponseObject<VoidData> saveAll(RequestObject<User> ro) {
		ro.amIValid(ValidationType.SAVE_ALL_USERS);
		userRepository.save(ro.getPayload());
		return new ResponseObject<>(true);
	}

	@RequestMapping("/delete")
	public ResponseObject<VoidData> delete(RequestObject<List<User>> ro) {
		ro.amIValid(ValidationType.SAVE_ALL_USERS);
		return new ResponseObject<>(true);

	}

	@RequestMapping("/get")
	public ResponseObject<List<User>> get(RequestObject<User> ro) {
		ro.amIValid(ValidationType.SAVE_ALL_USERS);
		return new ResponseObject<List<User>>(true,
				userRepository.selectUsersByCriteria(ro.getPayload(), ro.getOffset(), ro.getLimit()));
	}
}
