import java.util.Scanner;
import java.lang.Math;

public class ProblemaP1 {
	
	public static void main (String[] args) {
		Scanner scanner = new Scanner(System.in);
		int casosPrueba = scanner.nextInt();
		
		for (int p=0;p<casosPrueba;p++) {
			int R = scanner.nextInt();
			int C = scanner.nextInt();
			
			if (R>200 || C>200 || R%2==0 || C%2==0) {
				System.out.println("Dimensiones invalidas"); break;}
			
			int[][] piramide = new int[R][C];

			for (int i=0;i<=R-1;i++ ) {
				for (int j=0;j<=C-1;j++) {
					piramide[i][j]=scanner.nextInt();
					if (piramide[i][j]==-1) piramide[i][j]=Integer.MIN_VALUE;
				}
			}
			
			if (piramide[0][0]!=0 || piramide[0][C-1]!=0 || piramide[R-1][((C+1)/2)-1]!=0) {
				System.out.println("No cumple condiciones de entrada"); break;
			}
			
			
			int[][] piramideSMI=sallah(marion(indiana(llenar(piramide))));
			int totalRel1 = piramideSMI[0][0] + piramideSMI[0][C-1] + piramideSMI[R-1][((C+1)/2)-1];
			
			int[][]piramideSIM= sallah(indiana(marion(llenar(piramide))));
			int totalRel2 = piramideSIM[0][0] + piramideSIM[0][C-1] + piramideSIM[R-1][((C+1)/2)-1];
			
			int[][]piramideMIS= marion(indiana(sallah(llenar(piramide))));
			int totalRel3 = piramideMIS[0][0] + piramideMIS[0][C-1] + piramideMIS[R-1][((C+1)/2)-1];
			
			int[][]piramideMSI=marion(sallah(indiana(llenar(piramide))));
			int totalRel4 = piramideMSI[0][0] + piramideMSI[0][C-1] + piramideMSI[R-1][((C+1)/2)-1];
			
			int[][]piramideIMS=indiana(marion(sallah(llenar(piramide))));
			int totalRel5= piramideIMS[0][0] + piramideIMS[0][C-1] + piramideIMS[R-1][((C+1)/2)-1];

			int[][]piramideISM=indiana(sallah(marion(llenar(piramide))));
			int totalRel6= piramideISM[0][0] + piramideISM[0][C-1] + piramideISM[R-1][((C+1)/2)-1];

			int totalRel=Math.max(totalRel6,Math.max(totalRel5, Math.max(totalRel4,Math.max(totalRel3,Math.max(totalRel2, totalRel1)))));
			if (totalRel==0)totalRel=-1;
			System.out.println(totalRel);
			//System.out.println("indiana "+ totalRel1+" marion "+totalRel2+" sallah "+totalRel3+ " MSI "+totalRel4 + " IMS " +totalRel5 + " ISM " + totalRel6);
		}
		scanner.close();
	}
	
	public static int[][] llenar (int[][] piramide){
		int[][] copia =new int[piramide.length][piramide[0].length];
		for (int i = 0; i < piramide.length; i++) {
            for(int j = 0; j<piramide[i].length; j++) {
            	copia[i][j]=piramide[i][j];
            }
        }
		return copia;
	}
	
