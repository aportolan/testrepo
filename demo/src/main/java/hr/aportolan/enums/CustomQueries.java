package hr.aportolan.enums;

public final class CustomQueries {

	public static final String UPDATE_ALL_USER_MESSAGES_NAME = "UPDATE_ALL_USER_MESSAGES";
	public static final String UPDATE_ALL_USER_MESSAGES = "MERGE INTO MESSAGE(mid,title,body,validity,uid) KEY(mid) SELECT 1,:title,:body,:validity,uid from user";

	public static final String UPDATE_ALL_USER_MESSAGES_BY_TAG_NAME = "UPDATE_ALL_USER_MESSAGES_BY_TAG";
	public static final String UPDATE_ALL_USER_MESSAGES_BY_TAG = UPDATE_ALL_USER_MESSAGES
			+ " WHERE USER.TAG = :user.tag";

	public static final String UPDATE_ALL_USER_MESSAGES_BY_UID_NAME = "UPDATE_ALL_USER_MESSAGES_BY_UID_NAME";
	public static final String UPDATE_ALL_USER_MESSAGES_BY_UID = UPDATE_ALL_USER_MESSAGES
			+ " WHERE USER.TAG = :user.uid";

	public static final String UPDATE_ALL_USER_MESSAGES_BY_NAME_NAME = "UPDATE_ALL_USER_MESSAGES_BY_NAME_NAME";
	public static final String UPDATE_ALL_USER_MESSAGES_BY_NAME = UPDATE_ALL_USER_MESSAGES
			+ " WHERE USER.NAME = :user.name";

}
