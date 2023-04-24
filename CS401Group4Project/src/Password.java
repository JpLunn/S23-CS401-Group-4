
public class Password {
	private String password;
	private int length;
	private String[] validCharacters;
	
	
	public Password(String pw) {
		password = pw;
	}
	
	public bool checkPassword(String pw) {
		if(password.contentEquals(pw)) {
			return true;
		}
		else
			return false;
	}
	
	
}