	public static int[][] indiana (int[][] piramideI) {
		int R=piramideI.length;
		int C=piramideI[0].length;
		int indianaR=(R+1)/2;
		int[][] indiana = new int[indianaR][Math.min(C, indianaR)];
		//System.out.println("piramideMarion "+piramideI[0][C-1] );

		for (int i=indiana.length-1; i>=0 ;i--) {
			for (int j=0;j<=Math.min(indiana[0].length-1, i);j++){
				if (i==indiana.length-1) indiana[i][j] = piramideI[i][j];
				else if (i<indiana.length-1 && j==0) indiana[i][j] = piramideI[i][j]+Math.max(indiana[i+1][j],indiana[i+1][j+1]);
				else if (i<indiana.length-1 && j>0 && j<indiana[0].length-1) indiana[i][j] = piramideI[i][j] + Math.max(indiana[i+1][j-1],Math.max(indiana[i+1][j],indiana[i+1][j+1]));
				else if (i<indiana.length-1 && j==indiana[0].length-1) indiana[i][j] = piramideI[i][j] + Math.max(indiana[i+1][j-1],indiana[i+1][j]);

			}
		}
		piramideI[0][0]=indiana[0][0];
		if (piramideI[0][0]<0) piramideI[0][0]=0;
		if (indiana[0][0]>0) {
		//entrar si reliquias indiana>0
			int j=0;
			for (int i=0;i<=indiana.length-2;i++) {
				if (j==0) {
					if (indiana[i+1][j]>indiana[i+1][j+1]) {
						piramideI[i+1][j]=0;
						j=0;
					}
					else {
						piramideI[i+1][j+1]=0;
						j=1;
						}
				}
				else if (j>0 && j<indiana[0].length-1) {
					if (indiana[i+1][j-1] >= indiana[i+1][j] && indiana[i+1][j-1] >= indiana[i+1][j+1]) {
						piramideI[i+1][j-1]=0;
						j=j-1;
					}
					else if (indiana[i+1][j] > indiana[i+1][j-1] && indiana[i+1][j] >= indiana[i+1][j+1]) {
						piramideI[i+1][j]=0;
					}
					else if (indiana[i+1][j+1] > indiana[i+1][j] && indiana[i+1][j+1] > indiana[i+1][j-1]) {
						piramideI[i+1][j+1]=0;
						j=j+1;
					}
				}
				else if (j==indiana[0].length-1) {
					if (indiana[i+1][j-1] >= indiana[i+1][j]) {
						piramideI[i+1][j-1]=0;
						j=j-1;
					}
					else if (indiana[i+1][j] > indiana[i+1][j-1]) {
						piramideI[i+1][j]=0;
					}
				}
		}
		}
		//System.out.println("MARIONenIndiana "+piramideI[0][C-1]);
		return piramideI;
	}
	
	public static int[][] marion (int[][] piramideM){
		int R=piramideM.length;
		int C=piramideM[0].length;
		int marionR=(R+1)/2;
		int[][] marion = new int[marionR][Math.min(C, marionR)];
		int restante=C-marion[0].length;
		//System.out.println("piramideMarion "+piramideM[0][C-1] );

		for (int i=marionR-1; i>=0 ;i--) {
			for (int j=marion[0].length-1;j>=Math.max(0,(marion[0].length-1)-i);j--){
				//System.out.println("marion0 "+piramide[i][restante+j] );
				if (i==marionR-1) marion[i][j] = piramideM[i][restante+j];
				else if (i<marionR-1 && j==0) marion[i][j] = piramideM[i][restante+j]+Math.max(marion[i+1][j],marion[i+1][j+1]);
				else if (i<marionR-1 && j>0 && j<marion[0].length-1) marion[i][j] = piramideM[i][restante+j] + Math.max( marion[i+1][j-1] , Math.max(marion[i+1][j],marion[i+1][j+1]));
				else if (i<marionR-1 && j==marion[0].length-1) marion[i][j] = piramideM[i][restante+j] + Math.max(marion[i+1][j-1], marion[i+1][j]);
			}
		}
		
		//System.out.println("marion0 "+marion[0][marion[0].length-1]);
		piramideM[0][C-1]=marion[0][marion[0].length-1];
		if (piramideM[0][C-1]<0) piramideM[0][C-1]=0;
		//entrar si reliquias marion>0
		if (marion[0][marion[0].length-1]>0) {
			int j=marion[0].length-1;
//			int restante=piramide[0].length-marion[0].length;
			for (int i=0;i<=marion.length-2;i++) {
				if (j==0) {
					if (marion[i+1][j]>marion[i+1][j+1]) {
						piramideM[i+1][restante+j]=0;
						j=0;
					}
					else {
						piramideM[i+1][restante+j+1]=0;
						j=1;
						}
				}
				else if (j>0 && j<marion[0].length-1) {
					if (marion[i+1][j-1] >= marion[i+1][j] && marion[i+1][j-1] >= marion[i+1][j+1]) {
						piramideM[i+1][restante+j-1]=0;
						j=j-1;
					}
					else if (marion[i+1][j] > marion[i+1][j-1] && marion[i+1][j] >= marion[i+1][j+1]) {
						piramideM[i+1][restante+j]=0;
					}
					else if (marion[i+1][j+1] > marion[i+1][j] && marion[i+1][j+1] > marion[i+1][j-1]) {
						piramideM[i+1][restante+j+1]=0;
						j=j+1;
					}
				}
				else if (j==marion[0].length-1) {
					if (marion[i+1][j-1] >= marion[i+1][j]) {
						piramideM[i+1][restante+j-1]=0;
						j=j-1;
					}
					else if (marion[i+1][j] > marion[i+1][j-1]) {
						piramideM[i+1][restante+j]=0;
					}
				}
			}
		}
		//System.out.println("marion "+piramideM[0][C-1]);
		return piramideM;
	}
	
