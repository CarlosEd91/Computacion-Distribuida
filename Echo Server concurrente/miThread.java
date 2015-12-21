import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class miThread extends Thread
{
   	private DataInputStream streamDatosEntrada;
   	private DataOutputStream streamDatosSalida;
   	private String datosRecibidos;
   	private Socket socketCliente = null;
   	private String direccionCliente;
   	
 	public miThread(Socket cliente)
 	{
   		this.socketCliente = cliente;
   		direccionCliente = this.socketCliente.getInetAddress().toString();
   	}
 	
	public void run()
	{
   		try
   		{
   			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
  			streamDatosEntrada = new DataInputStream(socketCliente.getInputStream());
   			streamDatosSalida = new DataOutputStream(socketCliente.getOutputStream());
 			
   			// Si se presiona enter la conexion se cierra
   			do
 			{
   				datosRecibidos = streamDatosEntrada.readUTF();
   				System.out.println(" ("+this.getName()+") se ha recibido : " + datosRecibidos);  	
   				streamDatosSalida.writeUTF(datosRecibidos);
   			}
   			while(datosRecibidos.length()!=0);
   			
 			streamDatosEntrada.close();
   			socketCliente.close();
   			
   		}
   		catch(IOException e)
   		{
   			System.out.println(e.getMessage());
   			System.exit(1);
   		}
   		
   		System.out.println("Cliente desconectado: ("+direccionCliente+")");
		Thread.currentThread().stop();
	}
}
