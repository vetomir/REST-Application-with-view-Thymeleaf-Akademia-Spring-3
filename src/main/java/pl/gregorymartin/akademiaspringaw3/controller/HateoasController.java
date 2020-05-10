package pl.gregorymartin.akademiaspringaw3.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.RestController;
import pl.gregorymartin.akademiaspringaw3.model.Car;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
class HateoasController {
    private CarController carController;

    //

    public HateoasController( @Lazy CarController carController) {
        this.carController = carController;

    }

    //

    public boolean hateoasForFullRepository(CarController carController){
        List<Car> cars = carController.getService().getCars();
        Integer anyCarId = cars.stream()
                .findAny()
                .get().getId();

        boolean singleLink = carController.getService()
                .getCars()
                .get(anyCarId)
                .hasLinks();

        if(!singleLink){
            cars.forEach(
                    x -> x.add(linkTo(CarController.class)
                            .slash(x.getId())
                            .withSelfRel()
                    ));
        }
        Link link = linkTo(CarController.class).withSelfRel();
        CollectionModel<Car> carListEntityModel = new CollectionModel(cars,link);
        return true;
    }

    //

    public EntityModel hateoasForSingleObject(Car car){
            car.add(linkTo(CarController.class).slash(car.getId()).withSelfRel());
        Link link = linkTo(CarController.class).withSelfRel();
        return new EntityModel(car,link);
    }
}
