package com.suyash586.rest.service;

import java.util.List;

import com.suyash586.rest.domain.Office;

public interface OfficeService {

    Office save(Office office);

    List<Office> getList();

    List<Office> getNowOpenList();

    Office getOfficeById(String id);

}
