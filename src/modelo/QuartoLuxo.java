package modelo;

/**
 * Quarto de luxo é um Quarto (HERANÇA) com um valor adicional na diária.
 */
public class QuartoLuxo extends Quarto {

    private double adicionalLuxo;

    public QuartoLuxo(int numero, int capacidade, double valorDiaria, double adicionalLuxo) {
        super(numero, capacidade, valorDiaria);
        this.adicionalLuxo = adicionalLuxo;
    }

    public double getAdicionalLuxo() {
        return adicionalLuxo;
    }

    public void setAdicionalLuxo(double adicionalLuxo) {
        this.adicionalLuxo = adicionalLuxo;
    }

    // Sobrescreve: a diária do luxo é a diária normal + o adicional
    @Override
    public double getValorDiaria() {
        return super.getValorDiaria() + adicionalLuxo;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        QuartoLuxo outro = (QuartoLuxo) obj;
        return adicionalLuxo == outro.adicionalLuxo;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + " | LUXO (adicional: R$ "
                + String.format("%.2f", adicionalLuxo) + " já incluso)";
    }
}
