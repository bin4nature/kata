package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {
	
	private static final User REGISTERED_USER = new User();
	private static final User GUEST = null;
	private static final Trip TO_SEOUL = new Trip();
	private static final Trip TO_SOUTH = new Trip();
	private User loggedInUser = new User();
	
	@Mock private TripDAO tripDao = new TripDAO();
	
	@InjectMocks @Spy private TripService tripService = new TripService();
	
	@Test(expected = UserNotLoggedInException.class) public void 
	로그인_유저가_널일_경우_UserNotLoggedInException()
	{
		loggedInUser = GUEST;
		
		User friend = new User();
		friend.addTrip(TO_SEOUL);
		friend.addTrip(TO_SOUTH);
		friend.addFriend(loggedInUser);
		
		tripService.getTripsByUser(friend, loggedInUser);
	}
	
	@Test public void
	친구가_아닐경우_empty_list를_리턴한다() 
	{
		loggedInUser = REGISTERED_USER;
		
		User notFriend = new User();
		notFriend.addTrip(TO_SEOUL);
		notFriend.addTrip(TO_SOUTH);
		
		Assert.assertTrue(tripService.getTripsByUser(notFriend, loggedInUser).isEmpty());
	}
	
	@Test public void
	친구일경우_trip_list를_리턴한다()
	{
		loggedInUser = REGISTERED_USER;
		
		User friend = new User();
		friend.addTrip(TO_SEOUL);
		friend.addTrip(TO_SOUTH);
		friend.addFriend(loggedInUser);
		
		Mockito.when(tripDao.tripsBy(friend)).thenReturn(friend.trips());
		
		
		Assert.assertTrue(tripService.getTripsByUser(friend, loggedInUser).size() == 2);
	}
}
