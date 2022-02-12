package services;

import entities.Commentaire;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireCRUD {
    Connection cnxx;

    public CommentaireCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public int ajouterCommentaire(Commentaire c) {
        int id = 0;
        String req = "INSERT INTO commentaire (idPublication,description) VALUES (?,?)";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, c.getId_publication());
            pst.setString(2, c.getDescription());
            pst.executeUpdate();
            System.out.println("Commentaire ajouté avec succés");
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    return id;
    }

    public void modifierCommentaire(Commentaire c) {
        String req = "UPDATE commentaire SET idPublication=? ,description=? WHERE idCommentaire = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, c.getId_publication());
            pst.setString(2, c.getDescription());
            pst.setInt(3, c.getId_commentaire());
            pst.executeUpdate();
            System.out.println("Commentaire modifié avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void supprimerCommentaire(int idCommentaire) {
        String req = "delete from commentaire where idCommentaire = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idCommentaire);
            pst.executeUpdate();
            System.out.println("Commentaire supprimé avec succés");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Commentaire afficherCommentaire(int idC){
        Commentaire c = new Commentaire();
        try {
            String req = "SELECT * FROM commentaire WHERE idCommentaire = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1,idC);
            ResultSet rs = pst.executeQuery();
            rs.next();
            c.setId_commentaire(rs.getInt(1));
            c.setId_publication(rs.getInt(2));
            c.setDescription(rs.getString(3));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return c;
    }

    public List<Commentaire> afficherCommentaires() {
        ArrayList listeCommentaires = new ArrayList();
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM commentaire";
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Commentaire c = new Commentaire();
                c.setId_commentaire(rs.getInt(1));
                c.setId_publication(rs.getInt(2));
                c.setDescription(rs.getString(3));
                listeCommentaires.add(c);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return listeCommentaires;
    }

}
