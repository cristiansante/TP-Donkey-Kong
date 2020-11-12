package juego;

import java.awt.Color;
import entorno.Entorno;

	public class FugaDeGas {
		Entorno entorno;
		int x;
		int y;
		int ancho;
		int alto;
		boolean prendido;	
		
		FugaDeGas(int x,int y,Entorno entorno)
		{
			this.x=x;
			this.y=y;
			this.entorno=entorno;
			this.ancho= 300;
			this.alto=25;
		}
	
		public void dibujarse()
		{
			entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.blue);
		}
		
		public int getX() {
			return this.x;
		}
	
		public int getY() 
		{
			return this.x;
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
	
	
