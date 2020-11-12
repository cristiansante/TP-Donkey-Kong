package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Mono {
	
	//variables de instancia
	private int x;
	private int y;
	private int alto;
	private int ancho;
	private Image img;
	private Image armamento;
	
	public Mono(int x, int y) 
	{
		this.x = x;
		this.y = y;
		this.alto = 50;
		this.ancho = 50;
		this.img = Herramientas.cargarImagen("mono.gif");
		this.armamento = Herramientas.cargarImagen("armamento.png");
	}
	
	public void dibujarse(Entorno entorno) 
	{
		//entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.red);
		entorno.dibujarImagen(img, x, y, 0, 0.5);
	}
	
	public void dibujarArmamento( Entorno entorno, Plataforma [] plataforma) //solo cumple una funcion estetica
	{
		entorno.dibujarImagen(armamento, plataforma[plataforma.length-1].getX()/4, plataforma[plataforma.length-1].getY()-plataforma[plataforma.length-1].getAlto()-25, 0, 0.35);
	}
	
	public Barril lanzar() //devuelve UN barril
	{
		int x_barril = this.x;
		int y_barril = this.y;
		
		return new Barril (x_barril, y_barril);
	}
	
	public boolean monoCurado(Mario mario) //sirve para saber si mario hace contacto con donkey
	{
		if((this.x+(this.ancho/2)>=mario.getX()) && (this.x-(this.ancho/2)<=(mario.getX()) 
				&&(this.y+this.alto/2>=mario.getY())&&(this.y-this.alto/2<=mario.getY()))) {
				return true;
		}
		return false;
	}
}
