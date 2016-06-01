package hr.aportolan.dao;

import hr.aportolan.enums.DefaultUsersSetup;

public interface InitDataLoader {
	void loadInitialUsers(DefaultUsersSetup.UserNumber userNumber);

	void loadInitialUsers();
}
