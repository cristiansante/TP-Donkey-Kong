package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Escalera {
	
	//variables de instancia
	private int x;
	private int y;
	private int alto;
	private int ancho;
	private Image img;
	
	public Escalera(int x, int y) 
	{
		this.x = x;
		this.y = y;
		this.alto = 105;
		this.ancho = 30;
		this.img = Herramientas.cargarImagen("escalera.png");
	}
	
	public void dibujarse(Entorno entorno) 
	{
		//entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.gray);
		entorno.dibujarImagen(img, x, y, 0, 0.15);
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

}
