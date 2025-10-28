package com.itson.sistema.integral.de.tramites.vehiculares.dao.impl;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.PersonaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Licencia;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Persona;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Placa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PersonaDAOImpl implements PersonaDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Sistema-Integral-de-Tramites-Vehiculares_jar_1.0-SNAPSHOTPU");

    @Override
    public List<Persona> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Persona> cq = cb.createQuery(Persona.class);
            Root<Persona> root = cq.from(Persona.class);
            cq.select(root);
            return em.createQuery(cq).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Persona buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Persona buscarPorRFC(String rfc) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Persona p WHERE p.rfc = :rfc", Persona.class)
                    .setParameter("rfc", rfc)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Persona crear(Persona persona) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(persona);
            em.getTransaction().commit();
            return persona;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al crear la persona", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean actualizar(Long id, Persona persona) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Persona existente = em.find(Persona.class, id);

            if (existente == null) {
                em.getTransaction().rollback();
                return false;
            }

            existente.setRfc(persona.getRfc());
            existente.setNombreCompleto(persona.getNombreCompleto());
            existente.setFechaNacimiento(persona.getFechaNacimiento());
            existente.setTelefono(persona.getTelefono());
            existente.setPais(persona.getPais());

            existente.getLicencias().clear();
            for (Licencia licencia : persona.getLicencias()) {
                licencia.setPersona(existente);
                existente.getLicencias().add(licencia);
            }

            existente.getPlacas().clear();
            for (Placa placa : persona.getPlacas()) {
                placa.setPersona(existente);
                existente.getPlacas().add(placa);
            }

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Persona existente = em.find(Persona.class, id);

            if (existente == null) {
                em.getTransaction().rollback();
                return false;
            }

            em.remove(existente);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
