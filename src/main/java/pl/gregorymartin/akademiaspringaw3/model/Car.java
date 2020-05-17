package pl.gregorymartin.akademiaspringaw3.model;

import javax.persistence.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



import org.springframework.hateoas.RepresentationModel;

@Entity
public class Car extends RepresentationModel
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String mark;
    private String model;
    private Colors colors;


    //

    public Car() {
    }

    public Car(final String mark, final String model) {
        this.mark = mark;
        this.model = model;
    }

    public Car(final String mark, final String model, final Colors colors) {
        this.mark = mark;
        this.model = model;
        this.colors = colors;
    }

    public Car(Integer id, final String mark,final String model, final Colors colors) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.colors = colors;
    }


    //

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(final String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public Colors getColors() {
        return colors;
    }

    public void setColors(final Colors colors) {
        this.colors = colors;
    }
}
