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

* JDBC Notları
    * Spring Üzerinden JDBC ile Veri Erişimi
        - String veri erişiminde bu tekrarlayan kısımları
        ortadan kaldırmak için Template Method
        örüntüsü tabanlı bir kabiliyet sunar
        - JdbcTemplate isimli bu yapı utility veya helper
        sınıflarına benzetilebilir
        - JdbcTemplate sayesinde Template Method
        tarafından dikte edilen standart bir kullanım şekli kod geneline hakim olur

    * JdbcTemplate Kullanım Örnekleri
        String result = jdbcTemplate.queryForObject(
        "select last_name from t_owner where id = ?",
        String.class, 1212L);

        Map<String,Object> result = jdbcTemplate.queryForMap(
        "select last_name,first_name from t_owner where id = ?",
        1212L);
        
        List<String> result = jdbcTemplate.queryForList(
        "select last_name from t_owner", String.class);

        List<Map> result = jdbcTemplate.queryForList(
        "select * from t_owner");

        int insertCount = jdbcTemplate.update(
        "insert into t_owner (id,first_name,last_name) values (?,?,?)", 101L,
        "Ali", "Yücel");

        int updateCount = jdbcTemplate.update(
        "update t_owner set last_name = ? where id = ?", "Güçlü", 1L);

        int deleteCount = jdbcTemplate.update(
         "delete from t_owner where id = ?", 1L);

        List<Object[]> args = new ArrayList<Object[]>();

        args.add(new Object[]{"Kenan","Sevindik",1L});
        args.add(new Object[]{"Muammer","Yücel",2L});

        int[] batchUpdateCount = jdbcTemplate.batchUpdate(
        "update t_owner set first_name = ?, last_name = ? where id = ?",
        args);

        int[] batchUpdateCount = jdbcTemplate.batchUpdate(
        "delete from t_pet",
        "delete from t_owner");

        * Pozisyonel Parametreler

        String sql = "select count(*) from t_owner where first_name = ? and
        last_name = ? ";

        int count = jdbcTemplate.queryForObject(
        sql, Integer.class, new Object[]{"Kenan","Sevindik"});

        int count = jdbcTemplate.queryForObject(
        sql, Integer.class, "Kenan","Sevindik");

        * İsimlendirilmiş (Named) Parametreler

        String sql = "select count(*) from t_owner where
        first_name = :firstName and last_name = :lastName";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("firstName", "Kenan");
        paramMap.put("lastName", "Sevindik");

        int count = namedParameterJdbcTemplate.queryForObject(
        sql, paramMap, Integer.class);

        * NamedParameterJdbcTemplate Bean Konfigürasyonu
        
        - Spring Boot NamedParameterJdbcTemplate bean'ini
          otomatik olarak tanımlar.

        @Repository
        public class OwnerDaoJdbcImpl implements OwnerDao {
        @Autowired
        private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        ...
        }

        IN Clause'una Değişken Sayıda Parametre Geçilmesi

        String sql = "select * from t_owner where id in (:idList)";
        
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("idList", Arrays.asList(1,2,3));
        RowMapper<Owner> rowMapper = new RowMapper<Owner>() {
            public Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
                Owner owner = new Owner();
                owner.setFirstName(rs.getString("first_name"));
                owner.setLastName(rs.getString("last_name"));
                return owner;
            }
        };
        List<Owner> result = namedParameterJdbcTemplate.query(
        sql, paramMap,rowMapper);

    NOT-2
    	JDBC (Java Database Connectivity)
    		JDBC, Java'nın veritabanı ile iletişim kurmak için sunduğu bir API'dir.
    		Java uygulamalarında SQL sorguları çalıştırmak, veri almak, güncellemek gibi işlemleri yapmak için kullanılır.
    		JDBC, kodlama açısından düşük seviyede olduğundan, her işlem için tekrar tekrar kod yazmak gerekir (örneğin bağlantı açma, hata yönetimi).
    		Amacı: Java uygulamaları ile veritabanları arasında doğrudan iletişim kurmak için düşük seviyeli bir API sağlar.
    		Manuel İşlemler:
    		SQL sorgularını elle yazmanız gerekir.
    		Veritabanından dönen verileri manuel olarak map etmelisiniz (örneğin ResultSet nesnelerini Java nesnelerine dönüştürmek).
    	
    	JDBC Template
    		JdbcTemplate, Spring Framework tarafından sağlanan bir sınıftır. 
    		JDBC ile yapılması gereken tekrar eden görevleri basitleştirir ve kodu daha temiz hale getirir.
    		
    			<dependency>
    		<groupId>org.springframework.boot</groupId>
    		<artifactId>spring-boot-starter-jdbc</artifactId>
    		</dependency>
    		Bu bağımlılığı projenize eklediğinizde Spring Boot size JDBC ile çalışmak için gerekli altyapıyı sağlar.
    		JdbcTemplate sınıfı kullanılabilir hale gelir. Bu sınıf JDBC kodlarını kolaylaştırır ve SQL işlemlerini sadeleştirir.
    	
    	HikariCP Bağlantı Havuzu:
    		Bu bağımlılıkla(spring-boot-starter-jdbc) birlikte Spring Boot, varsayılan olarak HikariCP bağlantı havuzunu kullanır. 
    		HikariCP hızlı, hafif ve yüksek performanslı bir connection pool'dur.
    	
    	Yapılandırma
    		Bağlantıyı kullanabilmeniz için application.properties dosyanıza veritabanı bilgilerini eklemeniz gerekiyor:
    		
    		spring.datasource.url=jdbc:mysql://localhost:3306/db_name
    		spring.datasource.username=root
    		spring.datasource.password=your_password
    		spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    	
    	Ek Notlar
    		Eğer projenizde hem JDBC hem de JPA kullanmak isterseniz spring-boot-starter-jpa bağımlılığını da eklemeniz gerekir.
    		
    		<dependency>
    		<groupId>org.springframework.boot</groupId>
    		<artifactId>spring-boot-starter-jpa</artifactId>
    		</dependency>
    		
    	JPA'yı Kullanırken JDBC Kullanmalı mıyım?
    		JPA İle Çalışırken Genelde JDBC Kullanılmaz: JPA, JDBC'nin üzerine inşa edilmiş bir soyutlama sağladığı için çoğu durumda 
    		JPA ile çalışırken JDBC'ye doğrudan ihtiyaç duymazsınız. 
    		Ancak, belirli durumlarda JPA'nın yetmediği düşük seviyeli işlemleri yapmak için JDBC kullanabilirsiniz.
    		
    		
    	JPA Nedir?
    		Açılım: Java Persistence API.
    		Amacı: Nesne-ilişkisel eşleme (ORM-Object to Relational Mapping) yapısını sağlar, yani veritabanı ile Java nesneleri arasında otomatik dönüşüm gerçekleştirir.
    		Abstraction: SQL sorgularını ve veritabanı işlemlerini daha soyut bir seviyede yönetir.
    		Azaltılmış Kod: Boilerplate kod miktarını azaltır ve veri tabanından gelen verileri Java nesnelerine otomatik olarak map eder.
    		Kullanım Kolaylığı: Geliştirici SQL yerine HQL veya JPQL kullanabilir. Ayrıca birçok işlemi Spring Data JPA gibi araçlar otomatik hale getirir.
    	
    	Örnek Kod:
    		@Entity
    		public class User {
    			@Id
    			@GeneratedValue(strategy = GenerationType.IDENTITY)
    			private Long id;
    			private String name;
    			// Getter ve Setter metotları
    		}
    		
    		@Repository
    		public interface UserRepository extends JpaRepository<User, Long> {
    			List<User> findByName(String name); // SQL yazmanıza gerek yok!
    		}

        Popüler ORM Araçları
            Java için:
            Hibernate
            EclipseLink
            JPA (Java Persistence API)
    	
    	**************************************************************************

