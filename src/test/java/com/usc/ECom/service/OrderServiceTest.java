package com.usc.ECom.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.aopalliance.intercept.Invocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.usc.ECom.beans.OrderProduct;
import com.usc.ECom.beans.Product;
import com.usc.ECom.beans.Order;
import com.usc.ECom.beans.User;
import com.usc.ECom.dao.OrderDao;
import com.usc.ECom.dao.ProductDao;
import com.usc.ECom.dao.UserDao;
import com.usc.ECom.http.Response;
import com.usc.ECom.security.SecurityUtils;

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
	@InjectMocks// no need?
	private OrderService orderService;
	@Test
	public void deleteOrderById() {
		int id=1;
		Order mockOrder= Mockito.mock(Order.class);
		when (orderDao.findById(id)).thenReturn(Optional.of(mockOrder));
		Response response= orderService.deleteOrder(id);
		assertTrue(response.isSuccess());
		verify(orderDao, times(1)).deleteById(id);// Verify that deleteById was called exactly once
		
		when(orderDao.findById(id)).thenReturn(Optional.empty());
		response = orderService.deleteOrder(id);
		assertFalse(response.isSuccess());
		assertEquals("The Order is not found.", response.getMessage()); 
		verify(orderDao, times(1)).deleteById(id); // deleteById should not be called again
		
	}
	@Test
	void testAddOrder_SuccessfulUser() {
		Authentication authentication=mock(Authentication.class);
	    //when(authentication.getAuthorities()).thenAnswer(invocation-> List.of(new SimpleGrantedAuthority("ROLE_USER")));
	    when(authentication.getName()).thenReturn("testUser");
		User user = new User(); // Replace with your user creation logic
	    user.setUsername("testUser");
	    when(userDao.findByUsername("testUser")).thenReturn(user);
	    Product product=new Product();
	    product.setId(1);
	    when(productDao.findById(1)).thenReturn(Optional.of(product));
	    OrderProduct orderProduct=new OrderProduct();
	    orderProduct.setProduct(product);
	    Order order = new Order(new Date(), List.of(orderProduct));
	    order.setUser(user);

	    Response result = orderService.addOrder(order, authentication);
	    // Assert
	    verify(orderDao, times(1)).save(any(Order.class));//?????????????
	    assertTrue(result.isSuccess());
//	    assertNotNull(orderDao.findById(order.getId()));//test them all?
//	    assertEquals(1, orderDao.findById(order.getId()).get().getPurchases().size());
//	    //assertEquals(product, orderDao.findById(order.getId()).get().getPurchases().get(0).getProduct());
//	    assertEquals(user, orderDao.findById(order.getId()).get().getUser());
	}
	
	@Test
	public void testGetOrders_AdminRole() {
		// Given: User with ROLE_ADMIN
		Authentication authentication = mock(Authentication.class);
		// when(authentication.getAuthorities()).thenReturn((Collection<? extends
		// GrantedAuthority>) List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
		when(authentication.getAuthorities())
				.thenAnswer(invocation -> List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
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
		// when(authentication.getAuthorities()).thenReturn((Collection<? extends
		// GrantedAuthority>) List.of(new SimpleGrantedAuthority("ROLE_USER")));
		when(authentication.getAuthorities())
				.thenAnswer(invocation -> List.of(new SimpleGrantedAuthority("ROLE_USER")));
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
		assertThrows(NoSuchElementException.class, () -> orderService.findById(id)); 
		// throw new NoSuchElementException("No value present");
		// Assertions.assertThat(order).isNull();assertNull(order); throw new
		// NoSuchElementException("No value present");
	}
}

// helper methods:
//private Authentication createAdminAuthentication() {
//    UserDetails userDetails = new org.springframework.security.core.userdetails.User("admin", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
//    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//}
//
//private Authentication createUserAuthentication() {
//    UserDetails userDetails = new org.springframework.security.core.userdetails.User("user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
//    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//}
//
//private Order createOrderWithProducts() {
//    Order order = new Order(new Date(), Arrays.asList(createOrderProduct()));
//    return order;
//}
//
//private OrderProduct createOrderProduct() {
//    OrderProduct op = new OrderProduct(1, null, product);
//    return op;
//}
//@Test
//public void testAddOrder_ProductNotFound() {
//    // Mocking
//    when(authentication.getName()).thenReturn("testuser");
//    when(userDao.findByUsername("testuser")).thenReturn(user);
//    when(productDao.findById(1)).thenReturn(Optional.empty());
//
//    // Test
//    Response response = orderService.addOrder(order, authentication);
//
//    // Verify
//    assertEquals(false, response.isSuccess());
//    verify(orderDao, never()).save(order); // Ensure save is never called
//}
//
//@Test
//public void testAddOrder_UserNotFound() {
//    // Mocking
//    when(authentication.getName()).thenReturn("testuser");
//    when(userDao.findByUsername("testuser")).thenReturn(null);
//
//    // Test
//    Response response = orderService.addOrder(order, authentication);
//
//    // Verify
//    assertEquals(false, response.isSuccess());
//    verify(orderDao, never()).save(order); // Ensure save is never called
//}
//
//@Test
//public void testAddOrder_ExceptionHandling() {
//    // Mocking
//    when(authentication.getName()).thenReturn("testuser");
//    when(userDao.findByUsername("testuser")).thenThrow(new RuntimeException());
//
//    // Test
//    Response response = orderService.addOrder(order, authentication);
//
//    // Verify
//    assertEquals(false, response.isSuccess());
//    verify(orderDao, never()).save(order); // Ensure save is never called
//}