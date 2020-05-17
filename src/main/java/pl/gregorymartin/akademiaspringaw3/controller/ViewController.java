package pl.gregorymartin.akademiaspringaw3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.gregorymartin.akademiaspringaw3.model.Car;
import pl.gregorymartin.akademiaspringaw3.model.Colors;
import pl.gregorymartin.akademiaspringaw3.service.CarService;
import pl.gregorymartin.akademiaspringaw3.service.SortById;

import java.util.Collections;

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

    @GetMapping("/")
    public String getCarViewController(Model model){
        Collections.sort(service.getRepository().findAll(), new SortById());
        model.addAttribute("cars", service.getRepository().findAll());
        model.addAttribute("newCar", new Car());
        return "car";
    }

    @GetMapping("/add-car")
    public String addCarViewController( @RequestParam String mark, @RequestParam String model, @RequestParam Colors color ){

        carController.addCars(new Car(mark,model,color));
        return "redirect:/";

    }
    @GetMapping("/replace-car")
    public String replaceCarViewController(@RequestParam Integer id, @RequestParam String mark, @RequestParam String model, @RequestParam Colors color ){

        carController.putCar(id,new Car(mark,model,color));

        return "redirect:/";

    }
    @GetMapping("/modify-car")
    public String modifyCarViewController(@RequestParam Integer id, @RequestParam String mark, @RequestParam String model, @RequestParam Colors colors ){

        if(colors != Colors.NONE) {
            Colors colorIsPresent;
            colorIsPresent = colors;
            carController.modifyCar(id,new Car(mark,model,colorIsPresent));
            System.out.println();
        }
        else
            carController.modifyCar(id,new Car(mark,model));

        return "redirect:/";

    }
    @PostMapping("/remove-car")
    public String deleteCarViewController(@RequestParam Integer id){

        carController.removeCars(id);

        return "redirect:/";
    }
}

