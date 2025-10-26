package com.itson.sistema.integral.de.tramites.vehiculares.dao.impl;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.PersonaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Persona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PersonaDAOImpl implements PersonaDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Sistema-Integral-de-Tramites-Vehiculares_jar_1.0-SNAPSHOTPU");

    @Override
    public List<Persona> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Persona> cq = cb.createQuery(Persona.class);
        Root<Persona> root = cq.from(Persona.class);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Persona buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Persona.class, id);
    }

    @Override
    public Persona buscarPorRFC(String rfc) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Persona> cq = cb.createQuery(Persona.class);
        Root<Persona> root = cq.from(Persona.class);
        cq.select(root).where(cb.equal(root.get("rfc"), rfc));
        List<Persona> resultados = em.createQuery(cq).getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public Persona crear(Persona persona) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(persona);
        em.getTransaction().commit();
        return persona;
    }

    @Override
    public boolean actualizar(Long id, Persona persona) {
        EntityManager em = emf.createEntityManager();
        Persona existente = em.find(Persona.class, persona.getId());
        if (existente == null) {
            System.out.println("La persona con ID " + persona.getId() + " no existe.");
            return false;
        }
        em.getTransaction().begin();

        existente.setRfc(persona.getRfc());
        existente.setNombreCompleto(persona.getNombreCompleto());
        existente.setFechaNacimiento(persona.getFechaNacimiento());
        existente.setTelefono(persona.getTelefono());
        existente.setPais(persona.getPais());

        em.getTransaction().commit();
        return true;
    }

    @Override
    public boolean eliminar(Long id, Persona persona) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(persona)) {
                persona = em.merge(persona);
            }
            em.remove(persona);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

}
