package com.texo.challenge.config.converter;

import com.texo.challenge.config.converter.mocks.MockProducer;
import com.texo.challenge.model.Producer;
import com.texo.challenge.model.vo.ProducerVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


class DozerConverterTest {

    MockProducer inputObject;

    @BeforeEach
    void setUp() {
        inputObject = new MockProducer();
    }

    @Test
    void parseEntityToVOTest() {
        ProducerVO output = DozerConverter.parseObject(inputObject.mockEntity(), ProducerVO.class);
        Assertions.assertEquals(Long.valueOf(0L), output.getId());
        Assertions.assertEquals("Test0", output.getName());
        Assertions.assertEquals(0L, output.getIntervalOfWin());
        Assertions.assertEquals(1980L, output.getPreviousWin());
        Assertions.assertEquals(1988L, output.getFollowingWin());
    }

    @Test
    void parseEntityListToVOListTest() {
        List<ProducerVO> outputList = DozerConverter.parseListObjects(inputObject.mockEntityList(), ProducerVO.class);
        ProducerVO outputZero = outputList.get(0);

        Assertions.assertEquals(Long.valueOf(0L), outputZero.getId());
        Assertions.assertEquals("Test0", outputZero.getName());
        Assertions.assertEquals(0L, outputZero.getIntervalOfWin());
        Assertions.assertEquals(1980L, outputZero.getPreviousWin());
        Assertions.assertEquals(1988L, outputZero.getFollowingWin());

        ProducerVO outputSeven = outputList.get(7);

        Assertions.assertEquals(Long.valueOf(7L), outputSeven.getId());
        Assertions.assertEquals("Test7", outputSeven.getName());
        Assertions.assertEquals(7L, outputSeven.getIntervalOfWin());
        Assertions.assertEquals(1980L, outputSeven.getPreviousWin());
        Assertions.assertEquals(1988L, outputSeven.getFollowingWin());

        ProducerVO outputTwelve = outputList.get(12);

        Assertions.assertEquals(Long.valueOf(12L), outputTwelve.getId());
        Assertions.assertEquals("Test12", outputTwelve.getName());
        Assertions.assertEquals(12L, outputTwelve.getIntervalOfWin());
        Assertions.assertEquals(1980L, outputTwelve.getPreviousWin());
        Assertions.assertEquals(1988L, outputTwelve.getFollowingWin());
    }

    @Test
    void parseVOToEntityTest() {
        Producer output = DozerConverter.parseObject(inputObject.mockVO(), Producer.class);
        Assertions.assertEquals("Test0", output.getName());
        Assertions.assertEquals(0L, output.getIntervalOfWin());
        Assertions.assertEquals(1980L, output.getPreviousWin());
        Assertions.assertEquals(1988L, output.getFollowingWin());
    }
}