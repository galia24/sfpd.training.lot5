package io.hackages.learning.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import io.hackages.learning.model.Product;
import io.hackages.learning.util.HibernateUtil;

public class ProductDao {
    
    public Integer saveProduct(Product product) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the product object
            Integer id = (Integer) session.save(product);
            // commit transaction
            transaction.commit();

            return id;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public List < Product > getProducts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Product", Product.class).list();
        }
    }
}