package learn.springframework.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import learn.springframework.api.v1.mapper.VendorMapper;
import learn.springframework.api.v1.model.VendorDTO;
import learn.springframework.api.v1.model.VendorListDTO;
import learn.springframework.domain.Vendor;
import learn.springframework.repositories.VendorRepository;

public class VendorServiceTest {

	public static final String NAME_1 = "My Vendor";
	public static final long ID_1 = 1L;
	public static final String NAME_2 = "My Vendor";
	public static final long ID_2 = 1L;

	VendorService vendorService;

	@Mock
	VendorRepository vendorRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
	}

	@Test
	public void getVendorById() throws Exception {

		// given
		Vendor vendor = getVendor1();
		when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

		// when
		VendorDTO vendorDTO = vendorService.getVendorById(1L);

		// then
		verify(vendorRepository, times(1)).findById(anyLong());
		assertThat(vendorDTO.getName(), is(equalTo(NAME_1)));
	}

	@Test(expected = ResourceNotFoundException.class)
	public void getVendorByIdNotFound() throws Exception {
		// given
		when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());

		// when
		vendorService.getVendorById(1L);

		// then
		verify(vendorRepository, times(1)).findById(anyLong());
	}

	@Test
	public void getAllVendors() throws Exception {
		// given
		List<Vendor> vendors = Arrays.asList(getVendor1(), getVendor2());
		when(vendorRepository.findAll()).thenReturn(vendors);

		// when
		VendorListDTO vendorListDTO = vendorService.getAllVendors();

		// then
		verify(vendorRepository, times(1)).findAll();
		assertThat(vendorListDTO.getVendors().size(), is(equalTo(vendors.size())));
	}

	@Test
	public void createNewVendor() throws Exception {
		// given
		VendorDTO vendorDTO = new VendorDTO(NAME_1);

		Vendor vendor = getVendor1();
		when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

		// when
		VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

		// then
		verify(vendorRepository, times(1)).save(any(Vendor.class));
		assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
	}

	@Test
	public void saveVendorByDTO() throws Exception {
		// given
		VendorDTO vendorDTO = new VendorDTO(NAME_1);

		Vendor vendor = getVendor1();

		when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

		// when
		VendorDTO savedVendorDTO = vendorService.saveVendorByDTO(ID_1, vendorDTO);

		// then
		verify(vendorRepository, times(1)).save(any(Vendor.class));
		assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
	}

	@Test
	public void patchVendor() throws Exception {
		// given
		VendorDTO vendorDTO = new VendorDTO(NAME_1);

		Vendor vendor = getVendor1();

		when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
		when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

		// when
		VendorDTO savedVendorDTO = vendorService.patchVendor(ID_1, vendorDTO);

		// then
		verify(vendorRepository, times(1)).save(any(Vendor.class));
		verify(vendorRepository, times(1)).findById(anyLong());
		assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
	}

	@Test
	public void deleteVendorById() throws Exception {
		vendorRepository.deleteById(ID_1);
		verify(vendorRepository, times(1)).deleteById(anyLong());
	}

	private Vendor getVendor1() {
		Vendor vendor = new Vendor();
		vendor.setName(NAME_1);
		vendor.setId(ID_1);
		return vendor;
	}

	private Vendor getVendor2() {
		Vendor vendor = new Vendor();
		vendor.setName(NAME_2);
		vendor.setId(ID_2);
		return vendor;
	}
}
