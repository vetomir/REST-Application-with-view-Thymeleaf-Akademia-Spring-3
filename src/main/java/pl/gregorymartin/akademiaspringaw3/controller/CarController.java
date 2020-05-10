package pl.gregorymartin.akademiaspringaw3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@RestController
@RequestMapping("/cars")
@EnableSwagger2
class CarController {

    private CarService service;
    private Logger logger = LoggerFactory.getLogger(CarController.class);

    //


    public CarController(final CarService service) {
        this.service = service;
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Car>> getCars(){
        return new ResponseEntity(service, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Car> getById(@PathVariable Integer id){
        Optional<Car> result = service.getCars().stream().filter(x -> x.getId() == id).findFirst();

        if(result.isPresent()){
            return new ResponseEntity(result.get(), HttpStatus.OK);

        }else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping( produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Car>> getByColor(@RequestParam Colors color){
        try{
            List<Car> result = service.getCars().stream().filter(x -> x.getColors() == color).collect(Collectors.toList());

            return new ResponseEntity(result, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addCars(@RequestBody Car newCar){
        Optional<Car> result = service.getCars().stream().filter(x -> x.getId() == newCar.getId()).findAny();
        if(result.isPresent()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        else
            service.addNewCar(newCar);
            return new ResponseEntity(newCar,HttpStatus.OK);
    }
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Car>> replaceCar(@PathVariable Integer id, @RequestBody Car newCar){

            boolean delete =  service.deleteCar(id);

            if(delete){

                newCar.setId(id);
                service.addNewCar(newCar);
                return new ResponseEntity(newCar, HttpStatus.OK);

            }else

                return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Car>> modifyCar(@PathVariable Integer id, @RequestBody Car newCar){
        Optional<Car> result = service.getCars().stream().filter(x -> x.getId() == id).findFirst();
            if(result.isPresent()){
                service.partialUpdate(newCar, id, result);
                return new ResponseEntity(result, HttpStatus.OK);
            }else

                return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Car> removeCars(@PathVariable Integer id){
        boolean delete =  service.deleteCar(id);
        if(delete){
            return new ResponseEntity(HttpStatus.OK);
        }
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);




    }

}
