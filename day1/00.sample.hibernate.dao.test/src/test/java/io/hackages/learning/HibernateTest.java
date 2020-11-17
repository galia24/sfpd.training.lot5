package io.hackages.learning;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import io.hackages.learning.model.Category;
import io.hackages.learning.model.Product;
import io.hackages.learning.dao.ProductDao;

@TestMethodOrder(OrderAnnotation.class)
public class HibernateTest {

	private static ProductDao productDao;
	
	@BeforeAll
	public static void setup() {
		productDao = new ProductDao();
		System.out.println("Dao created");
	}
	
	@AfterAll
	public static void tearDown() {
		System.out.println("Dao destroyed");
	}	
	
	@Test
	@Order(1)
	public void testCreate() {
		System.out.println("Running testCreate...");
		
		Product product = new Product("my Phone v1", 699,	Category.BIRD_FOOD);
		Integer id = (Integer) productDao.saveProduct(product);
		
		Assertions.assertTrue(id > 0);
	}

	@Test
	@Order(2)
	public void testList() {
		System.out.println("Running testList...");
		
		List<Product> resultList = productDao.getProducts();
		
		Assertions.assertFalse(resultList.isEmpty());
		Assertions.assertEquals(resultList.size(), 1);
		Assertions.assertEquals(resultList.get(0).getName(), "my Phone v1");
		Assertions.assertEquals(resultList.get(0).getPrice(), 699);
		Assertions.assertEquals(resultList.get(0).getCreatedAt(), LocalDate.of(2020, 1, 2));
		Assertions.assertEquals(resultList.get(0).getUpdatedAt(), LocalDateTime.of(2020, 3, 4, 5, 6));
		Assertions.assertEquals(resultList.get(0).getCategory(), Category.BIRD_FOOD);

	}
	
}