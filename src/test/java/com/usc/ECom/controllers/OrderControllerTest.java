package com.usc.ECom.controllers;

import java.util.*;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usc.ECom.beans.Order;
import com.usc.ECom.beans.OrderProduct;
import com.usc.ECom.beans.User;
import com.usc.ECom.beans.UserProfile;
import com.usc.ECom.http.Response;
import com.usc.ECom.security.TestSecurityConfig;
import com.usc.ECom.service.OrderService;

@WebMvcTest(controllers = OrderController.class)
@ExtendWith(MockitoExtension.class)
@Import(TestSecurityConfig.class)
public class OrderControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private OrderService orderService;
	@Autowired
	private ObjectMapper objectMapper;
	private Order order;
	private User user;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach // @BeforeTest
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
	}

	@Test
	public void testAddOrder() throws Exception {
		order = new Order(new Date(), new ArrayList<OrderProduct>());
		order.setId(1);
//		user=new User(4,"testUser","pwd",new ArrayList<UserProfile>());
//		order.setUser(user);
		// Mocking the service behavior
		when(orderService.addOrder(ArgumentMatchers.any(), ArgumentMatchers.any()))// ArgumentMatchers.any(Order.class)
				.thenReturn(new Response(true));// given().willReturn();

		// Performing an HTTP POST request to create an employee
		mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(order)).with(user("testUser1").roles("USER")))
				.andExpect(status().isOk()).andExpect(jsonPath("$.success", CoreMatchers.is(true)));
	}

	@Test
	public void testDeleteOrder() throws Exception {
		int id = 1;
		given(orderService.deleteOrder(anyInt())).willReturn(new Response(true));// anyInt()
		mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{id}", id).with(user("1111").roles("USER")))
				.andExpect(status().isOk()).andExpect(jsonPath("$.success", CoreMatchers.is(true)));
	}

	@Test
	public void testGetOrders() throws Exception {
		Order order1 = new Order(new Date(), new ArrayList<>());
		order1.setId(1);
		Order order2 = new Order(new Date(), new ArrayList<>());
		order2.setId(2);
		given(orderService.getOrders(ArgumentMatchers.any())).willReturn(Arrays.asList(order1,order2));
		mockMvc.perform(MockMvcRequestBuilders.get("/orders").with(user("aaa").roles("ADMIN")))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].id", CoreMatchers.is(1)))
				.andExpect(jsonPath("$[1].id", CoreMatchers.is(2)));
		// .andExpect(jsonPath("$.success", CoreMatchers.is(true)));//The response body
		// contains a JSON object with a success field that is true.

	}

	@Test
	public void testGetOrderById() throws Exception {
		int id=1; 
		Order order1 = new Order(new Date(), new ArrayList<>());
		order1.setId(id);
		given(orderService.findById(anyInt())).willReturn(order1);
		mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", id)
				.with(user("aaa").roles("USER"))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", CoreMatchers.is(1)));
	}
}
