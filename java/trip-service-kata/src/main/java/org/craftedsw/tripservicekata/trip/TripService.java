package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.springframework.beans.factory.annotation.Autowired;

public class TripService {
	
	@Autowired
	private TripDAO tripDAO;
	
	public List<Trip> getTripsByUser(User user, User loggedUser) throws UserNotLoggedInException {
		validateLoggedInUserIsNotNull(loggedUser);
		
		return user.isFriendWith(loggedUser) 
				? tripsBy(user) 
				: new ArrayList<Trip>();
	}

	private void validateLoggedInUserIsNotNull(User loggedInUser) throws UserNotLoggedInException
	{
		if (loggedInUser == null)
		{
			throw new UserNotLoggedInException();
		}
	}

	private List<Trip> tripsBy(User user) {
		return tripDAO.tripsBy(user);
	}
}
