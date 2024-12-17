# Alınan Notlar
```
* Eclipse TCP/IP monitor kullanimi notlar
    STS de java sekmesine gelinip Window->Show View ->Others->TCP/IP Monitor eklenir.
    Panel altta konsol yanında acılır.
    Acılan panelde sol üst kısmında Show Header aktif edilebilir.
    Altında properties'a girilerek TCP/IP Monitor konfigurasyonları yapılır.
    Properties buttonuna tıkladıktan sonra açılan ekranda Add yapılarak yeni monitor eklenir.
    Local monitoring port bizim izlemek istedigimiz kullanacagımız boşta portu yazarız ben 8085 yazdım.
    Diger alanlar doldurulur
        hostname:localhost
        port:8080
    yazılarak OK yapılır.
    Sonrasında ekledigimiz monitor seçilerek start yapılır.
    Artık postman de monitor ettigimiz 8085 portuna istek gönderdigimizde 
    req ve resp gorebilecegiz.
    Postman cagirilan istek -> http://localhost:8085/rest/owners (Mevcutta 8080 de calısmaktadir.)
    
* H2 Database kurulumu Hk
    pom.xml e 2 dependency eklenir.
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    h2 console a girmek icin -> http://localhost:8080/h2-console/ yapılır.
    Acılan ekranda JDBC Url kısmında jdbc:h2:mem:testdb
    olarak düzeltilerek asagıdaki degerler aynı kalir
    username:sa
    Password:
    http://localhost:8080/h2-console/ acıldıgında Generic H2 (Embedded) olarak gelir bununla login olabiliriz.
    -> Generic H2(Embedded)
    -> Generic H2(Embedded)
    Projede Generic H2 (Server) seçtigimizde hata aldıgından dolayı 
    -> jdbc:h2:tcp://localhost/~/test olarak gelmekteydi
    -> jdbc:h2:file:C:/Users/MahmutDuman/test;AUTO_SERVER=TRUE olarak yaptık
    C:/User/MahmutDuman/test.mw altında oluşturulacaktır. Bunu yedeklersek database i yedeklemiş oluruz.
    spring.datasource.url=jdbc:h2:tcp://localhost/~/test
    spring.datasource.username=sa
    spring.datasource.password=
    olarak verilebilir.
    Burada c:/Users/${username}/altına database dosyaları olusturulur.

    application.properties'de 
    spring.datasource.url
    spring.datasource.username
    spring.datasource.password
    ekleyerek configure edebiliriz.

* HikariCP
    ********************* HikariCP Nedir? *********************
     
    HikariCP, yüksek performanslı bir veritabanı bağlantı havuzu (database connection pool) kütüphanesidir. 
    Veritabanı bağlantıları yönetimi için kullanılan HikariCP, veritabanına yapılan bağlantıları hızlı ve verimli bir şekilde yöneten, 
    minimum gecikme ile yüksek verim sağlayan bir çözüm sunar.
    spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=password
    spring.datasource.hikari.maximum-pool-size=10
    spring.datasource.hikari.minimum-idle=5
    spring.datasource.hikari.idle-timeout=600000
    spring.datasource.hikari.max-lifetime=1800000
    
    maximum-pool-size: Havuzda bulundurulacak maksimum bağlantı sayısını belirler.
    minimum-idle: Minimum boşta bağlantı sayısını belirtir.
    idle-timeout: Bağlantının boşta kalabileceği maksimum süreyi belirtir.
    max-lifetime: Bir bağlantının havuzda kalabileceği maksimum süredir.
     
    Eğer HikariCP eklenmezse ve Spring Boot veya başka bir uygulama kullanıyorsanız, Spring Boot'un varsayılan olarak kullandığı bağlantı havuzu, 
    Tomcat JDBC Connection Pool'dur. 
    Spring Boot, HikariCP'yi varsayılan olarak kullanmaya başlasa da, bağlantı havuzu sağlanmadığı durumlarda Tomcat JDBC Connection Pool'u kullanır.
     
     
    Tomcat JDBC'yi yapılandırmak için aşağıdaki gibi bir ayar ekleyebilirsiniz:
     
    properties
    Kodu kopyala
    spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=password
     
    # Tomcat JDBC ayarları
    spring.datasource.tomcat.max-active=10
    spring.datasource.tomcat.max-idle=5
    spring.datasource.tomcat.min-idle=2
    spring.datasource.tomcat.initial-size=5
    spring.datasource.tomcat.test-while-idle=true
    spring.datasource.tomcat.validation-query=SELECT 1
    --------------------------------------------------
     
     
    -------------------
    Aşağıdaki yapılandırma nedir Ne degildir?
     
    database.connection.acquireIncrement = 5
    database.connection.initialPoolSize = 0
    database.connection.maxIdleTime = 300
    database.connection.maxPoolSize = 200
    database.connection.maxStatements = 0
    database.connection.maxStatementsPerConnection = 0
    database.connection.minPoolSize = 0
    database.connection.idleConnectionTestPeriod = 0
    database.connection.preferredTestQuery = SELECT 1 FROM DUAL
    database.connection.maxAdministrativeTaskTime = 300
    database.connection.unreturnedConnectionTimeout = 295
    database.connection.debugUnreturnedConnectionStackTraces = false
    database.connection.checkoutTimeout = 5000
    database.connection.numHelperThreads = 10
    hibernate.dialect = org.hibernate.dialect.Oracle10gDialect
    hibernate.show_sql = false
     
    Cevap:
     
    Verdiğiniz yapılandırma, bir JDBC bağlantı havuzu (connection pool) yapılandırması gibi görünüyor ve bu, 
    özellikle Oracle veritabanı için optimize edilmiş bir yapılandırmadır. Ancak, burada kullanılan bağlantı havuzunun 
    tam olarak ne olduğunu belirlemek için birkaç ipucu var.
     
    Yapılandırmadaki Bağlantı Havuzu Özellikleri:
     
    *database.connection.acquireIncrement = 5:
    Yeni bağlantı alındığında, bağlantı havuzunun her seferinde 5 yeni bağlantı oluşturmasını belirtiyor.
     
    *database.connection.initialPoolSize = 0:
    Başlangıçta bağlantı havuzunda hiçbir bağlantı bulunmuyor. Bu genellikle "lazy loading" yaklaşımını ifade eder.
     
    *database.connection.maxIdleTime = 300:
    Boşta kalan (idle) bağlantıların en fazla 300 saniye (5 dakika) beklemelerine izin veriliyor. Sonrasında bu bağlantılar kapatılır.
     
    *database.connection.maxPoolSize = 200:
    Bağlantı havuzunun en fazla 200 bağlantı tutmasına izin verilir.
     
    *database.connection.maxStatements = 0 ve database.connection.maxStatementsPerConnection = 0:
    Bu, havuzda SQL ifadelerinin (statements) sayısını sınırlamıyor.
     
    *database.connection.minPoolSize = 0:
    Bağlantı havuzunda başlangıçta hiçbir bağlantı bulundurulmaz. Minimum bağlantı sayısı sıfır olarak belirlenmiş.
     
    *database.connection.idleConnectionTestPeriod = 0:
    Bu, bağlantı havuzunun boşta kalan bağlantıları düzenli olarak test etmeyeceğini belirtiyor. 0 değeri, test yapılmayacağı anlamına gelir.
     
    *database.connection.preferredTestQuery = SELECT 1 FROM DUAL:
    Boşta kalan bağlantıların geçerliliğini test etmek için kullanılacak SQL sorgusudur. Oracle veritabanı için kullanılan tipik bir sorgudur.
     
    *database.connection.maxAdministrativeTaskTime = 300:
    Yönetimsel görevlerin (bağlantı havuzuyla ilgili) maksimum süresi 300 saniye olarak belirlenmiş.
     
    *database.connection.unreturnedConnectionTimeout = 295:
    Kullanıcılar tarafından geri verilmeyen bağlantıların zaman aşımına uğrayacağı süreyi belirtiyor. 295 saniye, bağlantı havuzunun bu bağlantıyı tutacağı süredir.
     
    *database.connection.checkoutTimeout = 5000:
    Bağlantı havuzundan bir bağlantı almak için geçerli süreyi 5000 ms (5 saniye) olarak sınırlıyor.
     
    *database.connection.numHelperThreads = 10:
    Bağlantı havuzunun yardımcı iş parçacığı sayısını belirliyor.
     
    *hibernate.dialect = org.hibernate.dialect.Oracle10gDialect:
    Hibernate'in Oracle 10g veritabanı ile uyumlu şekilde çalışacak şekilde yapılandırıldığını belirtiyor.
     
    *hibernate.show_sql = false:
    SQL sorgularının konsolda görüntülenmesini engelliyor.
    --------------------------------------------------
     
     
    --------------------------------------------------
     
    HikariCP kullanmak için ne yapılmalı?
     
    --------------------------------------------------
    HikariCP kullanmak istiyorsanız, Spring Boot'un otomatik yapılandırmasını sağlamak için pom.xml 
    dosyanıza aşağıdaki bağımlılığı eklemeniz gerekecek. Spring Boot zaten HikariCP'yi varsayılan bağlantı havuzu olarak kullanır, 
    bu yüzden sadece spring-boot-starter-data-jpa veya spring-boot-starter-jdbc bağımlılıklarını ekleyerek HikariCP'yi aktif hale getirebilirsiniz.
     
    HikariCP Bağımlılığı Ekleme:
     
    <dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    </dependency>
     
    Spring Boot Starter Eklemek (JDBC veya JPA ile):
    JDBC ile Kullanım: Eğer sadece JDBC kullanıyorsanız (yani Hibernate veya JPA kullanmıyorsanız), şu bağımlılığı ekleyebilirsiniz:
     
     
    <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    JPA ile Kullanım (Hibernate ile): Eğer JPA kullanıyorsanız (veya Hibernate), aşağıdaki bağımlılığı eklemeniz yeterli olacaktır:
     
     
    <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
     
    HikariCP'yi Yapılandırmak:
    HikariCP'yi etkinleştirdikten sonra, application.properties veya application.yml dosyanızda HikariCP'ye özgü yapılandırmaları yapabilirsiniz.
     
    application.properties Örneği:
     
    spring.datasource.url=jdbc:mysql://localhost:3306/mydb
    spring.datasource.username=root
    spring.datasource.password=root
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     
    # HikariCP ayarları
    spring.datasource.hikari.maximum-pool-size=10
    spring.datasource.hikari.minimum-idle=5
    spring.datasource.hikari.idle-timeout=30000
    spring.datasource.hikari.max-lifetime=60000
    spring.datasource.hikari.connection-timeout=3000

* Connection Pool
    Spring boot connection Pool kabiliyeti saglamaktadır.
    Default olarak HikkariCp tercih edilir.
    spring framework jdbc ve spring framework jpa ile zaten dahil olmaktadır.
    Eger classpathte hikkariCp çıkartılmıssa tomcat-jdbc ye bakılır bu durumda tomcatjdbc kullanılacaktır.
    Son olarak da Commons DBCP2 tercih edilir.

* Connection Pool Ayarlarmaları
    Application altından özelleştirilebilir.
    spring.datasource.tomcat.*
    spring.datasource.hikkari.*
    spring.datasource.dbcp2.*

Burada spring zaten hikkariyi kullanacaktır özelleştirmek yada digerini kullanmak istersek 
    spring.datasource.type=org.apache.tomcat.jdbc.pool.DataSource olarak konfigure edilmektedir.

* Data Initialization(schema.sql ve data.sql icindeki sql leri calistirma)
    Spring Boot classpath'de 
    -> schema.sql ve data.sql 
    script dosyalari mevcut ise bu 
    dosyalarin icindeki sql ifadelerini bootstrap sirasinda calistirmaktadir
     
    application.properties icinde
    *spring.datasource.platform=all
    *spring.datasource.platform=dev
    *spring.datasource.platform=test
     
    gibi degerler verilebilir. Sırayla
     
    1-schema.${platform}.sql
    2-schema.sql
    3-data.${platform}.sql
    4-schema.${platform}.sql
     
    calistirilir.



```