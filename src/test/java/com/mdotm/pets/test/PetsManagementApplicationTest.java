package com.mdotm.pets.test;

import com.mdotm.pets.test.utils.PostgreSQLTestExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(PostgreSQLTestExtension.class)
public class PetsManagementApplicationTest {

    @Test
    public void contextLoads() {

    }
}
