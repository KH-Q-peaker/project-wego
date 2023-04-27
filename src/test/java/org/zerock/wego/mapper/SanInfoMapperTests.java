package org.zerock.wego.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
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
import org.zerock.wego.domain.common.BoardDTO;
import org.zerock.wego.domain.info.SanInfoViewSortVO;
import org.zerock.wego.domain.info.SanInfoViewVO;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/**/root-*.xml")

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SanInfoMapperTests {
	
	@Setter(onMethod_= {@Autowired})
	private SanInfoMapper mapper;

//	@Disabled
	@Test
	@Order(1)
	@DisplayName("testSelectAllBySort")
	@Timeout(value=1, unit=TimeUnit.SECONDS)
	void testSelectAllBySort() {
		log.trace("testSelectAllBySort() invoked.");

		BoardDTO dto = new BoardDTO();
		dto.setOrderBy("abc");
		dto.setLastItemId(0);
		dto.setPage(1);
		dto.setAmount(20);
		List<SanInfoViewSortVO> list = this.mapper.selectAllBySort(dto);
		assertNotNull(list);
		
		list.forEach(log::info);
	} // testSelectAllBySort
	
	
//	@Disabled
	@Test
	@Order(2)
	@DisplayName("testSelectAll")
	@Timeout(value=5, unit=TimeUnit.SECONDS)
	void testSelectAll() {
		log.trace("testSelectAll() invoked.");

		Set<SanInfoViewVO> list = this.mapper.selectAll();
		assertNotNull(list);
		
		list.forEach(log::info);
	} // testSelectAll
} // end class
