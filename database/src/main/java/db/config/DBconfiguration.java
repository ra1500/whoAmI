package db.config;

import db.entity.InquiryEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "EntityManagerFactory",
        transactionManagerRef = "TransactionManager",
        basePackages = {"db"})
public class DBconfiguration {

    private static final String DATASOURCE_PROPS_PREFIX = "spring.datasource";

    private static final String HIBERNATE_PROPS_PREFIX = "spring.jpa";

    protected static final String ENTITIES_PATH = InquiryEntity.class.getPackage()
            .getName();

    private HibernateProperties hibernateProperties;

    public DBconfiguration(HibernateProperties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = DATASOURCE_PROPS_PREFIX)
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Primary
    @Bean(name = "EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean EntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                             @Qualifier("dataSource")
                                                                                     DataSource dataSource) {
        return builder.dataSource(dataSource)
                .properties(hibernateProperties.getProperties(HIBERNATE_PROPS_PREFIX))
                .packages(ENTITIES_PATH)
                .persistenceUnit("PersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "TransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("EntityManagerFactory")
                    EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

