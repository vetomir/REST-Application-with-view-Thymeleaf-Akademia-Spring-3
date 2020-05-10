package pl.gregorymartin.akademiaspringaw3.service;

import org.springframework.stereotype.Service;
import pl.gregorymartin.akademiaspringaw3.model.Car;
import pl.gregorymartin.akademiaspringaw3.model.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CarService {
    private List<Car> cars;
    private HateoasService hateoasService;

    //

    public CarService() {
        cars = new ArrayList<>();
        cars.add(new Car(1,"BMW","F10", Colors.BLACK));
        cars.add(new Car(2,"Audi","A6", Colors.ORANGE));
        cars.add(new Car(3,"Ford","Focus", Colors.GREEN));
        cars.add(new Car(4,"Mercedes","Klasse C", Colors.WHITE));
        cars.add(new Car(5,"Fiat","Multipla", Colors.PINK));
        cars.add(new Car(6,"Porsche","911", Colors.BLACK));
        cars.add(new Car(7,"Volvo","S90", Colors.GRAY));
        cars.add(new Car(8,"Alfa Romeo","Giulia", Colors.RED));
    }

    //

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(final List<Car> cars) {
        this.cars = cars;
    }

    //

    public boolean addNewCar(Car car){
        cars.add(car);

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
        Optional<Car> carToDelete = cars.stream().filter(x -> x.getId() == id).findFirst();
        if(carToDelete.isPresent()){
            cars.remove(carToDelete.get());
            return true;
        }
        else
            return false;
    }
}
