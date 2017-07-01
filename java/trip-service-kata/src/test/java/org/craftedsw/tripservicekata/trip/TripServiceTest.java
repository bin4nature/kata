package org.craftedsw.tripservicekata.trip;

import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Assert;
import org.junit.Test;

public class TripServiceTest {
	
	private static final User GUEST = null;
	private static final User UNUSED_USER = null;
	private static final User REGISTERED_USER = new User();
	private static final Trip TO_SEOUL = new Trip();
	private static final Trip TO_SOUTH = new Trip();
	private User loggedInUser;
	
	@Test(expected = UserNotLoggedInException.class) public void 
	로그인_유저가_널일_경우_UserNotLoggedInException()
	{
		TripService service = new TestableTripService();
		
		loggedInUser = GUEST;
		
		service.getTripsByUser(UNUSED_USER);
		
	}
	
	@Test public void
	친구가아닐경우_empty_list를_리턴한다() 
	{
		TripService service = new TestableTripService();
		
		loggedInUser = REGISTERED_USER;
		
		Assert.assertTrue(service.getTripsByUser(loggedInUser).isEmpty());
	}
	
	@Test public void
	친구일경우_trip_list를_리턴한다() 
	{
		TripService service = new TestableTripService();
		
		loggedInUser = REGISTERED_USER;
		
		User friend = new User();
		friend.addFriend(loggedInUser);
		friend.addTrip(TO_SEOUL);
		friend.addTrip(TO_SOUTH);
		
		Assert.assertTrue(service.getTripsByUser(friend).size() == 2);
	}
	
	private class TestableTripService extends TripService
	{
		@Override
		protected User getLoggedInUser() {
			return loggedInUser;
		}
		
		@Override
		protected List<Trip> tripsBy(User user) {
			return user.trips();
		}
	}
}
