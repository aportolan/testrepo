package hr.aportolan.dao;

import org.springframework.data.repository.CrudRepository;

import hr.aportolan.domain.User;

public interface UserRepository extends CrudRepository<User, Long>, CustomUserRepository {

}
