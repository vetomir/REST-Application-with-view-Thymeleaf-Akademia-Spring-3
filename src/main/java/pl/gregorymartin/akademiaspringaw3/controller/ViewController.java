package pl.gregorymartin.akademiaspringaw3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.gregorymartin.akademiaspringaw3.controller.CarController;
import pl.gregorymartin.akademiaspringaw3.model.Car;
import pl.gregorymartin.akademiaspringaw3.model.CarRepo;
import pl.gregorymartin.akademiaspringaw3.model.Colors;
import pl.gregorymartin.akademiaspringaw3.service.CarService;
import pl.gregorymartin.akademiaspringaw3.service.SortById;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewController {

    private CarController carController;
    private CarService service;

    //

    public ViewController(final CarController carController, final CarService service) {
        this.carController = carController;
        this.service = service;

    }

    public CarController getCarController() {
        return carController;
    }

    public void setCarController(final CarController carController) {
        this.carController = carController;
    }

    public CarService getService() {
        return service;
    }

    public void setService(final CarService service) {
        this.service = service;
    }

    //

    @GetMapping("/all")
    public String getCarViewController(Model model){
        Collections.sort(service.getRepository().findAll(), new SortById());
        model.addAttribute("cars", service.getRepository().findAll());
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
        return "redirect:/all";

    }
    @GetMapping("/replace-car")
    public String replaceCarViewController(@RequestParam Integer id, @RequestParam String mark, @RequestParam String model, @RequestParam String colors ){
        if(!colors.isEmpty()) {
            Colors color;
            color = Colors.valueOf(colors.toUpperCase());
            carController.putCar(id,new Car(mark,model,color));
        }
        else
            carController.putCar(id,new Car(mark,model));
        return "redirect:/all";

    }
    @GetMapping("/modify-car")
    public String modifyCarViewController(@RequestParam Integer id, @RequestParam String mark, @RequestParam String model, @RequestParam String colors ){

        carController.modifyCar(id,new Car(mark,model));

        return "redirect:/all";

    }
    @GetMapping("/remove-car")
    public String deleteCarViewController(@RequestParam Integer id){

        carController.removeCars(id);

        return "redirect:/all";
    }
}

