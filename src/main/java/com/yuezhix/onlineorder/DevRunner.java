package com.yuezhix.onlineorder;


import com.yuezhix.onlineorder.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


// Creates a demo customer for local development only (run with the "dev" profile).
@Component
@Profile("dev")
public class DevRunner implements ApplicationRunner {


    private static final Logger logger = LoggerFactory.getLogger(DevRunner.class);

    private static final String DEMO_EMAIL = "foo@mail.com";


    private final CustomerService customerService;


    public DevRunner(
            CustomerService customerService) {
        this.customerService = customerService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Skip when the account already exists, e.g. when INIT_DB=never keeps the old data.
        if (customerService.getCustomerByEmail(DEMO_EMAIL) != null) {
            logger.info("Demo customer {} already exists", DEMO_EMAIL);
            return;
        }
        customerService.signUp(DEMO_EMAIL, "123456", "Foo", "Bar");
        logger.info("Created demo customer {}", DEMO_EMAIL);
    }
}
