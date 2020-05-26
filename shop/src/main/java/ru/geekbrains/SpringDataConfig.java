package ru.geekbrains;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(value = "ru.geekbrains.repositories", entityManagerFactoryRef = "emf", transactionManagerRef = "tm")
@EnableTransactionManagement
public class SpringDataConfig {
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource manager = new DriverManagerDataSource();
        manager.setDriverClassName("com.mysql.cj.jdbc.Driver");
        manager.setUrl("jdbc:mysql://localhost:3306/shop_test?serverTimezone=UTC&createDatabaseIfNotExist=true");
        manager.setUsername("root");
        manager.setPassword("root");
        return manager;
    }

    @Bean("emf")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(getDataSource());
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        factoryBean.setJpaVendorAdapter(adapter);
        factoryBean.setPackagesToScan("ru.geekbrains.entities");
        factoryBean.setJpaProperties(getJpaProperties());
        return factoryBean;
    }

    private Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.max_fetch_depth", 3);
        properties.put("hibernate.jdbc.fetch_size", 50);
        properties.put("hibernate.jdbc.batch_size", 10);
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        return properties;
    }

    @Bean("tm")
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory);
        return manager;
    }
}
