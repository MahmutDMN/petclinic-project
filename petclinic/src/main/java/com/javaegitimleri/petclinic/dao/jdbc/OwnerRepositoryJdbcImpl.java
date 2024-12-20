package com.javaegitimleri.petclinic.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.model.Owner;

/*
 * OwnerRepository interface inden 
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
    Springboot için ya impl yaptıklarımızdan birisine @Repository("ownerRepository") olarak yazdıgımızda hata almayacaktır.
    
 */


//@Repository
//@Repository("ownerRepository") // ownerRepositoryJpa Impl a yazdıgımız için bunu eski haline donderdik
@Repository
public class OwnerRepositoryJdbcImpl implements OwnerRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Owner> rowMapper = new RowMapper<Owner>() {

		@Override
		public Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
			Owner owner = new Owner();
			owner.setId(rs.getLong("id"));
			owner.setFirstName(rs.getString("first_name"));
			owner.setLastName(rs.getString("last_name"));
			return owner;
		}
	};



	public OwnerRepositoryJdbcImpl() {

	}

	@Override
	public List<Owner> findAll() {
		String sql = "select id, first_name, last_name from t_owner";
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public Owner findById(Long id) {
		String sql = "select id, first_name, last_name from t_owner where id = ?";
		return DataAccessUtils.singleResult(jdbcTemplate.query(sql, rowMapper, id));
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		String sql = "select id, first_name, last_name from t_owner where last_name like ?";
		return jdbcTemplate.query(sql, rowMapper, "%" + lastName + "%");
	}

	@Override
	public void createOwner(Owner owner) {
		// TODO Auto-generated method stub

	}

	@Override
	public Owner updateOwner(Owner owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOwner(Long id) {
		String sql="delete from t_owner where id = ?";
		jdbcTemplate.update(sql, id);

	}

	/*
	 * JDBC Notları Spring Üzerinden JDBC ile Veri Erişimi
	 * 
	 * - String veri erişiminde bu tekrarlayan kısımları ortadan kaldırmak için
	 * Template Method örüntüsü tabanlı bir kabiliyet sunar
	 * 
	 * - JdbcTemplate isimli bu yapı utility veya helper sınıflarına benzetilebilir
	 * - JdbcTemplate sayesinde Template Method tarafından dikte edilen standart bir
	 * kullanım şekli kod geneline hakim olur
	 * 
	 * JdbcTemplate Kullanım Örnekleri String result = jdbcTemplate.queryForObject(
	 * "select last_name from t_owner where id = ?", String.class, 1212L);
	 * 
	 * Map<String,Object> result = jdbcTemplate.queryForMap(
	 * "select last_name,first_name from t_owner where id = ?", 1212L);
	 * 
	 * List<String> result = jdbcTemplate.queryForList(
	 * "select last_name from t_owner", String.class);
	 * 
	 * List<Map> result = jdbcTemplate.queryForList( "select * from t_owner");
	 * 
	 * int insertCount = jdbcTemplate.update(
	 * "insert into t_owner (id,first_name,last_name) values (?,?,?)", 101L, "Ali",
	 * "Yücel");
	 * 
	 * int updateCount = jdbcTemplate.update(
	 * "update t_owner set last_name = ? where id = ?", "Güçlü", 1L);
	 * 
	 * int deleteCount = jdbcTemplate.update( "delete from t_owner where id = ?",
	 * 1L);
	 * 
	 * List<Object[]> args = new ArrayList<Object[]>();
	 * 
	 * args.add(new Object[]{"Kenan","Sevindik",1L}); args.add(new
	 * Object[]{"Muammer","Yücel",2L});
	 * 
	 * int[] batchUpdateCount = jdbcTemplate.batchUpdate(
	 * "update t_owner set first_name = ?, last_name = ? where id = ?", args);
	 * 
	 * int[] batchUpdateCount = jdbcTemplate.batchUpdate( "delete from t_pet",
	 * "delete from t_owner");
	 * 
	 * 
	 * Pozisyonel Parametreler
	 *
	 *        String sql = "select count(*) from t_owner where first_name = ? and
	 *        last_name = ? ";
	 *
	 *        int count = jdbcTemplate.queryForObject(
	 *        sql, Integer.class, new Object[]{"Kenan","Sevindik"});
	 *
	 *        int count = jdbcTemplate.queryForObject(
	 *        sql, Integer.class, "Kenan","Sevindik");
	 *
   	 *        * İsimlendirilmiş (Named) Parametreler
	 *
     *        String sql = "select count(*) from t_owner where
   	 *        first_name = :firstName and last_name = :lastName";
	 *
   	 *        Map<String, Object> paramMap = new HashMap<>();
   	 *        paramMap.put("firstName", "Kenan");
   	 *        paramMap.put("lastName", "Sevindik");
	 *
   	 *        int count = namedParameterJdbcTemplate.queryForObject(
   	 *        sql, paramMap, Integer.class);
	 *
   	 *        * NamedParameterJdbcTemplate Bean Konfigürasyonu
   	 *        
   	 *        - Spring Boot NamedParameterJdbcTemplate bean'ini
   	 *          otomatik olarak tanımlar.
	 *
   	 *        @Repository
   	 *        public class OwnerDaoJdbcImpl implements OwnerDao {
   	 *        @Autowired
   	 *        private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
   	 *        ...
   	 *        }
	 *
   	 *        IN Clause'una Değişken Sayıda Parametre Geçilmesi
	 *
   	 *        String sql = "select * from t_owner where id in (:idList)";
   	 *        
   	 *        Map<String, Object> paramMap = new HashMap<>();
   	 *        paramMap.put("idList", Arrays.asList(1,2,3));
   	 *        RowMapper<Owner> rowMapper = new RowMapper<Owner>() {
   	 *            public Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
   	 *                Owner owner = new Owner();
   	 *                owner.setFirstName(rs.getString("first_name"));
   	 *                owner.setLastName(rs.getString("last_name"));
   	 *                return owner;
   	 *            }
   	 *        };
   	 *        List<Owner> result = namedParameterJdbcTemplate.query(
   	 *        sql, paramMap,rowMapper);
	 */
}
