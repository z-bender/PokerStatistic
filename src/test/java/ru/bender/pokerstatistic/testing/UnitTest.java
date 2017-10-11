package ru.bender.pokerstatistic.testing;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-tests.yml")
public @interface UnitTest {
}
