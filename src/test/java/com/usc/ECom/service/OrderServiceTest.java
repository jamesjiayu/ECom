package com.usc.ECom.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.usc.ECom.beans.OrderProduct;
import com.usc.ECom.beans.Order;
import com.usc.ECom.beans.User;
import com.usc.ECom.dao.OrderDao;
import com.usc.ECom.dao.ProductDao;
import com.usc.ECom.dao.UserDao;
import com.usc.ECom.security.SecurityUtils;

//import static org.powermock.api.mockito.PowerMockito.mockStatic;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.junit.runner.RunWith;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(SecurityUtils.class)
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	@Mock
	private ProductDao productDao;
	@Mock
	private OrderDao orderDao;
//	@Mock // Mockito annotation : AI gave me
//    private SecurityUtils securityUtils;
	@Mock
	private Authentication authentication;
	@Mock
	private UserDao userDao;
	@InjectMocks
	private OrderService orderService;

	@Test
	public void findAllOrder() {
		Authentication authentication = mock(Authentication.class);
		User user1 = new User();
		Order order1 = new Order(new Date(), new ArrayList<OrderProduct>());
		order1.setUser(user1);
		User user2 = new User();
		Order order2 = new Order(new Date(), new ArrayList<OrderProduct>());
		order2.setUser(user2);

		when(orderDao.findAll()).thenReturn(List.of(order1, order2));
		when(authentication.getName()).thenReturn("testuser");
		when(userDao.findByUsername("testuser")).thenReturn(user1);
		// when(authentication.getAuthorities()).thenReturn(List.of(new
		// SimpleGrantedAuthority("ROLE_ADMIN")));
		// Mocking static method SecurityUtils.isAdmin() if needed
//	    mockStatic(SecurityUtils.class);
//	    when(SecurityUtils.isAdmin(any())).thenReturn(true);	    
		List<Order> orderList = orderService.getOrders(authentication);
		assertEquals(2, orderList.size());
		assertEquals(user1, orderList.get(0).getUser());
	}

	@Test
	public void testGetOrders_AdminRole() {
		// Given: User with ROLE_ADMIN
		Authentication authentication = mock(Authentication.class);
		when(authentication.getAuthorities()).thenReturn(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

		// Mock data for admin scenario
		Order order1 = new Order(new Date(), new ArrayList<>());
		Order order2 = new Order(new Date(), new ArrayList<>());

		when(orderDao.findAll()).thenReturn(List.of(order1, order2));

		// When: Service method is called
		List<Order> orderList = orderService.getOrders(authentication);

		// Then: All orders are returned
		assertEquals(2, orderList.size());
	}

	@Test
	public void testGetOrders_UserRole() {
		// Given: User with ROLE_USER
		Authentication authentication = mock(Authentication.class);
		when(authentication.getAuthorities()).thenReturn(List.of(new SimpleGrantedAuthority("ROLE_USER")));
		when(authentication.getName()).thenReturn("testuser");

		// Mock data for user scenario
		User user = new User();
		user.setId(1); // Set user ID to match the expected ID
		Order order = new Order(new Date(), new ArrayList<>());
		order.setUser(user);

		when(userDao.findByUsername("testuser")).thenReturn(user);
		when(orderDao.findByUserId(user.getId())).thenReturn(List.of(order));

		// When: Service method is called
		List<Order> orderList = orderService.getOrders(authentication);

		// Then: Only orders belonging to the authenticated user are returned
		assertEquals(1, orderList.size());
		assertEquals(user, orderList.get(0).getUser());
	}

	@Test
	public void findOrderById_WhenOrderExist() {
		int id = 1;
		Order mockOrder = Mockito.mock(Order.class);
		// when((Order)orderDao.findById(id).get()).thenReturn(mockOrder); //get()
		// throws: NoSuchElementException("No value present");
		when(orderDao.findById(id)).thenReturn(Optional.of(mockOrder));

		Order order = orderService.findById(id);

		assertSame(mockOrder, order);
	}

	@Test
	public void findOrderById_WhenOrderNotExist() {
		int id = 2;
		Mockito.mock(Order.class);// Order mockOrder=
		when(orderDao.findById(id)).thenReturn(Optional.empty());
		// Order order= orderService.findById(id);
		assertThrows(NoSuchElementException.class, () -> orderService.findById(id)); // public static <T extends
																						// Throwable> T
																						// assertThrows(Class<T>
																						// expectedType, Executable
																						// executable) { return
																						// AssertThrows.assertThrows(expectedType,
																						// executable);
		// throw new NoSuchElementException("No value present");
		// Assertions.assertThat(order).isNull();assertNull(order); throw new
		// NoSuchElementException("No value present");
	}

//	@Test
//	public void deleteOrderById() {
//		Date purchaseDate = new Date();
//	    List<OrderProduct> purchases = new ArrayList<>();
//	    User user = new User(/* Set user properties here */); // Replace with your user creation logic
//	    Order order = new Order(purchaseDate, purchases);
//	    order.setUser(user);
//		int id=1;
//		Order mockOrder= Mockito.mock(Order.class);
//		when(orderDao.deleteById(id)).thenReturn()
//	}

//	@Test
//	public void addOrder_SuccessfulCreation() {
//	    // Arrange
//	    Date purchaseDate = new Date();
//	    List<OrderProduct> purchases = new ArrayList<>();
//	    User user = new User(/* Set user properties here */); // Replace with your user creation logic
//	    Order order = new Order(purchaseDate, purchases);
//	    order.setUser(user);
//	    // Act
//	    Order addedOrder = orderService.addOrder(order); // addOrder(Order order, Authentication authentication)
//	    // Assert
//	    assertNotNull(addedOrder);
//	    assertEquals(order.getPurchase_date(), addedOrder.getPurchase_date());
//	    assertEquals(order.getPurchases(), addedOrder.getPurchases());
//	    assertEquals(order.getUser(), addedOrder.getUser());
//	}

}

//@Test //gemini ai gave me
//public List<Order> findAllOrder(Authentication authentication) {
//  if (securityUtils.isAdmin(authentication.getAuthorities())) {
//      return (List<Order>) orderDao.findAll();
//  }
//
//  User user = userDao.findByUsername(authentication.getName());
//  if (user != null) {
//      return orderDao.findByUserId(user.getId());
//  } else {
//      // Handle user not found scenario (e.g., return empty list)
//      return Collections.emptyList();
//  }
//}
