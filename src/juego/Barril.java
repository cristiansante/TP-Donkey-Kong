package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Barril {
	// Variables de instancia
	private int x;
	private int y;
	private double tam;
	private boolean avanzando;
	private Image img;
	
	public Barril(int x, int y) 
	{
		this.x = x;
		this.y = y;
		this.tam = 20;
		this.avanzando = true;
		this.img = Herramientas.cargarImagen("barril.gif");
	}
	
	public void dibujarse(Entorno entorno) 
	{
		//entorno.dibujarCirculo(this.x, this.y, this.tam, Color.yellow);
		entorno.dibujarImagen(img, x, y, x/40.0, 0.05);
	}
	
	public static void sobrePlataforma(Plataforma[] plataforma, Barril[] barril, Entorno entorno) //metodo de clase que le indica a los barriles moverse o en caso de llegar a cierta posicion "destruirse"
	{
		for (int i=0; i<barril.length;i++)
		{
			if (barril[i]!=null)
			{
				if (barril[i].sobrePlataforma(plataforma)) //aca le pasa al sobrePlataforma de abajo UN barril para que le diga si esta o no en la plataforma
				{
					barril[i].moverse(plataforma, entorno);

					if(barril[i].getX()<entorno.ancho()*1/6 && barril[i].getY()>=entorno.alto()-30) //esto NO deberia fallar porque la plataforma cero siempre se crea exactamente igual en cualquier resolucion de pantalla
						barril[i]=null;
				}
				else
				{
					barril[i].caer();
				}
			}
		}		
	}
	
	private boolean sobrePlataforma(Plataforma[] plataforma) //se llama igual que el anterior pero hacen cosas totalmente distintas, este es un booleano que te dice si esta o no el barril sobre la plataforma
	{
		for (int i=0; i<plataforma.length;i++)
		{
			if (this.x>=plataforma[i].getX()-plataforma[i].getAncho()/2  && this.x<=plataforma[i].getX()+plataforma[i].getAncho()/2 
					&& this.y<=plataforma[i].getY()-plataforma[i].getAlto()/2 && this.y>= plataforma[i].getY()-plataforma[i].getAlto() )
			{	
				return true;	
			}	
		}
		return false;
	}
		
	public void moverse(Plataforma [] plataforma, Entorno entorno) //avanza o retrocede al barril
	{
		if (avanzando)
			this.x+=3;
		else
			this.x-=3;
		
		for(int i=0;i<plataforma.length;i++) //lo que se hace aca es jugar con el ancho de la plataforma, si el barril llega ahi cambia el booleano
		{
			if(this.x>plataforma[i].getX()+plataforma[i].getAncho()/2)
				avanzando=false;
			if(this.x<plataforma[i].getX()-plataforma[i].getAncho()/2)
				avanzando=true;
		}
		//este limite lo ponemos PERO por como esta armada la caida de barriles NUNCA sucederia que un barril choque contra los bordes
		if (this.x >entorno.ancho())
			avanzando = false;
		
		if (this.x < 0)
			avanzando = true;
	}

	public void caer() 
	{
		this.y+=2;
	}
	
	public int getX() 
	{
		return this.x;
	}
	
	public int getY() 
	{
		return this.y;
	}
	
	public double getTam() 
	{
		return this.tam;
	}
}
