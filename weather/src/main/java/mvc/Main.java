package mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class Main {

    public static void main(String[] args) {
        //ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        SpringApplication.run(Main.class);
    }

}
