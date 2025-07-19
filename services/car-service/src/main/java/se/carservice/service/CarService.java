package se.carservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.carservice.dto.request.CarRequest;
import se.carservice.dto.response.CarResponse;
import se.carservice.exception.BadRequestException;
import se.carservice.exception.ResourceNotFoundException;
import se.carservice.pojo.CarInformation;
import se.carservice.pojo.Manufacturer;
import se.carservice.pojo.Supplier;
import se.carservice.repository.CarInformationRepository;
import se.carservice.repository.ManufacturerRepository;
import se.carservice.repository.SupplierRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {

    private final CarInformationRepository carRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final SupplierRepository supplierRepository;    public CarResponse createCar(CarRequest request) {
        Manufacturer manufacturer = manufacturerRepository.findById(request.getManufacturerId())
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer not found with id: " + request.getManufacturerId()));

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + request.getSupplierId()));

        if (carRepository.existsByCarNameAndManufacturer_ManufacturerId(request.getCarName(), request.getManufacturerId())) {
            throw new BadRequestException("Car with name '" + request.getCarName() + "' already exists for this manufacturer");
        }

        CarInformation car = CarInformation.builder()
                .carName(request.getCarName())
                .carDescription(request.getCarDescription())
                .numberOfDoors(request.getNumberOfDoors())
                .seatingCapacity(request.getSeatingCapacity())
                .fuelType(request.getFuelType())
                .year(request.getYear())
                .carStatus(request.getCarStatus())
                .carRentingPricePerDay(request.getCarRentingPricePerDay())
                .manufacturer(manufacturer)
                .supplier(supplier)
                .build();

        CarInformation savedCar = carRepository.save(car);
        return convertToResponse(savedCar);
    }

    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }    public CarResponse getCarById(Integer id) {
        CarInformation car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        return convertToResponse(car);
    }

    public List<CarResponse> getAvailableCars() {
        return carRepository.findByCarStatus((byte) 1).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }    public CarResponse updateCar(Integer id, CarRequest request) {
        CarInformation car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));

        Manufacturer manufacturer = manufacturerRepository.findById(request.getManufacturerId())
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer not found with id: " + request.getManufacturerId()));

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + request.getSupplierId()));

        boolean nameExists = carRepository.findAll().stream()
                .anyMatch(existingCar -> !existingCar.getCarId().equals(id)
                        && existingCar.getCarName().equals(request.getCarName())
                        && existingCar.getManufacturer().getManufacturerId().equals(request.getManufacturerId()));

        if (nameExists) {
            throw new BadRequestException("Car with name '" + request.getCarName() + "' already exists for this manufacturer");
        }

        car.setCarName(request.getCarName());
        car.setCarDescription(request.getCarDescription());
        car.setNumberOfDoors(request.getNumberOfDoors());
        car.setSeatingCapacity(request.getSeatingCapacity());
        car.setFuelType(request.getFuelType());
        car.setYear(request.getYear());
        car.setCarStatus(request.getCarStatus());
        car.setCarRentingPricePerDay(request.getCarRentingPricePerDay());
        car.setManufacturer(manufacturer);
        car.setSupplier(supplier);

        CarInformation updatedCar = carRepository.save(car);
        return convertToResponse(updatedCar);
    }    public void deleteCar(Integer id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }

    public List<CarResponse> getCarsByManufacturer(Integer manufacturerId) {
        return carRepository.findByManufacturer_ManufacturerId(manufacturerId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<CarResponse> getCarsBySupplier(Integer supplierId) {
        return carRepository.findBySupplier_SupplierId(supplierId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }    public CarResponse updateCarStatus(Integer id, Byte status) {
        CarInformation car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        
        car.setCarStatus(status);
        CarInformation updatedCar = carRepository.save(car);
        return convertToResponse(updatedCar);
    }

    private CarResponse convertToResponse(CarInformation car) {
        return CarResponse.builder()
                .carId(car.getCarId())
                .carName(car.getCarName())
                .carDescription(car.getCarDescription())
                .numberOfDoors(car.getNumberOfDoors())
                .seatingCapacity(car.getSeatingCapacity())
                .fuelType(car.getFuelType())
                .year(car.getYear())
                .carStatus(getCarStatusString(car.getCarStatus()))
                .carRentingPricePerDay(car.getCarRentingPricePerDay())
                .manufacturerName(car.getManufacturer().getManufacturerName())
                .supplierName(car.getSupplier().getSupplierName())
                .manufacturerId(car.getManufacturer().getManufacturerId())
                .supplierId(car.getSupplier().getSupplierId())
                .build();
    }

    private String getCarStatusString(Byte status) {
        return switch (status) {
            case 1 -> "Available";
            case 0 -> "Rented";
            case 2 -> "Maintenance";
            default -> "Unknown";
        };
    }
}