* Dublike impl hatası neden olusur?
    OwnerRepository interface inden 
        - OwnerRepositoryInMemoryImpl
        - OwnerRepositoryJdbcImpl
    yaptıgımızda spring ayaga kalkarken hata almaktadır.
    
    public interface OwnerRepository {}

    @Repository
    public class OwnerRepositoryJdbcImpl implements OwnerRepository {}

    @Repository
    public class OwnerRepositoryInMemoryImpl implements OwnerRepository {}

    ***************************
    APPLICATION FAILED TO START
    ***************************
    
    Description:
    
    Field ownerRepository in com.javaegitimleri.petclinic.service.PetClinicServiceImpl required a single bean, but 2 were found:
    	- ownerRepositoryJdbcImpl: defined in file [C:\Dev\petclinic-project\petclinic\target\classes\com\javaegitimleri\petclinic\dao\jdbc\OwnerRepositoryJdbcImpl.class]
    	- ownerRepositoryInMemoryImpl: defined in file [C:\Dev\petclinic-project\petclinic\target\classes\com\javaegitimleri\petclinic\dao\mem\OwnerRepositoryInMemoryImpl.class]
    
    
    Action:
    
    Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed

    ----------------------------------------------------------------------------------------------------------------
    Burada birkaç farklı çözüm var 
    1-Springboot için ya impl yaptıklarımızdan birisine @Repository("ownerRepository") olarak yazdıgımızda hata almayacaktır.

    
        public class OwnerRepositoryJdbcImpl implements OwnerRepository {
            // uygulama detayları
        }
    
        @Repository("ownerRepositoryInMemoryImpl")
        public class OwnerRepositoryInMemoryImpl implements OwnerRepository {
            // uygulama detayları
        }
    

        @Service
        public class OrderService {
            @Autowired
            private final OrderService paymentService;
        
        }

    2-autowire ettigimiz Petclinic clasında @Qualifier("") olarak seçilim yaparak configure etmemiz gereklidir.

        @Repository("ownerRepositoryJdbcImpl")
        public class OwnerRepositoryJdbcImpl implements OwnerRepository {
            // uygulama detayları
        }
    
        @Repository("ownerRepositoryInMemoryImpl")
        public class OwnerRepositoryInMemoryImpl implements OwnerRepository {
            // uygulama detayları
        }
        
        //Çagıracagımız servis katmanında Qualifier kullanarak taglerinden seçim yaptırabiliriz.
        
        @Service
        public class OrderService {

            private final OrderService paymentService;
        
            @Autowired
            public OrderService(@Qualifier("creditCardPaymentService") PaymentService paymentService) {
                this.paymentService = paymentService;
            }
        }


    3-Birini @Primary Olarak İşaretleme

        Aynı OwnerRepository arayüzünü implement eden iki farklı sınıfın (OwnerRepositoryJdbcImpl ve OwnerRepositoryInMemoryImpl) aynı anda bulunması 
        nedeniyle Spring Boot, hangi sınıfı kullanacağı konusunda karışıklık yaşıyor. Bu hatayı çözmek için birkaç farklı yöntem izleyebilirsiniz:
        
        Birini @Primary olarak işaretlemek, Spring'e bu bean'i tercih edilen bean olarak kullanmasını söyler.
    
        @Repository
        public class OwnerRepositoryJdbcImpl implements OwnerRepository {
            // uygulama detayları
        }
    
        @Repository
        @Primary
        public class OwnerRepositoryInMemoryImpl implements OwnerRepository {
            // uygulama detayları
        }

        @Service
        public class OrderService {
            @Autowired
            private final OrderService paymentService;
        
        }

    4. Configuration Sınıfında Bean Tanımlama 
      		Eğer yukarıdaki iki yöntem işinizi görmüyorsa, bir @Configuration sınıfında hangi bean'in kullanılacağını belirtebilirsiniz:
    		AppConfig.java
    		@Configuration
    		public class AppConfig {
    		    // OwnerRepositoryJdbcImpl bean'ini oluştur ve @Primary olarak işaretle
    		    @Bean
    		    @Primary
    		    public OwnerRepository ownerRepositoryJdbcImpl() {
    		        return new OwnerRepositoryJdbcImpl();
    		    }
    		    // OwnerRepositoryInMemoryImpl bean'ini oluştur
    		    @Bean
    		    public OwnerRepository ownerRepositoryInMemoryImpl() {
    		        return new OwnerRepositoryInMemoryImpl();
    		    }
    		}
    		Service sinifimiz:
    		@Service
    		public class PetClinicServiceImpl {
    		    private final OwnerRepository ownerRepository;
    		    // Spring varsayılan olarak @Primary bean'ini kullanır
    		    public PetClinicServiceImpl(OwnerRepository ownerRepository) {
    		        this.ownerRepository = ownerRepository;
    		    }
    		    // Eğer belirli bir bean istiyorsanız, @Qualifier kullanabilirsiniz
    		    // public PetClinicServiceImpl(@Qualifier("ownerRepositoryInMemoryImpl") OwnerRepository ownerRepository) {
    		    //     this.ownerRepository = ownerRepository;
    		    // }
    		}
    		Bu durumda, Spring @Primary anotasyonu sayesinde varsayılan olarak OwnerRepositoryJdbcImpl'i kullanır.
    		----@Qualifier("ownerRepositoryInMemoryImpl") bunun ownerRepositoryInMemoryImpl oldugunu nasil anliyor?
    		Spring, bean'leri tanımlarken @Bean anotasyonu ile metod adını varsayılan olarak bean adı olarak kullanır. 
    		Bu yüzden, aşağıdaki örnekte @Bean olarak tanımlanan ownerRepositoryInMemoryImpl bean'inin adı ownerRepositoryInMemoryImpl olacaktır.
    		Nasıl Çalışır?
    		Bean İsmi Varsayılan Olarak Metod Adıdır:
    		@Bean anotasyonu kullanıldığında, Spring, varsayılan olarak o metodun adını bean adı olarak alır.
    		Yukarıdaki örnekte, bean adı ownerRepositoryInMemoryImpl olacaktır.
    		@Qualifier ile Eşleşme:
    		@Qualifier("ownerRepositoryInMemoryImpl") yazdığınızda, Spring bu adı kullanarak ilgili bean'i bulur.
    		Bean adının @Qualifier ile verilen adla birebir eşleşmesi gerekir.
    		Ayrıca 
    		@Bean(name = "customOwnerRepository") ismini elle verebilirsin.		
   

    Buradaki islemi eski projelerdeki gibi factory olusturup hangi impl yapacagımıza göre return etmiştik.

* PersistenceContext Notu
    	
    @PersistenceContext Nedir?
    JPA (Java Persistence API) bağlamında EntityManager nesnelerini yönetmek için kullanılır.
    İşlevi: Bir EntityManager'i bir bean'e enjekte etmek için kullanılır. 
    Spring, arka planda bir EntityManager nesnesi oluşturur ve onu yönetir.
    	
* Spring Security Aktivasyonu

    pom.xml'e dependency eklenir.
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency> 
    
    Bu eklendikten sonra gelen tüm isteklerde auth olunması istenir.
    
    default olarak uygulama ayaga kalkarken console generate ettigi passwordü yazdırır
    Using generated security password: a5303675-48b3-4b3d-a223-cf39f2798dc0
    Default olarak birşey girilmesse
    username:user
    password:a5303675-48b3-4b3d-a223-cf39f2798dc0
    
    olarak rest isteginden önce login olunması beklenir.
    Ilk istekte direk login sayfasına yönlendirir.

    * application.properties' a 
    
    spring.security.user.name=admin
    spring.security.user.password=mahmut123

    bu şekilde girilerek şifrenin sabit olarak kaydedilmesi saglanabilir.

    Ekran goruntusu ektedir.

* Spring Security Detaylı Not
    
    Spring Security Nedir?
    Spring Security, Java tabanlı uygulamalar için 
    kimlik doğrulama (authentication) ve yetkilendirme (authorization) işlemlerini kolaylaştıran bir güvenlik çerçevesidir. 
    Web uygulamalarını ve RESTful API'leri saldırılara karşı korumak için yaygın olarak kullanılır.
     
    Spring Security'nin Özellikleri
    *   Kimlik Doğrulama (Authentication):
            Kullanıcının kim olduğunu doğrulama.
            Örnek: Kullanıcı adı ve şifre kontrolü.
    *   Yetkilendirme (Authorization):
            Kullanıcının hangi işlemleri yapabileceğini kontrol etme.
            Örnek: Sadece "admin" rolüne sahip kullanıcıların belirli sayfalara erişebilmesi.
    *   Web Güvenliği:
            URL tabanlı erişim kontrolleri.
            CSRF (Cross-Site Request Forgery) koruması.
    *   REST API Güvenliği:
            Token tabanlı kimlik doğrulama (JWT, OAuth2).
    *   Şifreleme:
            Şifreleri güvenli bir şekilde saklamak için hashing algoritmaları (örneğin, BCrypt) sağlar.
    Bir kullanıcı adı ve şifre ile korunan bir endpoint oluşturulacak.
     
        <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
        Bunu ekleyince otomatik login sayfasına yonlendirdi.Herhangi bir config yapmadan
        Bu eklendikten sonra gelen tüm isteklerde auth olunması istenir.
            default olarak uygulama ayaga kalkarken console generate ettigi passwordü yazdırır
            ------Loglarda böyle bir pass yazdı
            Using generated security password: a5303675-48b3-4b3d-a223-cf39f2798dc0
            -----
            Default olarak birşey girilmesse
            username:user
            password:a5303675-48b3-4b3d-a223-cf39f2798dc0
            olarak rest isteginden önce login olunması beklenir.
            Ilk istekte direk login sayfasına yönlendirir.
            Ekran goruntusu ektedir.
     
    spring-boot-starter-security bağımlılığı Spring Boot uygulamanıza eklendiğinde, 
    Spring Security otomatik olarak temel kimlik doğrulama sağlar. 
    Bu, Spring Security'nin varsayılan davranışıdır ve hemen hemen her Spring Boot 
    projesinde güvenlik sağlamak için basit bir başlangıç oluşturur.
     
    Neler Olur?
    Varsayılan Login Sayfası:
     
    Spring Security, eklediğinizde otomatik olarak bir giriş sayfası oluşturur.
    Bu sayfa, kullanıcıdan kullanıcı adı ve şifre ister.
    Varsayılan Kullanıcı:
     
    Varsayılan olarak, Spring Security, basit kimlik doğrulama için user adında bir kullanıcı oluşturur. 
    Şifre ise konsola yazdırılır.
     
    Varsayılan Login Sayfası
    Spring Security'nin varsayılan davranışı, herhangi bir özelleştirme yapılmadığı sürece, kullanıcıyı otomatik olarak login sayfasına yönlendirir. Bu, httpBasic() veya formLogin() gibi metodlar kullanılarak değiştirilmeden kalır.
     
    Örnek Akış:
    Uygulamanızı başlattığınızda, herhangi bir URL'yi ziyaret ettiğinizde (örneğin /admin gibi) güvenlik kontrolü devreye girer.
    Otomatik olarak bir giriş formu görüntülenir.
    Eğer doğru kullanıcı adı ve şifreyi girerseniz, sayfaya erişim sağlarsınız.
     
    Özelleştirme:
    Spring Security'nin sunduğu varsayılan login sayfası ve davranışları özelleştirmek mümkündür. Örneğin, kendi giriş sayfanızı tasarlayabilir veya 
    HTTP Basic yerine form tabanlı kimlik doğrulama kullanabilirsiniz.
     
        @Configuration
        public class SecurityConfig {
     
            @Bean
            public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                    .authorizeRequests()
                        .antMatchers("/", "/home").permitAll() // İzin verilen sayfalar
                        .anyRequest().authenticated() // Diğer sayfalara kimlik doğrulama gerekir
                        .and()
                    .formLogin()
                        .loginPage("/login") // Kendi giriş sayfanızı belirleyebilirsiniz
                        .permitAll()
                        .and()
                    .logout()
                        .permitAll();
                return http.build();
            }
        }    

