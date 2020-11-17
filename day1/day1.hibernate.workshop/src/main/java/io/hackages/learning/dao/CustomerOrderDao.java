package io.hackages.learning.dao;

import io.hackages.learning.model.CustomerOrder;
import io.hackages.learning.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CustomerOrderDao {

    public Integer saveCustomerOrder(CustomerOrder customerOrder) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the customerOrder object
            Integer id = (Integer) session.save(customerOrder);
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

    public List< CustomerOrder > getCustomerOrders() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CustomerOrder", CustomerOrder.class).list();
        }
    }

    public CustomerOrder getCustomerOrder(Integer orderId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(CustomerOrder.class, orderId);
        }
    }

	public List<String> getGeneratedInvoices(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CustomerOrder> cr = cb.createQuery(CustomerOrder.class);
		Root<CustomerOrder> root = cr.from(CustomerOrder.class);
		cr.select(root);

		Query<CustomerOrder> query = session.createQuery(cr);
		List<CustomerOrder> results = query.getResultList();

		return results.stream().map(result -> result.getInvoiceId()).collect(Collectors.toList());
	}
}
