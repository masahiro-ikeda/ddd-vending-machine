package vendingmachne.infrastructure;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import vendingmachne.domain.Drink;
import vendingmachne.domain.DrinkRepository;

@Repository
public class DrinkRepositoryImpl implements DrinkRepository {
  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  DrinkRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public Drink findById(int drinkId) {
    String sql = "SELECT drink_id, drink_name, drink_price FROM drink WHERE drink_id = :drink_id";
    SqlParameterSource params = new MapSqlParameterSource().addValue("drink_id", drinkId);
    DrinkDto dto = namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(DrinkDto.class));
    return dto.toEntity();
  }
}