SecurityConfig Ayarlamaları detay Hk.
    Bunlar ne iş yapar?
          .httpBasic()
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .csrf().disable()
                    .formLogin().disable(); bunları detaylı açıkla
     
     
    Bu Spring Security yapılandırma kodu, güvenlik ayarlarını belirlerken çeşitli özellikleri etkinleştirir 
    veya devre dışı bırakır. Kodun her bir satırını adım adım açıklayalım.
     
    1. .httpBasic()
     
        .httpBasic()
        Bu, HTTP Basic Authentication'ı etkinleştirir.
        HTTP Basic Authentication, kullanıcı adı ve şifreyi HTTP başlıkları içinde iletmek için kullanılan bir yöntemdir. Kullanıcı adı ve şifreyi her istekte base64 formatında şifreleyerek gönderir.
        Bu yöntem basittir ancak güvenli değildir, çünkü şifreler şifrelenmeden (ancak base64 kodlamasıyla) gönderildiğinden HTTPS üzerinden kullanılmalıdır.
        Örnek kullanım:
        Kullanıcı girişi, HTTP başlıkları aracılığıyla yapılır. Bir API'ye istek atarken, Authorization: Basic base64encoded(username:password) şeklinde bir başlık gönderilir.
     
    2. .and()
     
        .and()
        Bu metot, birden fazla güvenlik yapılandırmasını birbirine bağlar.
        İlk başta .httpBasic() ile HTTP Basic Authentication'ı etkinleştirdik. .and() ile bir sonraki güvenlik yapılandırma bloğuna geçiyoruz.
    3. .authorizeRequests()
     
        .authorizeRequests()
        Bu, gelen HTTP isteklerini yetkilendirme için yapılandırmamıza olanak tanır.
        authorizeRequests(), hangi isteklerin hangi yetkilere sahip kullanıcılar tarafından yapılabileceğini belirler.
    4. .anyRequest().authenticated()
     
        .anyRequest()
            .authenticated()
        .anyRequest() tüm gelen HTTP isteklerini temsil eder.
        .authenticated() ise tüm bu isteklere kimlik doğrulaması yapılmış bir kullanıcının erişmesine izin verir.
        Yani, bu yapılandırma, herhangi bir URL veya sayfaya yapılacak tüm isteklerin, önceden giriş yapmış bir kullanıcı tarafından yapılmasını gerektirir.
        Örneğin:
        /admin veya /profile gibi sayfalara giriş yapabilmek için, kullanıcı adı ve şifre ile giriş yapılması gerekir.
     
    5. .csrf().disable()
     
        .csrf().disable()
        CSRF (Cross-Site Request Forgery), bir kullanıcının yetkisiz bir şekilde başka bir kullanıcı adına işlem yapmasını sağlamak için kullanılan bir saldırıdır.
        .csrf().disable() komutu, Spring Security'deki CSRF korumasını devre dışı bırakır.
        Bu genellikle API tabanlı uygulamalarda veya istemci ve sunucu arasında stateless (durumsuz) iletişimde kullanılır. Bu durumda, kullanıcı doğrulama bilgileri genellikle her istekte gönderildiğinden CSRF korumasına ihtiyaç duyulmaz.
        Uyarı: CSRF'yi devre dışı bırakmak güvenlik açısından risk oluşturabilir, bu yüzden yalnızca gerektiğinde kullanılmalıdır.
    6. .formLogin().disable()
     
        .formLogin().disable()
        .formLogin() Spring Security'nin form tabanlı giriş yapmayı sağlar. Kullanıcı adı ve şifreyi bir HTML formu üzerinden göndererek giriş yapılır.
        .formLogin().disable() ile bu form tabanlı giriş işlevselliği devre dışı bırakılır.
        Bu genellikle API veya başka bir oturum yönetimi yöntemi (örneğin JWT veya OAuth) kullanıyorsanız tercih edilir. Eğer bir web formu kullanarak giriş yapmak istemiyorsanız, bu konfigürasyon ile bunu engellemiş olursunuz.
     
    Özet
    Yukarıdaki yapılandırma şunları yapar:
     
    HTTP Basic Authentication'ı etkinleştirir: Kullanıcı adı ve şifreyi HTTP başlıkları ile göndererek giriş yapılmasını sağlar.
    Tüm isteklerin kimlik doğrulaması yapılmış kullanıcılar tarafından erişilmesini sağlar: Kimlik doğrulaması yapılmamış kullanıcılar, herhangi bir kaynağa erişemez.
    CSRF korumasını devre dışı bırakır: API'ler için genellikle tercih edilir, ancak web uygulamaları için CSRF koruması devre dışı bırakılmamalıdır.
    Form tabanlı giriş işlemi devre dışı bırakılır: Form tabanlı giriş yerine, HTTP Basic Authentication kullanılır.
    Bu yapılandırma genellikle API tabanlı projelerde, şifrelerin HTTP başlıklarında güvenli bir şekilde taşınması gereken durumlar için uygun olacaktır.
    bağlam menüsü var.

* Login logout kısmı Hk

    http://localhost:8080/rest/owner/1 dedigimizde bizi login sayfasına yönlendirecek.
    http://localhost:8080/login sayfasında auth bilgilerini girerek sign in yaptıgımızda http://localhost:8080/rest/owner/1 sonucunu getirecek
    logout yapmak icin http://localhost:8080/logout'a girip logout yaptıgımız zaman cıkıs yapmıs olacaktır.

* H2SecurityConfiguration sayfasındaki yapılan kısım Hk.
    Bu kod Spring Security yapılandırmasıdır ve özel olarak H2 veritabanının konsoluna erişimi düzenlemek için hazırlanmıştır. 
    Detaylı açıklama aşağıdadır:
   
    1. @Configuration
        Bu sınıfın bir Spring yapılandırma sınıfı olduğunu belirtir.
        Spring IoC konteyneri, bu sınıfı bir yapılandırma bileşeni olarak tanır ve bu sınıf içerisindeki tanımlamaları uygular.
    2. @Order(value=0)
        Bu anotasyon, birden fazla WebSecurityConfigurerAdapter sınıfı bulunduğunda, yapılandırmaların hangi sırayla işleneceğini belirler.
        value=0, bu yapılandırmanın diğer WebSecurityConfigurerAdapter sınıflarından önce işleneceğini belirtir.
    3. extends WebSecurityConfigurerAdapter
        Bu sınıf, Spring Security'nin web güvenliği için kullanılan WebSecurityConfigurerAdapter sınıfını genişletir.
        Bu sayede, özel güvenlik yapılandırmaları yapabiliriz.
    4. @Override
        Bu, bir üst sınıf olan WebSecurityConfigurerAdapter'ın configure(HttpSecurity http) yöntemini geçersiz kıldığımızı belirtir.
        Bu yöntemde, uygulamamızın güvenlik kurallarını tanımlıyoruz.
    5. http.antMatcher("/h2-console/**")
        antMatcher metodu, belirtilen bir desenle eşleşen istekleri işlemek için kullanılır.
        "/h2-console/**", /h2-console/ ile başlayan ve devamında herhangi bir şey olabilecek tüm URL'leri kapsar.
        Örneğin: /h2-console/login.do veya /h2-console/some-path.
    6. authorizeRequests().anyRequest().permitAll()
        Bu yapılandırma, "/h2-console/**" ile eşleşen isteklerin tüm kullanıcılar tarafından erişilebilir olmasını sağlar.
        Yani, giriş yapmış olup olmadığınıza veya belirli bir role sahip olup olmadığınıza bakılmaksızın bu endpoint'lere erişebilirsiniz.
    7. http.csrf().disable();
        CSRF (Cross-Site Request Forgery) korumasını devre dışı bırakır.
        H2 konsol gibi özel araçlarda CSRF koruması gerekli olmadığından devre dışı bırakılır.
        Eğer aktif kalsaydı, H2 konsolu için ek CSRF token'ları tanımlamanız gerekirdi.
    8. http.headers().frameOptions().disable();
        Spring Security, varsayılan olarak Frame Options başlığını kullanır ve tarayıcının iframe içerisinde bir sayfayı yüklemesini engeller.
        H2 konsolu iframe kullanarak çalışır. Bu nedenle, frameOptions devre dışı bırakılır.
        Frame Options: XSS saldırılarını önlemek için sayfanın iframe'de yüklenmesini kısıtlar.
    Genel Olarak Bu Yapılandırma Ne Yapar?
        /h2-console/ ile başlayan tüm istekler için herhangi bir güvenlik kısıtlaması uygulanmaz.
        CSRF koruması ve Frame Options kontrolü devre dışı bırakılır.
        Bu ayar, yalnızca H2 konsoluna özel olarak yapılır ve diğer endpoint'leri etkilemez.
    Neden Gerekli?
        H2 veritabanı konsolu geliştirme sırasında kullanılır ve genellikle güvenlik kısıtlamalarını gereksiz kılmak için böyle bir yapılandırma yapılır.
        Ancak bu yapılandırma sadece geliştirme ortamında kullanılmalıdır; 
        üretim ortamında bu ayarları devre dışı bırakmak güvenlik açıklarına neden olabilir.

* SecurityConfiguration (Web Sayfalarının Yetkilendirilmesi)

    Spring Security, kullanıcı rolleri için ROLE_ ön ekini kullanır.
        Örneğin, bir kullanıcının "EDITOR" rolü varsa, bu genellikle veritabanında ROLE_EDITOR olarak saklanır. 
        Ancak, Spring Security, kullanıcıya bir rol atandığında, bu rolün başına ROLE_ ön ekini ekler.
        Bu acces olarak hasRole eklenmesi spesifik olandan genel olana gore sirayla yazilmalidir. 

    Burada userlara yetki rolleri verilmiştir.
    SELECT * FROM AUTHORITIES;

    USERNAME  		AUTHORITY  
    user1			ROLE_USER
    user2			ROLE_EDITOR
    user2			ROLE_USER
    user3			ROLE_ADMIN
    user3			ROLE_EDITOR
    user3			ROLE_USER
    
    SELECT * FROM USERS;

    USERNAME  	PASSWORD  			ENABLED  
    user1		{bcrypt}$2a....		TRUE
    user2		{bcrypt}$2a....		TRUE
    user3		{bcrypt}$2a....		TRUE

    SecurityConfiguration configure Methodu

    	@Override
    	protected void configure(HttpSecurity http) throws Exception {
    		
    		http.authorizeRequests().antMatchers("/**/favicon.ico", "/css/**", "js/**", "/images/**", "/webjars/**","/login.html").permitAll();
    		http.authorizeRequests().antMatchers("/rest/**").access("hasRole('EDITOR')");
    		http.authorizeRequests().antMatchers("/actuator/**").access("hasRole('ADMIN')");
    		http.authorizeRequests().anyRequest().authenticated();
    		http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").failureUrl("/login.html?loginFailed=true");
    		http.rememberMe().userDetailsService(userDetailsService);
    		http.httpBasic();
    
    	}
    
    Burada 

    "/**/favicon.ico", "/css/**", "js/**", "/images/**", "/webjars/**","/login.html" -> .permitAll() a herkes auth olmadan giriş yapabilir.
    "/rest/**" -> .access("hasRole('EDITOR')"); //EDITOR rolünde olan userlar girebilir yetkisi yoksa giremez.
    "/actuator/**" -> .access("hasRole('ADMIN')");  //ADMIN rolünde olan userlar girebilir yetkisi yoksa giremez.
    http.authorizeRequests().anyRequest().authenticated(); -> anyRequestte geriye kalan tüm istekler basicAut olunarak girilmelidir.
    
