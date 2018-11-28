package deformoid.mecanica.graficos;

import java.util.List;
import deformoid.mecanica.controle.Calculadora;
import deformoid.mecanica.entidades.Ponto2D;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.*;


public class DrawView extends View {
	final static double h=0.1;	//Constante da precisão.
	int width;
	int height;
    Paint paint = new Paint();
    Path deformacao = new Path();
    double comprimentoViga;
    double larguraViga;
    double alturaViga;
    double peso;
    double GPA;
    String seccao;
    double momentoInercia;
    List<Ponto2D> pontos;
    
    double menorX, maiorX, menorY, maiorY;
	double diferencaX, diferencaY;
	float largEscala, altEscala, escalaUsada;
    

	@SuppressWarnings("deprecation")
	public DrawView(Context context, double comprimentoViga, double larguraViga, double alturaViga, double peso, double GPA, String seccao) {
        super(context);
        this.comprimentoViga=comprimentoViga;
        this.larguraViga=larguraViga;
        this.alturaViga=alturaViga;
        this.peso=peso;
        this.GPA=GPA;
        this.seccao=seccao;
        
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
    }

    @Override
    public void onDraw(final Canvas canvas) {
    	
    	//ANTES DE TUDO CALCULA TODAS AS VARIAVEIS NECESSARIAS PRA COLOCAR TUDO NA TELA
    	
    	momentoInercia=Calculadora.calculaMomentoInercia(larguraViga, alturaViga, seccao);
    	pontos=Calculadora.calculaDeformacao(larguraViga, peso, GPA, momentoInercia);
    	
    	Log.v("DRAW VIEW", "CALCULOU PONTOS");
    	
    	//paint.setColor(Color.BLACK);
        //paint.setStrokeWidth(3);
        
        /*
         * Como Y positivo é desenhado PARA BAIXO, a escala deve ser calculada para desenhar não somente a viga ocupando toda
         * a horizontal da tela, mas também que ela comece da supoerior esquerda e vá indo pra inferio direita. 
         */
        menorX=pontos.get(0).getX();
        maiorX=pontos.get(pontos.size()-1).getX();
        menorY=pontos.get(0).getY();
        maiorY=pontos.get(pontos.size()-1).getY();
        
        diferencaX = maiorX - menorX;
        if (diferencaX < 0) diferencaX = diferencaX * -1;
        diferencaY = maiorY - menorY;
        if (diferencaY < 0) diferencaY = diferencaY * -1;
        
        largEscala = (float) (width/diferencaX);
        altEscala = largEscala;//(float) (height/diferencaY);
        
        //largEscala = 10;
        //altEscala = 10;
        
        ////Se baseia na menor dimensão da tela para escalar a porporção da viga deformada
        //if (largEscala <= altEscala) escalaUsada = largEscala;
        //else escalaUsada = altEscala;
        
        ////Se ainda assim o tamanho da viga for MENOR que a menor dimensão da tela, então não precisa-se de escala.
        //if(largEscala > 10) largEscala = 10;
        //if(altEscala > 10) altEscala = 10;
        
    	paint.setColor(Color.GRAY);
    	paint.setStyle(Style.FILL);
    	
    	/*
    	//RODA AQUI A ANIMAÇÃO DA VIGA SENDO DEFORMADA SEM DÓ
    	Thread animacao =  new Thread(){
    		int quadroAtual = 0;	//Contador genérico com nome bonito.
        	int qtdQuadros = 100;	//Use isso para determinar quantos quadros devem ser exibidos da viga sendo deformada. 0 = instantâneo.
			public void run(){
				do{
					try{
						desenhaDeformacao(quadroAtual, qtdQuadros, canvas);
						sleep(500);	//Tempo de espera entre cada quadro a ser exibido.
						deformacao.reset(); // only needed when reusing this path for a new build
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally{
						quadroAtual++;
					}
				}while(quadroAtual < qtdQuadros);
			}
		};
		animacao.start();
		*/
    	
		
		for(int c=0;c<pontos.size();c++){
			// used for first point
			if (c == 0)
				deformacao.moveTo((float) ((width*0.1) + (pontos.get(c).getX()*largEscala)*0.8),
					(float) ((height*0.1) + (pontos.get(c).getY()*-1)*0.8));
			if (c > 0)
				deformacao.lineTo((float) ((width*0.1) + (pontos.get(c).getX()*largEscala)*0.8),
					(float) ((height*0.1) + (pontos.get(c).getY()*largEscala*-1)*0.8));
			//if (c == (pontos.size()-1)) 
		}
	
		//AGORA DESENHA A PARTE INFERIOR DA VIGA, LENDO OS PONTOS EM ORDEM DECRESCENTE
		for(int c=pontos.size()-1;c>=0;c--){
			// used for first point
			if (c == (pontos.size()-1))
				deformacao.lineTo((float) ((width*0.1) + (pontos.get(c).getX()*largEscala)*0.8),
					(float) ((height*0.1) + (pontos.get(c).getY()*largEscala*-1)*0.8 + alturaViga*altEscala*0.8));
			if (c < (pontos.size()-1))
				deformacao.lineTo((float) ((width*0.1) + (pontos.get(c).getX()*largEscala)*0.8),
					(float) ((height*0.1) + (pontos.get(c).getY()*largEscala*-1)*0.8 + alturaViga*altEscala*0.8)); 
			//if (c < 0) 
		}
	
		//AGORA FECHA O POLÍGONO, INDO PRO PRIMEIRO PONTO DESENHADO
		//there is a setLastPoint action but i found it not to work as expected
		deformacao.moveTo((float) ((width*0.1) + (pontos.get(0).getX()*largEscala)*0.8),
				(float) ((height*0.1) + (pontos.get(0).getY()*-1)*0.8));
	
		//DESENHA A VIGA, FINALMENTE
		canvas.drawPath(deformacao, paint);
    	
    }
    
