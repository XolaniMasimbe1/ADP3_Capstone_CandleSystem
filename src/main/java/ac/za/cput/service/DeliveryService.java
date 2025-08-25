package ac.za.cput.service;

import ac.za.cput.domain.Delivery;
import ac.za.cput.factory.DeliveryFactory;
import ac.za.cput.repository.DeliveryRepository;
import ac.za.cput.service.Imp.IDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeliveryService implements IDeliveryService {
    private final DeliveryRepository repository;

    @Autowired
    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Delivery create(Delivery delivery) {
        // If deliveryNumber is not provided, use factory to create a new delivery
        if (delivery.getDeliveryNumber() == null || delivery.getDeliveryNumber().isEmpty()) {
            Delivery newDelivery = DeliveryFactory.createDelivery(delivery.getStatus());
            return this.repository.save(newDelivery);
        }
        return this.repository.save(delivery);
    }

    @Override
    public Delivery read(String deliveryNumber) { return this.repository.findById(deliveryNumber).orElse(null); }

    @Override
    public Delivery update(Delivery delivery) { return this.repository.save(delivery); }

    @Override
    public List<Delivery> getAll() { return this.repository.findAll(); }
}