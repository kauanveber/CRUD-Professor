
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        Connection conexao = Conexao.conectar();
        DaoProfessor newProf = new DaoProfessor();
        Professor p1 = new Professor("Kauan", "kauanveber0@gmail.com", "12092007Kauan!");
        if (conexao != null) {
            System.out.println("Conex�o realizada com sucesso!");
        } else {
            System.out.println("Falha na conex�o.");
        }
        
        
        
        
        
    }
}
