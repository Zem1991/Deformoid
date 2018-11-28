package deformoid.mecanica.graficos;

import java.util.List;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.annotations.XYTextAnnotation;
import org.afree.chart.axis.NumberAxis;
import org.afree.chart.plot.PlotOrientation;
import org.afree.chart.plot.XYPlot;
import org.afree.data.xy.XYDataset;
import org.afree.data.xy.XYSeries;
import org.afree.data.xy.XYSeriesCollection;
import org.afree.graphics.geom.Font;
import org.afree.ui.TextAnchor;

import deformoid.mecanica.controle.Calculadora;
import deformoid.mecanica.entidades.Ponto2D;


import android.content.Context;
import android.graphics.Typeface;

public class GraficoView extends ChartView {
	
	
	static double tamanhoXViga;
	static double tamanhoYViga;
	static double comprimetoViga;
	static double peso;
	static double GPA;
	static String seccao;
	

	/**
	 * Construtor
	 * 
	 * @param context
	 * @param larguraViga 
	 * @param comprimentoViga 
	 * @param seccao 
	 * @param f 
	 * @param e 
	 * @param comprimentoViga 
	 */
	public GraficoView(Context context,  double comprimentoViga, double larguraViga, double alturaViga, double pesoObjeto, double GPA, String seccao) {
		super(context);

		
		tamanhoXViga=larguraViga;
		tamanhoYViga=alturaViga;
		peso=pesoObjeto;
		this.GPA=GPA;
		this.seccao=seccao;
		this.comprimetoViga=comprimentoViga;	
		
		
		final AFreeChart grafico = criaGrafico();

		setChart(grafico);
	}

	/**
	 * Cria os dados para plotar no grafico
	 * 
	 * @return Conjunto de dados.
	 */
	private static XYSeriesCollection criaDados() {

		XYSeries xySerie = new XYSeries("xyS1", true, false); // serie XY do
																// gráfico

		// calcula momento de inercia
		double momento = Calculadora.calculaMomentoInercia(tamanhoXViga,tamanhoYViga,seccao);

		// calcula deformacao
		List<Ponto2D> listaDePontosPosDeformacao = Calculadora
				.calculaDeformacao(comprimetoViga, peso, GPA, momento);

		// varre pontos e adiciona no Conjunto de Pontos a serem plotados
		for (Ponto2D ponto : listaDePontosPosDeformacao) {
			xySerie.add(ponto.getX(), ponto.getY());
		}

		XYSeries xyS2 = new XYSeries("xyS2", true, false);

		
		//plotar viga sem deformacao
		for (int i = 0; i <= comprimetoViga; i++) {

			xyS2.add(i, 0);

		}

		

		XYSeriesCollection xySC = new XYSeriesCollection();
		xySC.addSeries(xySerie);
		xySC.addSeries(xyS2);
	

		return xySC;
	}

	/**
	 * Cria o gráfico
	 * 
	 * @param conjunto
	 *            de dados para plotagem.
	 * @return Grafico propriamente dito.
	 */
	private static AFreeChart criaGrafico() {
		XYDataset dataset = criaDados();
		
		
		AFreeChart grafico = ChartFactory.createXYLineChart(
				"Gráfico de Deformação da Viga", "X [m]", "Y [m]", dataset,
				PlotOrientation.VERTICAL, false, true, false);
		XYPlot plot = (XYPlot) grafico.getPlot();
		NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		domainAxis.setUpperMargin(0.2);

		// adiciona texto na linha
		XYTextAnnotation annotation = null;
		Font font = new Font("SansSerif", Typeface.NORMAL, 12);

		annotation = new XYTextAnnotation("Viga sem peso", 96, 52);
		annotation.setFont(font);
		annotation.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
		plot.addAnnotation(annotation);



		return grafico;
	}

}
