
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DaoProfessor {

    public void inserir(Professor professor) {
        String sql = "INSERT INTO professor (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEmail());
            stmt.setString(3, professor.getSenha());
            stmt.executeUpdate();

            System.out.println("Professor inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir professor: " + e.getMessage());
        }
    }

    public List<Professor> listaProfessores() {
        List<Professor> lista = new ArrayList();
        String sql = "SELECT nome,email,senha FROM Professor";
        try (Connection conn = Conexao.conectar(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery();) {
            while (rs.next()) {
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                Professor professor = new Professor(nome, email, senha);

                lista.add(professor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao lista o professor: " + e.getMessage());
        }
        return lista;
    }

    public void excluirProfessor() {
        String[] opcoes = {"nome", "E-email"};
        int escolha = JOptionPane.showOptionDialog(null, "Deseja excluir o professor por nome ou email?", "Excluir Professor", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

        String valor = "";
        String campo = "";
        String sql = "";

        if (escolha == 0) {
            valor = JOptionPane.showInputDialog("Digite o nome do professor a ser excluido:");
            campo = "nome";
            sql = "DELETE FROM Professor WHERE nome = ?";
        } else if (escolha == 1) {
            valor = JOptionPane.showInputDialog("Digite o email do professor a ser excluido:");
            campo = "email";
            sql = "DELETE FROM Professor WHERE email = ?";

        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada");
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir o professor com " + campo + ": " + valor + "? ", "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Exclusão Cancelada!!");
            return;
        }

        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, valor);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Professor excluido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum Professor encontrado com esse " + campo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir o professor " + e.getMessage());
        }
    }

    public void alterarProfessor() {
        String[] opcoes = {"nome", "E-email", "Senha"};
        int escolha = JOptionPane.showOptionDialog(null,
                "Deseja alterar o nome, email ou senha?",
                "Alterar Professor",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);

        String valor = "";
        String campo = "";
        String sql = "";
        String id = "";

        if (escolha == 0) {
            id = JOptionPane.showInputDialog("Digite o ID do professor a ser alterado");
            valor = JOptionPane.showInputDialog("Digite o novo nome do professor:");
            sql = "UPDATE Professor SET nome = ? WHERE idprofessor = ?";
            campo = "nome";
        } else if (escolha == 1) {
            id = JOptionPane.showInputDialog("Digite o ID do professor a ser alterado");
            valor = JOptionPane.showInputDialog("Digite o novo email do professor:");
            sql = "UPDATE Professor SET email = ? WHERE idprofessor = ?";
            campo = "email";
        } else if (escolha == 2) {
            id = JOptionPane.showInputDialog("Digite o ID do professor a ser alterado");
            valor = JOptionPane.showInputDialog("Digite a nova senha do professor:");
            sql = "UPDATE Professor SET senha = ? WHERE idprofessor = ?";
            campo = "senha";
        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja alterar " + campo + " para " + valor + "?",
                "Confirmar Alteração", JOptionPane.YES_NO_OPTION);

        if (confirmacao != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Alteração cancelada.");
            return;
        }

        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, valor);
            stmt.setString(2, id); 
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Professor alterado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum professor encontrado com esse " + campo + ".");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar o professor: " + e.getMessage());
        }
    }

}
