package com.texo.challenge.config.converter.mocks;



import com.texo.challenge.model.Producer;
import com.texo.challenge.model.vo.ProducerVO;

import java.util.ArrayList;
import java.util.List;

public class MockProducer {


    public Producer mockEntity() {
        return mockEntity(0);
    }

    public ProducerVO mockVO() {
        return mockVO(0);
    }

    public List<Producer> mockEntityList() {
        List<Producer> producers = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            producers.add(mockEntity(i));
        }
        return producers;
    }

    private Producer mockEntity(Integer number) {
        Producer producer = new Producer();
        producer.setId(number.longValue());
        producer.setName("Test" + number);
        producer.setIntervalOfWin(number.longValue());
        producer.setPreviousWin(1980L);
        producer.setFollowingWin(1988L);
        return producer;
    }

    private ProducerVO mockVO(Integer number) {
        ProducerVO producer = new ProducerVO();
        producer.setId(number.longValue());
        producer.setName("Test" + number);
        producer.setIntervalOfWin(number.longValue());
        producer.setPreviousWin(1980L);
        producer.setFollowingWin(1988L);
        return producer;
    }

}
