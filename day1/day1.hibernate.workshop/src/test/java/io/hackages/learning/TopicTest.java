package io.hackages.learning;

import io.hackages.learning.dao.CustomerDao;
import io.hackages.learning.dao.CustomerOrderDao;
import io.hackages.learning.dao.OrderDetailDao;
import io.hackages.learning.dao.ProductDao;
import io.hackages.learning.model.*;
import io.hackages.learning.util.HibernateUtil;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.platform.commons.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;

@TestMethodOrder(OrderAnnotation.class)
public class TopicTest {

	private static ProductDao productDao;
	private static CustomerDao customerDao;
	private static OrderDetailDao orderDetailDao;
	private static CustomerOrderDao customerOrderDao;

	@BeforeAll
	public static void setup() {
		productDao = new ProductDao();
		customerDao = new CustomerDao();
		orderDetailDao = new OrderDetailDao();
		customerOrderDao = new CustomerOrderDao();
		System.out.println("Daos created");
	}
	
	@AfterAll
	public static void tearDown() {
		System.out.println("Daos destroyed");
	}	
	
	@Test
	@Order(1)
	public void test_init_db() {
		System.out.println("Running testInitDb...");

		Product product = new Product("MyCat Food 500G", 500);
		Integer id = productDao.saveProduct(product);
		Assertions.assertTrue(id == 1);

		product = new Product("MyDog Food 500G", 500, ProductCategory.DOG_FOOD);
		id = productDao.saveProduct(product);
		Assertions.assertTrue(id == 2);

		product = new Product("MyBird Food 500G", 500, ProductCategory.DOG_FOOD);
		id = productDao.saveProduct(product);
		Assertions.assertTrue(id == 3);

		List<Product> productList = productDao.getProducts();
		Assertions.assertTrue(Objects.nonNull(productList));
		Assertions.assertEquals(3, productList.size());

		String alias = RandomStringUtils.randomAlphabetic(8);
		Customer customer = new Customer(alias, alias.concat("@foo.be"));
		id = customerDao.saveCustomer(customer);
		Assertions.assertTrue(id == 1);

		alias = RandomStringUtils.randomAlphabetic(8);
		customer = new Customer(alias, alias.concat("@foo.be"));
		id = customerDao.saveCustomer(customer);
		Assertions.assertTrue(id == 2);

		List<Customer> customerList = customerDao.getCustomers();
		Assertions.assertTrue(Objects.nonNull(customerList));
		Assertions.assertEquals(2, customerList.size());
		// Can be refactored

		OrderDetail orderDetail = new OrderDetail(new Product("DogFood", 500, ProductCategory.DOG_FOOD), 10);
		orderDetailDao.saveOrderDetail(orderDetail);

		orderDetail = new OrderDetail(new Product("DogFood v10", 100, ProductCategory.DOG_FOOD), 6);
		orderDetailDao.saveOrderDetail(orderDetail);

		orderDetail = new OrderDetail(new Product("DogFood v10", 100, ProductCategory.DOG_FOOD), 8);
		orderDetailDao.saveOrderDetail(orderDetail);

	}

	@Test
	@Order(2)
	public void test_new_orders() {
		System.out.println("Running testInitDbAndCreateOrders...");

		List<Product> productList = productDao.getProducts();
		Assertions.assertFalse(productList.isEmpty());
		Assertions.assertEquals(3, productList.size());

		List<Customer> customerList = customerDao.getCustomers();
		Assertions.assertFalse(customerList.isEmpty());
		Assertions.assertEquals(2, customerList.size());

		OrderDetail orderDetail1 = new OrderDetail(productList.get(0), 10);
		OrderDetail orderDetail2 = new OrderDetail(productList.get(1), 10);
		OrderDetail orderDetail3 = new OrderDetail(productList.get(2), 10);

		CustomerOrder orderForCustomer1 = new CustomerOrder(customerList.get(0), RandomStringUtils.randomAlphanumeric(15));
		orderForCustomer1.addOrderDetail(orderDetail1);
		orderForCustomer1.addOrderDetail(orderDetail2);
		orderForCustomer1.addOrderDetail(orderDetail3);

		Integer orderId = customerOrderDao.saveCustomerOrder(orderForCustomer1);
		Assertions.assertEquals(1, orderId);

		CustomerOrder customerOrder1 = customerOrderDao.getCustomerOrder(orderId);
		Assertions.assertTrue(Objects.nonNull(customerOrder1.getOrderDetails()));
		Assertions.assertFalse(customerOrder1.getOrderDetails().isEmpty());
		Customer customer = customerOrder1.getCustomer();
		Assertions.assertTrue(Objects.nonNull(customer));
		Assertions.assertTrue(StringUtils.endsWith(customer.getEmailAddress(), ".be"));

		UUID uuid = UUID.fromString(customerOrder1.getInvoiceId());
		Assertions.assertTrue(Objects.nonNull(uuid));


		List<String> invoices = customerOrderDao.getGeneratedInvoices();
		Assertions.assertEquals(1 , invoices.size());

		List<Number> mostPurchasedProduct = orderDetailDao.getMostPurchasedProduct();
		System.out.println(mostPurchasedProduct);
	}

}