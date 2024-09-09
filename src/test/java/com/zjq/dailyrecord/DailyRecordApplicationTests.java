package com.zjq.dailyrecord;

import com.zjq.dailyrecord.designpattern.observer.spring.event.service.FileExportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DailyRecordApplicationTests {


    @Autowired
    private FileExportService fileExportService;

    @Test
    public void exportRecordTest() {
        fileExportService.exportRecord(66668888L);
    }

    @Test
    void contextLoads() {
    }

}
