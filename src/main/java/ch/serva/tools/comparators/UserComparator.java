package ch.serva.tools.comparators;

import java.util.Comparator;

import ch.serva.db.User;

public class UserComparator implements Comparator<User> {

	public int compare(User user1, User user2) {

		// compare by username
		return user1.getUsername().compareTo(user2.getUsername());
	}

}
