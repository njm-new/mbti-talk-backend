package com.mbtitalkbackend.test.persistance;

import com.mbtitalkbackend.test.domain.SampleDataVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SampleMapper {

    @Select("SELECT * FROM sampledata")
    List<SampleDataVO> findAllSampleDataVO();

    @Insert("INSERT INTO sampledata (name, position) VALUES(#{name}, #{position})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveSampleDataVO(SampleDataVO SampleData);

    @Select("SELECT * FROM sampledata WHERE id = #{id}")
    SampleDataVO findSampleDataVOById(long id);

    @Insert("DELETE FROM sampledata WHERE id = #{id}")
    int deleteSampleDataVOById(long id);
}
