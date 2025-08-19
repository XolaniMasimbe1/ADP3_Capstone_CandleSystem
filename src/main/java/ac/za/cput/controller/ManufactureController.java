package ac.za.cput.controller;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.service.ManufactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manufacture")
public class ManufactureController {

    private final ManufactureService manufactureService;

    @Autowired
    public ManufactureController(ManufactureService manufactureService) {
        this.manufactureService = manufactureService;
    }

    @PostMapping("/create")
    public Manufacture create(@RequestBody Manufacture manufacture) {
        return manufactureService.create(manufacture);
    }

    @GetMapping("/read/{manufacturerNumber}")
    public Manufacture read(@PathVariable Long manufacturerNumber) {
        return manufactureService.read(manufacturerNumber);
    }

    @PutMapping("/update")
    public Manufacture update(@RequestBody Manufacture manufacture) {
        return manufactureService.update(manufacture);
    }



    @GetMapping("/all")
    public List<Manufacture> getAll() {
        return manufactureService.getAll();
    }
}