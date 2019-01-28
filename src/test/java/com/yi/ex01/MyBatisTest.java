package com.yi.ex01;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MyBatisTest {
	
	//Autowried와 같은 기능 = inject
	@Inject
	private SqlSessionFactory ss;
	
	@Test
	public void testFactory() {
		System.out.println(ss);
	}
	
	@Test
	public void testSession() {
		try(SqlSession session = ss.openSession()) {
			System.out.println(session);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
