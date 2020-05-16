package pl.gregorymartin.akademiaspringaw3.service;

import org.springframework.stereotype.Service;
import pl.gregorymartin.akademiaspringaw3.model.Car;
import pl.gregorymartin.akademiaspringaw3.model.CarRepo;
import pl.gregorymartin.akademiaspringaw3.model.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CarService {
    private CarRepo repository;
    private HateoasService hateoasService;

    //

    public CarService() {
        repository.save(new Car("BMW","F10", Colors.BLACK));
        repository.save(new Car("Audi","A6", Colors.ORANGE));
        repository.save(new Car("Ford","Focus", Colors.GREEN));
        repository.save(new Car("Mercedes","Klasse C", Colors.WHITE));
        repository.save(new Car("Fiat","Multipla", Colors.PINK));
        repository.save(new Car("Porsche","911", Colors.BLACK));
        repository.save(new Car("Volvo","S90", Colors.GRAY));
        repository.save(new Car("Alfa Romeo","Giulia", Colors.RED));
    }

    //

    public CarRepo getRepository() {
        return repository;
    }

    public void setRepository(final CarRepo repository) {
        this.repository = repository;
    }


    //

    public boolean addNewCar(Car car){
        repository.save(car);

        return true;
    }

    //

    public Car partialUpdate(Car newCar, Integer id, Optional<Car> result){
                if(newCar.getMark() != null){
                    result.get().setMark(newCar.getMark());
    }
                if(newCar.getModel() != null){
                    result.get().setModel(newCar.getModel());
    }
                if(newCar.getColors() != null) {
                    result.get().setColors(newCar.getColors());
                }
                return result.get();
    }

    //

    public boolean deleteCar(Integer id){
        Optional<Car> carToDelete = repository.findAll().stream().filter(x -> x.getId() == id).findFirst();
        if(carToDelete.isPresent()){
            cars.remove(carToDelete.get());
            return true;
        }
        else
            return false;
    }
}
