package com.itson.sistema.integral.de.tramites.vehiculares.dao.impl;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.PlacaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dominio.Placa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PlacaDAOImpl implements PlacaDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Sistema-Integral-de-Tramites-Vehiculares_jar_1.0-SNAPSHOTPU");

    @Override
    public List<Placa> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Placa> cq = cb.createQuery(Placa.class);
            Root<Placa> root = cq.from(Placa.class);
            cq.select(root);
            return em.createQuery(cq).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Placa buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Placa.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Placa buscarPorNumeroPlaca(String numeroPlaca) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Placa p WHERE p.numeroPlaca = :numeroPlaca", Placa.class)
                    .setParameter("numeroPlaca", numeroPlaca)
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
    public List<Placa> buscarPorAutomovilId(Long automovilId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Placa p WHERE p.automovil.id = :automovilId",
                    Placa.class)
                    .setParameter("automovilId", automovilId)
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
    public Placa crear(Placa placa) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(placa);
            em.getTransaction().commit();
            return placa;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al crear la placa", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean actualizar(Placa placa) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Placa existente = em.find(Placa.class, placa.getId());

            if (existente == null) {
                em.getTransaction().rollback();
                return false;
            }

            existente.setNumeroPlaca(placa.getNumeroPlaca());
            existente.setFechaEmision(placa.getFechaEmision());
            existente.setFechaRecepcion(placa.getFechaRecepcion());
            existente.setCosto(placa.getCosto());

            existente.setAutomovil(placa.getAutomovil());
            existente.setPersona(placa.getPersona());

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
            Placa existente = em.find(Placa.class, id);

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
