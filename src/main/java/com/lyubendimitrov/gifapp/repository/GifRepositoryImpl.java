package com.lyubendimitrov.gifapp.repository;

import com.lyubendimitrov.gifapp.model.Gif;
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
public class GifRepositoryImpl implements GifRepository {

    private static final Logger LOG = LoggerFactory.getLogger(GifRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Gif> findAll() {
        LOG.info("Fetching all gifs");
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
        LOG.info("Finding GIF with id {}", id);
        Session session = sessionFactory.openSession();
        try {
            Gif gif = session.get(Gif.class, id);
            return gif;
        } catch (RuntimeException re) {
            LOG.error("Finding GIF by id failed", re);
            throw re;
        } finally {
            session.close();
        }
    }

    @Override
    public void save(Gif gif) {
        LOG.info("Saving GIF instance");
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(gif);
            session.getTransaction().commit();
        } catch (RuntimeException re) {
            LOG.error("Finding GIF by id failed", re);
            throw re;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Gif gif) {
        LOG.info("Deleting GIF instance");
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(gif);
            session.getTransaction().commit();
        } catch (RuntimeException re) {
            LOG.error("Deleting GIF failed", re);
            throw re;
        } finally {
            session.close();
        }
    }
}
