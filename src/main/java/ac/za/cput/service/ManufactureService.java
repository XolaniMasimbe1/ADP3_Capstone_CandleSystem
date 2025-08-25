package ac.za.cput.service;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.repository.ManufactureRepository;
import ac.za.cput.service.Imp.IManufactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ManufactureService implements IManufactureService {
    private final ManufactureRepository repository;

    @Autowired
    public ManufactureService(ManufactureRepository repository) {
        this.repository = repository;
    }

    @Override
    public Manufacture create(Manufacture manufacture) { return this.repository.save(manufacture); }

    @Override
    public Manufacture read(Long manufactureNumber) { return this.repository.findById(manufactureNumber).orElse(null); }

    @Override
    public Manufacture update(Manufacture manufacture) { return this.repository.save(manufacture); }

    @Override
    public List<Manufacture> getAll() { return this.repository.findAll(); }
}