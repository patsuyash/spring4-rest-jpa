package com.suyash586.rest.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.suyash586.rest.domain.Office;
import com.suyash586.rest.repository.OfficeRepository;
import com.suyash586.rest.service.exception.OfficeAlreadyExistsException;

@Service
@Validated
public class OfficeServiceImpl implements OfficeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeServiceImpl.class);
    private final OfficeRepository repository;

    @Inject
    public OfficeServiceImpl(final OfficeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Office save(@NotNull @Valid final Office office) {
        LOGGER.debug("Creating {}", office);
        Office existing = repository.findOne(office.getId());
        if (existing != null) {
            throw new OfficeAlreadyExistsException(
                    String.format("There already exists a office with id=%s", office.getId()));
        }
        return repository.save(office);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Office> getList() {
        LOGGER.debug("Retrieving the list of all offices");
        return repository.findAll();
    }

    @Override
    public List<Office> getNowOpenList() {
        LOGGER.debug("Retrieving the list of all open now offices");
        final List<Office> allOffices = repository.findAll();

        Calendar c = getUTCCurrentDateCalendar();

        List<Office> resultList = new ArrayList<Office>();

        for (Office office : allOffices) {

            Calendar startTime = getUTCStartDateCal(office);
            Calendar endTime = getUTCEndDateCal(office);

            final int curentHourOfDay = c.get(Calendar.HOUR_OF_DAY);
            final int startHourOfDay = startTime.get(Calendar.HOUR_OF_DAY);
            final int endHourOfDay = endTime.get(Calendar.HOUR_OF_DAY);
            final int currentMinute = c.get(Calendar.MINUTE);
            final int startMin = startTime.get(Calendar.MINUTE);
            final int endMin = endTime.get(Calendar.MINUTE);
            final boolean currentHourInRange = (curentHourOfDay>startHourOfDay) && (curentHourOfDay<endHourOfDay);
            if (currentHourInRange|| (curentHourOfDay==startHourOfDay && currentMinute>startMin) || (curentHourOfDay==endHourOfDay && currentMinute<endMin)) {
                resultList.add(office);
            } 
            

        }
        return resultList;
    }

    private Calendar getUTCStartDateCal(Office office) {
        int timeDiff = office.getTimeDifference();
        int offsetHrsOffice = timeDiff / 100;
        int offsetMinsOffice = timeDiff % 100;

        Calendar startTime = Calendar.getInstance();
        startTime.setTime(office.getStartTime());
        startTime.add(Calendar.HOUR_OF_DAY, (-offsetHrsOffice));
        startTime.add(Calendar.MINUTE, (-offsetMinsOffice));

        return startTime;
    }

    private Calendar getUTCEndDateCal(Office office) {
        int timeDiff = office.getTimeDifference();
        int offsetHrsOffice = timeDiff / 100;
        int offsetMinsOffice = timeDiff % 100;

        Calendar endTime = Calendar.getInstance();
        endTime.setTime(office.getEndTime());
        endTime.add(Calendar.HOUR_OF_DAY, (-offsetHrsOffice));
        endTime.add(Calendar.MINUTE, (-offsetMinsOffice));

        return endTime;
    }

    private Calendar getUTCCurrentDateCalendar() {
        Calendar c = Calendar.getInstance();

        TimeZone z = c.getTimeZone();
        int offset = z.getRawOffset();

        int offsetHrs = offset / 1000 / 60 / 60;
        int offsetMins = offset / 1000 / 60 % 60;

        c.add(Calendar.HOUR_OF_DAY, (-offsetHrs));
        c.add(Calendar.MINUTE, (-offsetMins));
        return c;
    }

    @Override
    public Office getOfficeById(String id) {
        return repository.findOne(id);
    }

}
