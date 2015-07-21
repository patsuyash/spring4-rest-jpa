package com.suyash586.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
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
import com.suyash586.rest.repository.OfficeRepository;
import com.suyash586.rest.service.exception.OfficeAlreadyExistsException;
import com.suyash586.rest.util.OfficeUtil;

@RunWith(MockitoJUnitRunner.class)
public class OfficeServiceImplTest {

    @Mock
    private OfficeRepository officeRepository;

    private OfficeService officeService;

    @Before
    public void setUp() throws Exception {
        officeService = new OfficeServiceImpl(officeRepository);
    }

    @Test
    public void shouldSaveNewOffice_GivenThereDoesNotExistOneWithTheSameId_ThenTheSavedOfficeShouldBeReturned() throws Exception {
        final Office savedOffice = stubRepositoryToReturnOfficeOnSave();
        final Office office = OfficeUtil.createOffice();
        final Office returnedOffice = officeService.save(office);
        // verify repository was called with office
        verify(officeRepository, times(1)).save(office);
        assertEquals("Returned office should come from the repository", savedOffice, returnedOffice);
    }

    private Office stubRepositoryToReturnOfficeOnSave() {
        Office office = OfficeUtil.createOffice();
        when(officeRepository.save(any(Office.class))).thenReturn(office);
        return office;
    }

    @Test
    public void shouldSaveNewOffice_GivenThereExistsOneWithTheSameId_ThenTheExceptionShouldBeThrown() throws Exception {
        stubRepositoryToReturnExistingOffice();
        try {
            officeService.save(OfficeUtil.createOffice());
            fail("Expected exception");
        } catch (OfficeAlreadyExistsException ignored) {
        }
        verify(officeRepository, never()).save(any(Office.class));
    }

    private void stubRepositoryToReturnExistingOffice() {
        final Office office = OfficeUtil.createOffice();
        when(officeRepository.findOne(office.getId())).thenReturn(office);
    }

    @Test
    public void shouldListAllOffices_GivenThereExistSome_ThenTheCollectionShouldBeReturned() throws Exception {
        stubRepositoryToReturnExistingOffices(10);
        Collection<Office> list = officeService.getList();
        assertNotNull(list);
        assertEquals(10, list.size());
        verify(officeRepository, times(1)).findAll();
    }

    private void stubRepositoryToReturnExistingOffices(int howMany) {
        when(officeRepository.findAll()).thenReturn(OfficeUtil.createOfficeList(howMany));
    }

    @Test
    public void shouldListAllOffices_GivenThereNoneExist_ThenTheEmptyCollectionShouldBeReturned() throws Exception {
        stubRepositoryToReturnExistingOffices(0);
        Collection<Office> list = officeService.getList();
        assertNotNull(list);
        assertTrue(list.isEmpty());
        verify(officeRepository, times(1)).findAll();
    }
    
    @Test
    public void shouldListNowOpenOffices_GivenThereNoneExist_ThenTheCollectionShouldBeReturned() throws Exception {
       
        stubRepositoryToReturnExistingOffices(10);
        Collection<Office> list = officeService.getNowOpenList();
        assertNotNull(list);
        assertEquals(10, list.size());
        verify(officeRepository, times(1)).findAll();
    }

}
