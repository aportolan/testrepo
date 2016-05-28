package hr.aportolan.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.aportolan.dao.MessageRepository;
import hr.aportolan.domain.Message;
import hr.aportolan.dto.RequestObject;
import hr.aportolan.dto.ResponseObject;
import hr.aportolan.dto.ValidationType;
import hr.aportolan.dto.VoidData;

@RestController
@RequestMapping("/messages")
public class MessageController {
	@Autowired
	private MessageRepository messageRepository;

	@RequestMapping("/save")
	public ResponseObject<Message> save(RequestObject<Message> ro) {
		ro.amIValid(ValidationType.SAVE_MESSAGES);
		return new ResponseObject<>(true, messageRepository.save(ro.getPayload()));

	}

	@RequestMapping("/saveAll")
	public ResponseObject<VoidData> saveAll(RequestObject<Message> ro) {
		ro.amIValid(ValidationType.SAVE_ALL_MESSAGES);
		messageRepository.saveByUserData(ro.getPayload());
		return new ResponseObject<>(true);
	}

	@RequestMapping("/delete")
	public ResponseObject<VoidData> delete(RequestObject<List<Message>> ro) {// briši

		ro.amIValid(ValidationType.DELETE_MESSAGES);

		if (ro.getPayload().size() > 1)
			messageRepository.delete(ro.getPayload());
		else if (ro.getPayload().size() == 1 && ro.getPayload().get(0).getUser().getUid() != 0)
			messageRepository.deleteByUser(ro.getPayload().get(0));
		return new ResponseObject<>(true);

	}

	@RequestMapping("/getByUser")
	public ResponseObject<List<Message>> getMessagesByUser(RequestObject<Message> ro) {
		ro.amIValid(ValidationType.GET_BY_USER_MESSAGES);
		return new ResponseObject<>(true, messageRepository.getByUser(ro.getPayload()));
	}
}
