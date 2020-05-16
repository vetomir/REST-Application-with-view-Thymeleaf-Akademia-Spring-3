/*
package pl.gregorymartin.akademiaspringaw3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.gregorymartin.akademiaspringaw3.model.Car;
import pl.gregorymartin.akademiaspringaw3.model.Colors;
import pl.gregorymartin.akademiaspringaw3.service.CarService;
import pl.gregorymartin.akademiaspringaw3.service.SortById;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
@EnableSwagger2
class ViewController {

    private CarController carController;

    private CarService service;
    List<Car> cars;

    //

    public ViewController(final CarService service, CarController carController) {
        this.service = service;
        this.carController = carController;

        cars = service.getCars();
    }

    //

    @RequestMapping("/")
    public void handleRequest() {
        throw new RuntimeException("test exception");
    }

    @GetMapping("/all")
    public String getCarViewController(Model model){
        Collections.sort(cars, new SortById());
        model.addAttribute("cars", cars);
        model.addAttribute("newCar", new Car());
        return "car";
    }

    @GetMapping("/add-car")
    public String addCarViewController( @RequestParam String mark, @RequestParam String model, @RequestParam String colors ){
        Colors color;
        if(!colors.isEmpty()) {
            color = Colors.valueOf(colors.toUpperCase());
        }
        else
            color = Colors.NONE;

        carController.addCars(new Car(mark,model,color));
        return "redirect:/cars/all";

    }
    @GetMapping("/replace-car")
    public String replaceCarViewController(@RequestParam Integer id, @RequestParam String mark, @RequestParam String model, @RequestParam String colors ){
        if(!colors.isEmpty()) {
            Colors color;
            color = Colors.valueOf(colors.toUpperCase());
            carController.modifyCar(id,new Car(mark,model,color));
        }
        else
            carController.replaceCar(id,new Car(mark,model));
        return "redirect:/cars/all";

    }
    @GetMapping("/modify-car")
    public String modifyCarViewController(@RequestParam Integer id, @RequestParam String mark, @RequestParam String model, @RequestParam String colors ){

        if(!colors.isEmpty()) {
            Colors color;
            color = Colors.valueOf(colors.toUpperCase());
            carController.modifyCar(id,new Car(mark,model,color));
        }
        else
            carController.modifyCar(id,new Car(mark,model));

        return "redirect:/cars/all";

    }
    @GetMapping("/remove-car")
    public String deleteCarViewController(@RequestParam Integer id){

        Optional<Car> carToDelete = cars.stream().filter(x -> x.getId().equals(id)).findAny();

        if(carToDelete.isPresent()) {
            //todo
            cars.remove(id - 1);

        }

        return "redirect:/cars/all";
    }
}
*/
