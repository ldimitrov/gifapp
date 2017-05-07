package com.lyubendimitrov.gifapp.repository;

import com.lyubendimitrov.gifapp.model.Category;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Category> findAll() {
        LOG.info("Fetching all categories");
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        criteria.from(Category.class);

        // Execute Query
        List<Category> categories = session.createQuery(criteria).getResultList();
        session.close();
        return categories;
    }

    @Override
    public Category findById(Long id) {
        LOG.info("Finding Category with id {}", id);
        Session session = sessionFactory.openSession();
        try {
            Category category = session.get(Category.class, id);
            // Need access to this collection outside of session
            Hibernate.initialize(category.getGifs());
            return category;
        } catch (RuntimeException re) {
            LOG.error("Finding Category by id failed", re);
            throw re;
        } finally {
            session.close();
        }
    }

    @Override
    public void save(Category category) {
        LOG.info("Saving Category");
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(category);
            session.getTransaction().commit();
        } catch (RuntimeException re) {
            LOG.error("Saving Category failed", re);
            throw re;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Category category) {
        LOG.info("Deleting Category");
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(category);
            session.getTransaction().commit();
        } catch (RuntimeException re) {
            LOG.error("Deleting Category failed", re);
            throw re;
        } finally {
            session.close();
        }
    }
}
