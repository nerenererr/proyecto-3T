package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import modelos.Desarrollador;
import modelos.Proyecto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProyectoDAO {
    private EntityManagerFactory emf;
    public ProyectoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void insertarProyecto(Proyecto proyecto) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(proyecto);
        em.getTransaction().commit();
        em.close();
    }

    public void actualizarProyecto(int id, String nombre, double presupuesto, String lenguaje) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Proyecto p = em.find(Proyecto.class, id);
        if (p != null) {
            p.setNombre(nombre);
            p.setPresupuesto(presupuesto);
            p.setLenguajePrincipal(lenguaje);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void eliminarProyecto(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Proyecto proyecto = em.find(Proyecto.class, id);
        if (proyecto != null) {
            for (Desarrollador d : proyecto.getDesarrolladores()) {
                d.getProyectos().remove(proyecto);
            }
            proyecto.getDesarrolladores().clear();
            em.remove(proyecto);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Map<String, Long> getNumDesarrolladoresPorProyecto() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Object[]> query = em.createQuery("SELECT p.nombre, COUNT(d) FROM Proyecto p LEFT JOIN p.desarrolladores d GROUP BY p.nombre", Object[].class);
        List<Object[]> results = query.getResultList();

        Map<String, Long> map = new HashMap<>();
        for (Object[] row : results) {
            String nombreProyecto = (String) row[0];
            Long count = (Long) row[1];
            map.put(nombreProyecto, count);
        }
        em.close();
        return map;
    }

    public List<Desarrollador> getDesarrolladoresPorProyecto(int idProyecto) {
        EntityManager em = emf.createEntityManager();
        Proyecto p = em.find(Proyecto.class, idProyecto);

        List<Desarrollador> desarrolladores = new ArrayList<>();
        if (p != null) {
            desarrolladores.addAll(p.getDesarrolladores());
        }
        em.close();
        return desarrolladores;
    }

    public List<Proyecto> getProyectosMasDe5Desarrolladores() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Proyecto> query = em.createQuery("SELECT p FROM Proyecto p JOIN p.desarrolladores d GROUP BY p HAVING COUNT(d) > 5", Proyecto.class);
        List<Proyecto> results = query.getResultList();
        em.close();
        return results;
    }

    public List<Proyecto> getTop3ProyectosPresupuestoAlto() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Proyecto> query = em.createQuery("SELECT p FROM Proyecto p ORDER BY p.presupuesto DESC", Proyecto.class);
        query.setMaxResults(3);
        List<Proyecto> results = query.getResultList();
        em.close();
        return results;
    }

    public Proyecto getProyectoPresupuestoBajoPorLenguaje(String lenguajePrincipal) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Proyecto> query = em.createQuery("SELECT p FROM Proyecto p WHERE p.lenguajePrincipal = :lenguaje ORDER BY p.presupuesto ASC", Proyecto.class);
        query.setParameter("lenguaje", lenguajePrincipal);
        query.setMaxResults(1);
        List<Proyecto> results = query.getResultList();
        em.close();
        return results.isEmpty() ? null : results.get(0);
    }
}
