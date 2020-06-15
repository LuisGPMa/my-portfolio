import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CadastroFuncionariosOriginal {
	public static final int TAM = 5;
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

	// 1a
	public List<Funcionario> getInsalubridadeDependentes(){
		return lstf
			   .stream()
			   .filter(f->f.getInsalubridade())
			   .filter(f->(f.getNroDependentes() > 0))
			   .collect(Collectors.toList());
	} 

	// 1b
	public long quantidadeFuncionariosComDependentes(){
		return lstf
		 	   .stream()
			   .filter(f->(f.getNroDependentes() > 0))
			   .count();
	}

	//1c
	public double somatorioSalarioBruto(){
		return lstf
			   .stream()
			   .mapToDouble(f->f.getSalarioBruto())
			   .sum();
	}

	//1d
	public void aumentaSalarioInsalubres(){
		lstf
		.stream()
		.filter(f->f.getInsalubridade())
		.forEach(f->f.aumentaSalBase(1.2));
	}

	//1e
	public List<String> getNomeMatriculaSalarioBrutoMaiorQueBase(){
		return lstf
		       .stream()
		       .filter(f->(f.getSalarioBruto() > f.getSalarioBase()*1.1))
		       .map(f->f.getNome()+", "+f.getMatricula())
		       .collect(Collectors.toList());
	}

	@Override
	public String toString() {
		String str = "";
		for(Funcionario f:lstf){
			str += f.toString()+"\n";
		}
		return str;
	}
}
