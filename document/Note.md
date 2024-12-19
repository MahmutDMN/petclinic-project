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
        

```