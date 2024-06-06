package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.persistence.enums.TreatmentType;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Override
    public PatientEntity findOne(Long id) {
//        return super.findOne(id);
        return entityManager.find(PatientEntity.class, id);
    }

    @Override
    public List<PatientEntity> findByDoctor(String firstName, String lastName) { // TODO - napisac query
        Query<PatientEntity> query = (Query<PatientEntity>) entityManager.createQuery(
                "SELECT p FROM PatientEntity p JOIN p.visits v " +
                        "WHERE v.doctor.firstName = :firstName AND v.doctor.lastName = :lastName"
                , PatientEntity.class);
        query.setParameter("firstName", firstName).setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsHavingTreatmentType(TreatmentType treatmentType) { // TODO - napisac query

        Query<PatientEntity> query = (Query<PatientEntity>) entityManager.createQuery(
                "SELECT DISTINCT p FROM PatientEntity p " +
                        "JOIN p.visits v " +
                        "JOIN v.medicalTreatments t " +
                        "WHERE t.type = :treatmentType", PatientEntity.class);
        query.setParameter("treatmentType", treatmentType);

        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsSharingSameLocationWithDoc(String firstName, String lastName) { // TODO - napisac query
        Query<PatientEntity> query = (Query<PatientEntity>) entityManager.createQuery(
                "SELECT DISTINCT p FROM PatientEntity p " +
                        "JOIN p.addresses pa " +
                        "JOIN pa.doctors da " +
                        "WHERE da.firstName = :firstName AND da.lastName = :lastName", PatientEntity.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithoutLocation() { // TODO - napisac query
        Query<PatientEntity> query = (Query<PatientEntity>) entityManager.createQuery(
                "SELECT p FROM PatientEntity p WHERE p.addresses.size IS NULL", PatientEntity.class);
        return query.getResultList();
    }
}