/*
     * AbstractSecurityConfiguration sınıfını neden kullandığınız, projenizin tasarımı ve güvenlik yapılandırması ihtiyaçlarınıza bağlıdır. 
     * Ancak bu tür bir yapılandırmada genellikle şu sebeplerle bir abstract class tercih edilir:
     * 
     1. Yeniden Kullanılabilirlik
        Eğer projede birden fazla güvenlik yapılandırması sınıfı olacaksa, ortak olan kodları bir abstract sınıfta toplamak iyi bir yöntemdir.
        Örneğin, projede farklı modüller için farklı güvenlik yapılandırmaları (API, admin paneli, vs.) gerekiyorsa, 
        bu modüllerin hepsi AbstractSecurityConfiguration'dan türetilerek kolayca özelleştirilebilir.
    2. Ortak İşlevselliğin Merkezi Yönetimi
        Abstract sınıf, tüm türetilen sınıflar için bir "temel yapı" sağlar. Örneğin, sık kullanılan metotlar (şifreleme yapılandırmaları, genel filtreler veya erişim kontrolleri) bu sınıfta tanımlanabilir.
        Ortak davranışları bir kez tanımlayıp türetilen tüm sınıflarda kullanılabilir hale getirirsiniz.
    3. Kodun Daha Modüler ve Anlaşılır Olması
        Eğer doğrudan WebSecurityConfigurerAdapter'dan türetirseniz, her güvenlik yapılandırması sınıfı tamamen bağımsız olur. Bu, kodun modülerliğini azaltabilir.
        Abstract sınıf kullanmak, güvenlik yapılandırmanızı bir temel üzerinden yapılandırmanızı ve özelleştirmenizi sağlar.
    4. Kolay Özelleştirme
        Abstract sınıf, bazı metotları zorunlu olarak override edilmesi gereken abstract metotlar olarak bırakabilir. Bu, türetilen sınıfların belirli yapılandırmaları mutlaka tanımlamasını sağlar.
     * */

    Özetle 

    basicAuth
    user1
    user1 ile sadece login olabildik

    basicAuth
    user2
    user2 basicAuth yaptıgımızda 
    ile editor yetkimiz vardı 
    http://localhost:8080/rest/owner/1 sayfamızı sorguladıgımızda geldi fakat 
    http://localhost:8080/actuator/health sayfası Admin yetkisi olup user2 icin 
    tanımlı olmadıgı için 403 yetki hatası verdi.

    basicAuth
    user3
    user3 yaptıgımızda hem editor hemde admin yetkisi oldugu icin 
    http://localhost:8080/rest/owner/1
    http://localhost:8080/actuator/health
    
    sayfalarına erisebildi.

Test Kısımları Notlar

    @RunWith(SpringRunner.class)//junit tarafından spring runner kullarak çalıstırılması gerektigi belirtilir.
    @SpringBootTest(properties = {"spring.profiles.active=dev"})
    public class PetClinicIntegrationTests {
    
    	@Autowired
    	private PetClinicService petClinicService;
    
    	@Test
    	public void testFindOwners() {
    		List<Owner> owners = petClinicService.findOwners();
    		MatcherAssert.assertThat(owners.size(), Matchers.equalTo(10));
    		System.out.println("Tum owner sayisi : "+ owners.size());
    		System.out.println("****************************** TEST BASARILI ******************************");
    
    		
    	}
    }

    Bu kod, bir Spring Boot uygulamasının entegrasyon testini gerçekleştirmek için yazılmıştır. Kodun her bir kısmını detaylı bir şekilde açıklayalım:
     
    Kodun Yapısı ve Anlamı
    1. @RunWith(SpringRunner.class)
        Bu anotasyon, Spring'in JUnit 4 ile entegre edilmesini sağlar. Spring'in test desteği etkinleştirilir ve Spring uygulama bağlamı (ApplicationContext) test sırasında yüklenir.
     
    2. @SpringBootTest(properties = {"spring.profiles.active=dev"})
        @SpringBootTest: Spring Boot'un entegrasyon testi için kullanılan anotasyondur. Tüm uygulama bağlamını (ApplicationContext) yükler.
        properties = {"spring.profiles.active=dev"}: Test sırasında dev adlı Spring profili etkinleştirilir.
        Bu, application-dev.properties gibi bir konfigürasyon dosyasının test sırasında kullanılması anlamına gelir.
        Örneğin, test ortamına özgü bir veritabanı veya özel bir yapılandırma olabilir.
    
    3. PetClinicIntegrationTests
        Bu sınıf, PetClinic adlı bir Spring Boot uygulaması için entegrasyon testlerini içeren bir test sınıfıdır.
     
    4. @Autowired
        Bu anotasyon, Spring tarafından yönetilen bir bileşeni (Bean) test sınıfına enjekte etmek için kullanılır.
        petClinicService: Test edilen servis katmanıdır. Bu servis, Owner (sahip) nesnelerini veritabanından almak gibi işlemleri içerir.
    5. @Test
        JUnit tarafından kullanılan bir anotasyondur. Bu, bir test metodunu belirtir.
     
    6. testFindOwners Metodu
        Bu, PetClinicService'in findOwners metodunu test etmek için yazılmıştır:
     
        petClinicService.findOwners(): Bu metot, veritabanındaki tüm Owner (sahip) nesnelerini döndürür.
        owners.size(): Dönen listenin boyutunu alır.
        MatcherAssert.assertThat: JUnit'te bir test iddiasıdır.
        Matchers.equalTo(10): Listenin boyutunun 10 olması gerektiğini belirtir.
        Eğer boyut 10 değilse, test başarısız olur.
    Kodun Çalışma Akışı
        Test başlatılır.
        Spring uygulama bağlamı (ApplicationContext) yüklenir.
        dev profiline ait yapılandırma dosyası (ör. application-dev.properties) etkinleştirilir.
        petClinicService, Spring tarafından test sınıfına otomatik olarak enjekte edilir.
        testFindOwners metodu çalışır:
        findOwners metodu çağrılır.
        Dönen listenin boyutunun 10 olduğu doğrulanır.
        Eğer boyut 10 ise test başarılı olur; aksi takdirde test başarısızdır.
        Kodun Amaçları
        Servis katmanının doğru çalıştığını doğrulamak: PetClinicService.findOwners() metodunun beklendiği gibi çalıştığından emin olunur.
        Spring yapılandırmasını test etmek: dev profiliyle yapılan yapılandırmanın test ortamında doğru bir şekilde yüklendiğinden emin olunur.
        Entegrasyon testi: Servis katmanının, veri erişim katmanıyla (ör. bir repository) doğru bir şekilde iletişim kurduğunu doğrular.
        Örnek Durum
        Eğer Owner (sahip) nesneleri aşağıdaki gibi bir yapıdaysa:
     
    java
        public class Owner {
            private Long id;
            private String firstName;
            private String lastName;
         
            // Getters and Setters
        }
        ve veritabanında 10 adet Owner nesnesi varsa, bu test başarılı olacaktır.
     
    Testin Genişletilmesi
        Daha fazla senaryo için testler eklenebilir:
        Veritabanında hiç kayıt olmadığında.
        Kayıtların farklı filtreleme işlemlerine göre test edilmesi.
        Mock nesneler kullanılarak, veritabanına bağlı kalmadan testler yapılabilir. Örneğin:
    
        @MockBean
        private PetClinicService petClinicService;
        Böylece, findOwners metodunun dönüş değerini simüle edebilirsiniz.
        Bu kod, Spring Boot entegrasyon testi için temel bir örnektir ve gerçek bir uygulamanın servis katmanını kapsamlı bir şekilde test etmek için kullanılır.
    
    junit ile ayrı bir test class olusturarak yaptıgımdan ne farki olacak?

    Bir JUnit test sınıfı oluşturmak ile doğrudan entegrasyon testi sınıfında çalışmak arasında bazı farklar vardır. Her bir yöntemin avantajları ve kullanım yerleri şunlardır:
     
    1. Ayrı Bir Test Sınıfı Kullanmanın Avantajları
        Ayrı bir test sınıfı oluşturmak, testlerin daha izole, organize ve yönetilebilir olmasını sağlar. Örneğin:
     
    Farklar ve Avantajlar:

    a. Testlerin İzolasyonu
        Ayrı sınıf oluşturmak: Her test sınıfı yalnızca bir işlevselliği veya modülü test eder.
        Örneğin, bir sınıf yalnızca OwnerService ile ilgili testler içerirken, diğer bir sınıf PetService ile ilgilenebilir.
        Tüm testleri bir arada yazmak: Farklı modülleri veya katmanları test etmek birbiriyle karışabilir. Bu da kodun anlaşılabilirliğini azaltabilir.
    b. Kod Organizasyonu
        Ayrı test sınıfı: Her bir servis veya bileşen için bir test sınıfı oluşturduğunuzda, kodunuz daha düzenli hale gelir.
        Örneğin:
        public class OwnerServiceTests {
            // Owner ile ilgili testler
        }
     
        public class PetServiceTests {
            // Pet ile ilgili testler
        }
        Tek sınıf kullanmak: Tüm testleri tek bir sınıfa koymak, test sınıfını büyütebilir ve yönetimi zorlaştırabilir.
    c. Test Performansı
        Ayrı sınıflar: Test sınıfları daha az bağımlı hale gelir ve sadece ilgili bileşenler yüklenir. Uygulama bağlamı her bir sınıf için gerektiği kadar yüklenir.
        Tek bir sınıf: Eğer bir sınıfta çok fazla bağımlılık varsa, uygulama bağlamı çok büyük hale gelebilir ve bu da test süresini artırabilir.
    d. Spring Profillerini Yönetmek
        Ayrı sınıflarda, her bir test için farklı @SpringBootTest profilleri veya yapılandırmaları tanımlayabilirsiniz:
     
    Örneğin:

        @SpringBootTest(properties = {"spring.profiles.active=test"})
        public class OwnerServiceTests { }
         
        @SpringBootTest(properties = {"spring.profiles.active=dev"})
        public class PetServiceTests { }
        Bu sayede her test sınıfı, kendi özel yapılandırmasını kullanabilir.
         
    e. Testlerin Kolay Takibi
        Ayrı sınıflarda her bir test yöntemi daha kolay bulunabilir ve test sonuçları daha rahat analiz edilebilir.
        Örneğin, bir hata olduğunda OwnerServiceTests sınıfında olduğu doğrudan anlaşılır.
    2. Tek Sınıf İçinde Çalışmanın Dezavantajları
        Karmaşıklık: Çok fazla test bir arada olduğunda kod karmaşıklaşabilir.
        Test Bağımlılığı: Testler arasında bağımlılık oluşabilir. Bir test diğer testlerin başarısına bağlı hale gelebilir.
        Bakım Zorluğu: Test sınıfı büyüdükçe yeni testler eklemek ve hataları ayıklamak zorlaşır.
    3. Ne Zaman Tek Sınıf Kullanılabilir?
        Küçük ve basit bir projede.
        Tek bir bileşeni test ediyorsanız (örneğin, yalnızca OwnerService ile çalışıyorsanız).
        Uygulama bağlamını her test için yüklemenin maliyeti yüksekse.
    4. Ne Zaman Ayrı Sınıf Kullanılmalı?
        Büyük veya karmaşık projelerde.
        Farklı modülleri test ediyorsanız.
        Test organizasyonu ve bakımını önemsiyorsanız.
    Sonuç:
        Ayrı bir test sınıfı kullanmak daha iyi bir organizasyon ve test yönetimi sağlar. Ancak, küçük ve basit projelerde tek bir sınıfla çalışmak yeterli olabilir. Büyük projelerde veya farklı modüller test ediliyorsa, her modül için ayrı bir test sınıfı oluşturmak en iyi uygulamadır.

