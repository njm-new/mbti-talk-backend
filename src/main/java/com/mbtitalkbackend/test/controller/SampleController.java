package com.mbtitalkbackend.test.controller;

import com.mbtitalkbackend.common.model.ResultVO;
import com.mbtitalkbackend.test.domain.SampleDataVO;
import com.mbtitalkbackend.test.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            return new ResultVO(HttpStatus.NOT_FOUND, "not found"); // HTTP-STATUS.OK
        }
        else {
            return new ResultVO(HttpStatus.OK, "success"); // HTTP-STATUS.OK
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
            return new ResultVO(HttpStatus.NOT_FOUND, "not found"); // HTTP-STATUS.OK
        }
        else {
            return new ResultVO(HttpStatus.OK, "success"); // HTTP-STATUS.OK
        }
    }
}
