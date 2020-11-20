package com.usc.csci401.goatfacade;

import static org.mockito.Mockito.verify;

import com.usc.csci401.goatdao.model.Game;
import com.usc.csci401.goatservice.param.UserUpdateParam;
import com.usc.csci401.goatservice.service.GameRecordService;
import com.usc.csci401.goatservice.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
class GoatFacadeApplicationTests {

//	@Autowired
//	private GameRecordFacade gameRecordFacade;
//
//
//	@Test
//	void recordTest(){
//		gameRecordFacade.addGameRecord("undertale", "spike");
//		gameRecordFacade.addGameRecord("undertale", "john");
//
//
//	}


}