/* 
     * TestRestTemplate, Spring Boot uygulamalarındaki REST API'lerini test etmek için kullanılır. Bu araç, özellikle entegrasyon testlerinde faydalıdır ve doğru çalışıp çalışmadığını test etmek amacıyla gerçek HTTP istekleri gönderir. 
     * RestTemplate'in test için uyarlanmış versiyonudur ve testlerde kullanımı kolaylaştırır.
     * Spring Boot testlerinde HTTP istemcisi olarak kullanılan bir araçtır ve özellikle bir REST API'yi test etmek amacıyla tasarlanmıştır. 
     * TestRestTemplate, Spring Boot uygulamalarında endpoint'lerin doğru çalıştığını test etmek için kolay ve esnek bir yöntem sunar.
     * 
     TestRestTemplate ve RestTemplate Arasındaki Farklar:
        TestContext Entegrasyonu: TestRestTemplate, test senaryolarında Spring Boot uygulamalarıyla entegrasyonlu çalışmak üzere özelleştirilmiş 
        bir araçtır. 
        RestTemplate ise daha genel bir HTTP istemcisidir ve uygulama ortamında farklı istemci ihtiyaçlarını karşılamak için kullanılır.
        Başka Bir Avantaj: TestRestTemplate testler sırasında başlatılan Spring Boot uygulamasının bağlamını kullanır. 
        Böylece testler sırasında daha kolay bir yapı sunar ve gerçek bir sunucuyla API uç noktalarını test eder.
    */

    /*
    * Bu kod ne iş yapar?
    Bu kod, Spring Boot uygulamasındaki bir Web MVC testini içermektedir. 
    Bu test sınıfı, Spring Boot’un sunduğu test desteğini kullanarak, bir web katmanının doğru çalışıp çalışmadığını test etmek için yazılmıştır. 
    Aşağıda, bu sınıfın her bir bölümünü detaylıca açıklayacağım.
     
    Anlamlı Anotasyonlar ve Açıklamalar
     
    @RunWith(SpringRunner.class):
    Bu anotasyon, JUnit testlerinin Spring TestContext Framework ile çalışmasını sağlar. SpringRunner, testlerin Spring ortamında çalışmasını sağlar. Bu anotasyon sayesinde Spring, test sırasında gerekli olan tüm konfigürasyonları sağlar ve Spring konteyneri testlere entegre edilir.
     
    @SpringBootTest(webEnvironment = WebEnvironment.MOCK):
    @SpringBootTest anotasyonu, Spring Boot uygulamasını başlatır ve testler sırasında uygulamanın gerçek bir web sunucusu yerine, 
    mock bir web ortamı kullanır.
    webEnvironment = WebEnvironment.MOCK ayarı, test sırasında gerçek bir web sunucusu (tomcat gibi) başlatılmadan, 
    sadece Spring MVC konteynerini başlatarak, tüm HTTP isteklerinin mocklanmasını sağlar. Bu, sadece Web MVC katmanının test edilmesini sağlar.
     
    @ActiveProfiles("dev"):
    Bu anotasyon, Spring profili ayarlarını değiştirmek için kullanılır. Burada "dev" profili aktif hale getirilmiştir. 
    Uygulamanın konfigürasyonlarını bu profile göre belirler. Örneğin, application-dev.properties veya application-dev.yml dosyasındaki ayarlar kullanılır.
     
    @AutoConfigureMockMvc:
    Bu anotasyon, Spring’in MockMvc nesnesini otomatik olarak konfigüre eder. MockMvc, testlerde web katmanını simüle ederek HTTP istekleri 
    göndermeyi ve yanıtları kontrol etmeyi sağlar. Yani, gerçek bir HTTP sunucusuna bağlanmadan, web uygulamanızın işlevselliğini test edebilirsiniz.
    Bu anotasyon sayesinde, testlerde MockMvc'yi doğrudan kullanabilirsiniz.
     
    @WithMockUser(username = "user1", password = "user1", authorities = "USER"):
    Bu anotasyon, testlerde kullanılan kullanıcıyı sahte olarak (mock) tanımlar. 
    Yani, testler sırasında user1 adlı bir kullanıcı oturum açmış gibi davranılır.
    username: Kullanıcının adı.
    password: Kullanıcının şifresi.
    authorities: Kullanıcının rolü veya yetkisi. Burada "USER" rolü atanmış.
    Bu, özellikle güvenlik testlerinde yararlıdır. MockMvc isteklerini, belirli bir kullanıcı yetkisi ile simüle edebilirsiniz, 
    örneğin, bir sayfanın yalnızca belirli bir kullanıcı veya belirli bir rol tarafından erişilebilir olmasını test etmek için.
    */
     
    /*
    ----MockMvc Nedir?
    MockMvc, Spring MVC uygulamalarında web katmanını test etmek için kullanılan bir araçtır. Özellikle kontrolcüleri (controller) test etmek için tasarlanmıştır. 
    Gerçek bir sunucu başlatmadan Spring MVC bileşenlerini test etmenizi sağlar.
    MockMvc Nedir?
    MockMvc, Spring MVC uygulamalarında web katmanını test etmek için kullanılan bir araçtır. Özellikle kontrolcüleri (controller) test etmek için tasarlanmıştır. Gerçek bir sunucu başlatmadan Spring MVC bileşenlerini test etmenizi sağlar.
    MockMvc'nin Avantajları
    Sunucu Başlatmaya Gerek Yok:
    Gerçek bir uygulama sunucusu başlatılmadığı için testler daha hızlı çalışır.
    Spring MVC Katmanını İzole Eder:
    Sadece web katmanını (controller, filtreler, validasyonlar, vb.) test etmek istediğinizde, veri tabanı veya servis katmanını çalıştırmanıza gerek kalmaz.
    HTTP Çağrıları Simüle Eder:
    HTTP GET, POST, PUT, DELETE gibi istekleri simüle edebilir ve kontrolcünün bu isteklere nasıl cevap verdiğini test edebilirsiniz.
    Sonuçları Doğrulama:
    Dönen HTTP durum kodlarını, header bilgilerini ve JSON, XML gibi yanıtları kontrol edebilirsiniz.
     
     
    MockMvc Kullanımı ile Entegrasyon Testi Arasındaki Fark
    MockMvc: Sadece web katmanını test eder. Servis veya veritabanı gibi diğer katmanlarla doğrudan bağlantısı yoktur.
    Entegrasyon Testi: Tüm uygulama bağlamını yükler ve kontrolcü, servis, repository gibi tüm katmanları bir arada test eder.
     
    Ne Zaman MockMvc Kullanılır?
    Yalnızca kontrolcülerin (controller) doğru çalışıp çalışmadığını test etmek istediğinizde.
    Spring MVC validasyonları, filtreler veya interceptor'ları test etmek istediğinizde.
    Gerçek bir sunucu başlatmadan hızlı testler yapmak istediğinizde.
    Ne Zaman Entegrasyon Testi Kullanılır?
    Kontrolcüyle birlikte servis ve veritabanı gibi diğer katmanları da test etmek istediğinizde.
    Uygulamanın uçtan uca doğru çalışıp çalışmadığını doğrulamak istediğinizde.
     
    Sonuç
    MockMvc, Spring MVC kontrolcülerinin hızlı ve izole bir şekilde test edilmesini sağlar. 
    Bu, özellikle büyük projelerde testleri daha hızlı ve odaklı yaparak geliştirme sürecini kolaylaştırır. 
    Ancak, kontrolcü dışında kalan katmanları da 
    test etmek için entegrasyon testlerini tamamlayıcı olarak kullanmak gerekir.
     
     
    Hangisini Ne Zaman Kullanmalıyım?
    1. Controller Testlerini Kullanmanız Gereken Durumlar
    	Web katmanında bir hata olup olmadığını hızlı bir şekilde görmek istiyorsanız.
    	Performansı önemsiyor ve bağımsız bir test istiyorsanız.
    	Katmanlar arasındaki bağımlılıkları izole etmek istiyorsanız.
    	Bir API endpoint'inin doğru HTTP yanıtları verdiğini doğrulamak istiyorsanız.
    2. Entegrasyon Testlerini Kullanmanız Gereken Durumlar
    	Uygulamanın tamamının doğru çalıştığını test etmek istiyorsanız.
    	Veri tabanına erişimin doğruluğunu kontrol etmek istiyorsanız.
    	Servis katmanı ile controller arasındaki iletişimi test etmek istiyorsanız.
    	Gerçekçi senaryolar üzerinde çalışıyorsanız (ör. bir HTTP isteği gönderip yanıt alana kadar tüm iş akışını test etmek).
     
    * */

    
//OwnerRepositoryTests classi icin @Transactional yaptigim icin testCreateOwner3 metodu icin Owner tablosuna kayit ekler mi ?
		//@Transactional anotasyonunun etkisi nedeniyle testCreateOwner3 metodunda eklediğiniz veriler veritabanına kalıcı olarak kaydedilmez. 
		//Bunun nedeni, @Transactional anotasyonunun, her bir test metodunun başında bir işlem başlatıp, metodun sonunda işlemi rollback etmesidir.	

* FakeSMTP local mail server kurmak için kullanılır.

    İndirme linki (Chrome da hata veriyor.Edgeden indirilebilir.)
    https://nilhcem.com/FakeSMTP/download.html
    Dokumanda ekran alıntısı mevcut.
    java -jar C:\Dev\mail-jar\fakeSMTP-latest\fakeSMTP-2.0.jar ile ayaga kaldırılır.

