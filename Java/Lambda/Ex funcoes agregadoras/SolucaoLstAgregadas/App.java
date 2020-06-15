public class App{
    public static void main(String args[]){
        CadastroFuncionariosOriginal cf = new CadastroFuncionariosOriginal();
   
        System.out.println("Lista original:");
        System.out.println(cf);

        System.out.println("Lista dos que tem insalubridade e dependentes:");
        cf.getInsalubridadeDependentes()
          .forEach(f->System.out.println(f));

        System.out.println("\nQuantidade de funcionarios com dependentes:" +
                           cf.quantidadeFuncionariosComDependentes());
  
        System.out.println("\nSomatorio dos salarios brutos:" +
                           cf.somatorioSalarioBruto());

        System.out.println("\nCorrige salario dos insalubres em 20%:");
        cf.aumentaSalarioInsalubres();
        cf.getFuncionarios().forEach(f->System.out.println(f.getSalarioBase()));

        System.out.println("\n"+cf.getNomeMatriculaSalarioBrutoMaiorQueBase());
    }
}