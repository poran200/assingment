### JavaFx Application (Assignment)

Run this project 
* mysql 
* mariadb or mysql dialect  
`hibernet.cfg.xml`
``````````
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <!-- Database connection settings -->
        <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localhost:3306/stuentdb</property>
        <property name="connection.username">root</property>
        <property name="connection.password"/>
        <property name="dialect">org.hibernate.dialect.MariaDB53Dialect</property>
        <property name="show_sql">true</property>
        <property name="hibernate.format_sql">true</property>
        <property name="hibernate.hbm2ddl.auto">create-drop</property>
        <property name="connection.pool_size">20</property>
        <mapping class="assingment.javafx.model.Student"/>
        <mapping class="assingment.javafx.model.Class"/>
    </session-factory>
</hibernate-configuration>
```````````
change the database name what ever you are created 
* class diagram
![diagram](https://github.com/poran200/assingment/blob/master/classdaigram1.png?raw=true) 

![diagram](https://github.com/poran200/assingment/blob/master/classdiagram2.png?raw=true) 

![erd](https://github.com/poran200/assingment/blob/master/erdforassingment.png?raw=true)


