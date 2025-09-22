/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
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

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
    } finally {
        try {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            // Tratar exceções ao fechar as conexões
        }
    }
           //conn = new conectaDAO().connectDB(); 
    }
    
    public void venderProduto(int id){
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

    conn = new conectaDAO().connectDB();

    try {
        prep = conn.prepareStatement(sql);
        prep.setInt(1, id); 
        prep.execute();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
    } finally {
        try {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            // Trate exceções ao fechar as conexões
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

    } catch (Exception e) {
        System.out.println("Erro na listagem: " + e.getMessage());
        return null; // Retornar nulo em caso de erro
    } finally {
        try {
            if (resultset != null) resultset.close();
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            // Tratar exceções ao fechar as conexões
        }
    }

    return listagem;
}
        
    }
    
    