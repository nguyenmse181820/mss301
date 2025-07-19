package se.carservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.carservice.dto.request.SupplierRequest;
import se.carservice.dto.response.SupplierResponse;
import se.carservice.pojo.Supplier;
import se.carservice.repository.SupplierRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierResponse createSupplier(SupplierRequest request) {
        if (supplierRepository.existsBySupplierName(request.getSupplierName())) {
            throw new RuntimeException("Supplier with name already exists: " + request.getSupplierName());
        }

        Supplier supplier = Supplier.builder()
                .supplierName(request.getSupplierName())
                .supplierDescription(request.getSupplierDescription())
                .supplierAddress(request.getSupplierAddress())
                .build();

        Supplier savedSupplier = supplierRepository.save(supplier);
        return convertToResponse(savedSupplier);
    }

    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public SupplierResponse getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        return convertToResponse(supplier);
    }

    public SupplierResponse updateSupplier(Integer id, SupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));

        if (!supplier.getSupplierName().equals(request.getSupplierName()) &&
                supplierRepository.existsBySupplierName(request.getSupplierName())) {
            throw new RuntimeException("Supplier with name already exists: " + request.getSupplierName());
        }

        supplier.setSupplierName(request.getSupplierName());
        supplier.setSupplierDescription(request.getSupplierDescription());
        supplier.setSupplierAddress(request.getSupplierAddress());

        Supplier updatedSupplier = supplierRepository.save(supplier);
        return convertToResponse(updatedSupplier);
    }

    public void deleteSupplier(Integer id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }

    private SupplierResponse convertToResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .supplierId(supplier.getSupplierId())
                .supplierName(supplier.getSupplierName())
                .supplierDescription(supplier.getSupplierDescription())
                .supplierAddress(supplier.getSupplierAddress())
                .build();
    }
}
