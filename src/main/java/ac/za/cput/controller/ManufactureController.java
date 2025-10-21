package ac.za.cput.controller;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.factory.ManufactureFactory;
import ac.za.cput.service.ManufactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/manufacture")
@Transactional
public class ManufactureController {

    private final ManufactureService manufactureService;

    @Autowired
    public ManufactureController(ManufactureService manufactureService) {
        this.manufactureService = manufactureService;
    }

    @PostMapping("/create")
    public Manufacture create(@RequestBody Manufacture manufacture) {
        // Use ManufactureFactory to ensure proper validation
        Manufacture newManufacture = ManufactureFactory.createManufacture(manufacture.getManufacturerName());
        
        if (newManufacture == null) {
            throw new RuntimeException("Failed to create manufacturer - invalid data provided");
        }
        
        return manufactureService.create(newManufacture);
    }

    @GetMapping("/read/{manufacturerNumber}")
    public Manufacture read(@PathVariable Long manufacturerNumber) {
        return manufactureService.read(manufacturerNumber);
    }

    @PutMapping("/update")
    public Manufacture update(@RequestBody Manufacture manufacture) {
        return manufactureService.update(manufacture);
    }

    @DeleteMapping("/delete/{manufacturerNumber}")
    public ResponseEntity<Void> delete(@PathVariable Long manufacturerNumber) {
        boolean deleted = manufactureService.delete(manufacturerNumber);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }



    @GetMapping("/all")
    public List<Manufacture> getAll() {
        return manufactureService.getAll();
    }
}