	public static int[][] sallah (int[][] piramideS){
		int R=piramideS.length;
		int C=piramideS[0].length;
		int sallahR=(R+1)/2;
		int[][] sallah = new int[sallahR][Math.min(C, (2*sallahR)-1)];
		int sallahColCent=((sallah[0].length+1)/2)-1;
		int restanteR = R-sallahR;
		int restanteC = (C- sallah[0].length)/2; 
		for (int i=0; i<=sallahR-1; i++) {
			for (int j=Math.max(0, (sallahColCent)-(sallahR-1)+i);j<=Math.min((sallahR-1)+(sallahColCent)-i, sallah[0].length-1);j++){
			
				if (i==0) sallah[i][j] = piramideS[i+restanteR][j+restanteC];
				else if (i>0 && j==0) sallah[i][j] = piramideS[i+restanteR][restanteC+j]+Math.max(sallah[i-1][j],sallah[i-1][j+1]);
				else if (i>0 && j>0 && j<sallah[0].length-1) sallah[i][j] = piramideS[i+restanteR][restanteC+j] + Math.max(sallah[i-1][j-1],Math.max(sallah[i-1][j],sallah[i-1][j+1]));
				else if (i>0 && j==sallah[0].length-1) sallah[i][j] = piramideS[i+restanteR][restanteC+j] + Math.max(sallah[i-1][j-1],sallah[i-1][j]);
			}
			}
		piramideS[R-1][((C+1)/2)-1]=sallah[sallahR-1][sallahColCent];
		if (piramideS[R-1][((C+1)/2)-1]<0) piramideS[R-1][((C+1)/2)-1]=0;
		//System.out.println("sallah "+piramideS[R-1][((C+1)/2)-1]);
		
		if (sallah[sallahR-1][sallahColCent]>0) {
			int j=sallahColCent;
//			int restante=piramide[0].length-marion[0].length;
			for (int i=sallahR-1;i>0;i--) {
				if (j==0) {
					if (sallah[i-1][j]>sallah[i-1][j+1]) {
						piramideS[restanteR+i+1][restanteC+j]=0;
						j=0;
					}
					else {
						piramideS[restanteR+i-1][restanteC+j+1]=0;
						j=1;
						}
				}
				else if (j>0 && j<sallah[0].length-1) {
					if (sallah[i-1][j-1] >= sallah[i-1][j] && sallah[i-1][j-1] >= sallah[i-1][j+1]) {
						piramideS[restanteR+i-1][restanteC+j-1]=0;
						j=j-1;
					}
					else if (sallah[i-1][j] > sallah[i-1][j-1] && sallah[i-1][j] >= sallah[i-1][j+1]) {
						piramideS[restanteR+i-1][restanteC+j]=0;
					}
					else if (sallah[i-1][j+1] > sallah[i-1][j] && sallah[i-1][j+1] > sallah[i-1][j-1]) {
						piramideS[restanteR+i-1][restanteC+j+1]=0;
						j=j+1;
					}
				}
				else if (j==sallah[0].length-1) {
					if (sallah[i-1][j-1] >= sallah[i-1][j]) {
						piramideS[restanteR+i-1][restanteC+j-1]=0;
						j=j-1;
					}
					else if (sallah[i-1][j] > sallah[i-1][j-1]) {
						piramideS[restanteR+i-1][restanteC+j]=0;
					}
				}
			}
		}
		return piramideS;
	}
}
