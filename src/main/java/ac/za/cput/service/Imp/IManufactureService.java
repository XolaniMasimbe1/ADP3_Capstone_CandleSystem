package ac.za.cput.service.Imp;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.service.IService;

import java.util.List;
import java.util.Optional;


public interface IManufactureService extends IService<Manufacture, Long> {
    List<Manufacture> getAll();
}