    /**
     * Desenha o estado atual da viga sendo deformada, baseado no número de instâncias a desenhar e a instância atual.
     * Basicamente, varia a posição Y de cada ponto a ser desenhado entre a viga inicial e a viga deformada.
     * A altura da viga se mantém constante entre cada instância.
     * @param quadroAtual A instância atual a ser desenhada
     * @param qtdQuadros Número de instâncias a desenhar
     * @param canvas Canvas a ser utilizado
     */
    private void desenhaDeformacao(int quadroAtual, int qtdQuadros, Canvas canvas){
    	float instancia = 1;
    	if (qtdQuadros > 0) instancia = quadroAtual/qtdQuadros;
    	
    	//while (instancia < 1){
    		//PRIMEIRO DESENHA A PARTE SUPERIOR DA VIGA, LENDO OS PONTOS EM ORDEM CRESCENTE
    	
    	/*
    		for(int c=0;c<pontos.size();c++){
    			// used for first point
    			if (c == 0)
    				deformacao.moveTo((float) ((width*0.1) + (pontos.get(c).getX()*largEscala)*0.8),
    					(float) ((height*0.1) + (pontos.get(c).getY()*-1)*0.8));
    			if (c > 0)
    				deformacao.lineTo((float) ((width*0.1) + (pontos.get(c).getX()*largEscala)*0.8),
    					(float) ((height*0.1) + (pontos.get(c).getY()*largEscala*-1)*0.8));
    			//if (c == (pontos.size()-1)) 
    		}
    	
    		//AGORA DESENHA A PARTE INFERIOR DA VIGA, LENDO OS PONTOS EM ORDEM DECRESCENTE
    		for(int c=pontos.size()-1;c>=0;c--){
    			// used for first point
    			if (c == (pontos.size()-1))
    				deformacao.lineTo((float) ((width*0.1) + (pontos.get(c).getX()*largEscala)*0.8),
    					(float) ((height*0.1) + (pontos.get(c).getY()*largEscala*-1)*0.8 + alturaViga*0.8));
    			if (c < (pontos.size()-1))
    				deformacao.lineTo((float) ((width*0.1) + (pontos.get(c).getX()*largEscala)*0.8),
    					(float) ((height*0.1) + (pontos.get(c).getY()*largEscala*-1)*0.8* + alturaViga*0.8)); 
    			//if (c < 0) 
    		}
    	
    		//AGORA FECHA O POLÍGONO, INDO PRO PRIMEIRO PONTO DESENHADO
    		//there is a setLastPoint action but i found it not to work as expected
    		deformacao.moveTo((float) ((width*0.1) + (pontos.get(0).getX()*largEscala)*0.8),
    				(float) ((height*0.1) + (pontos.get(0).getY()*-1)*0.8));
    	
    		//DESENHA A VIGA, FINALMENTE
    		canvas.drawPath(deformacao, paint);
    		
    		*/
    	
    	//}
    }
}
	
