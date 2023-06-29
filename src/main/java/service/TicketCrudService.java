package service;

import entity.Client;
import entity.Planet;
import entity.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TicketCrudService {
    private final SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

    public void create(String createdAt, Client client, Planet fromPlanet, Planet toPlanet) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Ticket ticket = new Ticket();
            ticket.setCreatedAt(createdAt);
            ticket.setClient(client);
            ticket.setFromPlanet(fromPlanet);
            ticket.setToPlanet(toPlanet);
            session.persist(ticket);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if(client==null)System.out.println("\nне можна зберегти квиток для неіснуючого або null клієнта");
            if(fromPlanet==null || toPlanet==null)System.out.println("\nне можна зберегти квиток для неіснуючої або null планети");
        } finally {
            session.close();
        }
    }

    public Ticket get(long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Ticket.class, id);
        } finally {
            session.close();
        }
    }

    public void update(Client client, long id) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            Ticket ticket = session.get(Ticket.class, id);
            ticket.setClient(client);
            session.persist(ticket);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    public void delete(long id) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, id);
            session.remove(ticket);
            transaction.commit();
        } finally {
            session.close();
        }
    }
}
