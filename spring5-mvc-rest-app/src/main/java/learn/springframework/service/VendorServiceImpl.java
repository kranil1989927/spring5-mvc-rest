package learn.springframework.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import learn.springframework.api.v1.mapper.VendorMapper;
import learn.springframework.api.v1.model.VendorDTO;
import learn.springframework.api.v1.model.VendorListDTO;
import learn.springframework.controllers.v1.VendorController;
import learn.springframework.domain.Vendor;
import learn.springframework.repositories.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

	private final VendorMapper vendorMapper;
	private final VendorRepository vendorRepository;

	public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
		this.vendorMapper = vendorMapper;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public VendorDTO getVendorById(Long id) {
		return vendorRepository.findById(id)
				.map(vendorMapper::vendorToVendorDTO)
				.map(vendorDTO -> {
					vendorDTO.setVendorUrl(getVendorUrl(id));
					return vendorDTO;
				})
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public VendorListDTO getAllVendors() {
		List<VendorDTO> vendorDTOs = vendorRepository.findAll()
				.stream()
				.map(vendor->{
					VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
					vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
					return vendorDTO;
				})
				.collect(Collectors.toList());
		
		return new VendorListDTO(vendorDTOs);
	}

	@Override
	public VendorDTO createNewVendor(VendorDTO vendorDTO) {
		Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
		return saveAndReturnDTO(vendor);
	}

	private VendorDTO saveAndReturnDTO(Vendor vendor) {
		Vendor savedVendor = vendorRepository.save(vendor);
		
		VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
		vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
		
		return vendorDTO;
	}

	@Override
	public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
		Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
		vendor.setId(id);
		return saveAndReturnDTO(vendor);
	}

	@Override
	public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
		return vendorRepository.findById(id)
				.map(vendor->{
					if(vendorDTO.getName() != null){
                        vendor.setName(vendorDTO.getName());
                    }
                    return saveAndReturnDTO(vendor);
				})
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteVendorById(Long id) {
		vendorRepository.deleteById(id);
	}

	private String getVendorUrl(Long id) {
		return VendorController.BASE_URL + "/" + id;
	}
}
