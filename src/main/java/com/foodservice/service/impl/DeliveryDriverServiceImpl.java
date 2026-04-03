package com.foodservice.service.impl;

import com.foodservice.config.CustomMapper;
import com.foodservice.entity.DeliveryDriver;
import com.foodservice.entity.Order;
import com.foodservice.entity.dto.DeliveryDriverResponseDTO;
import com.foodservice.exception.DriverNotFoundException;
import com.foodservice.repository.DeliveryDriverRepository;
import com.foodservice.repository.OrderRepository;
import com.foodservice.service.DeliveryDriverService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryDriverServiceImpl implements DeliveryDriverService {

    private final DeliveryDriverRepository deliveryDriverRepository;
    private final OrderRepository orderRepository;
    private final CustomMapper mapper;
    
    
    public DeliveryDriverServiceImpl(DeliveryDriverRepository deliveryDriverRepository, OrderRepository orderRepository, CustomMapper mapper) {
				this.deliveryDriverRepository = deliveryDriverRepository;
				this.orderRepository = orderRepository;
				this.mapper = mapper;
	}
    
    Logger log = LoggerFactory.getLogger(DeliveryDriverServiceImpl.class);

    @Override
    public DeliveryDriverResponseDTO getDriverById(Integer driverId) {

        log.info("Fetching driver with ID: {}", driverId);

        DeliveryDriver driver = deliveryDriverRepository.findById(driverId)
                .orElseThrow(() -> {
                    log.error("Driver not found with ID: {}", driverId);
                    return new DriverNotFoundException("Driver not found");
                });

        log.info("Driver fetched successfully: {}", driver.getDriverName());

        return mapper.toDriverDTO(driver);
    }

    @Override
    public List<DeliveryDriverResponseDTO> getOrderByDriver(Integer driverId) {

        log.info("Fetching orders for driver ID: {}", driverId);

        List<Order> orders = orderRepository.findByDeliveryDriverId(driverId);

        log.info("Total orders found: {}", orders.size());

        return orders.stream()
                .map(mapper::toOrderDTO)
                .toList();
    }

    @Override
    public List<DeliveryDriverResponseDTO> getRestaurantsByDriver(Integer driverId) {

        log.info("Fetching restaurants for driver ID: {}", driverId);

        List<Order> orders = orderRepository.findByDeliveryDriverId(driverId);

        return orders.stream()
                .map(Order::getRestaurant)
                .distinct()
                .map(mapper::toRestaurantDTO)
                .toList();
    }

    @Override
    public List<DeliveryDriverResponseDTO> getAllDrivers(String sort) {

        log.info("Fetching all drivers with sort: {}", sort);

        List<DeliveryDriver> drivers;

        if ("desc".equalsIgnoreCase(sort)) {
            drivers = deliveryDriverRepository.findAllByOrderByDriverNameDesc();
        } else {
            drivers = deliveryDriverRepository.findAllByOrderByDriverNameAsc();
        }

        log.info("Total drivers fetched: {}", drivers.size());

        return drivers.stream()
                .map(mapper::toDriverDTO)
                .toList();
    }

	@Override
	public List<DeliveryDriverResponseDTO> getDriverDeliveries(Integer driverId) {
		// TODO Auto-generated method stub
		return List.of();
	}

	@Override
	public List<DeliveryDriverResponseDTO> getCustomerOrderByDriver(Integer driverId, Integer customerId) {
		log.info("Fetching customers for driver ID: {}", driverId);

		List<Order> orders =
                orderRepository.findByDeliveryDriverIdAndCustomerId(driverId, customerId);

        return orders.stream()
                .map(mapper::toOrderDTO)
                .toList();
	}

	@Override
	public List<DeliveryDriverResponseDTO> getAllDrivers() {
		log.info("Fetching All divers: " );
		return deliveryDriverRepository.findAll()
                .stream()
                .map(mapper::toDriverDTO)
                .toList();
	}

	@Override
	public List<DeliveryDriverResponseDTO> getcustomerByDriver(Integer driverId) {
		log.info("Fetching customers for driver ID: {}", driverId);
		List<Order> orders = orderRepository.findByDeliveryDriverId(driverId);

        return orders.stream()
                .map(Order::getCustomer)
                .distinct()
                .map(mapper::toCustomerDTO)
                .toList();
	}
}

/*@Service
@RequiredArgsConstructor
public class DeliveryDriverServiceImpl implements DeliveryDriverService {

    private final DeliveryDriverRepository deliveryDriverRepository;
    private final OrderRepository orderRepository;
    private final CustomMapper mapper;
    
    public DeliveryDriverServiceImpl(DeliveryDriverRepository deliveryDriverRepository, OrderRepository orderRepository, CustomMapper mapper) {
    			this.deliveryDriverRepository = deliveryDriverRepository;
    			this.orderRepository = orderRepository;
    			this.mapper = mapper;
    }

    // ---------- Get Driver By ID ------------
    @Override
    public DeliveryDriverResponseDTO getDriverById(Integer driverId) {

        DeliveryDriver driver = deliveryDriverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        return mapper.toDriverDTO(driver);
    }

    // ---------- Get All Drivers ------------
    @Override
    public List<DeliveryDriverResponseDTO> getAllDrivers() {
        return deliveryDriverRepository.findAll()
                .stream()
                .map(mapper::toDriverDTO)
                .toList();
    }

    // ---------- Get All Drivers with sorting ------------
    @Override
    public List<DeliveryDriverResponseDTO> getAllDrivers(String sort) {

        List<DeliveryDriver> drivers;

        if ("desc".equalsIgnoreCase(sort)) {
            drivers = deliveryDriverRepository.findAllByOrderByDriverNameDesc();
        } else {
            drivers = deliveryDriverRepository.findAllByOrderByDriverNameAsc();
        }

        return drivers.stream()
                .map(mapper::toDriverDTO)
                .toList();
    }

    // ---------- Get Orders by Driver ------------
    @Override
    public List<DeliveryDriverResponseDTO> getOrderByDriver(Integer driverId) {

        List<Order> orders = orderRepository.findByDeliveryDriverId(driverId);

        return orders.stream()
                .map(mapper::toOrderDTO)
                .toList();
    }

    // ---------- Get Customer Orders by Driver ------------
    @Override
    public List<DeliveryDriverResponseDTO> getCustomerOrderByDriver(Integer driverId, Integer customerId) {

        List<Order> orders =
                orderRepository.findByDeliveryDriverIdAndCustomerId(driverId, customerId);

        return orders.stream()
                .map(mapper::toOrderDTO)
                .toList();
    }

    // ---------- Get Restaurants by Driver ------------
    @Override
    public List<DeliveryDriverResponseDTO> getRestaurantsByDriver(Integer driverId) {

        List<Order> orders = orderRepository.findByDeliveryDriverId(driverId);

        return orders.stream()
                .map(Order::getRestaurant)
                .distinct()
                .map(mapper::toRestaurantDTO)
                .toList();
    }

	@Override
	public List<DeliveryDriverResponseDTO> getDriverDeliveries(Integer driverId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	// ---------- Get Customers by Driver ------------
	@Override
	public List<DeliveryDriverResponseDTO> getcustomerByDriver(Integer driverId) {
		List<Order> orders = orderRepository.findByDeliveryDriverId(driverId);

        return orders.stream()
                .map(Order::getCustomer)
                .distinct()
                .map(mapper::toCustomerDTO)
                .toList();
	}
}*/