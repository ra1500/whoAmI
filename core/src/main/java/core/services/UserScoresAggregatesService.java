/**

package core.services;

import db.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
public class UserScoresAggregatesService {

    private static StandardServiceRegistry registry;
    private static SessionFactory sf;

    public UserScoresAggregatesService() {


        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        //registryBuilder.applySettings(settings);
        registry = registryBuilder.build();
        MetadataSources sources = new MetadataSources(registry);
        Metadata metadata = sources.getMetadataBuilder().build();
        sf = metadata.getSessionFactoryBuilder().build();

        //SessionFactory sf=  new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        session.beginTransaction();

        CriteriaQuery<Object> crt1 = cb.createQuery(Object.class);
        Root<UserEntity> root1 = crt1.from(UserEntity.class);

        crt1.select(cb.sum(root1.get("answerPoints")));
        Query query1 = session.createQuery(crt1);
        Object answerPoints = query1.getSingleResult();

        System.out.println("User's total score : " + answerPoints);
        System.out.println();

        session.getTransaction().commit();
        session.close();
    }
}
**/