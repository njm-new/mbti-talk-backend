package com.mbtitalkbackend.service;

import com.mbtitalkbackend.domain.SampleDataVO;

import java.util.List;

public interface SampleService {

    List<SampleDataVO> findAllSampleDataVO();

    int saveSampleDataVO(SampleDataVO SampleData);

    SampleDataVO findSampleDataVOById(long id);

    int deleteSampleDataVOById(long id);
}
