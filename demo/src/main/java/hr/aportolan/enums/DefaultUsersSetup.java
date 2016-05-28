package hr.aportolan.enums;

public enum DefaultUsersSetup {

	NAME_URL("http://www.quietaffiliate.com/Files/CSV_Database_of_First_Names.csv"), LAST_NAME_URL(
			"http://www.quietaffiliate.com/Files/CSV_Database_of_Last_Names.csv");
	private final String value;

	private DefaultUsersSetup(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public enum UserNumber {
		USER_NUMBER(100000), TEST_USER_NUMBER(10);
		private final int value;

		private UserNumber(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

}
