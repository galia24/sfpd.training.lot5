package io.hackages.learning.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import io.hackages.learning.model.Order;
import io.hackages.learning.model.OrderDetail;
import io.hackages.learning.util.HibernateUtil;

public class OrderDetailDao {
    
    public Integer saveOrderDetail(OrderDetail orderDetail) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the product object
            Integer id = (Integer) session.save(orderDetail);
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

    public List < OrderDetail > getOrderDetails() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from OrderDetail", OrderDetail.class).list();
        }
    }
}