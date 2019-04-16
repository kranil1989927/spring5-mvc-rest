package learn.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import learn.springframework.api.v1.model.VendorDTO;
import learn.springframework.api.v1.model.VendorListDTO;
import learn.springframework.service.VendorService;

@Api(description = "Vendor operation specific api")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

	public static final String BASE_URL = "/api/v1/vendors";

	private final VendorService vendorService;

	public VendorController(VendorService vendorService) {
		this.vendorService = vendorService;
	}

	@ApiOperation(value = "Get the list of vendors", notes = "There are some API notes")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public VendorListDTO getAllVendors() {
		return vendorService.getAllVendors();
	}

	@ApiOperation(value = "Get the vendor details based on vendor id")
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO getVendorById(@PathVariable Long id) {
		return vendorService.getVendorById(id);
	}

	@ApiOperation(value = "Create the new vendor")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
		return vendorService.createNewVendor(vendorDTO);
	}

	@ApiOperation(value = "Update the vendor by vendor id")
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.saveVendorByDTO(id, vendorDTO);
	}

	@ApiOperation(value = "Patch the vendor by vendor id")
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.patchVendor(id, vendorDTO);
	}

	@ApiOperation(value = "Delete the vendor by vendor id")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteVendor(@PathVariable Long id) {
		vendorService.deleteVendorById(id);
	}
}
