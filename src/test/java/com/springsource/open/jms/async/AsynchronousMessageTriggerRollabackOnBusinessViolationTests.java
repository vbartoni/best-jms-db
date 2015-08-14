package com.springsource.open.jms.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations={"classpath*:META-INF/spring/jms-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class AsynchronousMessageTriggerRollabackOnBusinessViolationTests extends AbstractAsynchronousMessageTriggerTests {

    @Test
    public void testCleanData() {
        jmsTemplate.convertAndSend("async", "foo.before");
        jmsTemplate.convertAndSend("async", "bar.after");
    }

    @Override
    protected void checkPostConditions() {

        // Two messages committed
        assertEquals(0, SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate,
                "T_FOOS"));
        List<String> list = getMessages();
        // No messages rolled back so queue was empty
        assertEquals(2, list.size());

    }

}
