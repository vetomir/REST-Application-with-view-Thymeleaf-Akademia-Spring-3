package pl.gregorymartin.akademiaspringaw3.service;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.gregorymartin.akademiaspringaw3.model.Car;
import pl.gregorymartin.akademiaspringaw3.model.CarRepo;
import pl.gregorymartin.akademiaspringaw3.model.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Service
public class CarService {
    private CarRepo repository;
    private HateoasService hateoasService;

    //

    public CarService(CarRepo repository) {
        this.repository = repository;

        repository.save(new Car("BMW","F10", Colors.BLACK));
        repository.save(new Car("Audi","A6", Colors.ORANGE));
        repository.save(new Car("Ford","Focus", Colors.GREEN));
        repository.save(new Car("Mercedes","Klasse C", Colors.WHITE));
        repository.save(new Car("Fiat","Multipla", Colors.PINK));
        repository.save(new Car("Porsche","911", Colors.BLACK));
        repository.save(new Car("Volvo","S90", Colors.GRAY));
        repository.save(new Car("Alfa Romeo","Giulia", Colors.RED));
        repository.save(new Car("Audi","A6", Colors.ORANGE));
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

    public List<Car> findByColor(String colorName){

        Colors color = Colors.valueOf(colorName.toUpperCase());

        return repository.findAll().stream()
                .filter(x -> x.getColors() == color)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public Car replaceCar(Integer id, Car newCar){
        ;
        newCar.setId(id);

        Optional<Car> result = repository.findById(id)
                .map(x -> {

                    x.setMark(newCar.getMark());
                    x.setModel(newCar.getModel());
                    x.setColors(newCar.getColors());

                    return repository.save(x);
                });

        return result.get();
    }

    public Car partialUpdate(Car newCar, Integer id){
        Optional<Car> result = repository.findById(id)
                .map(x -> {
                    if (newCar.getMark() != null) {
                        x.setMark(newCar.getMark());
                    }
                    if (newCar.getModel() != null) {
                        x.setModel(newCar.getModel());
                    }
                    if (newCar.getColors() != null) {
                        x.setColors(newCar.getColors());
                    }
                    return repository.save(x);
                });


            return result.get();
    }

    //

    public boolean deleteCar(Integer id){
        Optional<Car> carToDelete = repository.findById(id);
        if(carToDelete.isPresent()){
            repository.deleteById(id);
            return true;
        }
        else
            return false;
    }



}
