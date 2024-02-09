package vendingmachne.infrastructure;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import vendingmachne.domain.Stock;
import vendingmachne.domain.StockRepository;

@Repository
public class StockRepositoryImpl implements StockRepository {
  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  StockRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public Stock findById(int drinkId) {
    String sql = "SELECT drink_id, drink_quantity FROM stock WHERE drink_id = :drink_id";
    SqlParameterSource params = new MapSqlParameterSource().addValue("drink_id", drinkId);
    StockDto dto = namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(StockDto.class));
    return dto.toEntity();
  }

  @Override
  public void replace(Stock stock) {
    String sql = "UPDATE stock SET drink_quantity = :drink_quantity WHERE drink_id = :drink_id";
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("drink_id", stock.drinkId())
        .addValue("drink_quantity", stock.drinkQuantity());
    namedParameterJdbcTemplate.update(sql, params);
  }


}
