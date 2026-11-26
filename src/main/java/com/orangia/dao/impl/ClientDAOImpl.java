package com.orangia.dao.impl;

import com.orangia.database.BaseDAO;
import com.orangia.dao.interfaces.ClientDAO;
import com.orangia.exception.DatabaseException;
import com.orangia.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAOImpl extends BaseDAO implements ClientDAO {

    private Client map(ResultSet rs) throws SQLException {
        Client c = new Client();
        c.setIdClient(rs.getLong("id_client"));
        c.setCodeClient(rs.getString("code_client"));
        c.setNomClient(rs.getString("nom_client"));
        c.setTelephone(rs.getString("telephone"));
        c.setEmail(rs.getString("email"));
        c.setAdresse(rs.getString("adresse"));
        c.setVille(rs.getString("ville"));
        c.setStatut(rs.getString("statut"));
        return c;
    }

    @Override
    public Optional<Client> findById(Long id) {
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement("SELECT * FROM CLIENT WHERE id_client = ?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture client", e);
        }
    }

    @Override
    public List<Client> findAll() {
        List<Client> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement("SELECT * FROM CLIENT ORDER BY id_client");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture clients", e);
        }
        return list;
    }

    @Override
    public List<Client> findByNom(String nom) {
        List<Client> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(
                        "SELECT * FROM CLIENT WHERE LOWER(nom_client) LIKE LOWER(?) ORDER BY nom_client")) {
            ps.setString(1, "%" + nom + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur recherche clients", e);
        }
        return list;
    }

    @Override
    public List<Client> findByStatut(String statut) {
        List<Client> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(
                        "SELECT * FROM CLIENT WHERE statut = ? ORDER BY nom_client")) {
            ps.setString(1, statut);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture clients par statut", e);
        }
        return list;
    }

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO CLIENT (code_client, nom_client, telephone, email, adresse, ville, statut) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, client);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erreur creation client", e);
        }
    }

    @Override
    public void update(Client client) {
        String sql = "UPDATE CLIENT SET code_client = ?, nom_client = ?, telephone = ?, email = ?, "
                + "adresse = ?, ville = ?, statut = ? WHERE id_client = ?";
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, client);
            ps.setLong(8, client.getIdClient());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erreur modification client", e);
        }
    }

    @Override
    public void deactivate(Long idClient) {
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(
                        "UPDATE CLIENT SET statut = 'INACTIF' WHERE id_client = ?")) {
            ps.setLong(1, idClient);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erreur desactivation client", e);
        }
    }

    private void fill(PreparedStatement ps, Client c) throws SQLException {
        ps.setString(1, c.getCodeClient());
        ps.setString(2, c.getNomClient());
        ps.setString(3, c.getTelephone());
        ps.setString(4, c.getEmail());
        ps.setString(5, c.getAdresse());
        ps.setString(6, c.getVille());
        ps.setString(7, c.getStatut() == null ? "ACTIF" : c.getStatut());
    }
}
