package com.vaibhavtraders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vaibhavtraders.dao.UserDetailsRepository;
import com.vaibhavtraders.dto.CountryDTO;
import com.vaibhavtraders.dto.CustomerDTO;
import com.vaibhavtraders.dto.DeliveryModeDTO;
import com.vaibhavtraders.dto.InvoiceDTO;
import com.vaibhavtraders.dto.ProductDTO;
import com.vaibhavtraders.dto.StateDTO;
import com.vaibhavtraders.entity.Auth;
import com.vaibhavtraders.entity.InvoiceItem;
import com.vaibhavtraders.exception.GeneralException;
import com.vaibhavtraders.requests.InvoiceCreationUpdationRequest;
import com.vaibhavtraders.requests.InvoiceMonthYearRequest;
import com.vaibhavtraders.response.CountryData;
import com.vaibhavtraders.response.ResponseObject;
import com.vaibhavtraders.response.StateData;
import com.vaibhavtraders.service.CountryService;
import com.vaibhavtraders.service.CustomerService;
import com.vaibhavtraders.service.DeliveryModeService;
import com.vaibhavtraders.service.InvoiceItemService;
import com.vaibhavtraders.service.InvoiceService;
import com.vaibhavtraders.service.ProductService;
import com.vaibhavtraders.service.StateService;
import com.vaibhavtraders.service.UserService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class VaibhavTradersRestController {
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	DeliveryModeService deliveryModeService;
	
	@Autowired
	InvoiceItemService invoiceItemService;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	StateService stateService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private UserDetailsRepository userDetailsRepository;  // Inject your repository to check for duplicates

    @Autowired
    private PasswordEncoder passwordEncoder;
	
	//Products
	@PostMapping("/products/add.vt")
	public ResponseObject createProduct(@RequestBody ProductDTO productDTO) throws GeneralException {
		return productService.createProduct(productDTO);
	}
	
	@PostMapping("/products/addMultipleProducts.vt")
	public ResponseObject createProducts(@RequestBody List<ProductDTO> productDTOList) throws GeneralException {
		return productService.createProducts(productDTOList);
	}
	
	@PostMapping("/products/edit.vt")
	public ResponseObject editProduct(@RequestBody ProductDTO productDTO) throws GeneralException {
		return productService.editProduct(productDTO);
	}
	
	@GetMapping("/products/findall.vt")
	public ResponseObject findAllProducts() throws GeneralException {
		return productService.findAllProducts();
	}
	
	@PostMapping("/products/deleteproduct.vt")
	public ResponseObject deleteProduct(@RequestBody ProductDTO productDTO) throws GeneralException {
		return productService.deleteProduct(productDTO);
	}
	
	//Customers
	@PostMapping("/customers/add.vt")
	public ResponseObject createCustomer(@RequestBody CustomerDTO customerDTO) throws GeneralException {
		return customerService.createCustomer(customerDTO);
	}
	
	@PostMapping("/customers/addMultipleCustomers.vt")
	public ResponseObject createCustomers(@RequestBody List<CustomerDTO> customerDTO) throws GeneralException {
		return customerService.createCustomers(customerDTO);
	}
	
	@PostMapping("/customers/edit.vt")
	public ResponseObject editCustomer(@RequestBody CustomerDTO customerDTO) throws GeneralException {
		return customerService.editCustomer(customerDTO);
	}
	
	@GetMapping("/customers/findall.vt")
	public ResponseObject findAllCustomers() throws GeneralException {
		return customerService.findAllCustomers();
	}
	
	@PostMapping("/customers/deletecustomer.vt")
	public ResponseObject deleteCustomer(@RequestBody CustomerDTO customerDTO) throws GeneralException {
		return customerService.deleteCustomer(customerDTO);
	}
	
	//Countries
	@PostMapping("/countries/add")
	public ResponseObject createCountry(@RequestBody CountryDTO countryDTO) throws GeneralException {
		return countryService.createCountry(countryDTO);
	}
	
	/* Implementing find by id for Country to support fetching of specific 
	 * country data for Android Spinners and webapp. */
	@GetMapping("/countries")
	public ResponseObject findCountryById(@RequestParam Long countryID) throws GeneralException {
		return countryService.findCountryById(countryID);
	}
	
	@GetMapping("/countries/findall")
	public ResponseObject findAllCountries() throws GeneralException {
		return countryService.findAllCountries();
	}
	
	@PostMapping("/countries/deletecountry")
	public ResponseObject deleteCountry(@RequestBody CountryDTO countryDTO) throws GeneralException {
		return countryService.deleteCountry(countryDTO);
	}
	
	@GetMapping("/countries/countrydata.vt")
	public List<CountryData> getCountryData() throws GeneralException {
		return countryService.getCountryData();
	}
	
	//Delivery Modes
	@PostMapping("/deliverymodes/add")
	public ResponseObject createDeliveryMode(@RequestBody DeliveryModeDTO deliveryModeDTO) throws GeneralException {
		return deliveryModeService.createDeliveryMode(deliveryModeDTO);
	}
	
	@GetMapping("/deliverymodes/findall")
	public ResponseObject findAllDeliveryModes() throws GeneralException {
		return deliveryModeService.findAllDeliveryModes();
	}
	
	@PostMapping("/deliverymodes/deletedeliverymode")
	public ResponseObject deleteDeliveryMode(@RequestBody DeliveryModeDTO deliveryModeDTO) throws GeneralException {
		return deliveryModeService.deleteDeliveryMode(deliveryModeDTO);
	}
	
	//States
	@PostMapping("/states/add")
	public ResponseObject createState(@RequestBody StateDTO stateDTO) throws GeneralException {
		return stateService.createState(stateDTO);
	}
	
	/* Implementing find by id for States to support fetching of specific 
	 * country data for Android Spinners and webapp. */
	@GetMapping("/states")
	public ResponseObject findStateById(@RequestParam Long stateID) throws GeneralException {
		return stateService.findStateById(stateID);
	}
	
	@GetMapping("/states/findall")
	public ResponseObject findAllStates() throws GeneralException {
		return stateService.findAllStates();
	}
	
	@PostMapping("/states/deletestate")
	public ResponseObject deleteState(@RequestBody StateDTO stateDTO) throws GeneralException {
		return stateService.deleteState(stateDTO);
	}
	
	@GetMapping("/states/statedata.vt")
	public List<StateData> getStateData() throws GeneralException {
		return stateService.getStateData();
	}
	
	//Invoice and Invoice Items : 
	//Invoice

    @PostMapping("/invoice/createInvoice")
    public ResponseObject createInvoice(@RequestBody InvoiceCreationUpdationRequest invoiceRequest) {
        return invoiceService.createInvoiceWithCustomerAndDeliveryMode(invoiceRequest);
    }

    @PostMapping("/invoice/updateInvoice")
    public ResponseObject updateInvoice(@RequestBody InvoiceCreationUpdationRequest invoiceRequest) {
        return invoiceService.updateInvoiceWithCustomerAndDeliveryMode(invoiceRequest);
    }

    @PostMapping("/invoice/deleteInvoice")
    public ResponseObject deleteInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.deleteInvoice(invoiceDTO);
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseObject findInvoiceById(@PathVariable Long invoiceId) {
        return invoiceService.findInvoiceById(invoiceId);
    }
    
    //Find invoice by id by requesting param in the URL : 
    @GetMapping("/invoice")
    public ResponseObject findInvoiceById2(@RequestParam Long invoiceId ) {
		return invoiceService.findInvoiceById(invoiceId);
    }

    //Commenting out temporarily, because its not yet implemented.
    /*@GetMapping("/all")
    public ResponseObject findAllInvoices() {
        return invoiceService.findAllInvoices();
    }*/

    /*@GetMapping("/invoice/byMonthYear")
    public ResponseObject findInvoicesByMonthYear(@RequestParam int year, @RequestParam Month month) throws GeneralException {
        return (ResponseObject) invoiceService.getInvoicesForMonthYear(year, month);
    }*/
    
    @GetMapping("/invoice/byMonthYear")
    public ResponseObject findInvoicesByMonthYear(@RequestBody InvoiceMonthYearRequest monthYearRequest) throws GeneralException {
    	return invoiceService.getInvoicesForMonthYear(monthYearRequest);
    }
    
    //InvoiceItem
    @PostMapping("/invoiceitem/createInvoiceItem")
    public ResponseObject createInvoiceItems(@RequestParam int invoiceId, @RequestBody List<InvoiceItem> invoiceItems) throws GeneralException {
        return invoiceItemService.createInvoiceItems((long)invoiceId, invoiceItems);
    }

    @PostMapping("/invoiceitem/updateInvoiceItem")
    public ResponseObject updateInvoiceItem(@RequestParam int invoiceId, @RequestBody InvoiceItem invoiceItem) throws GeneralException {
        return invoiceItemService.updateInvoiceItem((long)invoiceId, invoiceItem);
    }

    @PostMapping("/invoiceitem/deleteInvoiceItem")
    public ResponseObject deleteInvoiceItem(@RequestParam int invoiceId, @RequestParam InvoiceItem invoiceItem) throws GeneralException {
        return invoiceItemService.deleteInvoiceItem((long)invoiceId, invoiceItem);
    }

    @GetMapping("/invoiceitem/byInvoice/{invoiceId}")
    public ResponseObject findInvoiceItemsByInvoice(@PathVariable Long invoiceId) throws GeneralException {
        return (ResponseObject) invoiceItemService.findInvoiceItemsByInvoice(invoiceId);
    }
    
    /*Auth Implementation Phase 1*/
    @PostMapping("/register.vt")
    public ResponseEntity<String> registerUser(@RequestBody Auth user) {
        // Check if user with the same username already exists
        if (userDetailsRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Save the user
        userService.saveUser(user);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
