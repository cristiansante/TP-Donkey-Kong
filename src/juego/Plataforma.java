package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Plataforma {
	
	// Variables de instancia
	private int x;
	private int y;
	private int ancho;
	private int alto;
	//private Image img;
	
	public Plataforma(Entorno entorno, int x, int y) 
	{
		this.x=x;
		this.y=y;
		this.ancho=entorno.ancho();
		this.alto=20;
		//this.img = Herramientas.cargarImagen("plataforma.png");
	}
	
	public void dibujarse(Entorno entorno) 
	{
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.green);
		//entorno.dibujarImagen(img, x, y, 0, 1);
	}
	
	public void setX(int a) 
	{
		this.x=a;
	}
	public void setY(int b) 
	{
		this.y=b;
	}
	
	public int getX() 
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getAncho()
	{
		return this.ancho;
	}
	public int getAlto() 
	{
		return this.alto;
	}
	public void setAncho(int c) 
	{
		this.ancho=this.ancho+c;
	}

}
