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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.BDDMockito.given;
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
import com.usc.ECom.service.OrderService;

@WebMvcTest(controllers = OrderController.class)
@ExtendWith(MockitoExtension.class)
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

	@BeforeEach //@BeforeTest 
	public void setup() {
	    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
	            .apply(springSecurity())
	            .build();
	}
	@Test
	public void Controller_CreateOrder_ReturnCreated() throws Exception {
		order = new Order(new Date(), new ArrayList<OrderProduct>());
		order.setId(1);
		user=new User(4,"testUser","pwd",new ArrayList<UserProfile>());
		order.setUser(user);
		// Mocking the service behavior
		given(orderService.addOrder(ArgumentMatchers.any(Order.class), ArgumentMatchers.any()))
				.willReturn(new Response(true));

		// Performing an HTTP POST request to create an employee
		mockMvc.perform(
	            post("/orders")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(objectMapper.writeValueAsString(order))
	                    .with(user("testUser").roles("USER")))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.success", CoreMatchers.is(true)));
			}

}
