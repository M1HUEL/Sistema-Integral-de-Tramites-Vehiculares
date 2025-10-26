package com.itson.sistema.integral.de.tramites.vehiculares.dao.impl;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.PlacaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Placa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PlacaDAOImpl implements PlacaDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Sistema-Integral-de-Tramites-Vehiculares_jar_1.0-SNAPSHOTPU");

    @Override
    public List<Placa> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Placa> cq = cb.createQuery(Placa.class);
        Root<Placa> root = cq.from(Placa.class);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Placa buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Placa.class, id);
    }

    @Override
    public Placa buscarPorNumeroPlaca(String numeroPlaca) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Placa> cq = cb.createQuery(Placa.class);
        Root<Placa> root = cq.from(Placa.class);
        cq.select(root).where(cb.equal(root.get("numeroPlaca"), numeroPlaca));
        List<Placa> resultados = em.createQuery(cq).getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public List<Placa> buscarPorAutomovilId(Long automovilId) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Placa> cq = cb.createQuery(Placa.class);
        Root<Placa> root = cq.from(Placa.class);
        cq.select(root).where(cb.equal(root.get("automovil").get("id"), automovilId));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Placa crear(Placa placa) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(placa);
        em.getTransaction().commit();
        return placa;
    }

    @Override
    public boolean actualizar(Placa placa) {
        EntityManager em = emf.createEntityManager();
        Placa existente = em.find(Placa.class, placa.getId());
        if (existente == null) {
            return false;
        }

        em.getTransaction().begin();
        existente.setNumeroPlaca(placa.getNumeroPlaca());
        existente.setFechaEmision(placa.getFechaEmision());
        existente.setFechaRecepcion(placa.getFechaRecepcion());
        existente.setCosto(placa.getCosto());
        existente.setAutomovil(placa.getAutomovil());
        em.getTransaction().commit();
        return true;
    }

    @Override
    public boolean eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        Placa existente = em.find(Placa.class, id);
        if (existente == null) {
            return false;
        }

        em.getTransaction().begin();
        em.remove(existente);
        em.getTransaction().commit();
        return true;
    }

}
