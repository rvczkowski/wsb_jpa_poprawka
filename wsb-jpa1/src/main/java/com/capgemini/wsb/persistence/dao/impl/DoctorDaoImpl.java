package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.DoctorDao;
import com.capgemini.wsb.persistence.entity.DoctorEntity;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.persistence.enums.Specialization;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DoctorDaoImpl extends AbstractDao<DoctorEntity, Long> implements DoctorDao {
    @Override
    public List<DoctorEntity> findBySpecialization(Specialization specialization) { // TODO - napisac query

        Query<DoctorEntity> query = (Query<DoctorEntity>) entityManager.createQuery("FROM DoctorEntity where specialization = :spec", DoctorEntity.class);
        query.setParameter("spec", specialization);

        return query.getResultList();
    }

    @Override
    public long countNumOfVisitsWithPatient(String docFirstName, String docLastName, String patientFirstName, String patientLastName) { // TODO - napisac query
        Query<Long> query = (Query<Long>) entityManager.createQuery(
                "SELECT COUNT(v) FROM VisitEntity v " +
                        "JOIN v.doctor d " +
                        "JOIN v.patient p " +
                        "WHERE d.firstName = :docFirstName AND d.lastName = :docLastName AND " +
                        "p.firstName = :patientFirstName AND p.lastName = :patientLastName",
                Long.class);

        query.setParameter("docFirstName", docFirstName);
        query.setParameter("docLastName", docLastName);
        query.setParameter("patientFirstName", patientFirstName);
        query.setParameter("patientLastName", patientLastName);

        return query.getSingleResult();
    }

    @Override
    public DoctorEntity findOne(Long id) {
        return entityManager.find(DoctorEntity.class, id);
    }
}
