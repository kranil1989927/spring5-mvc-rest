package learn.springframework.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import learn.springframework.api.v1.model.VendorDTO;
import learn.springframework.domain.Vendor;

public class VendorMapperTest {

	private static final String VENDOR_NAME = "Amul";

	VendorMapper vendorMapper = VendorMapper.INSTANCE;

	@Test
	public void vendorToVendorDTO() throws Exception {

		Vendor vendor = new Vendor();
		vendor.setName(VENDOR_NAME);

		VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

		assertEquals(vendor.getName(), vendorDTO.getName());
	}

	@Test
	public void vendorDTOToVendor() throws Exception {

		VendorDTO vendorDTO = new VendorDTO(VENDOR_NAME, "/api/v1/vendors/1");

		Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

		assertEquals(vendorDTO.getName(), vendor.getName());
	}

}
