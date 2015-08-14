package com.springsource.open.foo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FooHandler implements Handler {

	private static final Log log = LogFactory.getLog(FooHandler.class);
	private SimpleJdbcTemplate jdbcTemplate;
	private AtomicInteger count = new AtomicInteger(0);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	public void handle(String msg) {

		log.debug("Received message: [" + msg + "]");
		Date date = new Date();

		beforeJPA(msg);

		jdbcTemplate.update(
				"INSERT INTO T_FOOS (ID, name, foo_date) values (?, ?,?)", count.getAndIncrement(), msg, date);

		afterJPA(msg);

		log.debug(String
				.format("Inserted foo with name=%s, date=%s", msg, date));
		
	}

	public void resetItemCount() {
		count.set(0);
	}

	private void beforeJPA(String msg){
		log.debug("Before JPA, msg" + msg);
		if (msg.contains("before")) {
			throw new RuntimeException("Failing Before JPA");
		}
	}

	private void afterJPA(String msg){
		log.debug("After JPA, msg" + msg);
		if (msg.contains("after")) {
			throw new RuntimeException("Failing After JPA");
		}
	}

	public int getItemCount() {
		return count.get();
	}

}
