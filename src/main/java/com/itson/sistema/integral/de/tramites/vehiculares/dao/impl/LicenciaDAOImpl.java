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
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Licencia> cq = cb.createQuery(Licencia.class);
            Root<Licencia> root = cq.from(Licencia.class);
            cq.select(root);
            return em.createQuery(cq).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Licencia buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Licencia.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Licencia> buscarPorPersonaId(Long personaId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT l FROM Licencia l WHERE l.persona.id = :personaId",
                    Licencia.class)
                    .setParameter("personaId", personaId)
                    .getResultList();
        } catch (Exception e) {
            return List.of();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Licencia crear(Licencia licencia) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(licencia);
            em.getTransaction().commit();
            return licencia;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al crear la licencia", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean actualizar(Licencia licencia) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Licencia existente = em.find(Licencia.class, licencia.getId());

            if (existente == null) {
                em.getTransaction().rollback();
                return false;
            }

            existente.setFechaExpedicion(licencia.getFechaExpedicion());
            existente.setVigenciaAnios(licencia.getVigenciaAnios());
            existente.setMonto(licencia.getMonto());
            existente.setDiscapacitado(licencia.isDiscapacitado());
            existente.setPersona(licencia.getPersona());

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
            Licencia existente = em.find(Licencia.class, id);

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