BaseEntity clasını neden normal class degil de abstract class yaptıgımızın aciklaması
/*
* 
	Bu sınıfın abstract (soyut) yapılmasının sebebi, doğrudan bir entity olarak kullanılmaması ve yalnızca diğer entity sınıflarının temel bir 
	yapı taşı (base class) olarak tasarlanmış olmasıdır. 
	Bu sınıf, genel özellikleri ve davranışları sağlayarak diğer entity sınıfları için bir şablon oluşturur. 
	ÖNEMLİ BURAYI OKU ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Abstract Yapılmasaydı?
	Eğer abstract olmasaydı:
	BaseEntity doğrudan kullanılabilir hale gelirdi (örneğin BaseEntity türünden bir instance oluşturulabilir). Ancak bu mantıklı değildir, çünkü bu sınıfın tek başına bir anlamı yoktur.
	Sınıfın doğrudan kullanılmaması gerektiğini belirtmek için yorumlar yazmanız gerekirdi, ancak bu açıkça anlaşılamayabilirdi.
	İşte detaylı açıklama:
	1. Doğrudan Kullanılmaması Amaçlanmıştır
	@MappedSuperclass ile işaretlenmiş sınıflar, veritabanında bir tabloya karşılık gelmezler. Bunun yerine, bu sınıfı miras alan sınıflar, BaseEntity içindeki alanları kendi tablolarında kullanabilir. Ancak, BaseEntity sınıfının kendisi bir tabloya eşlenmeyeceği için doğrudan bir entity olarak kullanılmaz. Bu durumda sınıfın abstract yapılması, geliştiricilere bu sınıfın doğrudan kullanılmaması gerektiğini net bir şekilde ifade eder.
	2. Ortak Alan ve Davranışların Paylaşımı
	BaseEntity sınıfı, id, createdBy, createdDate, updatedBy, updatedDate gibi birçok sınıfta tekrar eden alanları içerir. Bu sınıfı miras alan diğer entity'ler, bu alanları ve bunlara ait getter/setter gibi davranışları otomatik olarak kazanır. Böylece kod tekrarını önler.
	3. Auditing Özelliklerini Barındırması
	Sınıf, Spring Data JPA'nın auditing mekanizmasını kullanıyor (@CreatedBy, @CreatedDate, @LastModifiedBy, @LastModifiedDate). Bu da şu anlama gelir:
	createdBy ve createdDate: Kaydı kimin ve ne zaman oluşturduğunu otomatik olarak doldurur.
	updatedBy ve updatedDate: Kaydı en son kimin ve ne zaman güncellediğini otomatik olarak doldurur.
	Bu özellikler, genellikle her entity'de bulunur ve bu yüzden ortak bir sınıfta toplanması mantıklıdır.
	4. Kalıtım Amaçlı Tasarlanması
	BaseEntity sınıfı abstract yapılarak, bu sınıfın sadece kalıtım yoluyla kullanılabileceği belirtilir. Örneğin, bir Pet, Owner veya Visit sınıfı BaseEntity'yi miras alabilir ve bu ortak özelliklere sahip olur.
	@Entity
	public class Pet extends BaseEntity {
	    private String name;
	    private Date birthDate;
	    // Pet'e özgü diğer alanlar
	}
	5. Polimorfizm İçin Bir Temel Sınıf Sağlar
	Abstract sınıf olarak tasarlandığı için, daha sonra bu sınıfa ait tüm entity'ler üzerinde işlemler yapmanız kolaylaşır. Örneğin, tüm BaseEntity türündeki entity'ler üzerinde ortak işlemler gerçekleştirebilirsiniz:
	BaseEntity entity = findEntityById(id);
	System.out.println(entity.getCreatedDate());
* 
* */

Spring Auditing Nedir?
Spring Data Auditing, uygulamadaki entity'ler üzerinde belirli işlemleri (örneğin, bir kaydın kim tarafından oluşturulduğu veya güncellendiği gibi) otomatik olarak izlemek ve bu bilgileri kaydetmek için kullanılan bir özelliktir. Özellikle veritabanı işlemlerinde, kayıtların oluşturulma ve güncellenme süreçlerini takip etmek için kullanılır.
 
Spring Auditing'in Sağladığı Avantajlar
Otomasyon: createdBy, createdDate, lastModifiedBy, lastModifiedDate gibi alanlar otomatik olarak doldurulur. Elle doldurmanız gerekmez.
Tarihçe (Audit Log): Kayıtların oluşturulma ve değiştirilme süreçlerini izlemek için bir altyapı sağlar.
Standartlaşma: Entity'ler üzerinde aynı türde bilgiler tutulur ve tüm audit işlemleri tek bir yapı altında yönetilir.
Bakım Kolaylığı: Auditing işlemlerini manuel olarak yazmaktan kurtulursunuz.
Spring Auditing ile İzlenen Alanlar
Spring Auditing genellikle şu bilgileri izlemek için kullanılır:
 
createdBy: Kaydı oluşturan kullanıcı.
createdDate: Kaydın oluşturulma zamanı.
lastModifiedBy: Kaydı en son güncelleyen kullanıcı.
lastModifiedDate: Kaydın en son güncellenme zamanı.
Bu alanlar genellikle entity sınıfında tanımlanır ve Spring Auditing mekanizması tarafından doldurulur.
 
Nasıl Çalışır?
    1. EntityListener Kullanımı
    Auditing işlemleri, Spring'in sağladığı bir EntityListener mekanizması ile çalışır. Bu listener, veritabanı işlemleri sırasında (örneğin, bir kayıt oluşturulduğunda veya güncellendiğinde) tetiklenir ve ilgili alanları doldurur.
     
Spring Auditing'in Kullanımı
    1. Auditing'i Etkinleştirme
    Spring Auditing özelliğini etkinleştirmek için uygulamanızda @EnableJpaAuditing anotasyonunu ekleyin. Genellikle bu, @SpringBootApplication ile aynı yerde tanımlanır.
 

    @Configuration
    @EnableJpaAuditing
    public class AuditConfig {
    }
2. Entity Sınıfında Audit Alanlarını Tanımlama
    Entity sınıfınızda audit bilgilerini tutmak için alanlar oluşturun ve bunları Spring'in @CreatedBy, @CreatedDate, @LastModifiedBy ve @LastModifiedDate anotasyonları ile işaretleyin.
    
    import org.springframework.data.annotation.CreatedBy;
    import org.springframework.data.annotation.CreatedDate;
    import org.springframework.data.annotation.LastModifiedBy;
    import org.springframework.data.annotation.LastModifiedDate;
     
    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.GenerationType;
    import javax.persistence.Id;
    import java.util.Date;
     
    @Entity
    public class AuditEntity {
     
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
     
        @CreatedBy
        private String createdBy;
     
        @CreatedDate
        private Date createdDate;
     
        @LastModifiedBy
        private String lastModifiedBy;
     
        @LastModifiedDate
        private Date lastModifiedDate;
     
        // Getter ve Setter metotları
    }
3. AuditingListener ve Principal Ayarları
    Auditing işlemlerinde @CreatedBy ve @LastModifiedBy gibi alanlar için oturumdaki kullanıcının bilgisi gereklidir. Bunu sağlamak için Spring Security ile entegrasyon yapılır.
     
    Auditing işlemi sırasında hangi kullanıcı bilgisinin alınacağını belirlemek için bir AuditorAware bean tanımlayın:

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.data.domain.AuditorAware;
     
    import java.util.Optional;
     
    @Configuration
    public class AuditorAwareImpl implements AuditorAware<String> {
     
        @Override
        public Optional<String> getCurrentAuditor() {
            // Oturumdaki kullanıcı bilgilerini döndürün. Örnek:
            return Optional.of("currentUser"); // Spring Security ile entegrasyon varsa, oturumdaki kullanıcıyı alın.
        }
    }
    Bu yapı, audit bilgileri doldurulurken Spring tarafından çağrılır.
 
4. Database'de Audit Alanlarının Otomatik Doldurulması
    Auditing aktif hale getirildiğinde, ilgili alanlar (örneğin, createdBy ve createdDate) otomatik olarak doldurulur. Bir kayıt eklendiğinde createdDate, güncellendiğinde ise lastModifiedDate alanı otomatik olarak set edilir.
 
Örnek Senaryo
    Bir "Pet" entity'si üzerinde Spring Auditing'i uyguladığınızda, aşağıdaki gibi çalışır:
     
    Yeni bir kayıt oluşturulduğunda:
    createdBy: "admin"
    createdDate: 2025-01-20 12:34:56
    Kayıt güncellendiğinde:
    lastModifiedBy: "user123"
    lastModifiedDate: 2025-01-21 09:20:00
    Özet
    Spring Auditing, entity'lerin oluşturulma ve güncellenme süreçlerini izlemek için kullanışlı bir araçtır. Auditing özelliğiyle birlikte:
     
Otomasyon sağlanır.
Kodunuzu daha temiz ve standart bir yapıda tutabilirsiniz.
Audit işlemlerini manuel olarak yapmaktan kurtulursunuz.
Spring Security ile entegre edilirse, oturum açmış kullanıcıların bilgileri de otomatik olarak kaydedilir.

Ekte ekran alıntıları mevcut bura üzerinden data üzerindeki tüm objeleri çekip bunlar üzerinde http methodları çalıstırıabiliriz.
http://localhost:8080/hal yazarak erişebiliriz.

