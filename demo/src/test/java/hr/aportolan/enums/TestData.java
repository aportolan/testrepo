package hr.aportolan.enums;

public enum TestData {
	USER("korisnik"), PASS("dobar dan");

	private final String value;

	private TestData(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public enum Endpoints {
		GET_BY_USER_MESSAGES("/messages/get"), DELETE_MESSAGES("/messages/delete"), SAVE_ALL_MESSAGES(
				"/messages/saveAll"), SAVE_MESSAGES("/messages/save"), SAVE_USERS("/users/save"), SAVE_ALL_USERS(
						"/users/saveAll"), DELETE_USERS("/users/delete"), GET_USERS("/users/get");

		private final String value;

		private Endpoints(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

}
