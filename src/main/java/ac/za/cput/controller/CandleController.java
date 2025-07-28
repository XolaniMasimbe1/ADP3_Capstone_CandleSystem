package ac.za.cput.controller;

import ac.za.cput.domain.Candle;
import ac.za.cput.service.ICandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/candle")
public class CandleController {

    private final ICandleService service;

    @Autowired
    public CandleController(ICandleService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Candle create(@RequestBody Candle candle) {
        return service.create(candle);
    }

    @GetMapping("/read/{candleNumber}")
    public Candle read(@PathVariable String candleNumber) {
        return service.read(candleNumber);
    }

    @PutMapping("/update")
    public Candle update(@RequestBody Candle candle) {
        return service.update(candle);
    }

    @GetMapping("/find/{candleNumber}")
    public Candle findById(@PathVariable String candleNumber) {
        return service.read(candleNumber);
    }

    @GetMapping("/all")
    public List<Candle> getAll() {
        return service.getAll();
    }
}

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/


