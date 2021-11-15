package com.mbtitalkbackend.controller;

import com.mbtitalkbackend.domain.ResultVO;
import com.mbtitalkbackend.domain.SampleDataVO;
import com.mbtitalkbackend.service.SampleService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @GetMapping("/")
    public String printHelloWorld() {

        return "Hello World! with pr-test";
    }

    @GetMapping("/sampledata")
    public List<SampleDataVO> findAllSampleDataVO() {

        return sampleService.findAllSampleDataVO();
    }

    @PostMapping("/sampledata")
    public ResultVO saveSampleDataVO(@RequestBody SampleDataVO sampleDataVO) {

        int res = sampleService.saveSampleDataVO(sampleDataVO);

        if(res > 0) {
            return new ResultVO(0, "success");
        }
        else {
            return new ResultVO(100, "fail");
        }
    }

    @GetMapping("/sampledata/{id}")
    public SampleDataVO findSampleDataVOById(@PathVariable("id") long id) {

        return sampleService.findSampleDataVOById(id);
    }

    @DeleteMapping("/sampledata/{id}")
    public ResultVO deleteSampleDataVO(@PathVariable("id") long id) {

        int res = sampleService.deleteSampleDataVOById(id);

        if(res > 0) {
            return new ResultVO(0, "success");
        }
        else {
            return new ResultVO(100, "fail");
        }
    }
}
