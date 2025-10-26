package com.itson.sistema.integral.de.tramites.vehiculares.dao.impl;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.LicenciaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Licencia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class LicenciaDAOImpl implements LicenciaDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Sistema-Integral-de-Tramites-Vehiculares_jar_1.0-SNAPSHOTPU");

    @Override
    public List<Licencia> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Licencia> cq = cb.createQuery(Licencia.class);
        Root<Licencia> root = cq.from(Licencia.class);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Licencia buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Licencia.class, id);
    }

    @Override
    public List<Licencia> buscarPorPersonaId(Long personaId) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Licencia> cq = cb.createQuery(Licencia.class);
        Root<Licencia> root = cq.from(Licencia.class);
        cq.select(root).where(cb.equal(root.get("persona").get("id"), personaId));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Licencia crear(Licencia licencia) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(licencia);
        em.getTransaction().commit();
        return licencia;
    }

    @Override
    public boolean actualizar(Licencia licencia) {
        EntityManager em = emf.createEntityManager();
        Licencia existente = em.find(Licencia.class, licencia.getId());
        if (existente == null) {
            return false;
        }

        em.getTransaction().begin();
        existente.setFechaExpedicion(licencia.getFechaExpedicion());
        existente.setVigenciaAnios(licencia.getVigenciaAnios());
        existente.setMonto(licencia.getMonto());
        existente.setDiscapacitado(licencia.isDiscapacitado());
        existente.setPersona(licencia.getPersona());
        em.getTransaction().commit();
        return true;
    }

    @Override
    public boolean eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        Licencia existente = em.find(Licencia.class, id);
        if (existente == null) {
            return false;
        }

        em.getTransaction().begin();
        em.remove(existente);
        em.getTransaction().commit();
        return true;
    }

}
