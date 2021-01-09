package com.cap;

import com.cap.service.impl.SearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SearchOneApplicationTests {
	@Autowired
	SearchServiceImpl infoService;
	@Test
	void contextLoads() throws IOException {
	}

}
