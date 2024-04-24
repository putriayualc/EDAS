import java.util.ArrayList;

public class EdasSPK {

    public int[][] tambahKriteria(int[][] matriks, int[] tambahan){
        int[][] mat = new int[matriks.length][matriks[0].length+1];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < matriks[0].length; j++) {
                mat[i][j]=matriks[i][j];
            }
        }

        for (int i = 0; i < mat.length; i++) {
            mat[i][mat[0].length-1]=tambahan[i];
        }

        return mat;
    }

    public int[][] tambahAlternatif(int[][]  matriks, int[] tambahan){
        int[][] mat = new int[matriks.length+1][matriks[0].length];
        for (int i = 0; i < matriks.length; i++) {
            for (int j = 0; j < matriks[0].length; j++) {
                mat[i][j]=matriks[i][j];
            }
        }

        for (int j = 0; j < mat[0].length; j++) {
            mat[mat.length-1][j]=tambahan[j];
        }

        return mat;
    }

    public double[] rataAv(int[][] matriks){
        double[] AV = new double[matriks[0].length];
        
        for (int j = 0; j < matriks[0].length; j++) {
            int sum=0;
            for (int i = 0; i < matriks.length; i++) {
                sum+=matriks[i][j];
            }
            AV[j] = (double)sum / matriks.length;
        }
        return AV;
    }

    public double PDA(int x, double AV, boolean jenis){
        double form;
        if(jenis){
            form = (x - AV) / AV;
        }else{
            form = (AV - x) / AV;
        }
        
        return (form > 0 ? form : 0);
    }

    public double NDA(int x, double AV, boolean jenis){
        double form;
        if(jenis){
            form = (AV - x) / AV;
        }else{
            form = (x - AV) / AV;
        }
        
        return (form > 0 ? form : 0);
    }

    public double[] SPSN(double[][] da, ArrayList<Kriteria> kriteria){
        double[] spn = new double[da.length];

        for (int i = 0; i < da.length; i++) {
            for (int j = 0; j < da[0].length; j++) {
                spn[i] += da[i][j] * kriteria.get(j).getBobot() ;
            }
        }
        return spn;
    }

}
