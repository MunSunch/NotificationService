package com.munsun.notifications.service.impl;

import com.munsun.notifications.dto.in.EmployeeDtoIn;
import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import com.munsun.notifications.exceptions.NotificationEmployeeException;
import com.munsun.notifications.exceptions.NotificationNotFoundException;
import com.munsun.notifications.mapping.EmployeeMapper;
import com.munsun.notifications.mapping.impl.NotificationMapperImpl;
import com.munsun.notifications.model.Employee;
import com.munsun.notifications.model.OthersParameters;
import com.munsun.notifications.model.embeddable.EmployeesEmbeddable;
import com.munsun.notifications.repository.EmployeeRepository;
import com.munsun.notifications.repository.NotificationRepository;
import com.munsun.notifications.repository.OthersParametersRepository;
import com.munsun.notifications.service.NotificationCheckerProvider;
import com.munsun.notifications.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository repository;
    private EmployeeRepository employeeRepository;
    private OthersParametersRepository othersParametersRepository;

    private NotificationMapperImpl mapper;
    private EmployeeMapper employeeMapper;
    private NotificationCheckerProvider checker;

    @Transactional
    @Override
    public NotificationDtoOut add(NotificationDtoIn dto) throws Exception {
        checker.checkNotification(dto);
        var newNotification = mapper.map(dto);
        newNotification.setEmployees(new EmployeesEmbeddable(addEmployee(dto.headEmployee()), addEmployee(dto.tailEmployee()), addEmployee(dto.machinist())));
        newNotification.getParameters().setOthersParameters(addOtherParameters(dto));
        newNotification.setTimeCreate(LocalDateTime.now());
        return mapper.map(repository.save(newNotification));
    }

    private List<OthersParameters> addOtherParameters(NotificationDtoIn dto) {
        return dto.parameters().other().stream()
                .map(this::addOtherParameter)
                .collect(Collectors.toList());
    }

    private OthersParameters addOtherParameter(String x) {
        var otherParameter = othersParametersRepository.findByName(x);
        if(otherParameter.isEmpty())
            return othersParametersRepository.save(new OthersParameters(null, x));
        return otherParameter.get();
    }

    private Employee addEmployee(EmployeeDtoIn employeeDtoIn) {
        var employee = employeeRepository.findEmployeeByNameAndSurnameAndPatronymic(employeeDtoIn.getName(),
                employeeDtoIn.getSurname(), employeeDtoIn.getPatronymic());
        if(employee.isEmpty())
            return employeeRepository.save(employeeMapper.map(employeeDtoIn));
        return employee.get();
    }

    @Override
    public List<NotificationDtoOut> find(FindDtoIn findDto, Pageable pageable) throws Exception {
        log.info("find ...");
        checker.checkFindDto(findDto);
        return repository.findAll(NotificationSpecification.get(findDto), pageable)
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDtoOut> getNotifications(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page, size))
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDtoOut getById(Integer id) throws NotificationNotFoundException {
        return mapper.map(repository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id)));
    }
}
