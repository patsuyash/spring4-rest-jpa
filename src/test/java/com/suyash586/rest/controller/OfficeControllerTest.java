package com.suyash586.rest.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.suyash586.rest.domain.Office;
import com.suyash586.rest.service.OfficeService;
import com.suyash586.rest.util.OfficeUtil;

@RunWith(MockitoJUnitRunner.class)
public class OfficeControllerTest {

    @Mock
    private OfficeService officeService;

    private OfficeController officeController;

    @Before
    public void setUp() throws Exception {
        officeController = new OfficeController(officeService);
    }

    @Test
    public void shouldCreateOffice() throws Exception {
        final Office savedOffice = stubServiceToReturnStoredOffice();
        final Office office = OfficeUtil.createOffice();
        Office returnedOffice = officeController.createOffice(office);
        // verify office was passed to OfficeService
        verify(officeService, times(1)).save(office);
        assertEquals("Returned office should come from the service", savedOffice, returnedOffice);
    }

    private Office stubServiceToReturnStoredOffice() {
        final Office office = OfficeUtil.createOffice();
        when(officeService.save(any(Office.class))).thenReturn(office);
        return office;
    }


    @Test
    public void shouldListAllOffices() throws Exception {
        stubServiceToReturnExistingOffices(10);
        Collection<Office> offices = officeController.listOffices(false);
        assertNotNull(offices);
        assertEquals(10, offices.size());
        // verify office was passed to OfficeService
        verify(officeService, times(1)).getList();
    }

    private void stubServiceToReturnExistingOffices(int howMany) {
        when(officeService.getList()).thenReturn(OfficeUtil.createOfficeList(howMany));
    }

}
