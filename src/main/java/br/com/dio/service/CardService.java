package br.com.dio.service;

import br.com.dio.dto.BoardColumnInfoDTO;
import br.com.dio.exception.CardBlockedException;
import br.com.dio.exception.CardFinishedException;
import br.com.dio.exception.EntityNotFoundException;
import br.com.dio.persistence.dao.BlockDAO;
import br.com.dio.persistence.dao.CardDAO;
import br.com.dio.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static br.com.dio.persistence.entity.BoardColumnKindEnum.CANCEL;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.FINAL;

@AllArgsConstructor
public class CardService {

    private final Connection connection;

    public CardEntity create(final CardEntity entity) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void moveToNextColumn(final Long cardId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            var dto = dao.findById(cardId).orElseThrow(
                    () -> new EntityNotFoundException("Card com ID %s não encontrado.".formatted(cardId))
            );

            if (dto.blocked()) {
                throw new CardBlockedException("Card %s está bloqueado. Desbloqueie antes de mover.".formatted(cardId));
            }

            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Card pertence a outro board."));

            if (currentColumn.kind().equals(FINAL)) {
                throw new CardFinishedException("Card já está finalizado.");
            }

            var nextColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.order() == currentColumn.order() + 1)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Não há próxima coluna disponível."));

            dao.moveToColumn(nextColumn.id(), cardId);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void cancel(final Long cardId, final Long cancelColumnId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            var dto = dao.findById(cardId).orElseThrow(
                    () -> new EntityNotFoundException("Card com ID %s não encontrado.".formatted(cardId))
            );

            if (dto.blocked()) {
                throw new CardBlockedException("Card %s está bloqueado. Desbloqueie antes de cancelar.".formatted(cardId));
            }

            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Card pertence a outro board."));

            if (currentColumn.kind().equals(FINAL)) {
                throw new CardFinishedException("Card já está finalizado.");
            }

            dao.moveToColumn(cancelColumnId, cardId);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void block(final Long id, final String reason, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            var dto = dao.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Card com ID %s não encontrado.".formatted(id))
            );

            if (dto.blocked()) {
                throw new CardBlockedException("Card %s já está bloqueado.".formatted(id));
            }

            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Card pertence a outro board."));

            if (currentColumn.kind().equals(FINAL) || currentColumn.kind().equals(CANCEL)) {
                throw new IllegalStateException("Card está em uma coluna do tipo %s e não pode ser bloqueado.".formatted(currentColumn.kind()));
            }

            var blockDAO = new BlockDAO(connection);
            blockDAO.block(reason, id);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void unblock(final Long id, final String reason) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            var dto = dao.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Card com ID %s não encontrado.".formatted(id))
            );

            if (!dto.blocked()) {
                throw new CardBlockedException("Card %s não está bloqueado.".formatted(id));
            }

            var blockDAO = new BlockDAO(connection);
            blockDAO.unblock(reason, id);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
}
