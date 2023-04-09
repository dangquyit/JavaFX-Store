package utc2.itk62.sneaker.repositories;

import utc2.itk62.sneaker.common.Paging;
import utc2.itk62.sneaker.connection.ConnectionUtil;
import utc2.itk62.sneaker.models.Category;
import utc2.itk62.sneaker.models.Product;
import utc2.itk62.sneaker.models.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepo {
    public ProductRepo() {
    }

    public List<Product> getAllProducts(Paging paging) {
        paging.checkPageLimit();
        List<Product> productList = new ArrayList<Product>();
        String query = "SELECT * FROM product WHERE status = 1 LIMIT ? OFFSET ? ";
        try {
            PreparedStatement ptmt = ConnectionUtil.getConnection().prepareStatement(query);
            ptmt.setInt(1,paging.getLimit());
            ptmt.setInt(2,paging.getOffset());
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setIdSupplier(rs.getInt("id_supplier"));
                product.setIdCategory(rs.getInt("id_category"));
                product.setName(rs.getString("name"));
                product.setSize(rs.getDouble("size"));
                product.setDescription(rs.getString("desc"));
                product.setPrice(rs.getDouble("price"));
                product.setAvatar(rs.getString("avatar"));
                product.setStatus(rs.getInt("status"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.closeConnection();
        }
    }

    public int createProduct(Product product) {
        String query = "INSERT INTO product(id_supplier, id_category, name, size, desc, price, avatar" +
                " VALUES(?, ?, ?, ?, ?, ?, ?)";
        int result = 0;
        Connection conn = ConnectionUtil.getConnection();

        try{
            conn.setAutoCommit(false);
            PreparedStatement ptmt = conn.prepareStatement(query);
            ptmt.setInt(1, product.getIdSupplier());
            ptmt.setInt(2, product.getIdCategory());
            ptmt.setString(3, product.getName());
            ptmt.setDouble(4, product.getSize());
            ptmt.setString(5, product.getDescription());
            ptmt.setDouble(6, product.getPrice());
            ptmt.setString(7, product.getAvatar());
            result = ptmt.executeUpdate();
            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            ConnectionUtil.closeConnection();
        }
        return result;
    }

    public int updateProduct(Product product){
        String query = "UPDATE product SET " +
                "id_supplier = ?, " +
                "id_category = ?, " +
                "name = ?, " +
                "size = ?, " +
                "desc = ?, " +
                "price = ?, " +
                "avatar = ?, " +
                "WHERE id = ?";
        int result = 0;
        Connection conn = ConnectionUtil.getConnection();
        try{
            conn.setAutoCommit(false);
            PreparedStatement ptmt = conn.prepareStatement(query);
            ptmt.setInt(1, product.getIdSupplier());
            ptmt.setInt(2, product.getIdCategory());
            ptmt.setString(3, product.getName());
            ptmt.setDouble(4, product.getSize());
            ptmt.setString(5, product.getDescription());
            ptmt.setDouble(6, product.getPrice());
            ptmt.setString(7, product.getAvatar());
            ptmt.setInt(8, product.getId());
            result = ptmt.executeUpdate();
            conn.commit();
        }catch (SQLException e){
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        } finally{
            ConnectionUtil.closeConnection();
        }
        return result;
    }

    public int deleteProduct(int productId) {
        String query = "UPDATE product SET status = 0 WHERE id = ?";
        int result = 0;
        Connection conn = ConnectionUtil.getConnection();

        try{
            conn.setAutoCommit(false);
            PreparedStatement ptmt = conn.prepareStatement(query);
            ptmt.setInt(1, productId);
            result = ptmt.executeUpdate();
            conn.commit();
        }catch (SQLException e){
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        } finally {
            ConnectionUtil.closeConnection();
        }
        return result;
    }
}