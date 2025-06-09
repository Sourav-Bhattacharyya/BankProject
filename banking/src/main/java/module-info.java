module banking {
    // Requires (dependencies on other modules)
    requires jakarta.validation;
    requires jakarta.persistence; // Added if not already present, often used with JPA/Hibernate
    requires jakarta.annotation; // IMPORTANT: Often needed for Spring annotations like @PostConstruct, @PreDestroy, @Resource

    requires spring.web;
    requires spring.beans;
    requires spring.data.jpa;
    requires org.slf4j;
    requires org.hibernate.orm.core;
    requires spring.context;
    requires jjwt.api;
    requires org.apache.tomcat.embed.core; // Typically needed for embedded Tomcat in Spring Boot Web
    requires spring.security.core;
    requires spring.security.web;
    requires spring.security.crypto;
    requires spring.security.config;
    requires static lombok; // 'static' is correct for Lombok as it's a compile-time dependency
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires io.swagger.v3.oas.models;
    requires io.swagger.v3.oas.annotations;

    // Opens (for deep reflection on private members by specified modules)
    // The previous error pointed to "unnamed module".
    // If specifying spring.beans, spring.context is not enough, consider 'to all-unnamed' as a diagnostic.
    // Try with spring.beans, spring.context first. If it fails, then try all-unnamed.
    opens com.sourav.banking.controller to spring.beans, spring.context;
    opens com.sourav.banking.service to spring.beans, spring.context;
    opens com.sourav.banking to spring.beans, spring.context; // For BankingApplication if it has @Autowired fields
    opens com.sourav.banking.DTO to spring.beans, spring.context;
    opens com.sourav.banking.security to spring.beans, spring.context;
    opens com.sourav.banking.repositoires to spring.beans, spring.context;

    // Specific opening for Hibernate (for entity scanning and bytecode enhancement)
    opens com.sourav.banking.entity to org.hibernate.orm.core, spring.core, spring.beans; // Added spring.core, spring.beans as they might also reflect on entities

    // Exports (for public access by other modules)
    exports com.sourav.banking; // For the main application class and any public utilities
    exports com.sourav.banking.controller;
    exports com.sourav.banking.service;
    exports com.sourav.banking.DTO;
    exports com.sourav.banking.security;
    exports com.sourav.banking.repositoires;
    exports com.sourav.banking.entity; // Entity classes usually need to be exported for JPA repositories
}