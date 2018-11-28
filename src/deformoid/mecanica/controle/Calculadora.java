package deformoid.mecanica.controle;

import java.util.ArrayList;
import java.util.List;

import deformoid.mecanica.entidades.Ponto2D;

import android.util.Log;

public class Calculadora {

	static ArrayList<Ponto2D> listaDePontos;
	final static double h = .1; // precisão
	final static double CONSTANTES=0;


	/**
	 * * Efetua o calculo das deformações causadas na viga a partir da inserção de
	 * um objeto na sua extremidade
	 * 
	 * Formula utilizada: Equação da Linha Elástica
	 * y=(P/6Ei)(x^3-3Lx^2)
	 * 
	 * 
	 * @param L = Tamanho da viga [m]
	 * @param P = Peso do objeto sobre a viga [N]
	 * @param E = Elasticidade do Material 
	 * @param I = Momento de Inércia
	 * @return Retorna lista de Pontos da Viga Deformada
	 */
	public static List<Ponto2D> calculaDeformacao(double L,double P,double E,double I) {
		
		Log.v("vigas","Inicio do Calculo da Deformação: Tamanho Viga: "+L+" Peso: "+P+" Elasticidade: "+E+" Momento: "+I);

		// inicializa lista de pontos
		listaDePontos = new ArrayList<Ponto2D>();
		double dy0,dy1,dy2,dy3;
		double dv0,dv1,dv2,dv3;
		double x=0.0; // Condição de contorno
		double y=0.0;
		double yLinha=0.0;
		listaDePontos.add(new Ponto2D(x, y));
		for (double i = h; i < L; i += h) {
            dy0 = h*yLinha;
            dv0 = h*f(x,L,P,E,I);
            dy1 = h*(yLinha+(dv0/2));	
            dv1 = h*f(x+(h/2),L,P,E,I);
            dy2 = h*(yLinha+(dv1/2));
            dv2 = h*f(x+(h/2),L,P,E,I);
            dy3 = h*(yLinha+dv2);;
            dv3 = h*f(x+h,L,P,E,I);
            y=y+(1/6.0)*(dy0+2*dy1+2*dy2+dy3);
            yLinha=yLinha+(1/6.0)*(dv0+2*dv1+2*dv2+dv3);
			x=i;
			listaDePontos.add(new Ponto2D(x, y));
			Log.v("Vigas","Pontos: X:"+x+" Y: "+y);

		}

		return listaDePontos;
	}
	
	public static double f(double x, double L,double P,double E,double I) 
	{
		return (-P/(2.0*E*I))*((x*x)-(2.0*x*L)+(L*L));
	}

	/**
	 * Calcula momento de inercia
	 * @param seccao 
	 * @param tamanhoYViga 
	 * @param tamanhoXViga 
	 * @return momento de inércia
	 */


	public static double calculaMomentoInercia(double larViga, double altViga, String idSec){
		
		Log.v("vigas","Calculo do Momento do Inercia: Largura: "+larViga+" Altura: "+altViga);
		char inicial = idSec.toLowerCase().charAt(0);
		switch((int)inicial){
		case ((int) 'r'):
			Log.v("vigas","utilizou retângulo. Momento: "+(larViga*altViga*altViga*altViga)/12);
			return (larViga*altViga*altViga*altViga)/12;
		case ((int) 't'):
			return (larViga*(Math.pow(altViga, 3)))/12;
		case ((int) 'c'):
			return (Math.pow(altViga, 4)*Math.PI)/4;
		case ((int) 's'):
			return (Math.pow(altViga, 4)*Math.PI)/8;
		case ((int) 'q'):
			return (Math.pow(altViga, 4)*Math.PI)/16;
		case ((int) 'e'):
			return (Math.PI*(larViga)*(Math.pow(altViga, 3)))/4;
		default:
			return 0;

		}


	}

	/**
	 * * Efetua o calculo da flecha máxima que a viga suporta baseado no peso do objeto (P),
	 * comprimento da viga(L), elasticidade do material (E) e no momento de inércia
	 * 
	 * Formula utilizada: Flecha máxima
	 * (-P*(L^3))/(3*E*I)
	 * 
	 * 
	 * @param L = Tamanho da viga [m]
	 * @param P = Peso do objeto sobre a viga [N]
	 * @param E = Elasticidade do Material 
	 * @param I = Momento de Inércia
	 * @return flecha = a flecha máxima que a viga suporta
	 */
	public static double calculaFlechaMaxima(double L,double P,double E,double I){
		double flecha;

		flecha = -((P*Math.pow(L, 3))/(3*E*I));

		return flecha;
	}

}
