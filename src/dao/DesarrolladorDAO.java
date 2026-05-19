package dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import modelos.Desarrollador;
import modelos.Proyecto;

import java.util.ArrayList;
import java.util.List;

public class DesarrolladorDAO {
    private EntityManagerFactory emf;
    public DesarrolladorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void insertarDesarrollador(Desarrollador desarrollador) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(desarrollador);
        em.getTransaction().commit();
        em.close();
    }

    public void actualizarDesarrollador(int id, String nombre, int anyosExperiencia, double salario) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Desarrollador d = em.find(Desarrollador.class, id);
        if (d != null) {
            d.setNombre(nombre);
            d.setAnyosExperiencia(anyosExperiencia);
            d.setSalario(salario);
        }
        em.getTransaction().commit();
        em.close();
    }


    public void eliminarDesarrollador(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Desarrollador desarrollador = em.find(Desarrollador.class, id);
        if (desarrollador != null) {
            for (Proyecto proyecto : desarrollador.getProyectos()) {
                proyecto.getDesarrolladores().remove(desarrollador);
            }
            desarrollador.getProyectos().clear();
            em.remove(desarrollador);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void asignarDesarrollador(int idDesarrollador, int idProyecto) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Desarrollador d = em.find(Desarrollador.class, idDesarrollador);
        Proyecto p = em.find(Proyecto.class, idProyecto);

        if (d != null && p != null) {
            if (!d.getProyectos().contains(p)) {
                d.getProyectos().add(p);
            }
            if (!p.getDesarrolladores().contains(d)) {
                p.getDesarrolladores().add(d);
            }
        }
        em.getTransaction().commit();
        em.close();
    }

    public void eliminarAsignacion(int idDesarrollador, int idProyecto) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Desarrollador d = em.find(Desarrollador.class, idDesarrollador);
        Proyecto p = em.find(Proyecto.class, idProyecto);

        if (d != null && p != null) {
            d.getProyectos().remove(p);
            p.getDesarrolladores().remove(d);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Proyecto> getProyectosPorDesarrollador(int idDesarrollador) {
        EntityManager em = emf.createEntityManager();
        Desarrollador d = em.find(Desarrollador.class, idDesarrollador);
        List<Proyecto> proyectos = new ArrayList<>();
        if (d != null) {
            proyectos.addAll(d.getProyectos());
        }
        em.close();
        return proyectos;
    }

    public Double getMediaAnyosExperiencia() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Double> query = em.createQuery("SELECT AVG(d.anyosExperiencia) FROM Desarrollador d", Double.class);
        List<Double> results = query.getResultList();
        em.close();
        return results.isEmpty() || results.get(0) == null ? 0.0 : results.get(0);
    }

    public List<Desarrollador> getDesarrolladoresSinProyectos() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Desarrollador> query = em.createQuery("SELECT d FROM Desarrollador d", Desarrollador.class);
        List<Desarrollador> todos = query.getResultList();
        List<Desarrollador> sinProyectos = new ArrayList<>();
        for (Desarrollador d : todos) {
            if (d.getProyectos().isEmpty()) {
                sinProyectos.add(d);
            }
        }
        em.close();
        return sinProyectos;
    }
}
