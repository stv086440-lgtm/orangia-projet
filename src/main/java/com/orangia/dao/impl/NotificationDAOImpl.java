package com.orangia.dao.impl;

import com.orangia.database.BaseDAO;
import com.orangia.dao.interfaces.NotificationDAO;
import com.orangia.exception.DatabaseException;
import com.orangia.model.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl extends BaseDAO implements NotificationDAO {

    private Notification map(ResultSet rs) throws SQLException {
        Notification n = new Notification();
        n.setIdNotification(rs.getLong("id_notification"));
        n.setIdUtilisateur(rs.getLong("id_utilisateur"));
        n.setTitre(rs.getString("titre"));
        n.setMessage(rs.getString("message"));
        n.setTypeNotification(rs.getString("type_notification"));
        n.setLu(rs.getBoolean("lu"));
        Timestamp dc = rs.getTimestamp("date_creation");
        if (dc != null) {
            n.setDateCreation(dc.toInstant().atOffset(ZoneOffset.UTC));
        }
        return n;
    }

    @Override
    public Notification findById(Long idNotification) {
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement("SELECT * FROM NOTIFICATION WHERE id_notification = ?")) {
            ps.setLong(1, idNotification);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture notification", e);
        }
    }

    @Override
    public List<Notification> findAll() {
        List<Notification> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(
                        "SELECT * FROM NOTIFICATION ORDER BY date_creation DESC");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture notifications", e);
        }
        return list;
    }

    @Override
    public List<Notification> findByDestinataireId(Long idDestinataire) {
        List<Notification> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(
                        "SELECT * FROM NOTIFICATION WHERE id_utilisateur = ? "
                                + "ORDER BY date_creation DESC")) {
            ps.setLong(1, idDestinataire);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture notifications du destinataire", e);
        }
        return list;
    }

    @Override
    public List<Notification> findUnreadByDestinataireId(Long idDestinataire) {
        List<Notification> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(
                        "SELECT * FROM NOTIFICATION WHERE id_utilisateur = ? AND lu = FALSE "
                                + "ORDER BY date_creation DESC")) {
            ps.setLong(1, idDestinataire);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture notifications non lues", e);
        }
        return list;
    }

    @Override
    public void save(Notification notification) {
        String sql = "INSERT INTO NOTIFICATION (id_utilisateur, titre, message, type_notification, lu) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            fill(ps, notification);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    notification.setIdNotification(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur creation notification", e);
        }
    }

    @Override
    public void update(Notification notification) {
        String sql = "UPDATE NOTIFICATION SET id_utilisateur = ?, titre = ?, message = ?, "
                + "type_notification = ?, lu = ? WHERE id_notification = ?";
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, notification);
            ps.setLong(6, notification.getIdNotification());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erreur modification notification", e);
        }
    }

    @Override
    public void markAsRead(Long idNotification) {
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(
                        "UPDATE NOTIFICATION SET lu = TRUE WHERE id_notification = ?")) {
            ps.setLong(1, idNotification);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erreur marquage notification lue", e);
        }
    }

    private void fill(PreparedStatement ps, Notification n) throws SQLException {
        ps.setLong(1, n.getIdUtilisateur());
        ps.setString(2, n.getTitre());
        ps.setString(3, n.getMessage());
        ps.setString(4, n.getTypeNotification());
        ps.setBoolean(5, n.isLu());
    }
}
