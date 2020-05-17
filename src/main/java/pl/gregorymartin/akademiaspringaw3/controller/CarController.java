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
import pl.gregorymartin.akademiaspringaw3.service.HateoasService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.OptionalDataException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping("/api/cars")
@EnableSwagger2
public class CarController {

    private CarService service;
    private HateoasService hateoasService;

    //

    public CarController(final CarService service, HateoasService hateoasService) {
        this.service = service;
        this.hateoasService = hateoasService;
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

        return new ResponseEntity(service.getRepository().findAll(), HttpStatus.OK);
    }

    //

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<Car> getById(@PathVariable Integer id){
        Optional<Car> result = service.getRepository().findById(id);


        if(result.isPresent()){

            return new ResponseEntity(result, HttpStatus.OK);

        }else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    //

    @GetMapping( produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<List<Car>> getByColor(@RequestParam String color) {

        try {
            List<Car> result = service.findByColor(color);
            return new ResponseEntity(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }


    //

    @PostMapping( produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<EntityModel<Car>> addCars(@RequestBody Car newCar){
        hateoasService.hateoasForSingleObject(newCar);
        service.addNewCar(newCar);

        return new ResponseEntity(newCar,HttpStatus.OK);

    }

    //

    @PutMapping( value = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<EntityModel<Car>> putCar(@PathVariable Integer id, @RequestBody Car newCar){
        try {
            service.replaceCar(id, newCar);
            Car updatedCar = service.getRepository().findById(id).get();
            return new ResponseEntity(updatedCar, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    //

    @PatchMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<Car> modifyCar(@PathVariable Integer id, @RequestBody Car newCar){
            try{
                service.partialUpdate(newCar,id);
                Car updatedCar = service.getRepository().findById(id).get();
                return new ResponseEntity(updatedCar, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
    }

    //

    @DeleteMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<Car> removeCars(@PathVariable Integer id){

        boolean delete =  service.deleteCar(id);

        if(delete){
            return new ResponseEntity("You deleted Car with id=" + id ,HttpStatus.OK);
        }
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

}
