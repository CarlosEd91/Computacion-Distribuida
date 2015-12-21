import java.io.*;
import java.net.*; 

public class Servidor
{
   	public static void main(String[] args)
   	{
   		ServerSocket socketServidor = null;
   		Socket socketCliente = null;
   		int puerto = 10000;
   		
   		try
   		{
   			if (args.length > 0)
   				puerto = Integer.parseInt(args[0]);
   		}
   		catch(NumberFormatException ex)
   		{
   			System.out.println("Error en argumento, se usa el puerto por defecto");
   		}
   		
   		boolean escuchando = true;
   		
 		try
 		{
   			socketServidor = new ServerSocket(puerto); 			
   			while(escuchando)
 			{
   				socketCliente = socketServidor.accept();
   				miThread hilo = new miThread(socketCliente);
   				hilo.start();
   				
   				System.out.println("Cliente conectado: " + socketCliente.getInetAddress().toString() + ":"+socketCliente.getPort());
   				System.out.println("Se ha creado el hilo "+ hilo.getName());
   			}
	 		socketServidor.close();
   		}
 		catch (IOException e)
 		{
   			System.err.println(e.getMessage());
   			System.exit(1);
   		}
   	}
}


