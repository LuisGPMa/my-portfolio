import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CadastroFuncionarios {
	public static final int TAM = 40;
	private List<Funcionario> lstf;

	public CadastroFuncionariosOriginal() {
		lstf = new LinkedList<>();

		Random r = new Random();

		for (int i = 0; i < TAM; i++) {
			int matricula = r.nextInt(1000) + 100;
			String nome = "Fulano" + i;
			boolean insalubridade = r.nextBoolean();
			int nroDep = r.nextInt(3);
			double salBase = (r.nextDouble() * 15000) + 500;
			Funcionario f = new Funcionario(matricula, nome, salBase, nroDep, insalubridade);
			lstf.add(f);
		}
	}

	public List<Funcionario> getFuncionarios() {
		// Retorna um clone da lista
		return new LinkedList<Funcionario>(lstf);
	}

	@Override
	public String toString() {
		String str = "";
		for(Funcionario f:lstf){
			str += f.toString()+"\n";
		}
		return str;
	}

	public List<Funcionario> filterFuncionarios(Predicate<Funcionario> teste){
		List<Funcionario> novaLista = new LinkedList<Funcionario>();
		for(Funcionario f: novaLista){
			if(f.test()){
				novaLista.add(f);
			}
		}
	}

	public int getQtd(Predicate<Funcionario> teste){
		int count = 0;
		for (Funcionario f: lstf){
			if(f.test())
				count++;
		}
		return count;
	}

	public double getSoma(){

	}

	public void aplicaAumentoEmPessoas(Predicate teste, double aumentoEmPorcentagem){
		double aumento = aumentoEmPorcentagem/100.;
		for(int)
	}
}
