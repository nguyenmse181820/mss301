package se.carservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.carservice.dto.request.ManufacturerRequest;
import se.carservice.dto.response.ManufacturerResponse;
import se.carservice.pojo.Manufacturer;
import se.carservice.repository.ManufacturerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerResponse createManufacturer(ManufacturerRequest request) {
        if (manufacturerRepository.existsByManufacturerName(request.getManufacturerName())) {
            throw new RuntimeException("Manufacturer with name already exists: " + request.getManufacturerName());
        }

        Manufacturer manufacturer = Manufacturer.builder()
                .manufacturerName(request.getManufacturerName())
                .description(request.getDescription())
                .manufacturerCountry(request.getManufacturerCountry())
                .build();

        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        return convertToResponse(savedManufacturer);
    }

    public List<ManufacturerResponse> getAllManufacturers() {
        return manufacturerRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ManufacturerResponse getManufacturerById(Integer id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found with id: " + id));
        return convertToResponse(manufacturer);
    }

    public ManufacturerResponse updateManufacturer(Integer id, ManufacturerRequest request) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found with id: " + id));

        if (!manufacturer.getManufacturerName().equals(request.getManufacturerName()) &&
                manufacturerRepository.existsByManufacturerName(request.getManufacturerName())) {
            throw new RuntimeException("Manufacturer with name already exists: " + request.getManufacturerName());
        }

        manufacturer.setManufacturerName(request.getManufacturerName());
        manufacturer.setDescription(request.getDescription());
        manufacturer.setManufacturerCountry(request.getManufacturerCountry());

        Manufacturer updatedManufacturer = manufacturerRepository.save(manufacturer);
        return convertToResponse(updatedManufacturer);
    }

    public void deleteManufacturer(Integer id) {
        if (!manufacturerRepository.existsById(id)) {
            throw new RuntimeException("Manufacturer not found with id: " + id);
        }
        manufacturerRepository.deleteById(id);
    }

    private ManufacturerResponse convertToResponse(Manufacturer manufacturer) {
        return ManufacturerResponse.builder()
                .manufacturerId(manufacturer.getManufacturerId())
                .manufacturerName(manufacturer.getManufacturerName())
                .description(manufacturer.getDescription())
                .manufacturerCountry(manufacturer.getManufacturerCountry())
                .build();
    }
}
