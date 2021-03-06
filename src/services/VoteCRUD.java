package services;

import entities.Vote;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoteCRUD {

    Connection cnxx;

    public VoteCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterVote(Vote v) {
        String req = "INSERT INTO vote (idClient,idPublication,type) VALUES (?,?,?)";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, v.getId_client());
            pst.setInt(2, v.getId_publication());
            pst.setString(3, v.getType());
            pst.executeUpdate();
            System.out.println("Vote ajouté avec succés");
        } catch (SQLException e) {
            System.err.println("Exception cause : " + e.getMessage());
        }
    }

    public void modifierVote(Vote v) {
        String req = "UPDATE vote SET type = ? WHERE idClient = ? AND idPublication = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setString(1, v.getType());
            pst.setInt(2, v.getId_client());
            pst.setInt(3, v.getId_publication());
            pst.executeUpdate();
            System.out.println("Vote modifié avec succés");
        } catch (SQLException e) {
            System.err.println("Exception cause : " + e.getMessage());
        }
    }

    public void supprimerVote(int idClient, int idPublication) {
        String req = "delete from vote where idClient = ? AND idPublication = ?";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idClient);
            pst.setInt(2, idPublication);
            pst.executeUpdate();
            System.out.println("Vote supprimé avec succés");
        } catch (SQLException e) {
            System.err.println("Exception cause : " + e.getMessage());
        }
    }

    public Vote afficherVote(int idClient, int idPublication) {
        Vote v = new Vote();
        try {
            String req = "SELECT * FROM vote WHERE idClient = ? AND idPublication = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idClient);
            pst.setInt(2, idPublication);
            ResultSet rs = pst.executeQuery();
            rs.next();
            v.setId_client(rs.getInt(2));
            v.setId_publication(rs.getInt(1));
            v.setType(rs.getString(3));
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.err.println("Exception cause : " + e.getMessage());
        }
        return v;
    }

    public List<Vote> afficherVote() {
        ArrayList listeVotes = new ArrayList();
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM vote";
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Vote v = new Vote();
                v.setId_client(rs.getInt(2));
                v.setId_publication(rs.getInt(1));
                v.setType(rs.getString(3));
                listeVotes.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Exception cause : " + e.getMessage());
        }
        return listeVotes;
    }

    public boolean verifVote(int idClient, int idPublication) {
        String req = "SELECT * FROM vote WHERE idClient = ? and idPublication = ?";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idClient);
            pst.setInt(2, idPublication);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public int calculNbrVote(int idP) {
        String reqUP = "SELECT count(*) FROM vote WHERE idPublication = ? AND type = 'UP'";
        String reqDOWN = "SELECT count(*) FROM vote WHERE idPublication = ? AND type = 'DOWN'";
        try {
            PreparedStatement pstUP = cnxx.prepareStatement(reqUP);
            PreparedStatement pstDOWN = cnxx.prepareStatement(reqDOWN);
            pstUP.setInt(1, idP);
            pstDOWN.setInt(1, idP);
            ResultSet rsUP = pstUP.executeQuery();
            ResultSet rsDOWN = pstDOWN.executeQuery();
            if (rsUP.next()&& rsDOWN.next()) {
                return rsUP.getInt(1)-rsDOWN.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return -1; //en cas d'erreur
    }

    public int voter(int idClient, int idPublication, String type) {
        Vote v = new Vote(idClient, idPublication, type);
        if (verifVote(idClient, idPublication)) { //on modifie le type en cas d'existence ou supprime en cas de similarité
            if (afficherVote(idClient, idPublication).getType().equals(type)) {
                supprimerVote(idClient, idPublication);
            } else {
                modifierVote(v);
            }
        } else { //on ajoute si le vote n'existe pas
            ajouterVote(v);
        }
        return calculNbrVote(idPublication);
    }

}
