package com.cap;

import com.cap.service.InfoService;
import com.cap.util.HtmlParseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SearchOneApplicationTests {
	@Autowired
	InfoService infoService;
	@Test
	void contextLoads() throws IOException {
	}

}
