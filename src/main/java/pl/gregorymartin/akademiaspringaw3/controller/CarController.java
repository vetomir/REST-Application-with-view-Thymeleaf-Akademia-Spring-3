package pl.gregorymartin.akademiaspringaw3.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gregorymartin.akademiaspringaw3.model.Car;
import pl.gregorymartin.akademiaspringaw3.model.Colors;
import pl.gregorymartin.akademiaspringaw3.service.CarService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping("/cars")
@EnableSwagger2
class CarController {

    private CarService service;
    private HateoasController hateoasController;

    //

    public CarController(final CarService service, HateoasController hateoasController) {
        this.service = service;
        this.hateoasController = hateoasController;

        hateoasController.hateoasForFullRepository(this);
    }

    public CarService getService() {
        return service;
    }

    public void setService(final CarService service) {
        this.service = service;
    }

    //

    @GetMapping(value = "/all", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<List<Car>> getCars(){

        return new ResponseEntity(service, HttpStatus.OK);
    }

    //

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<Car> getById(@PathVariable Integer id){
        Optional<Car> result = service.getCars().stream().filter(x -> x.getId() == id).findFirst();


        if(result.isPresent()){

            return new ResponseEntity(result, HttpStatus.OK);

        }else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    //

    @GetMapping( produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<List<Car>> getByColor(@RequestParam Colors color){

        try{
            List<Car> result = service.getCars().stream().filter(x -> x.getColors() == color).collect(Collectors.toList());

            return new ResponseEntity(result, HttpStatus.OK);

        }catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    //

    @PostMapping( produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<EntityModel<Car>> addCars(@RequestBody Car newCar){
        Optional<Car> result = service.getCars().stream().filter(x -> x.getId() == newCar.getId()).findAny();

        if( !result.isPresent()){

            service.addNewCar(newCar);
            hateoasController.hateoasForSingleObject(newCar);
            return new ResponseEntity(newCar,HttpStatus.OK);
        }

        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    //

    @PutMapping( value = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<Car> replaceCar(@PathVariable Integer id, @RequestBody Car newCar){

            boolean delete =  service.deleteCar(id);

            if(delete){

                newCar.setId(id);
                hateoasController.hateoasForSingleObject(newCar);
                service.addNewCar(newCar);
                return new ResponseEntity(newCar, HttpStatus.OK);

            }else

                return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    //

    @PatchMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<Car> modifyCar(@PathVariable Integer id, @RequestBody Car newCar){
        Optional<Car> result = service.getCars().stream().filter(x -> x.getId() == id).findFirst();

            if(result.isPresent()){
                service.partialUpdate(newCar, id, result);
                return new ResponseEntity(result, HttpStatus.OK);
            }
            else if(!result.isPresent()){
                return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
            }
            else
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    //

    @DeleteMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<Car> removeCars(@PathVariable Integer id){

        boolean delete =  service.deleteCar(id);

        if(delete){
            Link link = linkTo(CarController.class).slash(id).withSelfRel();
            return new ResponseEntity(HttpStatus.OK);
        }
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

}
