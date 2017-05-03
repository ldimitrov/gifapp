package com.lyubendimitrov.gifapp.repository;

import com.lyubendimitrov.gifapp.model.Category;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Category> findAll() {
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
        Session session = sessionFactory.openSession();
        Category category = session.get(Category.class, id);
        // Need access to this collection outside of session
        Hibernate.initialize(category.getGifs());
        session.close();
        return category;
    }

    @Override
    public void save(Category category) {
        // TODO add Logging
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(category);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Category category) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(category);
        session.getTransaction().commit();
        session.close();
    }
}
