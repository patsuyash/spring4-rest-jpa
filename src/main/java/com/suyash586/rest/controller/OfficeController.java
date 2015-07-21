package com.suyash586.rest.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.suyash586.rest.domain.Office;
import com.suyash586.rest.service.OfficeService;
import com.suyash586.rest.service.exception.OfficeAlreadyExistsException;

@RestController
public class OfficeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeController.class);
    private final OfficeService officeService;

    @Inject
    public OfficeController(final OfficeService officeService) {
        this.officeService = officeService;
    }

    @RequestMapping(value = "/office", method = RequestMethod.POST)
    public Office createOffice(@RequestBody @Valid final Office office) {
        LOGGER.debug("Received request to create the {}", office);
        return officeService.save(office);
    }

    @RequestMapping(value = "/office/{id}", method = RequestMethod.GET)
    public Office getOfficeById(@PathVariable("id") String id) {
        LOGGER.debug("Received request to getOfficeById {}", id);
        return officeService.getOfficeById(id);
    }

    @RequestMapping(value = "/office", method = RequestMethod.GET)
    public List<Office> listOffices(
            @RequestParam(value = "open", defaultValue = "false" ,required=false) boolean open) {

        LOGGER.debug("Received request to list all offices");
        return open ? officeService.getNowOpenList() : officeService.getList();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleOfficeAlreadyExistsException(OfficeAlreadyExistsException e) {
        return e.getMessage();
    }

}
