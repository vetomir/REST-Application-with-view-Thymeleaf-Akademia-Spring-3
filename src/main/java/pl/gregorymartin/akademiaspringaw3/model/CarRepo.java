package pl.gregorymartin.akademiaspringaw3.model;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepo extends JpaRepository<Car, Integer> {

    @Override
    List<Car> findAll();

    @Override
    <S extends Car> List<S> findAll(Example<S> example);

    @Override
    <S extends Car> S save(S s);

    @Override
    boolean existsById(Integer integer);

    @Override
    void deleteById(Integer integer);

    @Override
    void delete(Car car);
}
