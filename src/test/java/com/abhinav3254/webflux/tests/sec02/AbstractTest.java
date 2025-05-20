package com.abhinav3254.webflux.tests.sec02;

import org.springframework.boot.test.context.SpringBootTest;


// logging.level.org.springframework.r2dbc=DEBUG this will sql commands
@SpringBootTest(properties = {
        "sec=sec02",
        "logging.level.org.springframework.r2dbc=DEBUG"
})
public abstract class AbstractTest {
}
