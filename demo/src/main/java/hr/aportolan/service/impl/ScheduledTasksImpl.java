package hr.aportolan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hr.aportolan.dao.MessageRepository;
import hr.aportolan.service.ScheduledTasks;

@Service
public class ScheduledTasksImpl implements ScheduledTasks {

	@Autowired
	private MessageRepository messageRepository;

	@Scheduled(fixedRate = 5000)
	@Override
	public void deleteExpired() {
		messageRepository.deleteExpired();
	}
}