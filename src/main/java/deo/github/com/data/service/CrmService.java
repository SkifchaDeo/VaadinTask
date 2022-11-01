package deo.github.com.data.service;

import deo.github.com.data.entity.Customer;
import deo.github.com.data.entity.Product;
import deo.github.com.data.entity.Status;
import deo.github.com.data.repository.ContactRepository;
import deo.github.com.data.repository.ProductRepository;
import deo.github.com.data.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final ContactRepository contactRepository;
    private final ProductRepository productRepository;
    private final StatusRepository statusRepository;

    public CrmService(ContactRepository contactRepository,
                      ProductRepository productRepository,
                      StatusRepository statusRepository) {
        this.contactRepository = contactRepository;
        this.productRepository = productRepository;
        this.statusRepository = statusRepository;
    }

    public List<Customer> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public void deleteContact(Customer customer) {
        contactRepository.delete(customer);
    }

    public void saveContact(Customer customer) {
        if (customer == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(customer);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Status> findAllStatuses() {
        return statusRepository.findAll();
    }
}
