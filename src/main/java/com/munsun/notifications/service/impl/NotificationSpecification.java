package com.munsun.notifications.service.impl;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.model.Employee_;
import com.munsun.notifications.model.Notification;
import com.munsun.notifications.model.Notification_;
import com.munsun.notifications.model.embeddable.EmployeesEmbeddable_;
import com.munsun.notifications.model.embeddable.TimeEmbeddable_;
import com.munsun.notifications.model.embeddable.TrainEmdeddable_;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class NotificationSpecification {
    public static Specification<Notification> get(FindDtoIn dto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!ObjectUtils.isEmpty(dto.getNumberTrain()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.TRAIN).get(TrainEmdeddable_.TRAIN_NUMBER), dto.getNumberTrain()));

            if(!ObjectUtils.isEmpty(dto.getWagonOncomingNumber()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.TRAIN).get(TrainEmdeddable_.TRAIN_NUMBER_ONCOMING), dto.getWagonOncomingNumber()));

            if(!ObjectUtils.isEmpty(dto.getWagonTailNumber()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.TRAIN).get(TrainEmdeddable_.TRAIN_NUMBER_TAIL), dto.getWagonTailNumber()));

            if(!ObjectUtils.isEmpty(dto.getStartPeriodDate()))
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Notification_.TIMES).get(TimeEmbeddable_.DATETIME_FINISH), dto.getStartPeriodDate()));

            if(!ObjectUtils.isEmpty(dto.getEndPeriodDate()))
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Notification_.TIMES).get(TimeEmbeddable_.DATETIME_FINISH), dto.getEndPeriodDate()));

            if(!ObjectUtils.isEmpty(dto.getHeadEmployee().getName()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.EMPLOYEES).get(EmployeesEmbeddable_.HEAD_EMPLOYEE).get(Employee_.NAME), dto.getHeadEmployee().getName()));

            if(!ObjectUtils.isEmpty(dto.getHeadEmployee().getSurname()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.EMPLOYEES).get(EmployeesEmbeddable_.HEAD_EMPLOYEE).get(Employee_.SURNAME), dto.getHeadEmployee().getSurname()));

            if(!ObjectUtils.isEmpty(dto.getHeadEmployee().getPatronymic()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.EMPLOYEES).get(EmployeesEmbeddable_.HEAD_EMPLOYEE).get(Employee_.PATRONYMIC), dto.getHeadEmployee().getPatronymic()));


            if(!ObjectUtils.isEmpty(dto.getTailEmployee().getName()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.EMPLOYEES).get(EmployeesEmbeddable_.TAIL_EMPLOYEE).get(Employee_.NAME), dto.getTailEmployee().getName()));

            if(!ObjectUtils.isEmpty(dto.getTailEmployee().getSurname()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.EMPLOYEES).get(EmployeesEmbeddable_.TAIL_EMPLOYEE).get(Employee_.SURNAME), dto.getTailEmployee().getSurname()));

            if(!ObjectUtils.isEmpty(dto.getTailEmployee().getPatronymic()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.EMPLOYEES).get(EmployeesEmbeddable_.TAIL_EMPLOYEE).get(Employee_.PATRONYMIC), dto.getTailEmployee().getPatronymic()));


            if(!ObjectUtils.isEmpty(dto.getMachinist().getName()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.EMPLOYEES).get(EmployeesEmbeddable_.MACHINIST).get(Employee_.NAME), dto.getMachinist().getName()));

            if(!ObjectUtils.isEmpty(dto.getMachinist().getSurname()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.EMPLOYEES).get(EmployeesEmbeddable_.MACHINIST).get(Employee_.SURNAME), dto.getMachinist().getSurname()));

            if(!ObjectUtils.isEmpty(dto.getMachinist().getPatronymic()))
                predicates.add(criteriaBuilder.equal(root.get(Notification_.EMPLOYEES).get(EmployeesEmbeddable_.MACHINIST).get(Employee_.PATRONYMIC), dto.getMachinist().getPatronymic()));

            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)))
                    .getRestriction();
        };
    }
}
