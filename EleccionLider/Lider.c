#include <stdio.h>
#include <mpi.h>
#include <string.h>
#include <stdlib.h> 


#define TAG 0 // tag para MPI

void eleccion_lider(int cadena[],int n,int *pos,int *num);

int main(int argc, char* argv[])
{
	int numero_procesos, rank, i, j, numero, posicion, gnum;
	int numeros[numero_procesos];

  	MPI_Status stat;
	MPI_Init(&argc, &argv); 
  	MPI_Comm_size(MPI_COMM_WORLD,&numero_procesos); 
  	MPI_Comm_rank(MPI_COMM_WORLD,&rank);
	
		
	srand(time(0)+rank);  
	numero=rand()% 37;

	numeros[rank]=numero;
	
	   for(i=0;i<numero_procesos;i++)
   	{		
		if(rank!=i)
		{
			MPI_Send(&numero, 1, MPI_INT, i, TAG, MPI_COMM_WORLD);
			printf("Proceso %d,  con ID =%d, envio al proceso %d \n", rank, numero, i);
		}
		if(rank!=i)
		{		
			MPI_Recv(&numero, 1, MPI_INT, i, TAG, MPI_COMM_WORLD, &stat);
			printf("proceso %d, que recibe el ID =%d del proceso %d\n", rank, numero, i);
			numeros[i]=numero;
		}
				
	}

	if((i==numero_procesos))
	{
		eleccion_lider(numeros,numero_procesos,&posicion,&gnum);
		
		if(rank==posicion)
		{
			printf("Proceso %d, con ID %d, SOY EL LIDER!\n",rank,numeros[rank]);
		}
		
		else
		{
			printf("Proceso %d, con ID %d, no soy el lider\n",rank,numeros[rank]);
		}
	}
  	MPI_Finalize(); 
  	return 0;
}

void eleccion_lider(int cadena_procesos[],int n,int *pos,int *num)
{
		int j;
	*pos = 0;
	*num = cadena_procesos[0];
	  for (j=0;j<n;j++)
        {
            if (*num<cadena_procesos[j])
            {
				*pos=j;
				*num=cadena_procesos[j];
			}	
        }
	
}
