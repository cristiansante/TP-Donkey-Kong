package juego;


import java.awt.Color;

import java.awt.Image;
import java.util.Random;

import javax.sound.sampled.Clip;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	Plataforma plataforma []; //arreglo de plataformas
	Mario mario;
	Escalera escalera [];
	Barril barril [];
	Mono mono;
	//FugaDeGas fugaDeGas[];
		
	//las imagenes que se van a usar en esta clase
	Image fondo;
	Image puerta;
	Image perdiste;
	Image ganaste;
	//Clip beat;
	Image inicio;
			
	Random generador;
	int[] aleatorio= { 100, 180, 260, 300 }; //este arreglo de enteros se va a usar para lanzar los barriles
	int cont= 0; //este lo uso para ver la velocidad del tick y usarlo para los barriles aleatorios
	int rand= 0;
	
	//estos booleanos son para determinar la victoria o derrota y asi mandar sus respectivas imagenes
	boolean terminado=true;
	boolean derrota=false;
	boolean victoria=false;
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Donkey - Grupo 2: Carrocio - Dutto - Sante - V0.01", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		fondo = Herramientas.cargarImagen("fondo.png");
		puerta = Herramientas.cargarImagen("puerta.png");
		perdiste = Herramientas.cargarImagen("derrota.png");
		ganaste = Herramientas.cargarImagen("victoria.png");
		inicio= Herramientas.cargarImagen("inicio.png");
		
		//beat= Herramientas.cargarSonido("beat.wav");
		
		plataforma=new Plataforma[6];
		escalera=new Escalera[plataforma.length-1]; //la cantidad de escalera siempre va a ser una menos que la cantidad de plataformas
		barril=new Barril[5];
		//fugaDeGas=new FugaDeGas[5];
		
		generarPlataformas(); //este lo puse dentro de un metodo pero NO SE si es prolijo
		generarEscaleras();
		//generarFugas();
		
		mono=new Mono(plataforma[plataforma.length-1].getX()/2, plataforma[plataforma.length-1].getY()-plataforma[plataforma.length-1].getAlto()-25); //creo mono y mario despues de los metodos anteriores xq uso esos datos
		mario=new Mario(entorno.ancho()*1/6, entorno.alto()-30); //asi sale en la misma posicion que la puerta
		
		generador = new Random(); //se usa para los barriles
		
		//Herramientas.loop("beat.wav");


		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	
	public void tick()
	{
		entorno.dibujarImagen(inicio, entorno.ancho()/2, entorno.alto()/2, 0, 0.8);
		
		if(entorno.sePresiono(entorno.TECLA_ESPACIO))
		{		
			terminado=false;
		}
		if(!terminado && !derrota && !victoria) 
		{
			// Procesamiento de un instante de tiempo
			cont++;
			
			dibujarEntorno(); //con esto dibujo el fondo, las plataformas y las escaleras
						
			if(cont % aleatorio[rand] == 0) //cuando cont sea divisible genera los barriles y el rand va a cambiando
			{
				generarBarriles(plataforma, barril, entorno);
				rand = generador.nextInt(aleatorio.length - 1);
			}
			
			Barril.sobrePlataforma(plataforma, barril, entorno); //este es un static pero lo armamos asi porque lo necesitamos para que el barril vaya sobre la plataforma
			
			dibujarBarriles(barril, entorno); //no lo puse con los dibujar de arriba xq los barriles se tienen que generar primero

			moverMario(); //movimientos de mario
			
			curarAlMono(); //cuando mario cura a donkey
			
			marioMuerte(); //cuando mario es impactado por un barril
		}
		else 
		{
			//aca se lanzan las imagenes posteriores a la victoria o la derrota
			if(derrota)
				entorno.dibujarImagen(perdiste, entorno.ancho()/2, entorno.alto()/2, 0, 0.8);
			if(victoria)
				entorno.dibujarImagen(ganaste, entorno.ancho()/2, entorno.alto()/2, 0, 0.8);
		}
	}

	private void generarPlataformas() 
	{
		int posicionY=entorno.alto(); //este y esta para darselo a la plataformas e ir restandoselo para q cambien de posicion
		
		for(int i=0;i<plataforma.length;i++) 
		{ 
			//aca va llenando el arreglo con posiciones distintas
			
			plataforma[0]= new Plataforma(entorno, (entorno.ancho()/2), entorno.alto()); //la plataforma cero siempre va a ser igual
			
			if( i%2==0) 
			{
				plataforma[i]= new Plataforma(entorno ,entorno.ancho()*6/10, posicionY); //usamos porcentajes para que funcione AUN modificando el ancho y alto de la pantalla
			}
			else
			{
				plataforma[i]= new Plataforma(entorno ,entorno.ancho()*4/10, posicionY);
			}	
			posicionY=posicionY-entorno.alto()/plataforma.length; //le va restando al y para cambiar la altura. se divide por la longitud de la plataforma asi que si la cambias sigue quedando bien
		}	
	}

	private void generarEscaleras() { //lamentablemente cuando modificamos el ancho y algo de pantalla las escaleras no quedan proporcionales al igual que si lo hacen las plataformas
		for(int j=0;j<plataforma.length-1;j++) 
		{
			if(j%2!=0)
					escalera [j]= new Escalera(plataforma[j].getAncho()*1/7,plataforma[j].getY()-60); //usamos porcentajes para ubicar las escaleras al igual que las plataformas pero usamos un -60 para que quede exacto 
			else 
					escalera [j]= new Escalera(plataforma[j].getAncho()*6/7,plataforma[j].getY()-60);
		}	
	}
	
	private void dibujarEntorno() 
	{
		entorno.dibujarImagen(fondo, entorno.ancho()/2, entorno.alto()/2, 0, 0.75); //dibuja el fondo de pantalla
		
		entorno.dibujarImagen(puerta, entorno.ancho()*1/6, entorno.alto()-30, 0, 0.1); //dibuja la puerta
		
		for(int i=0;i<plataforma.length;i++) //que lea el arreglo
		{ 
			plataforma[i].dibujarse(entorno); //las dibuje
		}
		for(int i=0;i<escalera.length;i++)
		{
			escalera[i].dibujarse(entorno); //las escaleras
		}
		
//		for(int i=0;i<fugaDeGas.length;i++) 
//		{
//			if(fugaDeGas[i]!=null)
//				fugaDeGas[i].dibujarse();
//		}		
		mono.dibujarArmamento(entorno, plataforma); //esto dibuja el armamento del mono, es solo algo estetico
		
		//siempre y cuando no sean nulos
		if(mario!=null) 
		{
			mario.dibujarse(entorno);
		}
		if(mono!=null) 
		{
			mono.dibujarse(entorno);
		}
	}

	private void generarBarriles(Plataforma[] plataforma, Barril[] barriles, Entorno entorno) 
	{
		for (int i=0;i<barriles.length;i++)
		{
			if (barriles[i]==null)
			{
				if(mono!=null && mario!=null)
					barriles[i]= mono.lanzar(); //aca el mono lanza un barril
					break; //un break sino lanza un barril solamente
			}	
		}
	}
			
	private void dibujarBarriles (Barril[] barril, Entorno entorno) //siempre y cuando el barril no sea null que se dibuje
	{
		//primero dibujamos el armamento del mono, es solo estetico 
		
		
		//ahora los barriles que lanza
		for (int i =0;i<barril.length;i++)
		{
			if (barril[i]!=null)
			{
				barril[i].dibujarse(entorno);
			}	
		}
	}

	private void moverMario() 
	{
		if(mario!=null) 
		{
			mario.setDibujo(1);	
			if(mario.sobrePlataforma(plataforma))                                                      //solo si esta sobre una plataforma puede hacer las siguientes cosas
			{			
				if (entorno.estaPresionada(entorno.TECLA_DERECHA) && mario.getX()<=entorno.ancho()-10) //el -10 es para q mario no se caiga por la plataforma cero
					{
					mario.moverH(+2);
					mario.setDibujo(2);
					}
				
				if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && mario.getX()>=entorno.ancho()-entorno.ancho()+10) //el +10 es para q mario no se caiga por la plataforma cero
					{
					mario.moverH(-2);
					mario.setDibujo(3);
					}
			
				if(entorno.sePresiono(entorno.TECLA_ESPACIO)) 
				    {
						mario.saltar();
						mario.setDibujo(4);
						if(entorno.estaPresionada(entorno.TECLA_DERECHA)) 
						{
							mario.moverH(+30);
							mario.setDibujo(2);
						}
						if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA))
						{
							mario.moverH(-30);
							mario.setDibujo(3);
						}
				    }

			}
			
			if(mario.sobreEscalera(escalera) && entorno.estaPresionada(entorno.TECLA_ARRIBA))
					{
					mario.moverV(-2);
					mario.setDibujo(6);
					}
			
			if(mario.sobreEscalera(escalera) && entorno.estaPresionada(entorno.TECLA_ABAJO))
					{
					mario.moverV(+2);
					mario.setDibujo(9);
					}
			
			if(mario.sobreEscalera(escalera) && entorno.estaPresionada(entorno.TECLA_DERECHA))
					{
					mario.moverH(+1);
					mario.setDibujo(10);
					}
			
			if(mario.sobreEscalera(escalera) && entorno.estaPresionada(entorno.TECLA_IZQUIERDA))
					{
					mario.moverH(-1);
					mario.setDibujo(11);
					}
			
			if(!mario.sobrePlataforma(plataforma) && !mario.sobreEscalera(escalera))
				   {
				   mario.caer();
				   mario.setDibujo(8);
				   }
			
		}
	}

	
	private void curarAlMono() 
	{
		if(mono!=null && mario!=null) 
		{
			if(mono.monoCurado(mario)) 
			{ 
				mono=null;
				terminado=true; //corta el juego
				victoria=true;
			}
		}
	}
	
	private void marioMuerte()
	{
		for(int i=0;i<barril.length;i++) 
		{
			if (mario!=null && barril[i]!=null && mono!=null && mario.lePega(barril[i]))
			{
					mario=null;
					terminado=true; //corta el juego
					derrota=true;
					barril[i]=null; //el barril que golpeo a mario se vuelve null tambien, no se va a ver porq se corta el juego con el booleano terminado pero lo hace
			}
		}
//		if ( mario.quemado(fugaDeGas))
//		{
//				mario=null;
//				terminado=true; //corta el juego
//				derrota=true;
//		}
	}
	
	private void generarFugas(){ //lo dejamos pero no lo utilizamos porque no pudimos solucionar ciertas cosas
		
//		int posicionY=entorno.alto()-50;
//		
//		for(int i=0;i<fugaDeGas.length;i++) 
//		{
//			if( i%2==0) 
//			{
//				fugaDeGas[i]= new FugaDeGas(entorno.ancho(), posicionY,entorno); 
//			}
//			else
//			{
//				fugaDeGas[i]= new FugaDeGas(0, posicionY ,entorno); 
//			}	
//			posicionY=posicionY-entorno.alto()/fugaDeGas.length;  
//		}	
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
