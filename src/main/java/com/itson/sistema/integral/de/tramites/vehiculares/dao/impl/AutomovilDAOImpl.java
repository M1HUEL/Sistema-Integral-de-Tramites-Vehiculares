package com.itson.sistema.integral.de.tramites.vehiculares.dao.impl;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.AutomovilDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Automovil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class AutomovilDAOImpl implements AutomovilDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Sistema-Integral-de-Tramites-Vehiculares_jar_1.0-SNAPSHOTPU");

    @Override
    public List<Automovil> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Automovil> cq = cb.createQuery(Automovil.class);
        Root<Automovil> root = cq.from(Automovil.class);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Automovil buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Automovil.class, id);
    }

    @Override
    public Automovil buscarPorNumeroSerie(String numeroSerie) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Automovil> cq = cb.createQuery(Automovil.class);
        Root<Automovil> root = cq.from(Automovil.class);
        cq.select(root).where(cb.equal(root.get("numeroSerie"), numeroSerie));
        List<Automovil> resultados = em.createQuery(cq).getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public Automovil crear(Automovil automovil) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(automovil);
        em.getTransaction().commit();
        return automovil;
    }

    @Override
    public boolean actualizar(Automovil automovil) {
        EntityManager em = emf.createEntityManager();
        Automovil existente = em.find(Automovil.class, automovil.getId());
        if (existente == null) {
            return false;
        }

        em.getTransaction().begin();
        existente.setNumeroSerie(automovil.getNumeroSerie());
        existente.setMarca(automovil.getMarca());
        existente.setLinea(automovil.getLinea());
        existente.setColor(automovil.getColor());
        existente.setModelo(automovil.getModelo());
        existente.setPersona(automovil.getPersona());
        em.getTransaction().commit();
        return true;
    }

    @Override
    public boolean eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        Automovil existente = em.find(Automovil.class, id);
        if (existente == null) {
            return false;
        }

        em.getTransaction().begin();
        em.remove(existente);
        em.getTransaction().commit();
        return true;
    }

}
