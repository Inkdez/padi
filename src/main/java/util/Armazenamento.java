package util;

import entidade.Questao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class Armazenamento<T> {
    public T criarObjeto(T objeto) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(objeto);
            entityTransaction.commit();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return objeto;
    }

    public T actualizarObjeto(T objeto) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.merge(objeto);
            entityTransaction.commit();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return objeto;
    }

    public boolean removerObjecto(int id,Class<T> typeClass) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.createQuery("delete from " +  typeClass.getSimpleName() +" p where p.id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            entityTransaction.commit();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return true;
    }

    public boolean esvaziarTabela(Class<T> typeClass) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.createQuery("delete from " +  typeClass.getSimpleName() )
                    .executeUpdate();
            entityTransaction.commit();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return true;
    }


    public boolean esvaziarTabela(int id,Class<T> typeClass) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.createQuery("delete from " +  typeClass.getSimpleName() +" p where p.testeId=:testeId")
                    .setParameter("testeId",id)
                    .executeUpdate();
            entityTransaction.commit();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return true;
    }


    public List<T> listaDeObjectos(Class<T> typeClass) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        List<T> result = new ArrayList<>();
        try {
            entityTransaction.begin();
            List<?> ems = (List<?>) entityManager.createQuery("select o from " + typeClass.getSimpleName() + " o").getResultList();
            for (Object o: ems)
                result.add(typeClass.cast(o));
            entityTransaction.commit();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return result;
    }


    public List<Questao> listaDeQuestoesPorTestId(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        List<Questao> result = new ArrayList<>();
        try {
            entityTransaction.begin();
            List<Questao> ems = (List<Questao>) entityManager.createNamedQuery("Questao.porTestId")
                    .setParameter(1,id)
                    .getResultList();
            for (Object o: ems)
                result.add(Questao.class.cast(o));
            entityTransaction.commit();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return result;
    }

    public T prourarObjecto(int id,Class<T> typeClass) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        T result;
        try {
            entityTransaction.begin();
             result = entityManager.find(typeClass,id);
            entityTransaction.commit();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
      return result;
    }
}