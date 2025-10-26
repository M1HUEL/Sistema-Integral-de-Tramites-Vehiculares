package com.itson.sistema.integral.de.tramites.vehiculares.dao.impl;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.TramiteDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.TipoTramite;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Tramite;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TramiteDAOImpl implements TramiteDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Sistema-Integral-de-Tramites-Vehiculares_jar_1.0-SNAPSHOTPU");

    @Override
    public List<Tramite> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tramite> cq = cb.createQuery(Tramite.class);
        Root<Tramite> root = cq.from(Tramite.class);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Tramite buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Tramite.class, id);
    }

    @Override
    public List<Tramite> buscarPorTipo(TipoTramite tipo) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tramite> cq = cb.createQuery(Tramite.class);
        Root<Tramite> root = cq.from(Tramite.class);
        cq.select(root).where(cb.equal(root.get("tipoTramite"), tipo));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Tramite> buscarPorPersona(Long personaId) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tramite> cq = cb.createQuery(Tramite.class);
        Root<Tramite> root = cq.from(Tramite.class);
        cq.select(root).where(cb.equal(root.get("persona").get("id"), personaId));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Tramite> buscarPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tramite> cq = cb.createQuery(Tramite.class);
        Root<Tramite> root = cq.from(Tramite.class);
        cq.select(root).where(cb.between(root.get("fechaTramite"), inicio, fin));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Tramite> buscarPorFiltros(Long personaId, TipoTramite tipo, LocalDateTime inicio, LocalDateTime fin) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tramite> cq = cb.createQuery(Tramite.class);
        Root<Tramite> root = cq.from(Tramite.class);

        List<Predicate> predicates = new ArrayList<>();

        if (personaId != null) {
            predicates.add(cb.equal(root.get("persona").get("id"), personaId));
        }
        if (tipo != null) {
            predicates.add(cb.equal(root.get("tipoTramite"), tipo));
        }
        if (inicio != null && fin != null) {
            predicates.add(cb.between(root.get("fechaTramite"), inicio, fin));
        }

        cq.select(root).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Tramite crear(Tramite tramite) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tramite);
        em.getTransaction().commit();
        return tramite;
    }

    @Override
    public Tramite actualizar(Tramite tramite) {
        EntityManager em = emf.createEntityManager();
        Tramite existente = em.find(Tramite.class, tramite.getId());
        if (existente == null) {
            return null;
        }
        em.getTransaction().begin();
        existente.setTipoTramite(tramite.getTipoTramite());
        existente.setCosto(tramite.getCosto());
        existente.setFechaTramite(tramite.getFechaTramite());
        existente.setPersona(tramite.getPersona());
        em.getTransaction().commit();
        return existente;
    }

    @Override
    public boolean eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        Tramite tramite = em.find(Tramite.class, id);
        if (tramite == null) {
            return false;
        }
        em.getTransaction().begin();
        em.remove(tramite);
        em.getTransaction().commit();
        return true;
    }

}
