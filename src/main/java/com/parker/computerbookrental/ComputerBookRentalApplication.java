package com.parker.computerbookrental;

import com.parker.computerbookrental.model.Address;
import com.parker.computerbookrental.model.Book;
import com.parker.computerbookrental.model.security.Role;
import com.parker.computerbookrental.model.User;
import com.parker.computerbookrental.repositories.BookRepository;
import com.parker.computerbookrental.repositories.RoleRepository;
import com.parker.computerbookrental.repositories.UserRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.parker.computerbookrental.model.security.Role.Roles.ROLE_ADMIN;
import static com.parker.computerbookrental.model.security.Role.Roles.ROLE_USER;

@SpringBootApplication
public class ComputerBookRentalApplication {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ComputerBookRentalApplication.class);

    }

    @Bean
    public CommandLineRunner loadInitialData() {
        return (args) -> {
            if (roleRepository.findAll().isEmpty()) {
                Role USER = new Role(ROLE_USER);
                roleRepository.save(USER);
                Role ADMIN = new Role(ROLE_ADMIN);
                roleRepository.save(ADMIN);
            }

            if (userRepository.findAll().isEmpty()) {
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .firstName("admin")
                        .lastName("admin")
                        .address(Address.builder()
                                .streetAddress("admin-street")
                                .city("admin-city")
                                .state("admin-state")
                                .zip("admin-zip")
                                .build())
                        .email("admin@email.com")
                        .role(roleRepository.findRoleById(2L))
                        .build();
                userRepository.save(admin);
            }

            if (bookRepository.findAll().isEmpty()) {
                try {
                    FileInputStream file = new FileInputStream(new File("/Users/chrisparker/Documents/" +
                            "CustomerWebsite/src/main/resources/static/bookData.xlsx"));
                    XSSFWorkbook workbook = new XSSFWorkbook(file);
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    ArrayList<String> rowData;
                    for (Row row : sheet) {
                        rowData = new ArrayList<>();
                        for (Cell cell : row) {
                            rowData.add(cell.getStringCellValue());
                        }
                        Book book = Book.builder()
                                .title(rowData.get(0))
                                .author(rowData.get(1))
                                .isbn(rowData.get(2))
                                .imageFile(rowData.get(3))
                                .dateAdded(LocalDate.now())
                                .build();
                        bookRepository.save(book);
                    }
                    file.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