PetClinicAuditorAware (SpringSecurity kısmının ilgili auth bilgisine göre kullanıcıya setlenmesi)
/*
	Bu sınıf, Spring Boot'un auditing (denetim) mekanizmasıyla birlikte çalışan bir yapı sunar.
	Ne İşe Yarıyor?
	PetClinicAuditorAware sınıfı, veritabanına bir kayıt eklenirken veya bir kayıt güncellenirken, bu işlemi kimin gerçekleştirdiğini 
	(yani "auditor" / denetçi bilgisini) otomatik olarak sağlar. 
	Bu bilgi, genellikle bir kullanıcının kimlik bilgilerini (örneğin, kullanıcı adını) içerir ve Spring Auditing mekanizması tarafından
	 otomatik olarak @CreatedBy ve @LastModifiedBy gibi alanlara kaydedilir.
	Kodun Detaylı Açıklaması
	AuditorAware Arayüzü
	AuditorAware arayüzü, Spring Data JPA tarafından sağlanır ve denetçi (auditor) bilgisini belirlemek için kullanılır.
	getCurrentAuditor() metodu, sistemde şu anda kimlik doğrulaması yapmış kullanıcının bilgilerini döner.
	getCurrentAuditor() Metodu
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	return Optional.ofNullable(authentication !=null ? authentication.getName() : null);
	SecurityContextHolder: Spring Security'nin kimlik doğrulama bağlamını sakladığı bir sınıftır. Şu anda oturum açmış kullanıcıya dair tüm bilgileri içerir.
	authentication.getName(): Oturum açmış kullanıcının kullanıcı adını döner.
	Optional.ofNullable: Kullanıcı doğrulanmamışsa null döner; bu da oturum açmamış kullanıcılar için denetim kaydı yapılmamasını sağlar.
	@Component Anotasyonu
	Sınıfı bir Spring Bean olarak işaretler ve otomatik olarak Spring konteynerine dahil eder.
	Spring Auditing mekanizması bu bean'i kullanarak denetçi bilgisini alır.
	Sınıfın Kullanımı
	Bu sınıf, Spring Data Auditing ile birlikte çalışır ve genellikle şu alanlarla birlikte kullanılır:
	@CreatedBy: Kaydı oluşturan kullanıcı.
	@LastModifiedBy: Kaydı son düzenleyen kullanıcı.
	Örneğin, bir BaseEntity sınıfında:
	@MappedSuperclass
	@EntityListeners(AuditingEntityListener.class)
	public abstract class BaseEntity {
	    @CreatedBy
	    private String createdBy;
	    @LastModifiedBy
	    private String updatedBy;
	    // Diğer alanlar ve getter/setter metodları
	}
	Bu alanlar, otomatik olarak PetClinicAuditorAware tarafından sağlanan denetçi bilgileriyle doldurulur.
	Örnek Çalışma Senaryosu
	Kullanıcı, bir REST API üzerinden oturum açar.
	Oturum açan kullanıcının bilgileri SecurityContextHolder içinde tutulur.
	Kullanıcı bir kayıt oluşturur veya günceller.
	Spring Auditing mekanizması devreye girer ve PetClinicAuditorAware sınıfını çağırarak oturum açmış kullanıcının adını alır.
	Bu kullanıcı bilgisi, veritabanına createdBy ve updatedBy alanları olarak kaydedilir.
	Örnek
	Diyelim ki oturum açan kullanıcı "admin" ve bir yeni kayıt oluşturuyor.
	createdBy alanı "admin" olarak doldurulur.
	Kaydı tekrar düzenlediğinde, updatedBy alanı "admin" olarak güncellenir.
	Faydalı Olduğu Yerler
	Loglama: Hangi kullanıcı hangi kaydı oluşturmuş veya değiştirmiş, bu bilgi otomatik olarak tutulur.
	Denetim Gereklilikleri: Özellikle finansal ya da yasal düzenlemelerin geçerli olduğu projelerde, kullanıcı aktivitelerinin izlenmesi gerekir.
	Otomasyon: Kullanıcı bilgilerini manuel olarak her defasında ayarlamaya gerek kalmadan denetim süreçleri otomatikleşir.

	Özet
	PetClinicAuditorAware, Spring Security ile entegre çalışarak şu anda oturum açmış kullanıcının bilgilerini alır.
	Bu bilgileri, Spring Auditing mekanizması aracılığıyla @CreatedBy ve @LastModifiedBy gibi alanlara otomatik olarak kaydeder.
	Projelerde denetim (auditing) süreçlerini kolaylaştırır.
*/

Cache kısmında denemelerde restController methoduna caching yapmıstık
    public class PetClinicRestController {
        @Cacheable("allOwners")//cachede yoksa methodu çalıstırıyor varsa cacheden getiriyor.
        @RequestMapping(method = RequestMethod.GET, value = "/owners")
        public ResponseEntity<List<Owner>> getOwners() {
            System.out.println(">>>inside getOwners...");
            List<Owner> owners = petClinicService.findOwners();
            return ResponseEntity.ok(owners);
        }
        //...
    }

http://localhost:8080/rest/owners url ine tarayıcıdan get istegi attıgımızda 
Basic Auth yetkisi olan (user2,user3) ile auth yapılarak istek atılır.

İlk istekte method cache de olmadıgı için method çalışır.
Logda >>>inside getOwners... yazarak methoda girdigi anlasılır.
ikinci http://localhost:8080/rest/owners isteginde tetikledigimizde cache'de oldugu için methoda girmez cache'den getirilir.

Cache Detaylı Not 1 

Spring Cache nedir ? Ne yapılır ?
Spring Cache, Spring Framework'ün sunduğu bir özellik olup, uygulamalarda verilerin daha 
hızlı erişimini sağlamak için cache (önbellek) mekanizmasını kolayca kullanmanıza olanak tanır. 
Önbellekleme, sık erişilen veya hesaplanması pahalı olan verileri bir süreliğine bellekte tutarak, 
sonraki erişimlerde bu verileri hızlı bir şekilde kullanmayı sağlar. 
Bu da performansı artırır ve sistem üzerindeki yükü azaltır.

Spring Cache'in Temel İşlevleri:
Performansı Artırma: Önbelleğe alınmış verilere erişim çok hızlıdır, 
bu da veritabanı sorgularını, ağır işleme gerektiren işlemleri veya uzaktan çağrıları azaltır.
Maliyeti Azaltma: Tekrar tekrar hesaplanması pahalı olan işlemler, önbellekte saklanarak işlem maliyetleri düşürülür.
Kullanımı Kolaylaştırma: Spring Cache ile kodunuzu fazla değiştirmeden mevcut projelere önbellekleme özelliği ekleyebilirsiniz.

Spring Cache Nasıl Çalışır?
Spring Cache, bir cache abstraction (önbellek soyutlama katmanı) sağlar. Bu soyutlama, cache işlemlerini 
Spring tabanlı bir yapı ile kolayca entegre eder. 
Uygulamada, Spring'in önbellekleme özelliklerini kullanmak için sadece birkaç basit adım gereklidir.

Veri Cachelenir (Önbelleğe Alınır): Cache'e alınacak veriler, bir metot tarafından hesaplanır ve sonuç önbellekte saklanır.
Önbellekten Veri Okuma: Eğer aynı işlem tekrar yapılmak istenirse, Spring önce önbellekte bu verinin olup olmadığını kontrol eder. Varsa, önbellekten döner; yoksa metodu çalıştırır ve sonuçları önbelleğe alır.

Spring Cache Kullanımı
Spring Cache, önbellekleme işlemleri için anotasyonlar kullanır. Örneğin:

@EnableCaching: Önbellekleme özelliğini etkinleştirir.
@Cacheable: Bir metodun sonucunu önbelleğe almak için kullanılır.
@CachePut: Metot sonucunu önbellekte günceller.
@CacheEvict: Önbellekten veri temizlemek için kullanılır.
@Caching: Birden fazla cache işlemini bir arada yapmak için kullanılır.

Örnek:
    -->pom.xml
    <!-- Spring Cache-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    
    -->PetClinicApplication.java
    
    @ServletComponentScan
    @SpringBootApplication
    @EnableConfigurationProperties(value=PetClinicProperties.class)
    @EnableJpaAuditing(auditorAwareRef = "petClinicAuditorAware")
    @EnableCaching
    public class PetClinicApplication {
    
    	@Autowired
    	private PetClinicShowService petClinicShowService;
    
    	public static void main(String[] args) {
    		SpringApplication.run(PetClinicApplication.class, args);
    		System.out.println("Hello Alper");
    		}
    }
    
    -->PetClinicRestController
    @Configuration
    @EnableCaching // Caching özelliğini aktif eder
    public class CacheConfig {
        // Cache yapılandırmaları (isteğe bağlı olarak özelleştirilebilir)
    }

    @Service
    public class UserService {
    
        @Cacheable("users") // Sonuç "users" adlı cache'e kaydedilir
        public User getUserById(Long id) {
            // Veritabanından veya başka bir kaynaktan veri çekilir
            return userRepository.findById(id).orElse(null);
        }
    
        @CacheEvict(value = "users", key = "#id") // Belirli bir cache'i temizler
        public void deleteUser(Long id) {
            userRepository.deleteById(id);
        }
    }
Spring Cache'in Desteklediği Cache Çözümleri
Spring Cache, farklı cache sağlayıcılarıyla entegre çalışabilir. Bunlardan bazıları:

ConcurrentMapCache (Java’nın ConcurrentMap sınıfını kullanır, temel bir cache sağlar)
Ehcache
Hazelcast
Redis
Caffeine
Infinispan
Guava Cache


Spring Cache, Spring Framework'ün sunduğu bir özellik olup, uygulamalarda verilerin daha hızlı erişimini sağlamak için cache (önbellek) mekanizmasını kolayca kullanmanıza olanak tanır. Önbellekleme, sık erişilen veya hesaplanması pahalı olan verileri bir süreliğine bellekte tutarak, sonraki erişimlerde bu verileri hızlı bir şekilde kullanmayı sağlar. Bu da performansı artırır ve sistem üzerindeki yükü azaltır.

Spring Cache'in Temel İşlevleri:
Performansı Artırma: Önbelleğe alınmış verilere erişim çok hızlıdır, bu da veritabanı sorgularını, ağır işleme gerektiren işlemleri veya uzaktan çağrıları azaltır.
Maliyeti Azaltma: Tekrar tekrar hesaplanması pahalı olan işlemler, önbellekte saklanarak işlem maliyetleri düşürülür.
Kullanımı Kolaylaştırma: Spring Cache ile kodunuzu fazla değiştirmeden mevcut projelere önbellekleme özelliği ekleyebilirsiniz.
Spring Cache Nasıl Çalışır?
Spring Cache, bir cache abstraction (önbellek soyutlama katmanı) sağlar. Bu soyutlama, cache işlemlerini Spring tabanlı bir yapı ile kolayca entegre eder. Uygulamada, Spring'in önbellekleme özelliklerini kullanmak için sadece birkaç basit adım gereklidir.

Veri Cachelenir (Önbelleğe Alınır): Cache'e alınacak veriler, bir metot tarafından hesaplanır ve sonuç önbellekte saklanır.
Önbellekten Veri Okuma: Eğer aynı işlem tekrar yapılmak istenirse, Spring önce önbellekte bu verinin olup olmadığını kontrol eder. Varsa, önbellekten döner; yoksa metodu çalıştırır ve sonuçları önbelleğe alır.
Spring Cache Kullanımı
Spring Cache, önbellekleme işlemleri için anotasyonlar kullanır. Örneğin:

@EnableCaching: Önbellekleme özelliğini etkinleştirir.
@Cacheable: Bir metodun sonucunu önbelleğe almak için kullanılır.
@CachePut: Metot sonucunu önbellekte günceller.
@CacheEvict: Önbellekten veri temizlemek için kullanılır.
@Caching: Birden fazla cache işlemini bir arada yapmak için kullanılır.
Örnek:
java
Kopyala
Düzenle
@Configuration
@EnableCaching // Caching özelliğini aktif eder
public class CacheConfig {
    // Cache yapılandırmaları (isteğe bağlı olarak özelleştirilebilir)
}
java
Kopyala
Düzenle
@Service
public class UserService {

