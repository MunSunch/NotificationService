package com.munsun.notifications.service.impl.spec;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.model.Notification;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class NotificationSpecification {
    public static Specification<Notification> getSpecEquals(FindDtoIn dto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!ObjectUtils.isEmpty(dto.getNumberTrain()))
                predicates.add(criteriaBuilder.equal(root.get("trainNumber"), dto.getNumberTrain()));

            if(!ObjectUtils.isEmpty(dto.getSurnameHeadEmployee()))
                predicates.add(criteriaBuilder.equal(root.get("surnameHeadEmployee"), dto.getSurnameHeadEmployee()));

            if(!ObjectUtils.isEmpty(dto.getSurnameTailEmployee()))
                predicates.add(criteriaBuilder.equal(root.get("surnameTailEmployee"), dto.getSurnameTailEmployee()));

            if(!ObjectUtils.isEmpty(dto.getSurnameMachinist()))
                predicates.add(criteriaBuilder.equal(root.get("surnameMachinist"), dto.getSurnameMachinist()));

            if(!ObjectUtils.isEmpty(dto.getWagonOncomingNumber()))
                predicates.add(criteriaBuilder.equal(root.get("trainNumberOncoming"), dto.getWagonOncomingNumber()));

            if(!ObjectUtils.isEmpty(dto.getWagonTailNumber()))
                predicates.add(criteriaBuilder.equal(root.get("trainNumberTail"), dto.getWagonTailNumber()));

            if(!ObjectUtils.isEmpty(dto.getStartPeriodDate()))
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timeCreate"), dto.getStartPeriodDate()));

            if(!ObjectUtils.isEmpty(dto.getEndPeriodDate()))
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("timeCreate"), dto.getEndPeriodDate()));

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
