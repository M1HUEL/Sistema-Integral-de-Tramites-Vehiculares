package com.itson.sistema.integral.de.tramites.vehiculares.dao.impl;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.AutomovilDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Automovil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class AutomovilDAOImpl implements AutomovilDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Sistema-Integral-de-Tramites-Vehiculares_jar_1.0-SNAPSHOTPU");

    @Override
    public List<Automovil> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Automovil> cq = cb.createQuery(Automovil.class);
            Root<Automovil> root = cq.from(Automovil.class);
            cq.select(root);
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            return List.of();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Automovil buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Automovil.class, id);
        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Automovil buscarPorNumeroSerie(String numeroSerie) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Automovil a WHERE a.numeroSerie = :serie", Automovil.class)
                    .setParameter("serie", numeroSerie)
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
    public Automovil crear(Automovil automovil) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(automovil);
            em.getTransaction().commit();
            return automovil;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al crear el automóvil.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean actualizar(Automovil automovil) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Automovil existente = em.find(Automovil.class, automovil.getId());

            if (existente == null) {
                em.getTransaction().rollback();
                return false;
            }

            existente.setNumeroSerie(automovil.getNumeroSerie());
            existente.setMarca(automovil.getMarca());
            existente.setLinea(automovil.getLinea());
            existente.setColor(automovil.getColor());
            existente.setModelo(automovil.getModelo());
            existente.setPersona(automovil.getPersona());

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
            Automovil existente = em.find(Automovil.class, id);

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
