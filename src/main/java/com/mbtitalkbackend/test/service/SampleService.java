package com.mbtitalkbackend.test.service;

import com.mbtitalkbackend.test.domain.SampleDataVO;

import java.util.List;

public interface SampleService {

    List<SampleDataVO> findAllSampleDataVO();

    int saveSampleDataVO(SampleDataVO SampleData);

    SampleDataVO findSampleDataVOById(long id);

    int deleteSampleDataVOById(long id);
}
