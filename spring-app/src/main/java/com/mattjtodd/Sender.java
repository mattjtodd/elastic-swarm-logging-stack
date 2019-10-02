package com.mattjtodd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Sender {

    private final Logger logger = LoggerFactory.getLogger(Sender.class);

    @RequestMapping("/greeting")
    public void send(@RequestParam("message") String message) {
        logger.debug(message);
        logger.info(message);
        logger.warn(message);
        logger.error(message);
    }
}
