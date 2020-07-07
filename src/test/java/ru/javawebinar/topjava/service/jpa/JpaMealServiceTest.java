package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(value = {"jpa", "postgres"})
public class JpaMealServiceTest extends AbstractMealServiceTest {

}
