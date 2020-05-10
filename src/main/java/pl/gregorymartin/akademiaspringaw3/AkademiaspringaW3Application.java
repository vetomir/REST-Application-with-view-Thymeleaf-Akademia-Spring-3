package pl.gregorymartin.akademiaspringaw3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AkademiaspringaW3Application {

    public static void main(String[] args) {
        SpringApplication.run(AkademiaspringaW3Application.class, args);
    }

}
