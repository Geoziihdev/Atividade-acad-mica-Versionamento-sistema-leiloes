import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
    String sql = "INSERT INTO produtos(nome, valor, status) VALUES(?, ?, ?)";
    conn = new conectaDAO().connectDB();

    try {
        prep = conn.prepareStatement(sql);
        prep.setString(1, produto.getNome());
        prep.setInt(2, produto.getValor());
        prep.setString(3, produto.getStatus());

        prep.execute();
        JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

    } catch (HeadlessException | SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
    } finally {
        try {
            if (prep != null) prep.close();
        } catch (SQLException e) {
           
        }
    }
           
    }
    
    public void venderProduto(int id){
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

    conn = new conectaDAO().connectDB();

    try {
        prep = conn.prepareStatement(sql);
        prep.setInt(1, id); 
        prep.execute();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
    } finally {
        try {
            if (prep != null) prep.close();
        } catch (SQLException e) {
            
        }
    }
}
    
    public ArrayList<ProdutosDTO> listarProdutos(){
    String sql = "SELECT * FROM produtos";
    conn = new conectaDAO().connectDB();

    try {
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));

            listagem.add(produto);
        }

    } catch (SQLException e) {
        System.out.println("Erro na listagem: " + e.getMessage());
        return null;
    } finally {
        try {
            if (resultset != null) resultset.close();
            if (prep != null) prep.close();
        } catch (SQLException e) {
          
        }
    }

    
    
    return listagem;
}
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
    conn = new conectaDAO().connectDB();
    ArrayList<ProdutosDTO> lista = new ArrayList<>();

    try {
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));
            lista.add(produto);
        }

    } catch (SQLException e) {
        System.out.println("Erro na listagem de produtos vendidos: " + e.getMessage());
        return null;
    } finally {
        try {
            if (resultset != null) resultset.close();
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            
        }
    }
    return lista;
}
        
    }
    
    