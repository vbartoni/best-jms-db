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

package com.springsource.open.jms.async.notxawarecf;

import com.springsource.open.jms.async.AbstractAsynchronousMessageTriggerTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath*:META-INF/spring/jms-context-ntxa.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AsyncTxNoTxAwareCFMessageTriggerSunnyDayPerformanceTests extends AbstractAsynchronousMessageTriggerTests {

	@Test
	public void testCleanData() {
		long start = System.nanoTime();
		for (int i = 0; i < 10000; i++){
			jmsTemplate.convertAndSend("async", "foo" + i);
		}
		long end = System.nanoTime();
		System.out.println("**************************** time past in seconds:" + (end-start));
	}

	@Override
	protected void checkPostConditions() {

	}

}
