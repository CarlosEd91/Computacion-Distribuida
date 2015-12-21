import java.io.*;
import java.net.*;


public class Cliente
{
   	public static void main(String[] args)
   	{
   		Socket servidor = null;
   		DataOutputStream streamDatosEnviadosServidor;
   		BufferedReader streamDatosRecibidosTeclado;
   		DataInputStream streamDatosRecibidosDesdeServidor;
   		String datos;
   		int puerto = 10000;
   		
   		try
   		{
   			if (args.length > 1)
   			{
   				puerto = Integer.parseInt(args[1]);
   			}		
   		}
   		catch(NumberFormatException ex)
   		{
   			System.out.println("Error en argumento, se usa el puerto por defecto");
   		}
   
   		try
   		{
   			try
   			{
   				servidor = new Socket(args[0], puerto);
   			}
   			catch (UnknownHostException e)
   			{
   				System.out.println(e.getMessage());
   				System.exit(1);
   			} 
   			
   			System.out.println("Se ha conectado al servidor");
   
   			streamDatosRecibidosTeclado = new BufferedReader(new InputStreamReader(System.in));
   			streamDatosEnviadosServidor = new DataOutputStream(servidor.getOutputStream());
   			streamDatosRecibidosDesdeServidor = new DataInputStream(servidor.getInputStream());
   
  	 		do
  	 		{
   				System.out.print("Ingrese el mensaje o presione enter para salir: ");
   				datos = streamDatosRecibidosTeclado.readLine();
   				streamDatosEnviadosServidor.writeUTF(datos);
   				System.out.println("Mensaje enviado:  "+streamDatosRecibidosDesdeServidor.readUTF()); 
   			}
  	 		while(datos.length()!=0);
   
   			System.out.println("Fin de la conexion");
   
   			streamDatosRecibidosDesdeServidor.close();
   			streamDatosRecibidosTeclado.close();
   			streamDatosEnviadosServidor.close();
   			servidor.close();
   
   		}
   		catch (IOException e)
   		{
   			System.err.println(e.getMessage());
   			System.exit(1);
   		}
   	}
}
