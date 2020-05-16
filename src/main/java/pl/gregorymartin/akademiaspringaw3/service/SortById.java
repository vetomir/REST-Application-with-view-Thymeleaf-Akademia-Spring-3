package pl.gregorymartin.akademiaspringaw3.service;

import pl.gregorymartin.akademiaspringaw3.model.Car;

import java.util.Comparator;

public class SortById implements Comparator<Car>
{
    public int compare(Car a, Car b)
    {
        return a.getId() - b.getId();
    }
}
