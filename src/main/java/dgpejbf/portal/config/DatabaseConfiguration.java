package dgpejbf.portal.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "dgpejbf.portal.repository",
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class DatabaseConfiguration {

    // 🔹 Propiedades del datasource principal
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    // 🔹 Datasource principal con autoCommit desactivado
    @Bean("primaryDataSource")
    @Primary
    public DataSource primaryDataSource(DataSourceProperties primaryDataSourceProperties) {
        HikariDataSource ds = primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        ds.setAutoCommit(false); // 🔹 clave para evitar errores de commit
        return ds;
    }

    // 🔹 EntityManagerFactory para la DB principal
    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("primaryDataSource") DataSource primaryDataSource
    ) {
        // CORRECCIÓN CLAVE: Usar un array de paquetes en una sola llamada a .packages()
        // para asegurar que escanee tanto el paquete raíz (donde está User) como el subpaquete.

        String[] entityPackages = {
            "dgpejbf.portal.domain", // Paquete raíz (donde está User.java)
            "dgpejbf.portal.domain.primaria", // Paquete para entidades movidas (si aplica)
        };

        LocalContainerEntityManagerFactoryBean em = builder
            .dataSource(primaryDataSource)
            // Usamos el array para escanear múltiples rutas
            .packages(entityPackages)
            .persistenceUnit("primary")
            .build();

        em.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto", "none"));

        return em;
    }

    // 🔹 TransactionManager para la DB principal
    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager(emf);
        txManager.setJpaDialect(new org.springframework.orm.jpa.vendor.HibernateJpaDialect());
        return txManager;
    }
}
