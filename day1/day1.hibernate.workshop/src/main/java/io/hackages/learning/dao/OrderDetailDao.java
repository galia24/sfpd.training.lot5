package io.hackages.learning.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import io.hackages.learning.model.CustomerOrder;
import io.hackages.learning.model.OrderDetail;
import io.hackages.learning.model.Product;
import io.hackages.learning.util.HibernateUtil;

public class OrderDetailDao {

    public Integer saveOrderDetail(OrderDetail orderDetail) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the customerOrder object
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

    public List< OrderDetail > getOrderDetails() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from OrderDetail", OrderDetail.class).list();
        }
    }

    public OrderDetail getOrderDetail(Integer orderDetailId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(OrderDetail.class, orderDetailId);
        }
    }

/*
	public Product getMostPurchasedProduct1(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Tuple> cr = cb.createTupleQuery();

		Root<OrderDetail> root = cr.from(OrderDetail.class);


		cr.multiselect(cb.tuple(root.get("product")),
				cb.sum(root.get("quantity")));
		cr.groupBy(root.get("product"));
		List<Tuple> result = cb.createQuery(cr).getResultList();


		Query<OrderDetail> query = session.createQuery(cr);
		List<OrderDetail> results = query.getResultList();

		return results.stream().map(result -> result.getInvoiceId()).collect(Collectors.toList());
	}
*/

	public List<Number> getMostPurchasedProduct() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<Number> cr = cb.createQuery(Number.class);

		Root<OrderDetail> root = cr.from(OrderDetail.class);

		Path<Number> quantityExpression = root.get("quantity");
		Expression<Number> maximumExpression = cb.max(quantityExpression);

		cr.select(maximumExpression);

		Query<Number> query = session.createQuery(cr);
		return query.getResultList();
	}
// Idea is to aggregate the orderDetails by product and find the one with the highest quantity
}
