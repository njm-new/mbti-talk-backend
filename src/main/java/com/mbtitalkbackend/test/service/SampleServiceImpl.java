package com.mbtitalkbackend.test.service;

import com.mbtitalkbackend.test.domain.SampleDataVO;
import com.mbtitalkbackend.test.persistance.SampleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService{

    private final SampleMapper sampleMapper;

    @Override
    public List<SampleDataVO> findAllSampleDataVO() {
        return sampleMapper.findAllSampleDataVO();
    }

    @Override
    public int saveSampleDataVO(SampleDataVO sampleData) {
        return sampleMapper.saveSampleDataVO(sampleData);
    }

    @Override
    public SampleDataVO findSampleDataVOById(long id) {
        return sampleMapper.findSampleDataVOById(id);
    }

    @Override
    public int deleteSampleDataVOById(long id) {
        return sampleMapper.deleteSampleDataVOById(id);
    }
}
