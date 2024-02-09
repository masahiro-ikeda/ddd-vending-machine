package vendingmachne.infrastructure;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import vendingmachne.domain.Paid;
import vendingmachne.domain.PaidRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class PaidRepositoryImpl implements PaidRepository {
  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public PaidRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public Paid findByUserId(int userId) {
    String sql = "SELECT user_id, paid_amount FROM paid WHERE user_id = :user_id";
    SqlParameterSource params = new MapSqlParameterSource().addValue("user_id", userId);
    try {
      PaidDto dto = namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(PaidDto.class));
      return dto.toEntity();
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void add(Paid paid) {
    String sql = "INSERT INTO paid (user_id, paid_amount) VALUES (:user_id, :paid_amount)";
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("user_id", paid.userId())
        .addValue("paid_amount", paid.amount().value());
    namedParameterJdbcTemplate.update(sql, params);
  }

  @Override
  public void replace(Paid paid) {
    String sql = "UPDATE paid SET paid_amount = :paid_amount WHERE user_id = :user_id";
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("user_id", paid.userId())
        .addValue("paid_amount", paid.amount().value());
    namedParameterJdbcTemplate.update(sql, params);
  }

}
