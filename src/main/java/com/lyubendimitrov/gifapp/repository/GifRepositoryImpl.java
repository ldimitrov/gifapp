package com.lyubendimitrov.gifapp.repository;

import com.lyubendimitrov.gifapp.model.Gif;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class GifRepositoryImpl implements GifRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Gif> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Gif> criteria = builder.createQuery(Gif.class);
        criteria.from(Gif.class);

        // Execute Query
        List<Gif> gifs = session.createQuery(criteria).getResultList();

        session.close();
        return gifs;
    }

    @Override
    public Gif findById(Long id) {
        Session session = sessionFactory.openSession();
        Gif gif = session.get(Gif.class, id);

        session.close();
        return gif;
    }

    @Override
    public void save(Gif gif) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(gif);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Gif gif) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(gif);
        session.getTransaction().commit();
        session.close();
    }
}