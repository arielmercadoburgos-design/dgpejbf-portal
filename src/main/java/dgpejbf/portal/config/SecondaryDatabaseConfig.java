package dgpejbf.portal.config;

import jakarta.persistence.EntityManagerFactory;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "dgpejbf.portal.repository.secundaria", // 🔹 repositorios de la DB secundaria
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDatabaseConfig {

    // 🔹 Datasource de la DB secundaria
    @Bean("secondaryDataSourceProperties")
    @ConfigurationProperties("spring.secundaria")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("secondaryDataSource")
    public DataSource secondaryDataSource(@Qualifier("secondaryDataSourceProperties") DataSourceProperties secondaryDataSourceProperties) {
        com.zaxxer.hikari.HikariDataSource ds = secondaryDataSourceProperties
            .initializeDataSourceBuilder()
            .type(com.zaxxer.hikari.HikariDataSource.class)
            .build();
        ds.setAutoCommit(false); // 🔹 necesario para evitar el error de commit
        return ds;
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("secondaryDataSource") DataSource secondaryDataSource
    ) { // 🔹 agregamos el qualifier
        LocalContainerEntityManagerFactoryBean em = builder
            .dataSource(secondaryDataSource)
            .packages("dgpejbf.portal.domain.secundaria") // 🔹 entidades de la secundaria
            .persistenceUnit("secondary")
            .build();
        // 🔹 Forzar el schema por defecto para Hibernate
        em.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto", "none", "hibernate.default_schema", "portal_dgpejbf"));

        return em;
    }
}
