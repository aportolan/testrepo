package hr.aportolan.controllers;

import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.aportolan.dao.InitDataLoader;
import hr.aportolan.dao.UserRepository;
import hr.aportolan.domain.User;
import hr.aportolan.dto.RequestObject;
import hr.aportolan.dto.ResponseObject;
import hr.aportolan.dto.ValidationType;
import hr.aportolan.dto.VoidData;

@Api(name = "users", description = "Methods formanipulating users", group = "Users", visibility = ApiVisibility.PUBLIC, stage = ApiStage.PRE_ALPHA)
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private InitDataLoader initDataLoader;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ApiMethod(description = "Saves user")
	@Transactional
	@ApiResponseObject
	public ResponseObject<User> save(@ApiBodyObject @RequestBody RequestObject<User> ro) {
		ro.amIValid(ValidationType.SAVE_USERS);
		return new ResponseObject<User>(true, userRepository.save(ro.getPayload()));
	}

	@ApiMethod(description = "Saves multiple users")
	@RequestMapping(value = "/saveAll", method = RequestMethod.POST)
	@ApiResponseObject
	public ResponseObject<VoidData> saveAll(@ApiBodyObject @RequestBody RequestObject<List<User>> ro) {
		ro.amIValid(ValidationType.SAVE_ALL_USERS);
		userRepository.save(ro.getPayload());
		return new ResponseObject<>(true);
	}

	@ApiMethod(description = "Deletes one or more users")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional
	@ApiResponseObject
	public ResponseObject<User> delete(@ApiBodyObject @RequestBody RequestObject<List<User>> ro) {
		ro.amIValid(ValidationType.DELETE_USERS);
		userRepository.delete(ro.getPayload());
		return new ResponseObject<>(true, ro.getPayload().get(0));

	}

	@ApiMethod(description = "Gets users by one of the user parameters")
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ApiResponseObject
	public ResponseObject<List<User>> get(@ApiBodyObject @RequestBody RequestObject<User> ro) {
		ro.amIValid(ValidationType.GET_USERS);
		return new ResponseObject<List<User>>(true,
				userRepository.selectUsersByCriteria(ro.getPayload(), ro.getOffset(), ro.getLimit()));
	}

	@ApiMethod(description = "Loads initial 100000 users as a combination of names from internet website containing list of common names and last names")
	@RequestMapping("/initLoad")
	@MessageMapping("/initLoad")
	@ApiResponseObject
	public ResponseObject<VoidData> initLoad() {
		initDataLoader.loadInitialUsers();
		return new ResponseObject<VoidData>(true);
	}
}
