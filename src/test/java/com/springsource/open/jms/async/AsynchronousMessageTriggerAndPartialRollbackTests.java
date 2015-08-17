/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.springsource.open.jms.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;
@ContextConfiguration(locations={"classpath*:META-INF/spring/jms-context-single-cf.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AsynchronousMessageTriggerAndPartialRollbackTests extends AbstractAsynchronousMessageTriggerTests {

	@Test
	public void testPartialFailureWithDuplicateMessage() {
		jmsTemplate.convertAndSend("async", "foo");
		jmsTemplate.convertAndSend("async", "bar.fail.partial");
	}

	@Override
	protected void checkPostConditions() {

		// Check how many items are in DB
		assertEquals(1, SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "T_FOOS"));

		// Check how many messages are in queue
		List<String> list = getMessages();
		assertEquals(1, list.size());

	}

}
