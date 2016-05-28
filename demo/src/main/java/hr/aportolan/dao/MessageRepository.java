package hr.aportolan.dao;

import org.springframework.data.repository.CrudRepository;

import hr.aportolan.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Long>, CustomMessageRepository {

}
