package br.com.dio.persistence.dao;

import br.com.dio.dto.CardDetailsDTO;
import br.com.dio.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CardDAO {

    private Connection connection;

    public CardEntity insert(final CardEntity entity) throws SQLException {
        var sql = "INSERT INTO cards (title, description, board_column_id) values (?, ?, ?);";
        try (var statement = connection.prepareStatement(sql)) {
            var i = 1;
            statement.setString(i++, entity.getTitle());
            statement.setString(i++, entity.getDescription());
            statement.setLong(i, entity.getBoardColumn().getId());
            statement.executeUpdate();
        }
        return entity;
    }

    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {
        // método já existente
        return Optional.empty(); // simplificado
    }

    public List<CardDetailsDTO> findByPriorityOrTags(final String priority, final List<String> tags) throws SQLException {
        var sql = new StringBuilder("SELECT * FROM cards WHERE 1=1 ");
        if (priority != null && !priority.isEmpty()) {
            sql.append("AND priority = ? ");
        }
        if (tags != null && !tags.isEmpty()) {
            for (int i = 0; i < tags.size(); i++) {
                sql.append("OR tags LIKE ? ");
            }
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int index = 1;
            if (priority != null && !priority.isEmpty()) {
                stmt.setString(index++, priority);
            }
            if (tags != null && !tags.isEmpty()) {
                for (String tag : tags) {
                    stmt.setString(index++, "%" + tag + "%");
                }
            }

            ResultSet rs = stmt.executeQuery();
            List<CardDetailsDTO> result = new ArrayList<>();
            while (rs.next()) {
                CardDetailsDTO dto = new CardDetailsDTO(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("blocked"),
                        rs.getObject("created_at", OffsetDateTime.class),
                        rs.getString("tags"),
                        rs.getInt("priority"),
                        rs.getLong("board_column_id"),
                        rs.getString("blocked_by")
                );
                result.add(dto);
            }
            return result;
        }
    }

    // ✅ Novo método para corrigir erro de compilação
    public void moveToColumn(Long columnId, Long cardId) throws SQLException {
        var sql = "UPDATE CARDS SET board_column_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, columnId);
            stmt.setLong(2, cardId);
            stmt.executeUpdate();
        }
    }
}
