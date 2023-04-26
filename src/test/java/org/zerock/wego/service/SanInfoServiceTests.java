package org.zerock.wego.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.wego.domain.info.SanInfoViewSortVO;
import org.zerock.wego.service.info.SanInfoService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/**/root-*.xml")

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SanInfoServiceTests {
	
	@Setter(onMethod_= {@Autowired})
	private SanInfoService service;

//	@Disabled
	@Test
	@Order(1)
	@DisplayName("testSelectAllBySort")
	@Timeout(value=1, unit=TimeUnit.SECONDS)
	void testSelectAllBySort() {
		log.trace("testSelectAllBySort() invoked.");

		Set<SanInfoViewSortVO> list = this.service.getSortList();
		assertNotNull(list);
		
		list.forEach(log::info);
	} // testSelectAllBySort
} // end class