    @Cacheable("users") // Sonuç "users" adlı cache'e kaydedilir
    public User getUserById(Long id) {
        // Veritabanından veya başka bir kaynaktan veri çekilir
        return userRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "users", key = "#id") // Belirli bir cache'i temizler
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
Spring Cache'in Desteklediği Cache Çözümleri
Spring Cache, farklı cache sağlayıcılarıyla entegre çalışabilir. Bunlardan bazıları:

ConcurrentMapCache (Java’nın ConcurrentMap sınıfını kullanır, temel bir cache sağlar)
Ehcache
Hazelcast
Redis
Caffeine
Infinispan
Guava Cache

Ne Zaman Spring Cache Kullanılmalı?
Veritabanı sorguları çok sık tekrarlandığında.
Uzun süren ve yoğun işlem gerektiren işlemleri hızlandırmak gerektiğinde.
Dış servislerden veri çekmenin yavaş olduğu durumlarda.
Çıktının sık değişmediği ve geçerliliğini koruduğu durumlarda.
Spring Cache, özellikle yüksek performans gereksinimi olan uygulamalarda oldukça faydalıdır ve uygulamaların ölçeklenebilirliğini artırabilir.

Örnek:
Controller kısmına

    @Cacheable("allOwners")
	@RequestMapping(method = RequestMethod.GET, value = "/owners")
	public ResponseEntity<List<Owner>> getOwners() {
		System.out.println(">>>inside getOwners....");
		List<Owner> findOwners = petClinicService.findOwners();
		return ResponseEntity.ok(findOwners);
	}
böyle bir metot yazdık. İlk istek de buraya düşüyor. Fakat 2. istek de Owners guncellense bile buraya düşmüyor Neden?

/*@Cacheable("allOwners") yazdım buraya ilk defa istek attıgımda servis cagrilir. 
	 * 2. istek attigimde ise buraya girmiyor Cache deki return degerini getirir
	 * Neden?
	 * 
	 * @Cacheable anotasyonunun çalışma prensibinden kaynaklanmaktadır. 
	 * @Cacheable, bir metot çağrıldığında önce önbellekte ilgili sonucu arar. 
	 * Eğer önbellekte bir sonuç varsa, metodu çalıştırmadan doğrudan önbellekteki sonucu döner. 
	 * Bu nedenle, ikinci istek yapıldığında getOwners() metodu çağrılmayacak ve ">>>inside getOwners...." çıktısı konsola yazılmayacaktır.
	 * 
	 * @Cacheable Nasıl Çalışır?
		İlk çağrıda:

		@Cacheable, verilen cache adıyla ("allOwners") önbellekte ilgili bir sonuç arar.
		Eğer önbellekte bir sonuç yoksa, metot çalıştırılır ve dönen sonuç önbelleğe kaydedilir.
		İkinci ve sonraki çağrılarda:
		
		@Cacheable, önbellekte ilgili bir sonuç bulursa, metodu çalıştırmadan doğrudan önbellekteki sonucu döner.
	 * 
	 * Mahmut Duman Açıklama:
	 * Cache kısmında denemelerde restController methoduna caching yapmıstık
		    public class PetClinicRestController {
		        @Cacheable("allOwners")//cachede yoksa methodu çalıstırıyor varsa cacheden getiriyor.
		        @RequestMapping(method = RequestMethod.GET, value = "/owners")
		        public ResponseEntity<List<Owner>> getOwners() {
		            System.out.println(">>>inside getOwners...");
		            List<Owner> owners = petClinicService.findOwners();
		            return ResponseEntity.ok(owners);
		        }
		        //...
		    }
		 
		http://localhost:8080/rest/owners url ine tarayıcıdan get istegi attıgımızda 
		Basic Auth yetkisi olan (user2,user2) ile auth yapılarak istek atılır.
		 
		İlk istekte method cache de olmadıgı için method çalışır.
		Logda >>>inside getOwners... yazarak methoda girdigi anlasılır.
		ikinci http://localhost:8080/rest/owners isteginde tetikledigimizde cache'de oldugu için methoda girmez cache'den getirilir.
	 * 
	 * */
	 
1-Cozum : Cache'i Manuel Temizlemek gerekir.
Eğer veriler zamanla değişiyorsa, 
@CacheEvict anotasyonunu kullanarak önbelleği temizleyebilirsiniz. 
Örneğin, yeni bir Owner kaydedildiğinde:

    @CacheEvict(value = "allOwners", allEntries = true)
    @RequestMapping(method = RequestMethod.POST, value = "/owners")
    public ResponseEntity<Void> addOwner(@RequestBody Owner owner) {
        petClinicService.addOwner(owner);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
2-Cozum: Cache'in Geçerlilik Süresini Belirleme
Bazı cache sağlayıcıları (örneğin, Redis, Ehcache, veya Caffeine) belirli bir süre sonunda 
önbelleğin otomatik temizlenmesini destekler. Bu süreyi yapılandırarak, cache'in eskimesini sağlayabilirsiniz. 
Örneğin:
    yaml dosyası
    
    spring:
      cache:
        caffeine:
          spec: expireAfterWrite=5m,maximumSize=100
3-Cozum: Metodu Her Zaman Çalıştırmak Ama Cache Güncellemek
    @CachePut("allOwners")
    @RequestMapping(method = RequestMethod.GET, value = "/owners")
    public ResponseEntity<List<Owner>> getOwners() {
        System.out.println(">>>inside getOwners....");
        List<Owner> findOwners = petClinicService.findOwners();
        return ResponseEntity.ok(findOwners);
    }
    
Eğer aynı veriyi sık sık döndürmek istiyorsanız ve performansı artırmak öncelikli ise @Cacheable doğru bir tercihtir. Ancak, her istekte metot çalıştırılmasını istiyorsanız, 
ya @Cacheable yerine @CachePut kullanmalı ya da önbellekleme anotasyonlarını tamamen kaldırmalısınız.

Soru:
Başka bir update metodunda bu owner ları guncelledim daha sonra owners a 
istek attıgımda güncellenen veriyi mi getirir yoksa cache dekini mi?

Eğer başka bir update metodu Owner verilerini güncellediyse ve önbellekteki "allOwners" cache temizlenmediyse, 
/owners endpoint'ine yapılan istekte önbellekteki eski veri dönecektir. 
Çünkü @Cacheable anotasyonu, önce önbellekteki veriyi kontrol eder ve eğer orada bir sonuç bulursa metodu çalıştırmaz. 
Bu durumda güncellenen veri yansıtılmaz.

Çözüm Yolları
1. Update İşleminde Cache'i Temizlemek
Update metodu içinde ilgili cache'i temizlemek için @CacheEvict anotasyonunu kullanabilirsiniz. 
Bu, update işleminden sonra cache'in eski veriyi tutmasını engeller. 

Örneğin:

    @CacheEvict(value = "allOwners", allEntries = true)
    @RequestMapping(method = RequestMethod.PUT, value = "/owners/{id}")
    public ResponseEntity<Void> updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
        petClinicService.updateOwner(id, owner);
        return ResponseEntity.ok().build();
    }
Bu yapılandırma ile updateOwner metodu çağrıldığında "allOwners" cache'i temizlenir. Daha sonra /owners endpoint'ine yapılan istekte @Cacheable anotasyonu devreye girer ve metodu çalıştırarak güncellenmiş veriyi döner.

2. Cache Güncellemek İçin @CachePut Kullanımı
Eğer update işleminden sonra cache'i temizlemek yerine doğrudan güncellenmiş veriyi cache'e eklemek istiyorsanız, 
@CachePut anotasyonunu kullanabilirsiniz. 

Örneğin:

    @CachePut(value = "allOwners")
    @RequestMapping(method = RequestMethod.PUT, value = "/owners/{id}")
    public ResponseEntity<Void> updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
        petClinicService.updateOwner(id, owner);
        return ResponseEntity.ok().build();
    }
Bu yöntem, güncel veriyi cache'e kaydeder ve eski cache'in üzerine yazar.

3. Cache'in Geçerlilik Süresini Ayarlamak
Eğer güncelleme işlemleri sık değilse, cache sağlayıcınızda bir geçerlilik süresi (TTL - Time to Live) ayarlayabilirsiniz. 
Bu, cache'in belirli bir süre sonunda otomatik olarak temizlenmesini sağlar. 

Örneğin, Redis veya Caffeine kullanıyorsanız:

    yaml

    spring:
      cache:
        caffeine:
          spec: expireAfterWrite=5m,maximumSize=100
Yukarıdaki örnekte, cache her 5 dakikada bir kendini temizler. Ancak, bu yöntem verilerinizi ne kadar sık güncellediğinize bağlı olarak uygun olmayabilir.

4. Manuel Cache Yönetimi
Bazı durumlarda programatik olarak cache'i yönetmek isteyebilirsiniz. 
Spring'in CacheManager veya Cache API'sini kullanarak belirli bir cache'i temizleyebilir veya güncelleyebilirsiniz:

    @Autowired
    private CacheManager cacheManager;
    
    public void evictAllOwnersCache() {
        Cache cache = cacheManager.getCache("allOwners");
        if (cache != null) {
            cache.clear();
        }
    }
Bu yöntemi update metodunuzda çağırarak cache'i manuel olarak temizleyebilirsiniz.

Sonuç
Update işlemi sonrasında /owners endpoint'inin güncellenmiş veriyi döndürmesi için aşağıdaki yöntemlerden birini kullanabilirsiniz:

Update metodunda @CacheEvict ile ilgili cache'i temizleyin.
Update metodunda @CachePut kullanarak cache'i güncelleyin.
Cache sağlayıcınızda bir TTL ayarlayarak eski verilerin belirli süre sonunda temizlenmesini sağlayın.
En yaygın çözüm @CacheEvict kullanarak cache'i temizlemek ve yeni bir istekte cache'in tekrar oluşturulmasına izin vermektir.

Burada yeni sekmede http://localhost:8080/owners/new çagırılarak yeni ekleme yaptıgımızda 
İlk tetikledigimiz http://localhost:8080/rest/owners sonucu cache de tutuldugu için db den sorgulamayıp cacheden getirecektir.
Yeni eklenen kayıtı getirmeyecektir.
Bunu tüm create işlemlerinde yapması için petClinic Service createOwners metodu üzerine yazdık. 
createOwners methodu üzerine CacheEvict yazarak cache'i siliyoruz..

public class PetClinicServiceImpl implements PetClinicService {
	@Override
	@CacheEvict(cacheNames = "allOwners", allEntries = true) 
    //burada allOwners ile cachelenen veriler allEntries = true oldugu için remove edilecek.
    Burada dönüş tipi el vermedigi için mevcut cache'i sıfırlıyoruz.
	public void createOwner(Owner owner) {
    //...
    }
}

CacheEvict anotasyonunu ekledikten sonra senaryoda 
İlk tetikledigimiz http://localhost:8080/rest/owners sonrasında yeni bir sekmede 
Burada yeni sekmede http://localhost:8080/owners/new çagırılarak yeni ekleme yaptıgımızda cache'i sildigi icin 
method çalısacak ve db sorgusunda yeni eklenen kayıtlarla birlikte güncel liste gelecektir.



```