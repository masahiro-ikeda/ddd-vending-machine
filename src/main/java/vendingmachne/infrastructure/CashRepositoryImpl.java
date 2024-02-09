package vendingmachne.infrastructure;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import vendingmachne.domain.Currency;
import vendingmachne.domain.Cash;
import vendingmachne.domain.CashRepository;
import vendingmachne.domain.TotalCash;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CashRepositoryImpl implements CashRepository {

  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  CashRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public Cash findBy(Currency currency) {
    String sql = "SELECT * FROM cash WHERE currency = :currency";
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("currency", currency.value());
    CashDto dto = namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(CashDto.class));
    return dto.toEntity();
  }

  @Override
  public TotalCash getTotalCash() {
    String sql = "SELECT * FROM cash";
    List<CashDto> dtos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CashDto.class));
    List<Cash> cash = dtos.stream().map(CashDto::toEntity).collect(Collectors.toList());
    return new TotalCash(cash);
  }

  @Override
  public void replace(Cash cash) {
    String sql = "UPDATE cash SET currency_quantity = :currency_quantity WHERE currency = :currency";
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("currency_quantity", cash.quantity())
        .addValue("currency", cash.currency().value());
    namedParameterJdbcTemplate.update(sql, params);
  }

  @Override
  public void replace(TotalCash totalCash) {
    totalCash.cash().forEach(this::replace);
  }
}
