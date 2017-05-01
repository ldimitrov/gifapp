package com.lyubendimitrov.gifapp.dao;

import com.lyubendimitrov.gifapp.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

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
        return null;
    }

    @Override
    public void save(Category category) {

    }

    @Override
    public void delete(Category category) {

    }
}
