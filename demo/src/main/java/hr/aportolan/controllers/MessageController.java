package hr.aportolan.controllers;

import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import hr.aportolan.dao.MessageRepository;
import hr.aportolan.domain.Message;
import hr.aportolan.dto.RequestObject;
import hr.aportolan.dto.ResponseObject;
import hr.aportolan.dto.ValidationType;
import hr.aportolan.dto.VoidData;

@Api(name = "messages", description = "Methods formanipulating messages", group = "Messages", visibility = ApiVisibility.PUBLIC, stage = ApiStage.PRE_ALPHA)
@RestController
@RequestMapping("/messages")
public class MessageController {
	@Autowired
	private MessageRepository messageRepository;

	@ApiMethod(description = "Saves message by user")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Transactional
	@ApiResponseObject
	public ResponseObject<Message> save(@ApiBodyObject @RequestBody RequestObject<Message> ro) {
		ro.amIValid(ValidationType.SAVE_MESSAGES);
		return new ResponseObject<>(true, messageRepository.save(ro.getPayload()));

	}

	@ApiMethod(description = "Saves multile messages by user")
	@RequestMapping(value = "/saveAll", method = RequestMethod.POST)
	@ApiResponseObject
	public ResponseObject<VoidData> saveAll(@ApiBodyObject @RequestBody RequestObject<Message> ro) {
		ro.amIValid(ValidationType.SAVE_ALL_MESSAGES);
		messageRepository.saveByUserData(ro.getPayload());
		return new ResponseObject<>(true);
	}

	@ApiMethod(description = "Deletes message")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional
	@ApiResponseObject
	public ResponseObject<Message> delete(@ApiBodyObject @RequestBody RequestObject<List<Message>> ro) {

		ro.amIValid(ValidationType.DELETE_MESSAGES);

		if (ro.getPayload().size() > 1)
			messageRepository.delete(ro.getPayload());
		else if (ro.getPayload().size() == 1 && ro.getPayload().get(0).getUser().getUid() != 0)
			messageRepository.deleteByUser(ro.getPayload().get(0));
		return new ResponseObject<>(true, ro.getPayload().get(0));

	}

	@ApiMethod(description = "Gets messages by user id or name or tag")
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ApiResponseObject
	public ResponseObject<List<Message>> getByUser(@ApiBodyObject @RequestBody RequestObject<Message> ro)
			throws JsonProcessingException {
		ro.amIValid(ValidationType.GET_BY_USER_MESSAGES);
		return new ResponseObject<>(true, messageRepository.getByUser(ro.getPayload()));


	}
}
